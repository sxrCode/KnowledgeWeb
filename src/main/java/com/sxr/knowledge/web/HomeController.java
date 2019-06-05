package com.sxr.knowledge.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(path = { "/hello" })
	public String hello(Model model, HttpServletRequest request) {
		System.out.println("hello!");
		return "hello";
	}

	@ResponseBody
	@RequestMapping(path = { "/data" })
	public String data(HttpServletRequest request) {
		Enumeration<String> headernames = request.getHeaderNames();
		System.out.println("\n");
		while (headernames.hasMoreElements()) {
			String name = headernames.nextElement();
			System.out.println("header_" + name + ": " + request.getHeader(name));
		}
		System.out.println("\n");
		return "this is data";

	}
}
