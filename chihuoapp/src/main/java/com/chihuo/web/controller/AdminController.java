package com.chihuo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chihuo.bussiness.Owner;

@Controller
public class AdminController {

	@RequestMapping("/shop/info")
	public ModelAndView infoManager() {
		Owner owner = 
		return new ModelAndView("info");
	}

	@RequestMapping("/shop/recipe")
	public ModelAndView recipeManager() {
		return new ModelAndView("recipe");
	}

	@RequestMapping("/shop/desk")
	public ModelAndView desksManager() {
		return new ModelAndView("desk");
	}

	@RequestMapping("/shop/order")
	public ModelAndView orderManager() {
		return new ModelAndView("order");
	}

	@RequestMapping("/shop/waiter")
	public ModelAndView waiterManager() {
		return new ModelAndView("waiter");
	}

}
