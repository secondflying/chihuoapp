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

import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.RecipeDao;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.util.PinyinUtil;
import com.chihuo.util.PublicHelper;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Service
@Transactional
public class RestaurantService {
	@Autowired
	private RestaurantDao dao;

	@Autowired
	private RecipeDao dao2;

	public Restaurant findById(int id) {
		Restaurant c = dao.findById(id);
		if (c == null || c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return c;
	}

	public List<Restaurant> getVerified(int city, String name) {
		// return dao.findByStatus(1);
		return dao.findNotDeleted(city, name);
	}

	public List<Restaurant> getToVerify() {
		return dao.findByStatus(0);
	}

	public List<Restaurant> getNotVerified() {
		return dao.findByStatus(2);
	}

	public List<Restaurant> findNotDeleted(int city, String name) {
		return dao.findNotDeleted(city, name);
	}

	public Restaurant findByUser(Owner u) {
		List<Restaurant> list = dao.findByUser(u);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Restaurant> findAround(double x, double y, double distance,
			int city) {
		double KmPerDegree = 111.12000071117;

		double dis = distance / 1000 / KmPerDegree;
		double xmin = x - dis;
		double xmax = x + dis;
		double ymin = y - dis;
		double ymax = y + dis;
		return dao.findByExtent(xmin, xmax, ymin, ymax, city);
	}

	public Restaurant create(String name, String telephone, String address,
			double x, double y, InputStream upImg,
			FormDataContentDisposition fileDetail, Owner loginUser) {

		Restaurant r = new Restaurant();
		r.setName(name);
		r.setPinyin(PinyinUtil.converterToFirstSpell(name));
		r.setAddress(address);
		r.setTelephone(telephone);
		if (x != -1000) {
			r.setX(x);
		}
		if (y != -1000) {
			r.setY(y);
		}
		r.setStatus(0);
		r.setOwner(loginUser);

		if (upImg != null && !StringUtils.isEmpty(fileDetail.getFileName())) {
			try {
				String image = PublicHelper.saveImage(upImg,"");
				r.setImage(image);

			} catch (IOException e) {
				throw new WebApplicationException(Response
						.status(Response.Status.BAD_REQUEST).entity("保存图片失败")
						.type(MediaType.TEXT_PLAIN).build());
			}
		}

		dao.saveOrUpdate(r);
		return r;
	}

	public void update(String name, String telephone, String address, double x,
			double y, InputStream upImg, FormDataContentDisposition fileDetail,
			Owner loginUser, Restaurant r) {

		// 判断该餐馆是否是该用户所有
		if (loginUser.getId() != r.getOwner().getId()) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		r.setName(name);
		r.setPinyin(PinyinUtil.converterToFirstSpell(name));
		r.setAddress(address);
		r.setTelephone(telephone);
		if (x != -1000) {
			r.setX(x);
		}
		if (y != -1000) {
			r.setY(y);
		}

		if (upImg != null && !StringUtils.isEmpty(fileDetail.getFileName())) {
			try {
				String image = PublicHelper.saveImage(upImg,r.getImage());
				r.setImage(image);

			} catch (IOException e) {
				throw new WebApplicationException(Response
						.status(Response.Status.BAD_REQUEST).entity("保存图片失败")
						.type(MediaType.TEXT_PLAIN).build());
			}
		}

		dao.saveOrUpdate(r);
	}

	public void saveOrUpdate(Restaurant r) {
		dao.saveOrUpdate(r);
	}

	public void saveOrUpdateImage(Restaurant r, InputStream upImage) {
		try {
			String image = PublicHelper.saveImage(upImage,r.getImage());
			r.setImage(image);
			dao.saveOrUpdate(r);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(Restaurant restaurant) {
		restaurant.setStatus(-1);
		dao.saveOrUpdate(restaurant);
	}

	public void verify(Restaurant restaurant) {
		restaurant.setStatus(1);
		dao.saveOrUpdate(restaurant);
	}

	public void notverify(Restaurant restaurant) {
		restaurant.setStatus(2);
		dao.saveOrUpdate(restaurant);
	}

	public void addPinyin() {
		List<Restaurant> list = dao.findAll();
		for (Restaurant restaurant : list) {
			restaurant.setPinyin(PinyinUtil.converterToFirstSpell(restaurant
					.getName()));
			dao.saveOrUpdate(restaurant);
		}

		List<Recipe> list2 = dao2.findAll();
		for (Recipe recipe : list2) {
			recipe.setPinyin(PinyinUtil.converterToFirstSpell(recipe.getName()));
			dao2.saveOrUpdate(recipe);
		}
	}

}
