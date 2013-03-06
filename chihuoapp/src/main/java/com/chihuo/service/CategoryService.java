package com.chihuo.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.chihuo.util.PublicHelper;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryDao dao;

	public List<Category> findByRestaurant(Restaurant restaurant) {
		return dao.findByRestaurant(restaurant);
	}

	public Category createOrUpdate(String name, String description,
			InputStream upImg, FormDataContentDisposition fileDetail,
			Restaurant restaurant,Category category) {

		category.setName(name);
		category.setDescription(description);
		category.setRestaurant(restaurant);
		category.setStatus(0);
		
		if (upImg != null && !StringUtils.isEmpty(fileDetail.getFileName())) {
			try {
				String image = PublicHelper.saveImage(upImg);
				category.setImage(image);

			} catch (IOException e) {
				throw new WebApplicationException(Response
						.status(Response.Status.BAD_REQUEST).entity("保存图片失败")
						.type(MediaType.TEXT_PLAIN).build());
			}
		}
		
		dao.saveOrUpdate(category);
		return category;
	}

	public Category findByIdInRestaurant(Restaurant restaurant, int id) {
		Category c =dao.findByIdInRestaurant(restaurant, id);
		if (c == null || c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return c;
	}

	public void delete(Category category) {
		category.setStatus(-1);
		dao.saveOrUpdate(category);
	}

}
