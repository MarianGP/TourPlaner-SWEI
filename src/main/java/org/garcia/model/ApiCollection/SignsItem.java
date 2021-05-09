package org.garcia.model.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class SignsItem{

	@SerializedName("extraText")
	private String extraText;

	@SerializedName("text")
	private String text;

	@SerializedName("type")
	private int type;

	@SerializedName("url")
	private String url;

	@SerializedName("direction")
	private int direction;

	public void setExtraText(String extraText){
		this.extraText = extraText;
	}

	public String getExtraText(){
		return extraText;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	public int getDirection(){
		return direction;
	}
}