package org.garcia.model.ApiCollection;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ManeuversItem{

	@SerializedName("distance")
	private double distance;

	@SerializedName("streets")
	private List<String> streets;

	@SerializedName("narrative")
	private String narrative;

	@SerializedName("turnType")
	private int turnType;

	@SerializedName("startPoint")
	private StartPoint startPoint;

	@SerializedName("index")
	private int index;

	@SerializedName("formattedTime")
	private String formattedTime;

	@SerializedName("directionName")
	private String directionName;

	@SerializedName("maneuverNotes")
	private List<Object> maneuverNotes;

	@SerializedName("linkIds")
	private List<Object> linkIds;

	@SerializedName("signs")
	private List<Object> signs;

	@SerializedName("transportMode")
	private String transportMode;

	@SerializedName("attributes")
	private int attributes;

	@SerializedName("time")
	private int time;

	@SerializedName("iconUrl")
	private String iconUrl;

	@SerializedName("direction")
	private int direction;

	@SerializedName("mapUrl")
	private String mapUrl;

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setStreets(List<String> streets){
		this.streets = streets;
	}

	public List<String> getStreets(){
		return streets;
	}

	public void setNarrative(String narrative){
		this.narrative = narrative;
	}

	public String getNarrative(){
		return narrative;
	}

	public void setTurnType(int turnType){
		this.turnType = turnType;
	}

	public int getTurnType(){
		return turnType;
	}

	public void setStartPoint(StartPoint startPoint){
		this.startPoint = startPoint;
	}

	public StartPoint getStartPoint(){
		return startPoint;
	}

	public void setIndex(int index){
		this.index = index;
	}

	public int getIndex(){
		return index;
	}

	public void setFormattedTime(String formattedTime){
		this.formattedTime = formattedTime;
	}

	public String getFormattedTime(){
		return formattedTime;
	}

	public void setDirectionName(String directionName){
		this.directionName = directionName;
	}

	public String getDirectionName(){
		return directionName;
	}

	public void setManeuverNotes(List<Object> maneuverNotes){
		this.maneuverNotes = maneuverNotes;
	}

	public List<Object> getManeuverNotes(){
		return maneuverNotes;
	}

	public void setLinkIds(List<Object> linkIds){
		this.linkIds = linkIds;
	}

	public List<Object> getLinkIds(){
		return linkIds;
	}

	public void setSigns(List<Object> signs){
		this.signs = signs;
	}

	public List<Object> getSigns(){
		return signs;
	}

	public void setTransportMode(String transportMode){
		this.transportMode = transportMode;
	}

	public String getTransportMode(){
		return transportMode;
	}

	public void setAttributes(int attributes){
		this.attributes = attributes;
	}

	public int getAttributes(){
		return attributes;
	}

	public void setTime(int time){
		this.time = time;
	}

	public int getTime(){
		return time;
	}

	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	public String getIconUrl(){
		return iconUrl;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}

	public int getDirection(){
		return direction;
	}

	public void setMapUrl(String mapUrl){
		this.mapUrl = mapUrl;
	}

	public String getMapUrl(){
		return mapUrl;
	}
}