package com.ryaltech.whitewater.gauges.server;
import org.apache.http.client.RedirectHandler;
import org.apache.http.impl.client.DefaultHttpClient;


public class RelaxedHttpClient extends DefaultHttpClient {
	@Override
	protected RedirectHandler createRedirectHandler() {
        return new RelaxedRedirectHandler();
    }



}
