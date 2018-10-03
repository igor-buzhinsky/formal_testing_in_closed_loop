#!/bin/bash
from=2
to=6

init_specs() {
    echo -n > $dir/spec.pml
    echo -n > $dir/spec-ltl.smv
    echo -n > $dir/spec.smv
}

blank_line() {
    echo >> $dir/spec.pml
    echo >> $dir/spec-ltl.smv
    echo >> $dir/spec.smv
}

usual_spec() {
    name="$1"
    x="$2"
    nusmv_ltl_script='s/<>/F /g; s/\[\]/G /g; s/||/|/g; s/\&\&/\&/g; s/==/=/g'
    nusmv_ctl_script='s/<>/AF /g; s/\[\]/AG /g; s/X(/AX(/g; s/||/|/g; s/\&\&/\&/g; s/==/=/g'
    x_ltl=$(echo "$x" | sed "$nusmv_ltl_script")
    x_ctl=$(echo "$x" | sed "$nusmv_ctl_script")
    echo "ltl $name { X( $x ) }" >> $dir/spec.pml
    echo "LTLSPEC X( $x_ltl )" >> $dir/spec-ltl.smv
    echo "CTLSPEC AX( $x_ctl )" >> $dir/spec.smv
}

lack_of_spurious_actuation_spec() {
    name="$1"
    q="($2)"
    p="($3)"
    nusmv_script='s/||/|/g; s/\&\&/\&/g; s/==/=/g'
    q_nusmv=$(echo "$q" | sed "$nusmv_script")
    p_nusmv=$(echo "$p" | sed "$nusmv_script")
    echo "ltl $name { X( (!$q U $p) || ([] !$q) ) }" >> $dir/spec.pml
    echo "LTLSPEC X( (!$q_nusmv U $p_nusmv) | (G !$q_nusmv) )" >> $dir/spec-ltl.smv
    echo "CTLSPEC AX( !E[(!$p_nusmv) U (!$p_nusmv & $q_nusmv)] )" >> $dir/spec.smv
}

