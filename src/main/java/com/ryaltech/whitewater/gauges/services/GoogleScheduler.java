package com.ryaltech.whitewater.gauges.services;


import com.google.appengine.api.taskqueue.RetryOptions;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.ryaltech.whitewater.gauges.model.RiverInfo;

public class GoogleScheduler implements Scheduler {

	@Override
	public void scheduleRiverLevelUpdate(RiverInfo ri) {
		Queue q = QueueFactory.getDefaultQueue();//getQueue("scheduleQueue");
		
		RetryOptions ro = RetryOptions.Builder.withTaskRetryLimit(2);
		
		q.add(TaskOptions.Builder.withUrl(String.format(
				"/services/updateRiverLevel/%s", ri.getRiverId())).retryOptions(ro)/*.method(TaskOptions.Method.GET)*/);

	}

}
