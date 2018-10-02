#!/bin/bash

source run-base.sh

dir="$1"
complexity="$2"

seconddir=${dir%-st}
set_dir "$dir/$seconddir-$complexity"
call_nusmv closed-loop-verify --dynamic --coi --verification_bmc_k 0
echo -e "go\nprint_reachable_states\nquit" | NuSMV -int -pre cpp -dynamic nusmvdir-last/model.smv
