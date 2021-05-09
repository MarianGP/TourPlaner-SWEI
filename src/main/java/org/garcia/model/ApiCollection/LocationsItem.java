package org.garcia.model.ApiCollection;

import com.google.gson.annotations.SerializedName;

public class LocationsItem{

	@SerializedName("dragPoint")
	private boolean dragPoint;

	@SerializedName("displayLatLng")
	private DisplayLatLng displayLatLng;

	@SerializedName("adminArea4")
	private String adminArea4;

	@SerializedName("adminArea5")
	private String adminArea5;

	@SerializedName("postalCode")
	private String postalCode;

	@SerializedName("adminArea1")
	private String adminArea1;

	@SerializedName("adminArea3")
	private String adminArea3;

	@SerializedName("type")
	private String type;

	@SerializedName("sideOfStreet")
	private String sideOfStreet;

	@SerializedName("geocodeQualityCode")
	private String geocodeQualityCode;

	@SerializedName("adminArea4Type")
	private String adminArea4Type;

	@SerializedName("linkId")
	private int linkId;

	@SerializedName("street")
	private String street;

	@SerializedName("adminArea5Type")
	private String adminArea5Type;

	@SerializedName("geocodeQuality")
	private String geocodeQuality;

	@SerializedName("adminArea1Type")
	private String adminArea1Type;

	@SerializedName("adminArea3Type")
	private String adminArea3Type;

	@SerializedName("latLng")
	private LatLng latLng;

	public void setDragPoint(boolean dragPoint){
		this.dragPoint = dragPoint;
	}

	public boolean isDragPoint(){
		return dragPoint;
	}

	public void setDisplayLatLng(DisplayLatLng displayLatLng){
		this.displayLatLng = displayLatLng;
	}

	public DisplayLatLng getDisplayLatLng(){
		return displayLatLng;
	}

	public void setAdminArea4(String adminArea4){
		this.adminArea4 = adminArea4;
	}

	public String getAdminArea4(){
		return adminArea4;
	}

	public void setAdminArea5(String adminArea5){
		this.adminArea5 = adminArea5;
	}

	public String getAdminArea5(){
		return adminArea5;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public void setAdminArea1(String adminArea1){
		this.adminArea1 = adminArea1;
	}

	public String getAdminArea1(){
		return adminArea1;
	}

	public void setAdminArea3(String adminArea3){
		this.adminArea3 = adminArea3;
	}

	public String getAdminArea3(){
		return adminArea3;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setSideOfStreet(String sideOfStreet){
		this.sideOfStreet = sideOfStreet;
	}

	public String getSideOfStreet(){
		return sideOfStreet;
	}

	public void setGeocodeQualityCode(String geocodeQualityCode){
		this.geocodeQualityCode = geocodeQualityCode;
	}

	public String getGeocodeQualityCode(){
		return geocodeQualityCode;
	}

	public void setAdminArea4Type(String adminArea4Type){
		this.adminArea4Type = adminArea4Type;
	}

	public String getAdminArea4Type(){
		return adminArea4Type;
	}

	public void setLinkId(int linkId){
		this.linkId = linkId;
	}

	public int getLinkId(){
		return linkId;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setAdminArea5Type(String adminArea5Type){
		this.adminArea5Type = adminArea5Type;
	}

	public String getAdminArea5Type(){
		return adminArea5Type;
	}

	public void setGeocodeQuality(String geocodeQuality){
		this.geocodeQuality = geocodeQuality;
	}

	public String getGeocodeQuality(){
		return geocodeQuality;
	}

	public void setAdminArea1Type(String adminArea1Type){
		this.adminArea1Type = adminArea1Type;
	}

	public String getAdminArea1Type(){
		return adminArea1Type;
	}

	public void setAdminArea3Type(String adminArea3Type){
		this.adminArea3Type = adminArea3Type;
	}

	public String getAdminArea3Type(){
		return adminArea3Type;
	}

	public void setLatLng(LatLng latLng){
		this.latLng = latLng;
	}

	public LatLng getLatLng(){
		return latLng;
	}
}