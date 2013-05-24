package com.chihuo.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.OwnerService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserContext;
import com.chihuo.util.PublicConfig;
import com.chihuo.util.PublicHelper;
import com.chihuo.web.form.BasicInfo;

@Controller
public class AdminController {

	@Autowired
	private UserContext userContext;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private RestaurantService restaurantService;

	@RequestMapping("/shop/info")
	public String infoManager(@ModelAttribute BasicInfo basicInfo, Model model) {

		Owner owner = userContext.getCurrentUser();
		List<Restaurant> list = restaurantService.findByUser(owner);

		model.addAttribute("restaurant", list.get(0));
		if (!StringUtils.isBlank(list.get(0).getImage())) {
			model.addAttribute("imageUrl",PublicConfig.getImageUrl() + list.get(0).getImage());
		}

		basicInfo.setId(list.get(0).getId());
		basicInfo.setName(list.get(0).getName());
		basicInfo.setTelephone(list.get(0).getTelephone());
		basicInfo.setAverage(list.get(0).getAverage());
		basicInfo.setDescription(list.get(0).getDescription());

		basicInfo.setId(list.get(0).getId());
		basicInfo.setAddress(list.get(0).getAddress());
		basicInfo.setX(list.get(0).getX());
		basicInfo.setY(list.get(0).getY());

		return "shop/info";
	}

	@RequestMapping(value = "/shop/info/basic", method = RequestMethod.POST)
	public String updateBasicInfo(@ModelAttribute BasicInfo basicInfo,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(basicInfo.getName())) {
			result.rejectValue("name", "name", "餐厅名称不能为空");
		}
		if (result.hasErrors()) {
			return "/shop/info";
		}

		Restaurant restaurant = restaurantService.findById(basicInfo.getId());
		restaurant.setName(basicInfo.getName());
		restaurant.setTelephone(basicInfo.getTelephone());
		restaurant.setAverage(basicInfo.getAverage());
		restaurant.setDescription(basicInfo.getDescription());
		restaurantService.saveOrUpdate(restaurant);
		redirectAttributes.addFlashAttribute("basic", "保存成功");
		return "redirect:/shop/info";
	}

	@RequestMapping(value = "/shop/info/image", method = RequestMethod.POST)
	@ResponseBody
	public String updateImageInfo(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException, ServletException {
		Owner owner = userContext.getCurrentUser();
		List<Restaurant> list = restaurantService.findByUser(owner);
		Restaurant restaurant = list.get(0);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        
		String imageName = PublicHelper.saveImage(file.getInputStream(), restaurant.getImage());
		restaurant.setImage(imageName);
		restaurantService.saveOrUpdate(restaurant);
		return PublicConfig.getImageUrl() + restaurant.getImage();
	}

	@RequestMapping(value = "/shop/info/address", method = RequestMethod.POST)
	public String updateAddressInfo(@ModelAttribute BasicInfo basicInfo,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(basicInfo.getAddress())) {
			result.rejectValue("address", "address", "地址不能为空");
		}
		if (result.hasErrors()) {
			return "/shop/info";
		}

		Restaurant restaurant = restaurantService.findById(basicInfo.getId());
		restaurant.setAddress(basicInfo.getAddress());
		restaurant.setX(basicInfo.getX());
		restaurant.setY(basicInfo.getY());
		restaurantService.saveOrUpdate(restaurant);
		redirectAttributes.addFlashAttribute("address", "保存成功");
		return "redirect:/shop/info";
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
