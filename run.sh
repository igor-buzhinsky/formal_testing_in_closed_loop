#!/bin/bash

name="$1"
timeout="$2"
opt="$3"
pandir="$4"
format="*** %U user, %S system, %e elapsed, %Mk maxresident ***\n"

mkdir -p "$pandir"
cd "$pandir"
echo "*** GENERATING PAN SOURCE ***"
/usr/bin/time -f "$format" $time spin -a ../"$name" 2>&1
echo "*** COMPILING PAN ***"
/usr/bin/time -f "$format" cc -O$opt -DVECTORSZ=1024 -o pan pan.c 2>&1

for prop in $(cat ../"$name" | grep "^ltl .*{.*}.*$" | sed 's/^ltl //; s/ .*$//'); do
    echo "*** RUNNING PAN FOR $prop ***"
    timeout "$timeout"s /usr/bin/time -f "$format" ./pan -a -N "$prop" -m5000000 2>&1
    if [[ "$?" == 124 ]]; then
        echo "*** $prop : TIMEOUT ***"
    elif [ -f "$name.trail" ]; then
        echo "*** $prop = FALSE ***";
        /usr/bin/time -f "$format" spin -k "$name".trail -pglrs ../"$name" 2>&1
    else
        echo "*** $prop = TRUE ***";
    fi
    rm -f "$name".trail
done

cd ..
rm -r "$pandir"

