package com.imaginea.cloudjee.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestApiCall {


	private final static JSONParser JSON_PARSER = new JSONParser();


	public  JSONObject getJSONResponse(String itemDeltaURL) throws ParseException {
		String response = null;

		for (int i = 0; i < 1; i++) {
			try {
				response = executeGet(itemDeltaURL);
				break;
			} catch (IOException e) {

			}
		}
		
		JSONObject jsonObject = (JSONObject) JSON_PARSER.parse(response);
		return jsonObject;
	}

	@SuppressWarnings("deprecation")
	public  String executeGet(String urlStr) throws IOException {
		BufferedReader reader = null;
		StringBuilder responseStr = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("auth_cookie", "WBXLtD0FvxK5RyhCN4y1ZktG3eb8944a7c839_V2");
			conn.setRequestProperty("JSESSIONID", "cloudserver-2344630359635079597");
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestMethod("GET");
	         /*conn.setDoInput(true);
	         conn.setDoOutput(true);*/
			/*conn.setRequestProperty("Cookie", "auth_cookie=8JJGnFT9JKPJDYcnY8ndH5pl3eb8944a7c832_V2");*/
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			System.out.println(reader.readLine());
			String line;
			while ((line = reader.readLine()) != null) {
				responseStr.append(line);
				responseStr.append("\n");
			}
		} catch (MalformedURLException exception) {

			throw exception;
		} catch (IOException exception) {

			throw exception;
		} finally {
			if (null != reader){
				reader.close();
				conn.disconnect();
			}
		}
		return responseStr.toString();
	}
	
	public static void main(String[] args) throws ParseException {
		new RestApiCall().getJSONResponse("https://apps.mywavemaker.com/userportal/rest/applicationService/listApps");
		 /*HttpHost targetHost = new HttpHost(hostname, AuthScope.ANY_PORT, AconexAPIs.HTTPS); 
         DefaultHttpClient httpclient = new DefaultHttpClient();
         httpclient.getCredentialsProvider().setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(username,password));
         AuthCache authCache = new BasicAuthCache();
         BasicScheme basicAuth = new BasicScheme();
         authCache.put(targetHost, basicAuth);
         BasicHttpContext localcontext = new BasicHttpContext();
         localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);   
         HttpGet httpget = new HttpGet(AconexAPIs.HTTPS+"://"+hostname+restAPI);
         return httpclient.execute(targetHost, httpget, localcontext);*/
	}
}
