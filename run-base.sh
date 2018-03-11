#!/bin/bash

# scripts which include this one must define $dir 

set_dir() {
    dir="$1"
    conffile=$(echo "$dir"/*.conf)
}

call_spin() {
    local name="$1"
    echo " >>> $name"
    shift
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$conffile" "$dir/header.pml" "$dir/plant.pml" "$dir/controller.pml" "$dir/spec.pml" --nusmvSpecCoverage "$dir/spec-ltl.smv" -l PROMELA $@ 2>&1
    echo
}

nusmv_spec_file=spec.smv
call_nusmv() {
    local name="$1"
    echo " >>> $name"
    shift
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$conffile" "$dir/header.smv" "$dir/plant.smv" "$dir/controller.smv" "$dir/$nusmv_spec_file" --nusmvSpecCoverage "$dir/spec-ltl.smv" -l NUSMV $@ 2>&1
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
    eval "$1"
    
    echo
    echo ">>> RUN spin [$1]"

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
    eval "$1"
    echo
    echo ">>> RUN nusmv [$1]"

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
    eval "$1"
    echo
    echo ">>> RUN bmc_verification [$1] with k=$2"
    nusmv_spec_file=spec-ltl.smv
    call_nusmv closed-loop-verify --verification_bmc_k "$2" --coi > log
    print_log
    nusmv_spec_file=spec.smv
}

seed="--seed 200"

finite="--checkFiniteCoverage"
#finite=

