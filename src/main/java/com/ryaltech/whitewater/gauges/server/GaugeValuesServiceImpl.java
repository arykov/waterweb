package com.ryaltech.whitewater.gauges.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GaugeValuesServiceImpl implements GaugeValuesService {

	public Date yesterday() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -1);
		return calendar.getTime();

	}

	public Date tomorrow() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		return calendar.getTime();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ryaltech.whitewater.gauges.server.GaugeValuesService#getGaugeValues
	 * (java.util.List)
	 */
	public List<GaugeValue> getGaugeValues(List<String> gaugeIds)
			throws Exception {
		List<GaugeValue> gaugeValues = new ArrayList<GaugeValue>();
		for (String gaugeId : gaugeIds) {
			Date tomorrow = tomorrow();
			Date yesterday = yesterday();

			String referer = String
					.format("http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=%s&syr=%d&smo=%d&sday=%d&eyr=%d&emo=%d&eday=%d&prm1=6",
							gaugeId, yesterday.getYear() + 1900,
							yesterday.getMonth() + 1, yesterday.getDate(),
							tomorrow.getYear() + 1900, tomorrow.getMonth() + 1,
							tomorrow.getDate());
			// referer="http://www.wateroffice.ec.gc.ca/graph/graph_e.html?mode=text&stn=02HB004&syr=2010&smo=09&sday=25&eyr=2010&emo=09&eday=27&prm1=6";
			URL url = new URL(
					"http://www.wateroffice.ec.gc.ca/include/disclaimer.php");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();

			
			httpURLConnection
					.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
			httpURLConnection.setRequestProperty("Referer", referer);
			httpURLConnection.setInstanceFollowRedirects(false);
			
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);

			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			StringBuffer sbContent = new StringBuffer();
			sbContent.append("disclaimer_action=");
			sbContent.append(URLEncoder.encode("I Agree", "UTF-8"));
			DataOutputStream stream = new DataOutputStream(
					httpURLConnection.getOutputStream());
			stream.writeBytes(sbContent.toString());
			stream.flush();
			stream.close();

			if(httpURLConnection.getResponseCode() != 302){
				throw new RuntimeException("Unexpected HTTP response from ");
			}else{
				String newPageUri = httpURLConnection.getHeaderField("Location");
				if(newPageUri == null){
					//log warning
					newPageUri = referer;
				}
				List<String>cookies = httpURLConnection.getHeaderFields().get("Set-Cookie");
				httpURLConnection.disconnect();
				
				httpURLConnection = (HttpURLConnection)new URL(newPageUri).openConnection();
				StringBuffer cookieBuffer = new StringBuffer();
				for(String cookie:cookies){
					String chunks[]=cookie.split(";");
					if(cookieBuffer.length()>0)cookieBuffer.append("; ");
					cookieBuffer.append(chunks[0]);				  
				}
				httpURLConnection.setRequestProperty("Cookie", cookieBuffer.toString());
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setDoOutput(false);
				httpURLConnection.setDoInput(true);
				InputStream inputStream = httpURLConnection.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						inputStream));
				String line = null;
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
				inputStream.close();
				
			}
			
			
			
/*			

			DefaultHttpClient httpClient = new DefaultHttpClient();
			ProxySelectorRoutePlanner routePlanner = new ProxySelectorRoutePlanner(
					httpClient.getConnectionManager().getSchemeRegistry(),
					ProxySelector.getDefault());
			httpClient.setRoutePlanner(routePlanner);
			httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {
				public boolean isRedirected(final HttpRequest request,
						final HttpResponse response, final HttpContext context)
						throws ProtocolException {
					boolean isDefaultRedirected = super.isRedirected(request,
							response, context);
					if (!isDefaultRedirected) {
						int statusCode = response.getStatusLine()
								.getStatusCode();
						// redirect regardless of method
						if (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
							return true;
					}
					return isDefaultRedirected;
				}

			});

			HttpPost method = new HttpPost(
					"http://www.wateroffice.ec.gc.ca/include/disclaimer.php");

			method.addHeader("Referer", referer);
			List<NameValuePair> requestParams = new ArrayList<org.apache.http.NameValuePair>();
			requestParams.add(new BasicNameValuePair("disclaimer_action",
					"I Agree"));
			method.setEntity(new UrlEncodedFormEntity(requestParams));

			HttpResponse response = httpClient.execute(method);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

				builder.setEntityResolver(new EntityResolver() {

					@Override
					public InputSource resolveEntity(String publicId,
							String systemId) throws SAXException, IOException {
						System.out.println("Ignoring " + publicId + ", "
								+ systemId);
						return new InputSource(new StringReader(""));
					}
				});
				Document doc = builder.parse(response.getEntity().getContent());

				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();
				// XPathExpression expr =
				// xpath.compile("//table[@id=\"dataTable\"]//tr/td");
				XPathExpression expr = xpath
						.compile("/html/body/div/div/div[6]/div[2]/div[2]/form[3]/div/div[2]/table/tbody/tr[last()]/td/text()");
				NodeList nodeList = (NodeList) expr.evaluate(doc,
						XPathConstants.NODESET);
				if (nodeList != null && nodeList.getLength() == 2) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String date = nodeList.item(0).getNodeValue();
					String value = nodeList.item(1).getNodeValue();
					System.out.println(sdf.parse(date));
					System.out.println(value);
				}

			}
			*/

		}

		return gaugeValues;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ryaltech.whitewater.gauges.server.GaugeValuesService#
	 * getRunnableConditions(java.util.List)
	 */
	public List<RunnableConditions> getRunnableConditions(List<String> gaugeIds) {
		return null;
	}

}
