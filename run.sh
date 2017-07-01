#!/bin/bash

floors=3
dir=elevator-$floors
a1="$dir/elevator.conf"
a2="$dir/header.pml"
a3="$dir/plant.pml"
a4="$dir/controller.pml"
a5="$dir/spec.pml"
run="java -jar"

run() {
    name="$1"
    shift
    java -jar jars/"$name".jar "$a1" "$a2" "$a3" "$a4" "$a5" $@
    echo
}

run synthesize-coverage-tests --maxlen 10 --includeInternal --plantCodeCoverage --controllerCodeCoverage --output test1 --minimize
run generate-random --number 10 --length 10 --output test2
run run --input test1
run run --input test2

