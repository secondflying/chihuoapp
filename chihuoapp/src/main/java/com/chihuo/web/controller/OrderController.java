package com.chihuo.web.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.CategoryService;
import com.chihuo.service.OrderService;
import com.chihuo.service.RecipeService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserContext;
import com.chihuo.util.PinyinUtil;
import com.chihuo.util.PublicConfig;
import com.chihuo.util.PublicHelper;

@Controller
public class OrderController {

	@Autowired
	private UserContext userContext;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private RecipeService recipeServic;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/shop/order", method = RequestMethod.GET)
	public String infoManager(Model model) {

		Restaurant restaurant = getCurrentRestaurant();

		List<com.chihuo.bussiness.Order> orders = orderService
				.findByRestaurant(restaurant);

		model.addAttribute("orders", orders);

		return "shop/order";
	}

	private Restaurant getCurrentRestaurant() {
		Owner owner = userContext.getCurrentUser();
		Restaurant restaurant = restaurantService.findByUser(owner);
		return restaurant;
	}
}
