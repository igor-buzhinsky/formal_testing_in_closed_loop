#!/bin/bash

floors=3

call() {
    local name="$1"
    local dir=elevator-$floors
    shift
    java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.pml" "$dir/plant.pml" "$dir/controller.pml" "$dir/spec.pml" $@
    echo
}

#minimize="--minimize"
minimize=

call synthesize-coverage-tests --maxlen 10 --includeInternal --output test1.bin $minimize --plantCodeCoverage --controllerCodeCoverage 
call run --input test1.bin --measureCoverage --plantCodeCoverage --controllerCodeCoverage 

call generate-random --number 10 --length 10 --output test2.bin
call run --input test2.bin --measureCoverage --plantCodeCoverage --controllerCodeCoverage

