#!/bin/bash

source run-base.sh

verbose=

basedir=elevator
#basedir=elevator-from-traces ; verbose=--verbose
#basedir=elevator-st

set_floors() {
    floors=$1
    set_dir "$basedir/elevator-$floors"
    #maxlen=$((floors * 3 + 3)) # optimum for just state coverage
    maxlen=$((floors * 3 + 6)) # a larger bound in used to cover additional variable combinations
}

comparison() {
    min="$1"
    max="$2"
    nomc="$3"
    for ((i = $min; i <= $max; i++)); do
        set_floors "$i"
        
        echo
        echo "==== comparison with $i floors, basedir=$basedir ===="
        
        # Framework: synthesis
        call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-small.bin $finite --minimize --coi > log; print_log
        print_test_suite test-small.bin > /dev/null
        
        # Framework: execution
        call_spin run $verbose --input test-small.bin --verify --output out-small.pml $finite --panO 0 > log; print_log
        
        if [[ "$nomc" == true ]]; then
            continue
        fi

        # BMC
        bmc_verification "set_floors $floors" $(((floors * 3 + 5) / 2))
        bmc_verification "set_floors $floors" $((floors * 3 + 5))
        
        # BDD-based LTL MC 
        nusmv_spec_file=spec-ltl.smv
        call_nusmv closed-loop-verify --verbose --dynamic --coi > log; print_log
        nusmv_spec_file=spec.smv
        
        # BDD-based CTL MC
        call_nusmv closed-loop-verify --verbose --dynamic --coi > log; print_log
    done
}

additional_tests() {
    set_floors 3

    call_spin closed-loop-verify 
    #call_nusmv closed-loop-verify 
    #call_spin generate-random --number 2 --length 2 --output test.bin $seed
    #call_spin run --input test.bin --verify --output out.pml $finite --panO 0
    #check_spin "set_floors 2" # takes long
    #check_nusmv "set_floors 2" # 16.03 passed
}

comparison 2 2 true
#comparison 3 15
#additional_tests

#check_spin "set_floors 2"
#check_nusmv "set_floors 2"
#set_floors 2
#call_spin synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-small.bin $finite --minimize
#call_spin run --input test.bin --verify --output out.pml $finite --panO 0
