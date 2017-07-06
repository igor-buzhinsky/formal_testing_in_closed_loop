#!/bin/bash

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
    /usr/bin/time -f "  >>> Elapsed time: %e s" java -jar jars/"$name".jar "$dir/elevator.conf" "$dir/header.smv" "$dir/plant.smv" "$dir/controller.smv" "$dir/spec.smv" -l NUSMV --nusmv_mode LTL $@ 2>&1
    echo
}

seed="--seed 200"
maxlen=12

finite="--checkFiniteCoverage"
#finite=

for language in nusmv spin; do
    for floors in 3 4 5; do
        echo ">>> RUN $language $floors"
        dir=elevator-$floors

        for minimize in "" "--minimize"; do
            call_$language synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test1.bin $minimize $finite

            call_$language run --input test1.bin --measureCoverage --includeInternal $finite
        done

        call_$language closed-loop-verify --verbose 
        call_$language generate-random --number 10 --length 10 --output test2.bin $seed
        call_$language run --input test2.bin --verify --measureCoverage --includeInternal $finite
    done
done

#call_spin synthesize-coverage-tests --maxlen $maxlen --includeInternal --output test1.bin $minimize --plantCodeCoverage --controllerCodeCoverage 

