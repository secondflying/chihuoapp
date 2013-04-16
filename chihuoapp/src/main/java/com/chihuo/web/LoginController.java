package com.chihuo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public ModelAndView login() {
		String message = "你好, Spring 3.0!";
		return new ModelAndView("hello", "message", message);
	}
}
