#!/bin/bash

source run-base.sh

verbose=

basedir=pick-and-place
#basedir=pick-and-place-st

set_complexity() {
    compl=$1
    set_dir "$basedir/pick-and-place-$compl"
    if (( compl <= 4 )); then
        maxlen=11 # proven
    elif (( compl == 5 )); then
        maxlen=17 # proven (437/487)
    else
        eval "maxlen=$(((1 << (compl - 1)) + 1))" # hypothesis
    fi
}

comparison() {
    min="$1"
    max="$2"
    nomc="$3"
    for ((i = $min; i <= $max; i++)); do
        set_complexity "$i"
        
        echo
        echo "==== comparison with complexity=$i, basedir=$basedir ===="
        
        # Framework: synthesis
        call_nusmv synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test-small.bin $finite --minimize --coi > log; print_log
        print_test_suite test-small.bin > /dev/null
        
        # Framework: execution
        call_spin run $verbose --input test-small.bin --verify --output out-small.pml $finite --panO 0 > log; print_log
        
        if [[ "$nomc" == true ]]; then
            continue
        fi

        # BMC
        eval "bmc_k=$(((1 << compl) + 12))" # checked for compl <= 4
        bmc_verification "set_complexity $compl" $((bmc_k / 4))
        bmc_verification "set_complexity $compl" $((bmc_k / 3))
        bmc_verification "set_complexity $compl" $((bmc_k / 2))
        bmc_verification "set_complexity $compl" $bmc_k
        
        # BDD-based LTL MC
        nusmv_spec_file=spec-ltl.smv
        call_nusmv closed-loop-verify --verbose --dynamic --coi > log; print_log
        nusmv_spec_file=spec.smv
        
        # BDD-based CTL MC
        call_nusmv closed-loop-verify --verbose --dynamic --coi > log; print_log
    done
}

comparison 2 2 false
