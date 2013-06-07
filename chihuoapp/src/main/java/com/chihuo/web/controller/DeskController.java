package com.chihuo.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.DeskService;
import com.chihuo.service.DeskTypeService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserContext;

@Controller
public class DeskController {

	@Autowired
	private UserContext userContext;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private DeskService deskService;

	@Autowired
	private DeskTypeService typeService;

	@RequestMapping(value = "/shop/desk", method = RequestMethod.GET)
	public String infoManager(@RequestParam(required = false) String cate,
			Model model) {

		Restaurant restaurant = getCurrentRestaurant();

		List<DeskType> types = typeService.findByRestaurant(restaurant);
		model.addAttribute("types", types);

		List<DeskStatusView> desks = null;
		if (!StringUtils.isBlank(cate)) {
			DeskType t = typeService.findByIdInRestaurant(restaurant,
					Integer.parseInt(cate));
			desks = deskService.findByType(t);
		} else {
			desks = deskService.findByRestaurant(restaurant);
		}
		model.addAttribute("desks", desks);

		return "shop/desk";
	}

	@RequestMapping(value = "/shop/desk/checknameexist", method = RequestMethod.GET)
	@ResponseBody
	public boolean nameCheck(@RequestParam(required = true) String dname) {
		Restaurant restaurant = getCurrentRestaurant();

		boolean exist = deskService.checkNameExistInRestaurant(restaurant, dname);

		return !exist;
	}

	@RequestMapping(value = "/shop/desk/cate", method = RequestMethod.POST)
	@ResponseBody
	public String cateEdit(@RequestParam String name) {

		Restaurant restaurant = getCurrentRestaurant();
		if (!StringUtils.isBlank(name)) {
			typeService.createOrUpdate(name, restaurant, new DeskType());
		}
		return "";
	}

	@RequestMapping(value = "/shop/desk/info", method = RequestMethod.POST)
	@ResponseBody
	public String deskEdit(@RequestParam String name,
			@RequestParam Integer capacity, @RequestParam Integer tid,
			@RequestParam(required = false) Integer id) {
		Restaurant restaurant = getCurrentRestaurant();
		
		DeskType category = typeService.findByIdInRestaurant(restaurant, tid);
		Desk desk;
		if (id != null) {
			desk = deskService.findByIdInRestaurant(restaurant, id);
		}else{
			desk = new Desk();
		}
		
		deskService.createOrUpdate(name, capacity, category, restaurant, desk);
		return "true";
	}
	
	@RequestMapping(value = "/shop/desk/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deskDelete(@RequestParam(required = true) Integer id) {
		Restaurant restaurant = getCurrentRestaurant();
		
		Desk desk = deskService.findByIdInRestaurant(restaurant, id);
		
		deskService.delete(desk);
		return "true";
	}

	private Restaurant getCurrentRestaurant() {
		Owner owner = userContext.getCurrentUser();
		Restaurant restaurant = restaurantService.findByUser(owner);
		return restaurant;
	}

}
