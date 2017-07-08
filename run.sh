#!/bin/bash

set_floors() {
    floors=$1
    dir=elevator-$floors
}

call_spin() {
    local name="$1"
    echo " >>> $name"
    shift
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.pml" "$dir/plant.pml" "$dir/controller.pml" "$dir/spec.pml" -l PROMELA $@ 2>&1
    echo
}

call_nusmv() {
    local name="$1"
    echo " >>> $name"
    shift
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.smv" "$dir/plant.smv" "$dir/controller.smv" "$dir/spec.smv" -l NUSMV --nusmv_mode BMC $@ 2>&1
    echo
}

print_test_suite() {
    java -jar jars/print-test-suite.jar --input "$1" -l PROMELA
    java -jar jars/print-test-suite.jar --input "$1" -l NUSMV
}

print_log() {
    cat log | grep ">>>"
    cat log | grep "Covered points: "
    cat log | grep "Exception"
    cat log | grep " = \\(true\\|false\\) \\*\\*\\*"
    cat log | grep " specification " | sed 's/.* specification //g'
}

check_spin() {
    set_floors "$1"
    echo
    echo ">>> RUN spin $floors"

    for minimize in "" "--minimize"; do
        call_spin synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test.bin $minimize $finite --plantCodeCoverage --controllerCodeCoverage --lengthExponent 1 > log
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

    for minimize in "" "--minimize"; do
        call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test.bin $minimize $finite > log
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

comparison() {
    set_floors "$1"
    echo
    echo ">>> RUN comparison $floors"
    # Synthesize tests
    call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test.bin $finite > log
    print_log
    print_test_suite test.bin > /dev/null
    # Run tests
    call_spin run --input test.bin --verify --output out.pml $finite > log
    print_log
    # Run verification
    call_nusmv closed-loop-verify --verbose --dynamic --coi > log
    print_log
}

seed="--seed 200"
maxlen=100

finite="--checkFiniteCoverage"
#finite=

comparison 15
#check_nusmv 5
#check_spin 3
