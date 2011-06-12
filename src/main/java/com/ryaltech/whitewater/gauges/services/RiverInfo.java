package com.ryaltech.whitewater.gauges.services;

public class RiverInfo {
	private String riverId;
	private String riverName;
	private String gaugeDataCollectorId;	
	private String gaugeId;	
	private float lowLevel;
	private float mediumLevel;
	private float highLevel;
	public String getRiverId() {
		return riverId;
	}
	public RiverInfo setRiverId(String riverId) {
		this.riverId = riverId;
		return this;
	}
	public String getRiverName() {
		return riverName;
	}
	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}
	public String getGaugeDataCollectorId() {
		return gaugeDataCollectorId;
	}
	public RiverInfo setGaugeDataCollectorId(String gaugeDataCollectorId) {
		this.gaugeDataCollectorId = gaugeDataCollectorId;
		return this;
	}
	public String getGaugeId() {
		return gaugeId;
	}
	public RiverInfo setGaugeId(String gaugeId) {
		this.gaugeId = gaugeId;
		return this;
	}
	
	public float getLowLevel() {
		return lowLevel;
	}
	public RiverInfo setLowLevel(float lowLevel) {
		this.lowLevel = lowLevel;
		return this;
	}
	public float getMediumLevel() {
		return mediumLevel;
	}
	public RiverInfo setMediumLevel(float mediumLevel) {
		this.mediumLevel = mediumLevel;
		return this;
	}
	public float getHighLevel() {
		return highLevel;
	}
	public RiverInfo setHighLevel(float highLevel) {
		this.highLevel = highLevel;
		return this;
	}
	
	
	
}
