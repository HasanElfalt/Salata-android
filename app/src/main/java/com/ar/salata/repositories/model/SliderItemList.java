package com.ar.salata.repositories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderItemList {
	
	@SerializedName("images")
	private List<SliderItem> list;
	
	public List<SliderItem> getList() {
		return list;
	}
	
	public void setList(List<SliderItem> list) {
		this.list = list;
	}
}
