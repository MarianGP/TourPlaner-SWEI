package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class RouteError {

	@SerializedName("errorCode")
	private int errorCode;

	@SerializedName("message")
	private String message;

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
