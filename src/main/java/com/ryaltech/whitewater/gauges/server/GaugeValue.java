package com.ryaltech.whitewater.gauges.server;

import java.util.Date;

public class GaugeValue {
	
	private String gaugeId;
	private float level;
	private Date lastUpdated;
	public String getGaugeId() {
		return gaugeId;
	}
	public GaugeValue setGaugeId(String gaugeId) {
		this.gaugeId = gaugeId;
		return this;
	}
	public float getLevel() {
		return level;
	}
	public GaugeValue setLevel(float level) {
		this.level = level;
		return this;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public GaugeValue setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
	
	public String toString(){
		return String.format("Gauge: %s at %s was: %s", gaugeId, lastUpdated, level);
	}

}
