package com.ryaltech.whitewater.gauges.server;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class GaugeValueServiceImplTest extends TestCase {

	public void testGetGaugeValues() throws Exception{
		System.out.println(System.getProperty("java.net.useSystemProxies"));
		
		List<String> ids = new ArrayList<String>();
		ids.add("02HB004");
		for(GaugeValue gv:new GaugeValuesServiceImpl().getGaugeValues(ids)){
			System.out.println(gv.getGaugeId()+":"+gv.getLevel()+":"+gv.getLastUpdated());
		}

	}

}
