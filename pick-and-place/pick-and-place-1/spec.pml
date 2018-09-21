ltl order0_MUST_BE_TRUE { X( [](wp[0] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && <>(vcyl_retracted && hcyl_retracted[0])))) ) }

ltl disappear0_MUST_BE_TRUE { X( []((wp[0] && !adding_wp[0]) -> <>(!wp[0] || adding_wp[0])) ) }

ltl additional0_MUST_BE_TRUE { X( [](carrying_wp -> <>(wp_output && !suction_on)) ) }
ltl additional1_MUST_BE_TRUE { X( [](suction_on -> (carrying_wp || wp[0])) ) }
ltl additional2_MUST_BE_TRUE { X( [](suction_on -> <> !suction_on) ) }

ltl order0_MUST_BE_FALSE { X( [](wp[0] -> <>(vcyl_extended && suction_on && carrying_wp && <>(wp_output && vcyl_extended && hcyl_retracted[0] && <>(vcyl_retracted && (hcyl_extended[0]))))) ) }

ltl disappear0_MUST_BE_FALSE { X( []((wp[0] && !adding_wp[0]) -> <> !wp[0]) ) }

ltl additional0_MUST_BE_FALSE { X( [](carrying_wp -> suction_on) ) }
ltl additional1_MUST_BE_FALSE { X( [](suction_on -> (wp[0])) ) }
ltl additional2_MUST_BE_FALSE { X( [](suction_on -> carrying_wp) ) }
