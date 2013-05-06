package com.chihuo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/")
	public String welcome() {
		return "index";
	}

	@RequestMapping("/login/form")
	public String login() {
		return "login";
	}

	@RequestMapping("/errors/403")
	public String error() {
		return "/errors/403";
	}
}
