package com.chihuo.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.City;
import com.chihuo.service.CityService;

@Component
@Path("/city")
public class CitysResource {

	@Autowired
	private CityService service;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<City> get() {
		return service.getAll();
	}
}
