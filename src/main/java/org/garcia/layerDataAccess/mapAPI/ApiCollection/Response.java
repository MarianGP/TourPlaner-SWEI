package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class Response {

	@SerializedName("route")
	private Route route;

	@SerializedName("info")
	private Info info;

	public void setRoute(Route route) {
		this.route = route;
	}

	public Route getRoute() {
		return route;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public Info getInfo() {
		return info;
	}
}
