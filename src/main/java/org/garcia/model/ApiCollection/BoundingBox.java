package org.garcia.model.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class BoundingBox{

	@SerializedName("lr")
	private Lr lr;

	@SerializedName("ul")
	private Ul ul;

	public void setLr(Lr lr){
		this.lr = lr;
	}

	public Lr getLr(){
		return lr;
	}

	public void setUl(Ul ul){
		this.ul = ul;
	}

	public Ul getUl(){
		return ul;
	}
}