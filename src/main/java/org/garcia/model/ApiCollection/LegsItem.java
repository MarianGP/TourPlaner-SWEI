package org.garcia.model.ApiCollection;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LegsItem{

	@SerializedName("hasTollRoad")
	private boolean hasTollRoad;

	@SerializedName("hasBridge")
	private boolean hasBridge;

	@SerializedName("destNarrative")
	private String destNarrative;

	@SerializedName("distance")
	private double distance;

	@SerializedName("hasTimedRestriction")
	private boolean hasTimedRestriction;

	@SerializedName("hasTunnel")
	private boolean hasTunnel;

	@SerializedName("hasHighway")
	private boolean hasHighway;

	@SerializedName("index")
	private int index;

	@SerializedName("formattedTime")
	private String formattedTime;

	@SerializedName("origIndex")
	private int origIndex;

	@SerializedName("hasAccessRestriction")
	private boolean hasAccessRestriction;

	@SerializedName("hasSeasonalClosure")
	private boolean hasSeasonalClosure;

	@SerializedName("hasCountryCross")
	private boolean hasCountryCross;

	@SerializedName("roadGradeStrategy")
	private List<Object> roadGradeStrategy;

	@SerializedName("destIndex")
	private int destIndex;

	@SerializedName("time")
	private int time;

	@SerializedName("hasUnpaved")
	private boolean hasUnpaved;

	@SerializedName("origNarrative")
	private String origNarrative;

	@SerializedName("maneuvers")
	private List<ManeuversItem> maneuvers;

	@SerializedName("hasFerry")
	private boolean hasFerry;

	public void setHasTollRoad(boolean hasTollRoad){
		this.hasTollRoad = hasTollRoad;
	}

	public boolean isHasTollRoad(){
		return hasTollRoad;
	}

	public void setHasBridge(boolean hasBridge){
		this.hasBridge = hasBridge;
	}

	public boolean isHasBridge(){
		return hasBridge;
	}

	public void setDestNarrative(String destNarrative){
		this.destNarrative = destNarrative;
	}

	public String getDestNarrative(){
		return destNarrative;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setHasTimedRestriction(boolean hasTimedRestriction){
		this.hasTimedRestriction = hasTimedRestriction;
	}

	public boolean isHasTimedRestriction(){
		return hasTimedRestriction;
	}

	public void setHasTunnel(boolean hasTunnel){
		this.hasTunnel = hasTunnel;
	}

	public boolean isHasTunnel(){
		return hasTunnel;
	}

	public void setHasHighway(boolean hasHighway){
		this.hasHighway = hasHighway;
	}

	public boolean isHasHighway(){
		return hasHighway;
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

	public void setOrigIndex(int origIndex){
		this.origIndex = origIndex;
	}

	public int getOrigIndex(){
		return origIndex;
	}

	public void setHasAccessRestriction(boolean hasAccessRestriction){
		this.hasAccessRestriction = hasAccessRestriction;
	}

	public boolean isHasAccessRestriction(){
		return hasAccessRestriction;
	}

	public void setHasSeasonalClosure(boolean hasSeasonalClosure){
		this.hasSeasonalClosure = hasSeasonalClosure;
	}

	public boolean isHasSeasonalClosure(){
		return hasSeasonalClosure;
	}

	public void setHasCountryCross(boolean hasCountryCross){
		this.hasCountryCross = hasCountryCross;
	}

	public boolean isHasCountryCross(){
		return hasCountryCross;
	}

	public void setRoadGradeStrategy(List<Object> roadGradeStrategy){
		this.roadGradeStrategy = roadGradeStrategy;
	}

	public List<Object> getRoadGradeStrategy(){
		return roadGradeStrategy;
	}

	public void setDestIndex(int destIndex){
		this.destIndex = destIndex;
	}

	public int getDestIndex(){
		return destIndex;
	}

	public void setTime(int time){
		this.time = time;
	}

	public int getTime(){
		return time;
	}

	public void setHasUnpaved(boolean hasUnpaved){
		this.hasUnpaved = hasUnpaved;
	}

	public boolean isHasUnpaved(){
		return hasUnpaved;
	}

	public void setOrigNarrative(String origNarrative){
		this.origNarrative = origNarrative;
	}

	public String getOrigNarrative(){
		return origNarrative;
	}

	public void setManeuvers(List<ManeuversItem> maneuvers){
		this.maneuvers = maneuvers;
	}

	public List<ManeuversItem> getManeuvers(){
		return maneuvers;
	}

	public void setHasFerry(boolean hasFerry){
		this.hasFerry = hasFerry;
	}

	public boolean isHasFerry(){
		return hasFerry;
	}
}