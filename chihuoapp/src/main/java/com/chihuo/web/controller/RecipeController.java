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
import com.chihuo.service.RecipeService;
import com.chihuo.service.RestaurantService;
import com.chihuo.service.UserContext;
import com.chihuo.util.PinyinUtil;
import com.chihuo.util.PublicConfig;
import com.chihuo.util.PublicHelper;

@Controller
public class RecipeController {

	@Autowired
	private UserContext userContext;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private RecipeService recipeServic;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/shop/recipe", method = RequestMethod.GET)
	public String infoManager(@RequestParam(required = false) String cate,
			Model model) {

		Restaurant restaurant = getCurrentRestaurant();

		model.addAttribute("imageUrl", PublicConfig.getImageUrl() + "small");

		List<Category> cates = categoryService.findByRestaurant(restaurant);
		model.addAttribute("types", cates);

		List<Recipe> recipes = null;
		if (!StringUtils.isBlank(cate)) {
			Category t = categoryService.findByIdInRestaurant(restaurant,
					Integer.parseInt(cate));
			recipes = recipeServic.findByCategory(t);
		} else {
			recipes = recipeServic.findByRestaurant(restaurant);
		}
		model.addAttribute("recipes", recipes);

		return "shop/recipe";
	}

	@RequestMapping(value = "/shop/recipe/cate", method = RequestMethod.POST)
	@ResponseBody
	public String cateEdit(@RequestParam String name,
			@RequestParam String description) {

		Restaurant restaurant = getCurrentRestaurant();
		if (!StringUtils.isBlank(name)) {
			categoryService.createOrUpdate(name, description, null, null,
					restaurant, new Category());
		}
		return "";
	}

	@RequestMapping(value = "/shop/recipe/addone", method = RequestMethod.POST)
	public String addOne(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws IllegalStateException,
			IOException, ServletException {
		Restaurant restaurant = getCurrentRestaurant();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("addImage");
		String imageName = PublicHelper.saveImage(file.getInputStream(), "");

		String name = multipartRequest.getParameter("addName");
		double price = Double.parseDouble(multipartRequest
				.getParameter("addPrice"));
		String description = multipartRequest.getParameter("addDescription");
		int cid = Integer
				.parseInt(multipartRequest.getParameter("addCateList"));

		Category category = categoryService.findByIdInRestaurant(restaurant,
				cid);

		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setPinyin(PinyinUtil.converterToFirstSpell(name));
		recipe.setPrice(price);
		recipe.setImage(imageName);
		recipe.setDescription(description);
		recipe.setRestaurant(restaurant);
		recipe.setStatus(0);
		recipe.setCategory(category);
		recipeServic.createOrUpdate(recipe);

		redirectAttributes.addFlashAttribute("success", "新增菜品成功");

		String referer = request.getHeader("Referer");
		if (!StringUtils.isBlank(referer)) {
			return "redirect:" + referer;
		} else {
			return "redirect:/shop/recipe";
		}
	}

	@RequestMapping(value = "/shop/recipe/editone", method = RequestMethod.POST)
	public String editOne(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws IllegalStateException,
			IOException, ServletException {
		Restaurant restaurant = getCurrentRestaurant();

		Integer id = Integer.parseInt(request.getParameter("editID"));
		Recipe recipe = recipeServic.findByIdInRestaurant(restaurant, id);

		String name = request.getParameter("editName");
		double price = Double.parseDouble(request.getParameter("editPrice"));
		String description = request.getParameter("editDescription");
		int cid = Integer.parseInt(request.getParameter("editCateList"));
		Category category = categoryService.findByIdInRestaurant(restaurant,
				cid);

		recipe.setName(name);
		recipe.setPinyin(PinyinUtil.converterToFirstSpell(name));
		recipe.setPrice(price);
		recipe.setDescription(description);
		recipe.setCategory(category);
		recipeServic.createOrUpdate(recipe);

		redirectAttributes.addFlashAttribute("success", "编辑菜品成功");

		String referer = request.getHeader("Referer");
		if (!StringUtils.isBlank(referer)) {
			return "redirect:" + referer;
		} else {
			return "redirect:/shop/recipe";
		}
	}

	@RequestMapping(value = "/shop/recipe/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(required = true) Integer id) {
		Restaurant restaurant = getCurrentRestaurant();

		Recipe recipe = recipeServic.findByIdInRestaurant(restaurant, id);

		recipeServic.delete(recipe);
		return "true";
	}

	private Restaurant getCurrentRestaurant() {
		Owner owner = userContext.getCurrentUser();
		Restaurant restaurant = restaurantService.findByUser(owner);
		return restaurant;
	}

}
