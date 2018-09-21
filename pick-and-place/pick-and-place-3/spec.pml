ltl order0_MUST_BE_TRUE { X( [](wp[0] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }
ltl order1_MUST_BE_TRUE { X( [](wp[1] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }
ltl order2_MUST_BE_TRUE { X( [](wp[2] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }
ltl order3_MUST_BE_TRUE { X( [](wp[3] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }
ltl order4_MUST_BE_TRUE { X( [](wp[4] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }
ltl order5_MUST_BE_TRUE { X( [](wp[5] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }
ltl order6_MUST_BE_TRUE { X( [](wp[6] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2])))) ) }

ltl disappear0_MUST_BE_TRUE { X( []((wp[0] && !adding_wp[0]) -> <>(!wp[0] || adding_wp[0])) ) }
ltl disappear1_MUST_BE_TRUE { X( []((wp[1] && !adding_wp[1]) -> <>(!wp[1] || adding_wp[0] || adding_wp[1])) ) }
ltl disappear2_MUST_BE_TRUE { X( []((wp[2] && !adding_wp[2]) -> <>(!wp[2] || adding_wp[0] || adding_wp[1] || adding_wp[2])) ) }
ltl disappear3_MUST_BE_TRUE { X( []((wp[3] && !adding_wp[3]) -> <>(!wp[3] || adding_wp[0] || adding_wp[1] || adding_wp[2] || adding_wp[3])) ) }
ltl disappear4_MUST_BE_TRUE { X( []((wp[4] && !adding_wp[4]) -> <>(!wp[4] || adding_wp[0] || adding_wp[1] || adding_wp[2] || adding_wp[3] || adding_wp[4])) ) }
ltl disappear5_MUST_BE_TRUE { X( []((wp[5] && !adding_wp[5]) -> <>(!wp[5] || adding_wp[0] || adding_wp[1] || adding_wp[2] || adding_wp[3] || adding_wp[4] || adding_wp[5])) ) }
ltl disappear6_MUST_BE_TRUE { X( []((wp[6] && !adding_wp[6]) -> <>(!wp[6] || adding_wp[0] || adding_wp[1] || adding_wp[2] || adding_wp[3] || adding_wp[4] || adding_wp[5] || adding_wp[6])) ) }

ltl additional0_MUST_BE_TRUE { X( [](carrying_wp -> <>(wp_output && !suction_on)) ) }
ltl additional1_MUST_BE_TRUE { X( [](suction_on -> (carrying_wp || wp[0] || wp[1] || wp[2] || wp[3] || wp[4] || wp[5] || wp[6])) ) }
ltl additional2_MUST_BE_TRUE { X( [](suction_on -> <> !suction_on) ) }

ltl order0_MUST_BE_FALSE { X( [](wp[0] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }
ltl order1_MUST_BE_FALSE { X( [](wp[1] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }
ltl order2_MUST_BE_FALSE { X( [](wp[2] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }
ltl order3_MUST_BE_FALSE { X( [](wp[3] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }
ltl order4_MUST_BE_FALSE { X( [](wp[4] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }
ltl order5_MUST_BE_FALSE { X( [](wp[5] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }
ltl order6_MUST_BE_FALSE { X( [](wp[6] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && hcyl_retracted[1] && hcyl_retracted[2] && <>(vcyl_retracted && (hcyl_extended[0] || hcyl_extended[1] || hcyl_extended[2]))))) ) }

ltl disappear0_MUST_BE_FALSE { X( []((wp[0] && !adding_wp[0]) -> <> !wp[0]) ) }
ltl disappear1_MUST_BE_FALSE { X( []((wp[1] && !adding_wp[1]) -> <> !wp[1]) ) }
ltl disappear2_MUST_BE_FALSE { X( []((wp[2] && !adding_wp[2]) -> <> !wp[2]) ) }
ltl disappear3_MUST_BE_FALSE { X( []((wp[3] && !adding_wp[3]) -> <> !wp[3]) ) }
ltl disappear4_MUST_BE_FALSE { X( []((wp[4] && !adding_wp[4]) -> <> !wp[4]) ) }
ltl disappear5_MUST_BE_FALSE { X( []((wp[5] && !adding_wp[5]) -> <> !wp[5]) ) }
ltl disappear6_MUST_BE_FALSE { X( []((wp[6] && !adding_wp[6]) -> <> !wp[6]) ) }

ltl additional0_MUST_BE_FALSE { X( [](carrying_wp -> suction_on) ) }
ltl additional1_MUST_BE_FALSE { X( [](suction_on -> (wp[0] || wp[1] || wp[2])) ) }
ltl additional2_MUST_BE_FALSE { X( [](suction_on -> carrying_wp) ) }
