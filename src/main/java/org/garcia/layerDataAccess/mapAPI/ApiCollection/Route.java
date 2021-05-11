package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {

	@SerializedName("hasTollRoad")
	private boolean hasTollRoad;

	@SerializedName("hasBridge")
	private boolean hasBridge;

	@SerializedName("boundingBox")
	private BoundingBox boundingBox;

	@SerializedName("distance")
	private double distance;

	@SerializedName("hasTimedRestriction")
	private boolean hasTimedRestriction;

	@SerializedName("hasTunnel")
	private boolean hasTunnel;

	@SerializedName("hasHighway")
	private boolean hasHighway;

	@SerializedName("computedWaypoints")
	private List<Object> computedWaypoints;

	@SerializedName("routeError")
	private RouteError routeError;

	@SerializedName("formattedTime")
	private String formattedTime;

	@SerializedName("sessionId")
	private String sessionId;

	@SerializedName("hasAccessRestriction")
	private boolean hasAccessRestriction;

	@SerializedName("realTime")
	private int realTime;

	@SerializedName("hasSeasonalClosure")
	private boolean hasSeasonalClosure;

	@SerializedName("hasCountryCross")
	private boolean hasCountryCross;

	@SerializedName("fuelUsed")
	private double fuelUsed;

	@SerializedName("legs")
	private List<LegsItem> legs;

	@SerializedName("options")
	private Options options;

	@SerializedName("locations")
	private List<LocationsItem> locations;

	@SerializedName("time")
	private int time;

	@SerializedName("hasUnpaved")
	private boolean hasUnpaved;

	@SerializedName("locationSequence")
	private List<Integer> locationSequence;

	@SerializedName("hasFerry")
	private boolean hasFerry;

	public void setHasTollRoad(boolean hasTollRoad) {
		this.hasTollRoad = hasTollRoad;
	}

	public boolean isHasTollRoad() {
		return hasTollRoad;
	}

	public void setHasBridge(boolean hasBridge) {
		this.hasBridge = hasBridge;
	}

	public boolean isHasBridge() {
		return hasBridge;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setHasTimedRestriction(boolean hasTimedRestriction) {
		this.hasTimedRestriction = hasTimedRestriction;
	}

	public boolean isHasTimedRestriction() {
		return hasTimedRestriction;
	}

	public void setHasTunnel(boolean hasTunnel) {
		this.hasTunnel = hasTunnel;
	}

	public boolean isHasTunnel() {
		return hasTunnel;
	}

	public void setHasHighway(boolean hasHighway) {
		this.hasHighway = hasHighway;
	}

	public boolean isHasHighway() {
		return hasHighway;
	}

	public void setComputedWaypoints(List<Object> computedWaypoints) {
		this.computedWaypoints = computedWaypoints;
	}

	public List<Object> getComputedWaypoints() {
		return computedWaypoints;
	}

	public void setRouteError(RouteError routeError) {
		this.routeError = routeError;
	}

	public RouteError getRouteError() {
		return routeError;
	}

	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}

	public String getFormattedTime() {
		return formattedTime;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setHasAccessRestriction(boolean hasAccessRestriction) {
		this.hasAccessRestriction = hasAccessRestriction;
	}

	public boolean isHasAccessRestriction() {
		return hasAccessRestriction;
	}

	public void setRealTime(int realTime) {
		this.realTime = realTime;
	}

	public int getRealTime() {
		return realTime;
	}

	public void setHasSeasonalClosure(boolean hasSeasonalClosure) {
		this.hasSeasonalClosure = hasSeasonalClosure;
	}

	public boolean isHasSeasonalClosure() {
		return hasSeasonalClosure;
	}

	public void setHasCountryCross(boolean hasCountryCross) {
		this.hasCountryCross = hasCountryCross;
	}

	public boolean isHasCountryCross() {
		return hasCountryCross;
	}

	public void setFuelUsed(double fuelUsed) {
		this.fuelUsed = fuelUsed;
	}

	public double getFuelUsed() {
		return fuelUsed;
	}

	public void setLegs(List<LegsItem> legs) {
		this.legs = legs;
	}

	public List<LegsItem> getLegs() {
		return legs;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public Options getOptions() {
		return options;
	}

	public void setLocations(List<LocationsItem> locations) {
		this.locations = locations;
	}

	public List<LocationsItem> getLocations() {
		return locations;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void setHasUnpaved(boolean hasUnpaved) {
		this.hasUnpaved = hasUnpaved;
	}

	public boolean isHasUnpaved() {
		return hasUnpaved;
	}

	public void setLocationSequence(List<Integer> locationSequence) {
		this.locationSequence = locationSequence;
	}

	public List<Integer> getLocationSequence() {
		return locationSequence;
	}

	public void setHasFerry(boolean hasFerry) {
		this.hasFerry = hasFerry;
	}

	public boolean isHasFerry() {
		return hasFerry;
	}
}
