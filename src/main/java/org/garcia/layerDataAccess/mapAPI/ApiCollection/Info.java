package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Info {

	@SerializedName("statuscode")
	private int statuscode;

	@SerializedName("copyright")
	private Copyright copyright;

	@SerializedName("messages")
	private List<Object> messages;

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public int getStatuscode() {
		return statuscode;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
	}

	public Copyright getCopyright() {
		return copyright;
	}

	public void setMessages(List<Object> messages) {
		this.messages = messages;
	}

	public List<Object> getMessages() {
		return messages;
	}
}
