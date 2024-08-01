package com.testboard2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

	@GetMapping("/fragments")
	public String indexFragments() {
		
		return "/tpl/tpl_fragments";
	}
	
	@GetMapping("/main")
	public String indexMain() {
		
		return "/tpl/tpl_main";
	}
	
	@GetMapping("/sub")
	public String indexSub() {
		
		return "/tpl/tpl_sub";
	}
	
}
