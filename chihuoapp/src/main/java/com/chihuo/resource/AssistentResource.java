package com.chihuo.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Assistent;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.AssistentService;

@Component
public class AssistentResource {
	private Restaurant restaurant;

	@Autowired
	AssistentService service;

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Assistent> get() {
		return service.findByRestaurant(restaurant);
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}
