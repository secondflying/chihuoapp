package com.chihuo.resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.chihuo.bussiness.Assistent;
import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.City;
import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Favorite;
import com.chihuo.bussiness.History;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderClientItem;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Owner;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private final JAXBContext context;

	@SuppressWarnings("rawtypes")
	private final Set<Class> types;

	@SuppressWarnings("rawtypes")
	private Class[] ctypes = { Restaurant.class, Category.class, Recipe.class,
			Desk.class, DeskType.class, Order.class, OrderItem.class,
			OrderClientItem.class, DeskStatusView.class, User.class,
			Waiter.class, Owner.class, History.class, Favorite.class, City.class,Assistent.class };

	@SuppressWarnings("rawtypes")
	public JAXBContextResolver() throws Exception {
		this.types = new HashSet<Class>(Arrays.asList(ctypes));
		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(),
				ctypes);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		return (types.contains(objectType)) ? context : null;
	}
}
