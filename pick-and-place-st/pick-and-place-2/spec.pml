ltl order0_MUST_BE_TRUE { X( [](wp[0] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1])))) ) }
ltl order1_MUST_BE_TRUE { X( [](wp[1] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1])))) ) }
ltl order2_MUST_BE_TRUE { X( [](wp[2] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1])))) ) }

ltl disappear0_MUST_BE_TRUE { X( []((wp[0] && !adding_wp[0]) -> <>(!wp[0] || adding_wp[0])) ) }
ltl disappear1_MUST_BE_TRUE { X( []((wp[1] && !adding_wp[1]) -> <>(!wp[1] || adding_wp[0] || adding_wp[1])) ) }
ltl disappear2_MUST_BE_TRUE { X( []((wp[2] && !adding_wp[2]) -> <>(!wp[2] || adding_wp[0] || adding_wp[1] || adding_wp[2])) ) }

ltl spurious0_MUST_BE_TRUE { X( (!(!vcyl_retracted && (total_hcyl_pos == 1)) U (wp[0])) || ([] !(!vcyl_retracted && (total_hcyl_pos == 1))) ) }
ltl spurious1_MUST_BE_TRUE { X( (!(!vcyl_retracted && (total_hcyl_pos == 2)) U (wp[1])) || ([] !(!vcyl_retracted && (total_hcyl_pos == 2))) ) }
ltl spurious2_MUST_BE_TRUE { X( (!(!vcyl_retracted && (total_hcyl_pos == 3)) U (wp[2])) || ([] !(!vcyl_retracted && (total_hcyl_pos == 3))) ) }

ltl order0_MUST_BE_FALSE { X( [](wp[0] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1]))))) ) }
ltl order1_MUST_BE_FALSE { X( [](wp[1] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1]))))) ) }
ltl order2_MUST_BE_FALSE { X( [](wp[2] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1]))))) ) }

ltl disappear0_MUST_BE_FALSE { X( []((wp[0] && !adding_wp[0]) -> <> !wp[0]) ) }
ltl disappear1_MUST_BE_FALSE { X( []((wp[1] && !adding_wp[1]) -> <> !wp[1]) ) }
ltl disappear2_MUST_BE_FALSE { X( []((wp[2] && !adding_wp[2]) -> <> !wp[2]) ) }

ltl spurious0_MUST_BE_FALSE { X( (!(total_hcyl_pos == 1) U (wp[0])) || ([] !(total_hcyl_pos == 1)) ) }
ltl spurious1_MUST_BE_FALSE { X( (!(total_hcyl_pos == 2) U (wp[1])) || ([] !(total_hcyl_pos == 2)) ) }
