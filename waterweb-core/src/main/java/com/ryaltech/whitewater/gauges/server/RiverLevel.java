package com.ryaltech.whitewater.gauges.server;

import java.util.Date;

public class RiverLevel implements Comparable<RiverLevel>{
	
	private String gaugeId;
	private float level;
	private Date lastUpdated;
	public String getGaugeId() {
		return gaugeId;
	}
	public RiverLevel setGaugeId(String gaugeId) {
		this.gaugeId = gaugeId;
		return this;
	}
	public float getLevel() {
		return level;
	}
	public RiverLevel setLevel(float level) {
		this.level = level;
		return this;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public RiverLevel setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
	
	public String toString(){
		return String.format("Gauge: %s at %s was: %s", gaugeId, lastUpdated, level);
	}
	@Override
	public int compareTo(RiverLevel o) {
		return lastUpdated.compareTo(o.lastUpdated);
	}

}