for ((compl = from; compl <= to; compl++)); do
    dir="pick-and-place-$compl"
    mkdir -p $dir

    eval "wp_num=$(( (1 << compl) - 1 ))"
    eval "hcyl_maxlen=$(( 1 << (compl - 1) ))"
    max_hpos_sum=$(( hcyl_maxlen * 2 - 1 ))
    
    cat header | sed "s/__1/$compl/g; s/__2/$wp_num/g" > tmp
    cp tmp $dir/header.smv
    cp tmp $dir/header.pml

    cp controller.pml plant.pml $dir

    cat pick-and-place.conf | sed "s/__1/$compl/g; s/__2/$wp_num/g; s/__3/$hcyl_maxlen/g; s/__4/$max_hpos_sum/g" > tmp
    mv tmp $dir/pick-and-place.conf

    # NuSMV
    echo "ASSIGN" >> tmp
    
    echo "    next(carrying_wp) := case" >> tmp
    echo "        carrying_wp & !suction_on: FALSE;" >> tmp
    echo "        !carrying_wp & vcyl_extended & suction_on & (total_hcyl_pos = 1 & wp[0]"$(for ((i = 1; i < $wp_num; i++)); do echo -n " | total_hcyl_pos = $((i + 1)) & wp[$i]"; done)"): TRUE;" >> tmp
    echo "        TRUE: carrying_wp;" >> tmp
    echo "    esac;" >> tmp
    echo "    next(wp_output) := carrying_wp & !next(carrying_wp) & total_hcyl_pos = 0 & vcyl_extended;" >> tmp
    for ((i = 0; i < $wp_num; i++)); do
        echo "    next(wp[$i]) := next(adding_wp[$i]) | wp[$i] & !(!carrying_wp & vcyl_extended & suction_on & total_hcyl_pos = $((i + 1)));" >> tmp
    done
    for ((i = 0; i < $compl; i++)); do
        echo "    next(hcyl_pos[$i]) := hcyl_pos${i}_3;" >> tmp
    done
    echo "    next(total_hcyl_pos) := HPOS_SUM <= $max_hpos_sum ? HPOS_SUM : $max_hpos_sum;" >> tmp
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
    
    echo "    HPOS_SUM := next(hcyl_pos[0])$(for ((i = 1; i < $compl; i++)); do echo -n " + next(hcyl_pos[$i])"; done);" >> tmp
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
    init_specs
    
    all_retracted="hcyl_retracted[0]"$(for ((i = 1; i < $compl; i++)); do echo -n " && hcyl_retracted[$i]"; done)
    for ((i = 0; i < $wp_num; i++)); do
        usual_spec "order${i}_MUST_BE_TRUE" "[](wp[$i] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && $all_retracted && <>(vcyl_retracted && $all_retracted))))"
    done
    
    blank_line
    
    adding_condition="adding_wp[0]"
    for ((i = 0; i < $wp_num; i++)); do
        usual_spec "disappear${i}_MUST_BE_TRUE" "[]((wp[$i] && !adding_wp[$i]) -> <>(!wp[$i] || $adding_condition))"
        adding_condition="$adding_condition || adding_wp[$((i + 1))]"
    done
    
    blank_line
    
    for ((i = 0; i < $wp_num; i++)); do
        lack_of_spurious_actuation_spec "spurious${i}_MUST_BE_TRUE" "!vcyl_retracted && (total_hcyl_pos == $((i + 1)))" "wp[$i]"
    done
    
    #blank_line
    
    #usual_spec "additional0_MUST_BE_TRUE" "[](carrying_wp -> <>(wp_output && !suction_on))"
    #usual_spec "additional1_MUST_BE_TRUE" "[](suction_on -> (carrying_wp$(for ((i = 0; i < $wp_num; i++)); do echo -n " || wp[$i]"; done)))"
    #usual_spec "additional2_MUST_BE_TRUE" "[](suction_on -> <> !suction_on)"
    #lack_of_spurious_actuation_spec "additional3_MUST_BE_TRUE" "vcyl_extend || vcyl_extended || suction_on" "wp[0]$(for ((i = 1; i < $wp_num; i++)); do echo -n " || wp[$i]"; done)"
    
    blank_line
    
    any_extended="hcyl_extended[0]"$(for ((i = 1; i < $compl; i++)); do echo -n " || hcyl_extended[$i]"; done)
    for ((i = 0; i < $wp_num; i++)); do
        usual_spec "order${i}_MUST_BE_FALSE" "[](wp[$i] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && $all_retracted && <>(vcyl_retracted && ($any_extended)))))"
    done
    
    blank_line
    
    for ((i = 0; i < $wp_num; i++)); do
        usual_spec "disappear${i}_MUST_BE_FALSE" "[]((wp[$i] && !adding_wp[$i]) -> <> !wp[$i])"
    done
    
    blank_line
    
    for ((i = 0; i < $((wp_num - 1)); i++)); do # the last spec is actually true, so do not generate it
        lack_of_spurious_actuation_spec "spurious${i}_MUST_BE_FALSE" "total_hcyl_pos == $((i + 1))" "wp[$i]"
    done
    
    #blank_line
    
    #usual_spec "additional0_MUST_BE_FALSE" "[](carrying_wp -> suction_on)"
    #usual_spec "additional1_MUST_BE_FALSE" "[](suction_on -> (wp[0]$(for ((i = 1; i < $wp_num; i++)); do echo -n " || wp[$i]"; done)))"
    #usual_spec "additional2_MUST_BE_FALSE" "[](suction_on -> carrying_wp)"
    #lack_of_spurious_actuation_spec "additional3_MUST_BE_FALSE" "vcyl_extend || vcyl_extended || suction_on" "wp[0]$(for ((i = 1; i < $wp_num; i++)); do echo -n " && wp[$i]"; done)"
done
