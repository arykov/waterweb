package com.ryaltech.whitewater.gauges.server;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;





/**
 * Unlike its parent this one ignores the request method. This way redirects work for POST.
 * @author arykov
 *
 */
public class RelaxedRedirectHandler extends DefaultRedirectHandler {
	public boolean isRedirectRequested(
            final HttpResponse response,
            final HttpContext context) {
        if (response == null) {
            throw new IllegalArgumentException("HTTP response may not be null");
        }
        
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
        case HttpStatus.SC_MOVED_TEMPORARILY:
        case HttpStatus.SC_MOVED_PERMANENTLY:
        case HttpStatus.SC_TEMPORARY_REDIRECT:
        case HttpStatus.SC_SEE_OTHER:
            return true;
        default:
            return false;
        } //end of switch
    }


}
