package com.chihuo.bussiness;

// Generated 2012-11-20 16:23:43 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.chihuo.util.JaxbDateSerializer;

/**
 * Order generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Order implements java.io.Serializable {
	private Integer id;

	private Desk desk;

	private Waiter waiter;

	private Integer number;

	private Date starttime;

	private Date endtime;

	private String code;

	private Integer status;

	private Double money;

	private List<OrderItem> orderItems;

	private Restaurant restaurant;

	public Order() {
	}

	public Order(Restaurant restaurant, Desk desk, Integer number,
			Date starttime, Date endtime, String code, Integer status,
			List<OrderItem> orderItems) {
		this.restaurant = restaurant;
		this.desk = desk;
		this.number = number;
		this.starttime = starttime;
		this.endtime = endtime;
		this.code = code;
		this.status = status;
		this.orderItems = orderItems;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Desk getDesk() {
		return this.desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@XmlElement
	public List<OrderClientItem> getClientItems() {
		if (this.orderItems != null && !this.orderItems.isEmpty()) {
			List<OrderClientItem> list = new ArrayList<OrderClientItem>();
			for (OrderItem item : orderItems) {
				OrderClientItem o = queryByRecipe(item.getRecipe().getId(),
						list);
				if (o == null) {
					o = new OrderClientItem();
					o.setCountNew(0);
					o.setCountDeposit(0);
					o.setCountConfirm(0);
					o.setRecipe(item.getRecipe());
					list.add(o);
				}

				if (item.getStatus() == 0) {
					o.setCountNew(o.getCountNew() + item.getCount());
				}
				if (item.getStatus() == 1) {
					o.setCountDeposit(o.getCountDeposit() + item.getCount());
				}

				if (item.getStatus() == 2) {
					o.setCountConfirm(o.getCountConfirm() + item.getCount());
				}
			}
			return list;
		}
		return null;
	}

	@XmlElement
	public double getPriceAll() {
		List<OrderClientItem> clientItems = getClientItems();
		double price = 0;
		if (clientItems != null && !clientItems.isEmpty()) {
			for (OrderClientItem item : clientItems) {
				price += (item.getCountNew() + item.getCountDeposit() + item
						.getCountConfirm()) * item.getRecipe().getPrice();
			}
		}
		return price;
	}

	@XmlElement
	public double getPriceDeposit() {
		List<OrderClientItem> clientItems = getClientItems();
		double price = 0;
		if (clientItems != null && !clientItems.isEmpty()) {

			for (OrderClientItem item : clientItems) {
				price += (item.getCountDeposit()) * item.getRecipe().getPrice();
			}
		}
		return price;
	}

	@XmlElement
	public double getPriceConfirm() {
		List<OrderClientItem> clientItems = getClientItems();
		double price = 0;
		if (clientItems != null && !clientItems.isEmpty()) {
			for (OrderClientItem item : clientItems) {
				price += (item.getCountConfirm()) * item.getRecipe().getPrice();
			}
		}
		return price;
	}

	private OrderClientItem queryByRecipe(Integer id, List<OrderClientItem> list) {
		for (OrderClientItem orderClientItem : list) {
			if (orderClientItem.getRecipe().getId() == id) {
				return orderClientItem;
			}
		}
		return null;
	}

	// private OrderItem queryByOrderItem(Integer recipeID, Integer itemStatus,
	// List<OrderItem>list){
	// for (OrderItem item : list) {
	// if(item.getRecipe().getId() == recipeID && item.getStatus() ==
	// itemStatus){
	// return item;
	// }
	// }
	// return null;
	// }

}
