package com.ryaltech.whitewater.gauges.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RiverInfo {
	@Id	
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
	public RiverInfo setRiverName(String riverName) {
		this.riverName = riverName;
		return this;
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
