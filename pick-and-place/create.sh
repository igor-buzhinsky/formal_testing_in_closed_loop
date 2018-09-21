#!/bin/bash
from=2
to=2

plantcomment="// "

for ((compl = from; compl <= to; compl++)); do
    dir="pick-and-place-$compl"
    mkdir -p $dir

    eval "wp_num=$(( (1 << compl) - 1 ))"
    eval "hcyl_maxlen=$(( 1 << (compl - 1) ))"
    
    cat header | sed "s/__1/$compl/g; s/__2/$wp_num/g" > tmp
    cp tmp $dir/header.smv
    cp tmp $dir/header.pml

    cp controller.pml plant.pml $dir

    cat pick-and-place.conf | sed "s/__1/$compl/g; s/__2/$wp_num/g; s/__3/$hcyl_maxlen/g" > tmp
    mv tmp $dir/pick-and-place.conf

    # NuSMV
    echo "ASSIGN" >> tmp
    
    echo "    next(carrying_wp) := case" >> tmp
    echo "        carrying_wp & !suction_on: FALSE;" >> tmp
    echo "        !carrying_wp & vcyl_extended & suction_on & (HPOS_SUM = 1 & wp[0]"$(for ((i = 1; i < $wp_num; i++)); do echo -n " | HPOS_SUM = $((i + 1)) & wp[$i]"; done)"): TRUE;" >> tmp
    echo "        TRUE: carrying_wp;" >> tmp
    echo "    esac;" >> tmp
    echo "    next(wp_output) := carrying_wp & !next(carrying_wp) & HPOS_SUM = 0 & vcyl_extended;" >> tmp
    for ((i = 0; i < $wp_num; i++)); do
        echo "    next(wp[$i]) := next(adding_wp[$i]) | wp[$i] & !(!carrying_wp & vcyl_extended & suction_on & HPOS_SUM = $((i + 1)));" >> tmp
    done
    for ((i = 0; i < $compl; i++)); do
        echo "    next(hcyl_pos[$i]) := hcyl_pos${i}_3;" >> tmp
    done
    echo "    next(vcyl_pos) := vcyl_pos_3;" >> tmp
    for ((i = 0; i < $compl; i++)); do
        echo "    next(hcyl_retracted[$i]) := next(hcyl_pos[$i]) = 0;" >> tmp
    done
    echo "    next(vcyl_retracted) := next(vcyl_pos) = 0;" >> tmp
    for ((i = 0; i < $compl; i++)); do
        echo "    next(hcyl_extended[$i]) := next(hcyl_pos[$i]) = HCYL${i}_MAXPOS;" >> tmp
    done
    echo "    next(vcyl_extended) := next(vcyl_pos) = VCYL_MAXPOS;" >> tmp
    
    echo "DEFINE" >> tmp
    
    echo "    HPOS_SUM := hcyl_pos[0]"$(for ((i = 1; i < $compl; i++)); do echo -n " + hcyl_pos[$i]"; done)";" >> tmp
    for ((i = 0; i < $compl; i++)); do
        eval "maxpos=$((1 << ($compl - 1 - i)))"
        echo "    HCYL${i}_MAXPOS := $maxpos;" >> tmp
    done
    echo "    VCYL_MAXPOS := 2;" >> tmp
    for ((i = 0; i < $compl; i++)); do
        echo "    hcyl_pos${i}_1 := hcyl_pos[$i] + (hcyl_extend[$i] ? 1 : -1);" >> tmp
        echo "    hcyl_pos${i}_2 := hcyl_pos${i}_1 > HCYL${i}_MAXPOS ? HCYL${i}_MAXPOS : hcyl_pos${i}_1;" >> tmp
        echo "    hcyl_pos${i}_3 := hcyl_pos${i}_2 < 0 ? 0 : hcyl_pos${i}_2;" >> tmp
    done
    echo "    vcyl_pos_1 := vcyl_pos + (vcyl_extend ? 1 : -1);" >> tmp
    echo "    vcyl_pos_2 := vcyl_pos_1 > VCYL_MAXPOS ? VCYL_MAXPOS : vcyl_pos_1;" >> tmp
    echo "    vcyl_pos_3 := vcyl_pos_2 < 0 ? 0 : vcyl_pos_2;" >> tmp

    mv tmp $dir/plant.smv

    echo "ASSIGN" >> tmp
    
    echo "    next(state) := case" >> tmp
    echo "        state = s_v_retract_output & next(vcyl_retracted) & (next(wp[0])"$(for ((i = 1; i < $wp_num; i++)); do echo -n " | next(wp[$i])"; done)"): s_h_extend;" >> tmp
    echo "        state = s_h_extend & (hcyl_target_wp0"$(for ((i = 1; i < $wp_num; i++)); do echo -n " | hcyl_target_wp$i"; done)"): s_v_extend;" >> tmp
    echo "        state = s_v_extend & next(vcyl_extended): s_suction_on;" >> tmp
    echo "        state = s_suction_on: s_v_retract;" >> tmp
    echo "        state = s_v_retract & next(vcyl_retracted): s_h_retract;" >> tmp
    echo "        state = s_h_retract"$(for ((i = 0; i < $compl; i++)); do echo -n " & next(hcyl_retracted[$i])"; done)": s_v_extend_output;" >> tmp
    echo "        state = s_v_extend_output & next(vcyl_extended): s_suction_off;" >> tmp
    echo "        state = s_suction_off: s_v_retract_output;" >> tmp
    echo "        TRUE: state;" >> tmp
    echo "    esac;" >> tmp
    echo "    next(suction_on) := case" >> tmp
    echo "        next(state) = s_suction_on: TRUE;" >> tmp
    echo "        next(state) = s_suction_off: FALSE;" >> tmp
    echo "        TRUE: suction_on;" >> tmp
    echo "    esac;" >> tmp
    for ((i = 0; i < $compl; i++)); do
        eval "divisor=$((1 << (compl - i - 1)))"
        echo "    next(hcyl_extend[$i]) := case" >> tmp
        echo "        next(state) = s_h_extend: (minimum_wp / $divisor) mod 2 = 1; -- binary digit" >> tmp
        echo "        next(state) = s_h_retract: FALSE;" >> tmp
        echo "        TRUE: hcyl_extend[$i];" >> tmp
        echo "    esac;" >> tmp
    done
    echo "    next(vcyl_extend) := case" >> tmp
    echo "        next(state) in {s_v_extend, s_v_extend_output}: TRUE;" >> tmp
    echo "        next(state) in {s_v_retract, s_v_retract_output}: FALSE;" >> tmp
    echo "        TRUE: vcyl_extend;" >> tmp
    echo "    esac;" >> tmp
    
    echo "DEFINE" >> tmp
    
    for ((i = 0; i < $wp_num; i++)); do
        echo "    hcyl_target_wp$i := next(wp[$i])"$(for ((j = 0; j < $compl; j++)); do echo -n " & next(hcyl_"; if (( ((i + 1) >> (compl - j - 1)) % 2 == 1)); then echo -n extended; else echo -n retracted; fi; echo -n "[$j])"; done)";" >> tmp
    done
    echo "    minimum_wp := "$(for ((i = 0; i < $wp_num; i++)); do echo -n "next(wp[$i]) ? $((i + 1)) : "; done)"0;" >> tmp

    mv tmp $dir/controller.smv
    
    # Promela (only requirements: the rest is copied from plant.pml and controller.pml)
    all_retracted="hcyl_retracted[0]"$(for ((i = 1; i < $compl; i++)); do echo -n " && hcyl_retracted[$i]"; done)
    for ((i = 0; i < $wp_num; i++)); do
        echo "ltl order${i}_MUST_BE_TRUE { X( [](wp[$i] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && $all_retracted && <>(vcyl_retracted && $all_retracted)))) ) }" >> tmp
    done
    
    echo >> tmp
    
    adding_condition="adding_wp[0]"
    for ((i = 0; i < $wp_num; i++)); do
        echo "ltl disappear${i}_MUST_BE_TRUE { X( []((wp[$i] && !adding_wp[$i]) -> <>(!wp[$i] || $adding_condition)) ) }" >> tmp
        adding_condition="$adding_condition || adding_wp[$((i + 1))]"
    done
    
    echo >> tmp
    
    echo "ltl additional0_MUST_BE_TRUE { X( [](carrying_wp -> <>(wp_output && !suction_on)) ) }" >> tmp
    echo "ltl additional1_MUST_BE_TRUE { X( [](suction_on -> (carrying_wp"$(for ((i = 0; i < $wp_num; i++)); do echo -n " || wp[$i]"; done)")) ) }" >> tmp
    echo "ltl additional2_MUST_BE_TRUE { X( [](suction_on -> <> !suction_on) ) }" >> tmp
    
    echo >> tmp
    
    any_extended="hcyl_extended[0]"$(for ((i = 1; i < $compl; i++)); do echo -n " || hcyl_extended[$i]"; done)
    for ((i = 0; i < $wp_num; i++)); do
        echo "ltl order${i}_MUST_BE_FALSE { X( [](wp[$i] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && $all_retracted && <>(vcyl_retracted && ($any_extended))))) ) }" >> tmp
    done
    
    echo >> tmp
    
    for ((i = 0; i < $wp_num; i++)); do
        echo "ltl disappear${i}_MUST_BE_FALSE { X( []((wp[$i] && !adding_wp[$i]) -> <> !wp[$i]) ) }" >> tmp
    done
    
    echo >> tmp
    
    echo "ltl additional0_MUST_BE_FALSE { X( [](carrying_wp -> suction_on) ) }" >> tmp
    echo "ltl additional1_MUST_BE_FALSE { X( [](suction_on -> (wp[0]"$(for ((i = 1; i < $compl; i++)); do echo -n " || wp[$i]"; done)")) ) }" >> tmp
    echo "ltl additional2_MUST_BE_FALSE { X( [](suction_on -> carrying_wp) ) }" >> tmp

    cat tmp | sed 's/<>/AF /g; s/\[\]/AG /g; s/X(/AX(/g; s/||/|/g; s/\&\&/\&/g; s/==/=/g; s/^ltl \w\+ { /CTLSPEC /g; s/}//g; s/\/\//--/g' > $dir/spec.smv
    cat tmp | sed 's/<>/F /g; s/\[\]/G /g; s/||/|/g; s/\&\&/\&/g; s/==/=/g; s/^ltl \w\+ { /LTLSPEC /g; s/}//g; s/\/\//--/g' > $dir/spec-ltl.smv
    mv tmp $dir/spec.pml
done
