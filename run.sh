#!/bin/bash

floors=3
dir=elevator-$floors
a1="$dir/elevator.conf"
a2="$dir/header.pml"
a3="$dir/plant.pml"
a4="$dir/controller.pml"
a5="$dir/spec.pml"
run="java -jar"

$run jars/synthesize-coverage-tests.jar "$a1" "$a2" "$a3" "$a4" "$a5" --maxlen 10 --includeInternal --plantCodeCoverage --controllerCodeCoverage --output test1
$run jars/generate-random.jar "$a1" "$a2" "$a3" "$a4" "$a5" --number 10 --length 10 --output test2
$run jars/run.jar "$a1" "$a2" "$a3" "$a4" "$a5" --input test1
$run jars/run.jar "$a1" "$a2" "$a3" "$a4" "$a5" --input test2

