package com.chihuo.bussiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderClientItem implements java.io.Serializable {

	@XmlElement
	private Integer countNew;
	@XmlElement
	private Integer countDeposit;
	@XmlElement
	private Integer countConfirm;
	@XmlElement
	private Recipe recipe;
	
	public OrderClientItem (){
		
	}
	

	public Integer getCountNew() {
		return countNew;
	}

	public void setCountNew(Integer countNew) {
		this.countNew = countNew;
	}

	public Integer getCountDeposit() {
		return countDeposit;
	}

	public void setCountDeposit(Integer countDeposit) {
		this.countDeposit = countDeposit;
	}

	public Integer getCountConfirm() {
		return countConfirm;
	}

	public void setCountConfirm(Integer countConfirm) {
		this.countConfirm = countConfirm;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}


}
