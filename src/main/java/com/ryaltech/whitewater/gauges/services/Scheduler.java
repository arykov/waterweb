package com.ryaltech.whitewater.gauges.services;

import com.ryaltech.whitewater.gauges.model.RiverInfo;

public interface Scheduler {
	public void scheduleRiverLevelUpdate(RiverInfo ri);

}
