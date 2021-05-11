package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class Lr {

	@SerializedName("lng")
	private double lng;

	@SerializedName("lat")
	private double lat;

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLng() {
		return lng;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return lat;
	}
}
