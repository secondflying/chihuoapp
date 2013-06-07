package com.chihuo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserContext;
import com.chihuo.service.WaiterService;

@Controller
public class WaiterController {

	@Autowired
	private UserContext userContext;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private WaiterService waiterService;

	@RequestMapping(value = "/shop/waiter", method = RequestMethod.GET)
	public String infoManager(Model model) {

		Restaurant restaurant = getCurrentRestaurant();

		List<Waiter> waiters = waiterService.findByRestaurant(restaurant);
		model.addAttribute("waiters", waiters);

		return "shop/waiter";
	}

	@RequestMapping(value = "/shop/waiter/edit", method = RequestMethod.POST)
	public String edit(@RequestParam String addName,
			@RequestParam String addPassword,
			@RequestParam(required = false) Integer addID,
			RedirectAttributes redirectAttributes) {
		Restaurant restaurant = getCurrentRestaurant();

		Waiter waiter;
		if (addID != null) {
			waiter = waiterService.findById(addID);
		} else {
			waiter = new Waiter();
		}

		waiterService.createOrUpdate(addName, addPassword, restaurant, waiter);
		if (addID != null) {
			redirectAttributes.addFlashAttribute("success", "编辑服务员信息成功");
		} else {
			redirectAttributes.addFlashAttribute("success", "新增服务员成功");
		}

		return "redirect:/shop/waiter";
	}

	@RequestMapping(value = "/shop/waiter/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(required = true) Integer id) {
		Waiter waiter = waiterService.findById(id);
		waiterService.delete(waiter);
		return "true";
	}

	private Restaurant getCurrentRestaurant() {
		Owner owner = userContext.getCurrentUser();
		Restaurant restaurant = restaurantService.findByUser(owner);
		return restaurant;
	}

}
