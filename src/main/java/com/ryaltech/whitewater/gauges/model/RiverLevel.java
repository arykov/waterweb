package com.ryaltech.whitewater.gauges.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RiverLevel implements Comparable<RiverLevel>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private RiverInfo riverInfo;
	private float level;
	private Date lastUpdated;
	
	public RiverInfo getRiverInfo() {
		return riverInfo;
	}
	public RiverLevel setRiverInfo(RiverInfo riverInfo) {
		this.riverInfo = riverInfo;
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
		return String.format("Gauge: %s at %s was: %s", riverInfo.getRiverId(), lastUpdated, level);
	}
	@Override
	public int compareTo(RiverLevel o) {
		return lastUpdated.compareTo(o.lastUpdated);
	}

}
