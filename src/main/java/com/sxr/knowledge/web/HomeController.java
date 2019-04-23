package com.sxr.knowledge.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(path = { "/hello" })
	public String hello(Model model) {
		System.out.println("hello!");
		return "hello";
	}
}
