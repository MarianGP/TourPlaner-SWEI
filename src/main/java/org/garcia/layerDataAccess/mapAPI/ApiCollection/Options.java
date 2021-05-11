package org.garcia.layerDataAccess.mapAPI.ApiCollection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Options {

	@SerializedName("arteryWeights")
	private List<Object> arteryWeights;

	@SerializedName("cyclingRoadFactor")
	private int cyclingRoadFactor;

	@SerializedName("timeType")
	private int timeType;

	@SerializedName("useTraffic")
	private boolean useTraffic;

	@SerializedName("returnLinkDirections")
	private boolean returnLinkDirections;

	@SerializedName("countryBoundaryDisplay")
	private boolean countryBoundaryDisplay;

	@SerializedName("enhancedNarrative")
	private boolean enhancedNarrative;

	@SerializedName("locale")
	private String locale;

	@SerializedName("tryAvoidLinkIds")
	private List<Object> tryAvoidLinkIds;

	@SerializedName("drivingStyle")
	private int drivingStyle;

	@SerializedName("doReverseGeocode")
	private boolean doReverseGeocode;

	@SerializedName("generalize")
	private int generalize;

	@SerializedName("mustAvoidLinkIds")
	private List<Object> mustAvoidLinkIds;

	@SerializedName("sideOfStreetDisplay")
	private boolean sideOfStreetDisplay;

	@SerializedName("routeType")
	private String routeType;

	@SerializedName("avoidTimedConditions")
	private boolean avoidTimedConditions;

	@SerializedName("routeNumber")
	private int routeNumber;

	@SerializedName("shapeFormat")
	private String shapeFormat;

	@SerializedName("maxWalkingDistance")
	private int maxWalkingDistance;

	@SerializedName("destinationManeuverDisplay")
	private boolean destinationManeuverDisplay;

	@SerializedName("transferPenalty")
	private int transferPenalty;

	@SerializedName("narrativeType")
	private String narrativeType;

	@SerializedName("walkingSpeed")
	private int walkingSpeed;

	@SerializedName("urbanAvoidFactor")
	private int urbanAvoidFactor;

	@SerializedName("stateBoundaryDisplay")
	private boolean stateBoundaryDisplay;

	@SerializedName("unit")
	private String unit;

	@SerializedName("highwayEfficiency")
	private int highwayEfficiency;

	@SerializedName("maxLinkId")
	private int maxLinkId;

	@SerializedName("maneuverPenalty")
	private int maneuverPenalty;

	@SerializedName("avoidTripIds")
	private List<Object> avoidTripIds;

	@SerializedName("filterZoneFactor")
	private int filterZoneFactor;

	@SerializedName("manmaps")
	private String manmaps;

	public void setArteryWeights(List<Object> arteryWeights) {
		this.arteryWeights = arteryWeights;
	}

	public List<Object> getArteryWeights() {
		return arteryWeights;
	}

	public void setCyclingRoadFactor(int cyclingRoadFactor) {
		this.cyclingRoadFactor = cyclingRoadFactor;
	}

	public int getCyclingRoadFactor() {
		return cyclingRoadFactor;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public int getTimeType() {
		return timeType;
	}

	public void setUseTraffic(boolean useTraffic) {
		this.useTraffic = useTraffic;
	}

	public boolean isUseTraffic() {
		return useTraffic;
	}

	public void setReturnLinkDirections(boolean returnLinkDirections) {
		this.returnLinkDirections = returnLinkDirections;
	}

	public boolean isReturnLinkDirections() {
		return returnLinkDirections;
	}

	public void setCountryBoundaryDisplay(boolean countryBoundaryDisplay) {
		this.countryBoundaryDisplay = countryBoundaryDisplay;
	}

	public boolean isCountryBoundaryDisplay() {
		return countryBoundaryDisplay;
	}

	public void setEnhancedNarrative(boolean enhancedNarrative) {
		this.enhancedNarrative = enhancedNarrative;
	}

	public boolean isEnhancedNarrative() {
		return enhancedNarrative;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLocale() {
		return locale;
	}

	public void setTryAvoidLinkIds(List<Object> tryAvoidLinkIds) {
		this.tryAvoidLinkIds = tryAvoidLinkIds;
	}

	public List<Object> getTryAvoidLinkIds() {
		return tryAvoidLinkIds;
	}

	public void setDrivingStyle(int drivingStyle) {
		this.drivingStyle = drivingStyle;
	}

	public int getDrivingStyle() {
		return drivingStyle;
	}

	public void setDoReverseGeocode(boolean doReverseGeocode) {
		this.doReverseGeocode = doReverseGeocode;
	}

	public boolean isDoReverseGeocode() {
		return doReverseGeocode;
	}

	public void setGeneralize(int generalize) {
		this.generalize = generalize;
	}

	public int getGeneralize() {
		return generalize;
	}

	public void setMustAvoidLinkIds(List<Object> mustAvoidLinkIds) {
		this.mustAvoidLinkIds = mustAvoidLinkIds;
	}

	public List<Object> getMustAvoidLinkIds() {
		return mustAvoidLinkIds;
	}

	public void setSideOfStreetDisplay(boolean sideOfStreetDisplay) {
		this.sideOfStreetDisplay = sideOfStreetDisplay;
	}

	public boolean isSideOfStreetDisplay() {
		return sideOfStreetDisplay;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setAvoidTimedConditions(boolean avoidTimedConditions) {
		this.avoidTimedConditions = avoidTimedConditions;
	}

	public boolean isAvoidTimedConditions() {
		return avoidTimedConditions;
	}

	public void setRouteNumber(int routeNumber) {
		this.routeNumber = routeNumber;
	}

	public int getRouteNumber() {
		return routeNumber;
	}

	public void setShapeFormat(String shapeFormat) {
		this.shapeFormat = shapeFormat;
	}

	public String getShapeFormat() {
		return shapeFormat;
	}

	public void setMaxWalkingDistance(int maxWalkingDistance) {
		this.maxWalkingDistance = maxWalkingDistance;
	}

	public int getMaxWalkingDistance() {
		return maxWalkingDistance;
	}

	public void setDestinationManeuverDisplay(boolean destinationManeuverDisplay) {
		this.destinationManeuverDisplay = destinationManeuverDisplay;
	}

	public boolean isDestinationManeuverDisplay() {
		return destinationManeuverDisplay;
	}

	public void setTransferPenalty(int transferPenalty) {
		this.transferPenalty = transferPenalty;
	}

	public int getTransferPenalty() {
		return transferPenalty;
	}

	public void setNarrativeType(String narrativeType) {
		this.narrativeType = narrativeType;
	}

	public String getNarrativeType() {
		return narrativeType;
	}

	public void setWalkingSpeed(int walkingSpeed) {
		this.walkingSpeed = walkingSpeed;
	}

	public int getWalkingSpeed() {
		return walkingSpeed;
	}

	public void setUrbanAvoidFactor(int urbanAvoidFactor) {
		this.urbanAvoidFactor = urbanAvoidFactor;
	}

	public int getUrbanAvoidFactor() {
		return urbanAvoidFactor;
	}

	public void setStateBoundaryDisplay(boolean stateBoundaryDisplay) {
		this.stateBoundaryDisplay = stateBoundaryDisplay;
	}

	public boolean isStateBoundaryDisplay() {
		return stateBoundaryDisplay;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public void setHighwayEfficiency(int highwayEfficiency) {
		this.highwayEfficiency = highwayEfficiency;
	}

	public int getHighwayEfficiency() {
		return highwayEfficiency;
	}

	public void setMaxLinkId(int maxLinkId) {
		this.maxLinkId = maxLinkId;
	}

	public int getMaxLinkId() {
		return maxLinkId;
	}

	public void setManeuverPenalty(int maneuverPenalty) {
		this.maneuverPenalty = maneuverPenalty;
	}

	public int getManeuverPenalty() {
		return maneuverPenalty;
	}

	public void setAvoidTripIds(List<Object> avoidTripIds) {
		this.avoidTripIds = avoidTripIds;
	}

	public List<Object> getAvoidTripIds() {
		return avoidTripIds;
	}

	public void setFilterZoneFactor(int filterZoneFactor) {
		this.filterZoneFactor = filterZoneFactor;
	}

	public int getFilterZoneFactor() {
		return filterZoneFactor;
	}

	public void setManmaps(String manmaps) {
		this.manmaps = manmaps;
	}

	public String getManmaps() {
		return manmaps;
	}
}
