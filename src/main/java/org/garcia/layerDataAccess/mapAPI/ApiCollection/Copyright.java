package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class Copyright {

	@SerializedName("imageAltText")
	private String imageAltText;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("text")
	private String text;

	public void setImageAltText(String imageAltText) {
		this.imageAltText = imageAltText;
	}

	public String getImageAltText() {
		return imageAltText;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
