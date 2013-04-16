package com.chihuo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@RequestMapping("/infoManager")
	public ModelAndView infoManager() {
		return new ModelAndView("infoManager");
	}

	@RequestMapping("/recipeManager")
	public ModelAndView recipeManager() {
		return new ModelAndView("recipeManager");
	}

	@RequestMapping("/desksManager")
	public ModelAndView desksManager() {
		return new ModelAndView("desksManager");
	}

	@RequestMapping("/orderManager")
	public ModelAndView orderManager() {
		return new ModelAndView("orderManager");
	}

	@RequestMapping("/waiterManager")
	public ModelAndView waiterManager() {
		return new ModelAndView("waiterManager");
	}

}
