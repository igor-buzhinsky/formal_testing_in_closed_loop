#!/bin/bash

floors=3
dir=elevator-$floors
seed="--seed 200"

call_spin() {
    local name="$1"
    shift
    java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.pml" "$dir/plant.pml" "$dir/controller.pml" "$dir/spec.pml" -l PROMELA $@
    echo
}

call_nusmv() {
    local name="$1"
    shift
    java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.smv" "$dir/plant.smv" "$dir/controller.smv" "$dir/spec.smv" -l NUSMV $@
    echo
}

#minimize="--minimize"
minimize=


#call_nusmv closed-loop-verify --verbose 
call_spin generate-random --number 10 --length 10 --output test2.bin $seed
call_spin run --input test2.bin --measureCoverage --includeInternal
call_nusmv run --input test2.bin --measureCoverage --includeInternal

#call_spin closed-loop-verify --verbose 
#call_spin generate-random --number 10 --length 10 --output test2.bin
#call_spin run --input test2.bin --measureCoverage --includeInternal --plantCodeCoverage --controllerCodeCoverage
#call_spin synthesize-coverage-tests --maxlen 10 --includeInternal --output test1.bin $minimize --plantCodeCoverage --controllerCodeCoverage 
#call_spin run --input test1.bin --measureCoverage --includeInternal --plantCodeCoverage --controllerCodeCoverage 


exit





