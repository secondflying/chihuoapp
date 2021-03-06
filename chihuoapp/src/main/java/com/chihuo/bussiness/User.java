package com.chihuo.bussiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// Generated 2012-11-27 16:53:06 by Hibernate Tools 3.4.0.CR1

/**
 * Users generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements java.io.Serializable {

	@XmlElement
	private Integer id;
	@XmlElement
	private String name;
	
	@XmlElement
	private String thumbnail;
	
	@XmlElement
	private Integer fromsns;
	
	@XmlTransient
	private String password;
	@XmlTransient
	private Integer status;

	public User() {
	}

	public User(String name, String password, Integer status) {
		this.name = name;
		this.password = password;
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getFromsns() {
		return fromsns;
	}

	public void setFromsns(Integer fromsns) {
		this.fromsns = fromsns;
	}

}
