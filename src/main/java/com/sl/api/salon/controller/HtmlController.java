package com.sl.api.salon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {
	
	@RequestMapping("/pages/{pageName}")
	public String getPage(@PathVariable String pageName){
		return pageName;
	}
	
	@RequestMapping("/error")
	public String getError(){
		return "error";
	}
}
