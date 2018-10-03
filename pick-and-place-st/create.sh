#!/bin/bash
from=2
to=6

st_convert() {
    java -jar $(cat converter-location) --input "$1" --promelaOutput "$2" --nusmvOutput "$3" --nusmvPreviousStepInputs suction_on,vcyl_extend,hcyl_extend
}

for ((compl = from; compl <= to; compl++)); do
    dir="pick-and-place-$compl"
    mkdir -p $dir

    eval "wp_num=$(( (1 << compl) - 1 ))"
    eval "hcyl_maxlen=$(( 1 << (compl - 1) ))"
    max_hpos_sum=$(( hcyl_maxlen * 2 - 1 ))

    # Manual models must be created first!
    echo -n > $dir/header.smv
    echo -n > $dir/header.pml
    cp ../pick-and-place/$dir/spec.* ../pick-and-place/$dir/spec-ltl.smv $dir
    
    cat pick-and-place.conf | sed "s/__1/$compl/g; s/__2/$wp_num/g; s/__3/$hcyl_maxlen/g; s/__4/$max_hpos_sum/g; s/__5/$((wp_num + 1))/g" > $dir/pick-and-place.conf
    
    # Structured text without loops: plant model
    echo -n > tmp
    echo "VAR_INPUT" >> tmp
    echo "    adding_wp: ARRAY[0..$((wp_num - 1))] OF BOOL;" >> tmp
    echo "    suction_on: BOOL;" >> tmp
    echo "    vcyl_extend: BOOL;" >> tmp
    echo "    hcyl_extend: ARRAY[0..$((compl - 1))] OF BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_OUTPUT" >> tmp
    echo "    wp: ARRAY[0..$((wp_num - 1))] OF BOOL;" >> tmp
    echo "    wp_output: BOOL;" >> tmp
    echo "    hcyl_retracted: ARRAY[0..$((compl - 1))] OF BOOL;" >> tmp
    echo "    hcyl_extended: ARRAY[0..$((compl - 1))] OF BOOL;" >> tmp
    echo "    vcyl_retracted: BOOL;" >> tmp
    echo "    vcyl_extended: BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR" >> tmp
    echo "    hcyl_pos: ARRAY[0..$((compl - 1))] OF INT;" >> tmp
    echo "    total_hcyl_pos: INT;" >> tmp
    echo "    vcyl_pos: INT;" >> tmp
    echo "    carrying_wp: BOOL;" >> tmp
    echo "    prev_carrying_wp: BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_CONSTANT" >> tmp
    echo "    vcyl_maxpos: INT := 2;" >> tmp
    echo "END_VAR" >> tmp

    echo >> tmp
    echo "// plant" >> tmp
    echo "prev_carrying_wp := carrying_wp;" >> tmp
    
    echo "IF carrying_wp AND NOT suction_on THEN" >> tmp
        echo "    carrying_wp := FALSE;" >> tmp
    echo "ELSIF NOT carrying_wp AND vcyl_extended AND suction_on AND (total_hcyl_pos = 1 AND wp[0]"$(for ((i = 1; i < $wp_num; i++)); do echo -n " OR total_hcyl_pos = $((i + 1)) AND wp[$i]"; done)") THEN" >> tmp
    echo "    carrying_wp := TRUE;" >> tmp
    echo "END_IF" >> tmp
    
    echo "wp_output := prev_carrying_wp AND NOT carrying_wp AND (total_hcyl_pos = 0) AND vcyl_extended;" >> tmp
    for ((i = 0; i < $wp_num; i++)); do
        echo "wp[$i] := adding_wp[$i] OR wp[$i] AND NOT(NOT prev_carrying_wp AND vcyl_extended AND suction_on AND (total_hcyl_pos = $((i + 1))));" >> tmp
    done
    
    for ((i = 0; i < $compl; i++)); do
        eval "maxpos=$((1 << ($compl - 1 - i)))"
        echo "IF hcyl_extend[$i] THEN" >> tmp
        echo "    hcyl_pos[$i] := hcyl_pos[$i] + 1;" >> tmp
        echo "ELSE" >> tmp
        echo "    hcyl_pos[$i] := hcyl_pos[$i] - 1;" >> tmp
        echo "END_IF" >> tmp
        echo "IF hcyl_pos[$i] > $maxpos THEN" >> tmp
        echo "    hcyl_pos[$i] := $maxpos;" >> tmp
        echo "ELSIF hcyl_pos[$i] < 0 THEN" >> tmp
        echo "    hcyl_pos[$i] := 0;" >> tmp
        echo "END_IF" >> tmp
        echo "hcyl_retracted[$i] := hcyl_pos[$i] = 0;" >> tmp
        echo "hcyl_extended[$i] := hcyl_pos[$i] = $maxpos;" >> tmp
    done
    
    echo "total_hcyl_pos := hcyl_pos[0]$(for ((i = 1; i < $compl; i++)); do echo -n " + hcyl_pos[$i]"; done);" >> tmp

    echo "IF vcyl_extend THEN" >> tmp
    echo "    vcyl_pos := vcyl_pos + 1;" >> tmp
    echo "ELSE" >> tmp
    echo "    vcyl_pos := vcyl_pos - 1;" >> tmp
    echo "END_IF" >> tmp
    echo "IF vcyl_pos > vcyl_maxpos THEN" >> tmp
    echo "    vcyl_pos := vcyl_maxpos;" >> tmp
    echo "ELSIF vcyl_pos < 0 THEN" >> tmp
    echo "    vcyl_pos := 0;" >> tmp
    echo "END_IF" >> tmp
    echo "vcyl_retracted := vcyl_pos = 0;" >> tmp
    echo "vcyl_extended := vcyl_pos = vcyl_maxpos;" >> tmp
    
    mv tmp $dir/plant-st.txt
    
    # Structured text without loops: controller model
    echo -n > tmp
    echo "VAR_INPUT" >> tmp
    echo "    wp: ARRAY[0..$((wp_num - 1))] OF BOOL;" >> tmp
    echo "    wp_output: BOOL;" >> tmp
    echo "    hcyl_retracted: ARRAY[0..$((compl - 1))] OF BOOL;" >> tmp
    echo "    hcyl_extended: ARRAY[0..$((compl - 1))] OF BOOL;" >> tmp
    echo "    vcyl_retracted: BOOL;" >> tmp
    echo "    vcyl_extended: BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_OUTPUT" >> tmp
    echo "    suction_on: BOOL;" >> tmp
    echo "    vcyl_extend: BOOL;" >> tmp
    echo "    hcyl_extend: ARRAY[0..$((compl - 1))] OF BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR" >> tmp
    echo "    state: INT;" >> tmp
    echo "    // controller temporary variables" >> tmp
    echo "    target_condition: BOOL;" >> tmp
    echo "    minimum_wp: INT;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_CONSTANT" >> tmp
    echo "    // state enumeration" >> tmp
    echo "    s_h_extend: INT := 0;" >> tmp
    echo "    s_v_extend: INT := 1;" >> tmp
    echo "    s_suction_on: INT := 2;" >> tmp
    echo "    s_v_retract: INT := 3;" >> tmp
    echo "    s_h_retract: INT := 4;" >> tmp
    echo "    s_v_extend_output: INT := 5;" >> tmp
    echo "    s_suction_off: INT := 6;" >> tmp
    echo "    s_v_retract_output: INT := 7;" >> tmp
    echo "END_VAR" >> tmp
    
    echo >> tmp
    echo "// controller" >> tmp
    echo "target_condition := FALSE;" >> tmp
    for ((i = 0; i < $wp_num; i++)); do
        echo "target_condition := target_condition OR wp[$i]"$(for ((j = 0; j < $compl; j++)); do echo -n " AND hcyl_"; if (( ((i + 1) >> (compl - j - 1)) % 2 == 1)); then echo -n extended; else echo -n retracted; fi; echo -n "[$j]"; done)";" >> tmp
    done
    
    echo "IF state = s_v_retract_output AND vcyl_retracted AND (wp[0]"$(for ((i = 1; i < $wp_num; i++)); do echo -n " OR wp[$i]"; done)") THEN" >> tmp
    echo "    state := s_h_extend;" >> tmp
    echo "ELSIF state = s_h_extend AND target_condition THEN" >> tmp
    echo "    state := s_v_extend;" >> tmp
    echo "ELSIF state = s_v_extend AND vcyl_extended THEN" >> tmp
    echo "    state := s_suction_on;" >> tmp
    echo "ELSIF state = s_suction_on THEN" >> tmp
    echo "    state := s_v_retract;" >> tmp
    echo "ELSIF state = s_v_retract AND vcyl_retracted THEN" >> tmp
    echo "    state := s_h_retract;" >> tmp
    echo "ELSIF state = s_h_retract"$(for ((i = 0; i < $compl; i++)); do echo -n " AND hcyl_retracted[$i]"; done)" THEN" >> tmp
    echo "    state := s_v_extend_output;" >> tmp
    echo "ELSIF state = s_v_extend_output AND vcyl_extended THEN" >> tmp
    echo "    state := s_suction_off;" >> tmp
    echo "ELSIF state = s_suction_off THEN" >> tmp
    echo "    state := s_v_retract_output;" >> tmp
    echo "END_IF" >> tmp
    
    for ((i = 0; i < $wp_num; i++)); do
        if (( i > 0 )); then
            echo -n "ELS" >> tmp
        fi
        echo "IF wp[$i] THEN" >> tmp
        echo "    minimum_wp := $((i + 1));" >> tmp
    done
    echo "ELSE" >> tmp
    echo "    minimum_wp := 0;" >> tmp
    echo "END_IF" >> tmp
    
    echo "IF state = s_suction_on THEN" >> tmp
    echo "    suction_on := TRUE;" >> tmp
    echo "ELSIF state = s_suction_off THEN" >> tmp
    echo "    suction_on := FALSE;" >> tmp
    echo "ELSIF state = s_h_extend THEN" >> tmp
    for ((i = 0; i < $compl; i++)); do
        eval "divisor=$((1 << (compl - i - 1)))"
        echo "    hcyl_extend[$i] := (minimum_wp / $divisor) MOD 2 = 1;" >> tmp
    done
    echo "ELSIF state = s_h_retract THEN" >> tmp
    for ((i = 0; i < $compl; i++)); do
        echo "    hcyl_extend[$i] := FALSE;" >> tmp
    done
    echo "ELSIF state = s_v_extend OR state = s_v_extend_output THEN" >> tmp
    echo "    vcyl_extend := TRUE;" >> tmp
    echo "ELSIF state = s_v_retract OR state = s_v_retract_output THEN" >> tmp
    echo "    vcyl_extend := FALSE;" >> tmp
    echo "END_IF" >> tmp

    mv tmp $dir/controller-st.txt

    st_convert $dir/plant-st.txt $dir/plant.pml $dir/plant.smv
    st_convert $dir/controller-st.txt $dir/controller.pml $dir/controller.smv

    for name in $dir/plant.smv $dir/controller.smv; do
        cat $name | grep -Pv "^MODULE main$" | grep -Pv "^VAR$" | grep -Pv "^    \\w+: .*;$" | grep -Pv "^    init\\(.*;$"> tmp
        mv tmp $name
    done
    for name in $dir/plant.pml $dir/controller.pml; do
        cat $name | grep -Pv "^(bool|short) .*$" | sed 's/^init {.*$/d_step {/; s/} } od }/}/' > tmp
        mv tmp $name
    done
done
