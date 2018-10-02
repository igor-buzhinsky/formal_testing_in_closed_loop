## Introduction ##

This toolset implements a testing framework for closed-loop industrial automation systems.
The framework is based on model checking and includes (1) test suite generation based on several coverage criteria, and (2) test case execution.
As input data, the framework requires the formal model of the closed-loop system in Promela and NuSMV languages, and the temporal specification (LTL or CTL) to be satisfied for the system.
The temporal specification is checked on the generated test cases during the second stage of the framework.

Two sets of example models (elevator, pick-and-place manipulator) with varying complexity are available to play with the framework.

## Prerequisites ##

To build the toolset, you need JDK 1.8 (or greater) and [ant](https://ant.apache.org/). On the other hand, the precompiled version is already supplied, so only JRE 1.8 (or greater) will suffice.

The toolset is intended to work on Linux. In Windows 10, a possible solution to run the toolset is to use Linux Subsystem.

To run the toolset, model checkers [NuSMV](http://nusmv.fbk.eu/) 2.6.0 and [SPIN](http://spinroot.com/) 6 are needed (must be accessible in command line as "NuSMV" and "spin").
If you work in Linux Subsystem, you need Linux versions of the tools accessible under these names.

## Included tools ##

This toolset includes the following tools:

* [jars/synthesize-coverage-tests.jar](/jars/synthesize-coverage-tests.jar): generate a test suite based on coverage (stage 1 of the framework);
* [jars/run.jar](/jars/run.jar): run the generated test suite (stage 2 of the framework);
* [jars/closed-loop-verify.jar](/jars/closed-loop-verify.jar): model-check the given system conventionally, without testing;
* [jars/generate-random.jar](/jars/generate-random.jar): generate random test cases;
* [jars/print-test-suite.jar](/jars/print-test-suite.jar): print a generated test suite.

The files [run-elevator.sh](/run-elevator.sh) and [run-pnp.sh](/run-pnp.sh) provide additional functions for the ease of work with the tool.
You can play with the commands in the end of these files to run the framework on the elevator and the pick-and-place manipulator (PnP) examples.

## Building and running ##

You can use the precompiled version of the toolset as described above, but you can also build it yourself with the following command:
> ant

To run the toolset, use the following command:
> java -jar jars/<tool-name\>.jar

Alternatively, use the files [run-elevator.sh](/run-elevator.sh) and [run-pnp.sh](/run-pnp.sh) as described above.

## Troubleshooting, questions, etc.

Email Igor Buzhinsky (igor.buzhinsky@gmail.com).
