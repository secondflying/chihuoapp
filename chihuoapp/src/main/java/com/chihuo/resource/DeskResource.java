package com.chihuo.resource;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.service.DeskService;
import com.chihuo.service.DeskTypeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sun.jersey.multipart.FormDataParam;

@Component
public class DeskResource {
	Restaurant restaurant;
	Desk desk;
	
	@Autowired
	DeskService deskService;
	
	@Autowired
	DeskTypeService typeService;

	@GET
	@Produces("application/json; charset=UTF-8")
	public Desk get() {
		return desk;
	}
	

	@POST
	@RolesAllowed({"OWNER"})
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("capacity") int capacity,
			@DefaultValue("-1") @FormDataParam("tid") int tid){
		
		DeskType category = null;
		if (tid != -1) {
			 category = typeService.findByIdInRestaurant(restaurant, tid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("餐桌类型不存在").type(MediaType.TEXT_PLAIN).build();
			}
		}
		
		deskService.createOrUpdate(name, capacity, category, restaurant, desk);

		return Response.status(Response.Status.OK).build();
	}
	

	@DELETE
	@RolesAllowed({"OWNER"})
	public void delete() {
		deskService.delete(desk);
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Desk getDesk() {
		return desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}

}
