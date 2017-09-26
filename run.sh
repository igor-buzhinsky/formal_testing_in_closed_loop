#!/bin/bash

set_floors() {
    floors=$1
    dir=elevator-$floors
    maxlen=$((floors * 6 + 10))
}

call_spin() {
    local name="$1"
    echo " >>> $name"
    shift
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.pml" "$dir/plant.pml" "$dir/controller.pml" "$dir/spec.pml" --nusmvSpecCoverage "$dir/spec-ltl.smv" -l PROMELA $@ 2>&1
    echo
}

nusmv_spec_file=spec.smv
call_nusmv() {
    local name="$1"
    echo " >>> $name"
    shift
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.smv" "$dir/plant.smv" "$dir/controller.smv" "$dir/$nusmv_spec_file" --nusmvSpecCoverage "$dir/spec-ltl.smv" -l NUSMV $@ 2>&1
    echo
}

print_test_suite() {
    call_spin print-test-suite --input "$1"
    call_nusmv print-test-suite --input "$1"
}

print_log() {
    #cat log
    cat log | grep "\\(>>> \\|Loaded test suite\\|Covered points: \\|Exception\\| = \\(true\\|false\\) \\*\\*\\*\\)" | grep -v "\\*\\*\\* test_passed ="
}

check_spin() {
    set_floors "$1"
    
    echo
    echo ">>> RUN spin $floors"

    for minimize in "--minimize" ""; do
        call_spin synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test.bin $minimize $finite --plantCodeCoverage --controllerCodeCoverage --panO 0 > log
        print_log
        call_spin run --input test.bin --measureCoverage --includeInternal $finite --plantCodeCoverage --controllerCodeCoverage > log
        print_log
    done

    call_spin closed-loop-verify --verbose > log
    print_log
    call_spin generate-random --number 10 --length 10 --output test.bin $seed > log
    print_log
    call_spin run --input test.bin --verify --measureCoverage --includeInternal $finite --plantCodeCoverage --controllerCodeCoverage > log
    print_log
}

check_nusmv() {
    set_floors "$1"
    echo
    echo ">>> RUN nusmv $floors"

    for minimize in "--minimize" ""; do
        call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test.bin $minimize $finite --coi > log
        print_log
        call_nusmv run --input test.bin --measureCoverage --includeInternal $finite > log
        print_log
    done

    call_nusmv closed-loop-verify --verbose --dynamic --coi > log
    print_log
    call_nusmv generate-random --number 10 --length 10 --output test.bin $seed > log
    print_log
    call_nusmv run --input test.bin --verify --measureCoverage --includeInternal --dynamic --coi $finite > log
    print_log
}

bmc_verification() {
    set_floors "$1"
    echo
    echo ">>> RUN bmc_verification $floors $2"
    nusmv_spec_file=spec-ltl.smv
    call_nusmv closed-loop-verify --verification_bmc_k "$2" --coi > log
    print_log
    nusmv_spec_file=spec.smv
}

old_comparison() {
    set_floors "$1"

    #echo
    #echo ">>> comparison-no-minimization $floors"
    #call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-large.bin $finite --coi > log
    #print_log
    #print_test_suite test-large.bin > /dev/null
    #call_spin run --input test-large.bin --verify --output out-large.pml $finite --panO 0 > log
    #print_log

    echo
    echo ">>> comparison-with-minimization $floors"
    call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-small.bin $finite --minimize > log
    print_log
    print_test_suite test-small.bin > /dev/null
    call_spin run --input test-small.bin --verify --output out-small.pml $finite --panO 0 > log
    print_log

    #echo
    #echo ">>> comparison-with-minimization-and-value-pair-coverage $floors"
    #call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-pair.bin $finite --minimize --valuePairCoverage > log
    #print_log
    #print_test_suite test-pair.bin > /dev/null
    #call_spin run --input test-pair.bin --verify --output out-pair.pml $finite --panO 0 > log
    #print_log

    # Run verification
    bmc_verification $floors $floors
    bmc_verification $floors $((floors * 2))
    call_nusmv closed-loop-verify --verbose --dynamic --coi > log
    
    print_log
}
comparison() {
    set_floors "$1"

    echo
    echo ">>> comparison $floors"
    
    # Framework: synthesis
    call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-small.bin $finite --minimize > log
    print_log
    print_test_suite test-small.bin > /dev/null
    
    # Framework: execution
    call_spin run --input test-small.bin --verify --output out-small.pml $finite --panO 0 > log
    print_log

    # BMC
    bmc_verification $floors $(((floors * 3 + 5) / 2))
    bmc_verification $floors $((floors * 3 + 5))
    
    # BDD-based LTL MC 
    nusmv_spec_file=spec-ltl.smv
    call_nusmv closed-loop-verify --verbose --dynamic --coi > log
    nusmv_spec_file=spec.smv
    print_log
    
    # BDD-based CTL MC
    call_nusmv closed-loop-verify --verbose --dynamic --coi > log
    print_log
}

seed="--seed 200"

finite="--checkFiniteCoverage"
#finite=

#set_floors 4
#call_spin closed-loop-verify 
#exit

#for ((i = 12; i <= 15; i++)); do
#    echo
#    echo "==== $i FLOORS ===="
#    comparison $i
#done
comparison 2
#comparison 10
#comparison 15
#comparison 20
#check_nusmv 3
#set_floors 2
#call_spin synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-small.bin $finite --minimize
#call_spin generate-random --number 2 --length 2 --output test.bin $seed
#call_spin run --input test.bin --verify --output out.pml $finite --panO 0
