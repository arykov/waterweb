package com.ryaltech.whitewater.gauges.services;


import com.google.appengine.api.taskqueue.RetryOptions;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class GoogleScheduler implements Scheduler {

	@Override
	public void scheduleRiverLevelUpdate(RiverInfo ri) {
		Queue q = QueueFactory.getQueue("scheduleQueue");
		
		
		
		q.add(TaskOptions.Builder.withUrl(String.format(
				"/services/updateRiverLevel/%s", ri.getRiverId()))/*.method(TaskOptions.Method.GET)*/);

	}

}
