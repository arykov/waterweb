package com.ryaltech.whitewater.gauges.server;

import java.util.Date;

public class GaugeValue {
	
	private String gaugeId;
	private float level;
	private Date lastUpdated;
	public String getGaugeId() {
		return gaugeId;
	}
	public void setGaugeId(String gaugeId) {
		this.gaugeId = gaugeId;
	}
	public float getLevel() {
		return level;
	}
	public void setLevel(float level) {
		this.level = level;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
