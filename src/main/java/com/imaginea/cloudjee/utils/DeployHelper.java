package com.imaginea.cloudjee.utils;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeployHelper extends BaseTest {

	private String auth;

	public DeployHelper() {
		if (authCookie == null) {
			authenticate();
		}
		this.auth = getAuthCookie().getName() + "="
				+ getAuthCookie().getValue() + "; " + getjsessionId().getName()
				+ "=" + getjsessionId().getValue();
	}

	static {
		javax.net.ssl.HttpsURLConnection
				.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals(ConfigProperties.HOST_NAME)) {
							return true;
						}
						return false;
					}
				});
	}
	
	static{
		// Create a trust manager that does not validate certificate chains
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs,
							String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs,
							String authType) {
					}
				} };

				// Install the all-trusting trust manager
				try {
					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, trustAllCerts, new SecureRandom());
					HttpsURLConnection
							.setDefaultSSLSocketFactory(sc.getSocketFactory());
				} catch (Exception e) {
					e.printStackTrace();
				}

	}

	private String deploy(File file, String appName) throws Exception {

		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httppost = new HttpPost(ConfigProperties.DEPLOY + appName);
		httppost.setHeader("Cookie", auth);

		FileBody uploadFilePart = new FileBody(file);
		MultipartEntity reqEntity = new MultipartEntity();
		reqEntity.addPart("file", uploadFilePart);
		httppost.setEntity(reqEntity);

		HttpResponse response = httpclient.execute(httppost);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		
		return readResponse(response);

	}
	
	private String start() throws Exception {

		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(ConfigProperties.START + ConfigProperties.APP_NAME);
		httpget.setHeader("Cookie", auth);
		System.out.println("Start Of APP : "+httpget.getURI());
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		
		return readResponse(response);

	}

	private String stop(String appName) throws Exception {

		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(ConfigProperties.STOP + appName);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		
		return readResponse(response);

	}
	
	private String list() throws Exception {
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpGet httpget = new HttpGet(ConfigProperties.LIST);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		return readResponse(response);
	}
	
	private String undeploy() throws Exception{
		DefaultHttpClient httpclient = CreateHttpClient
				.createHttpClientConnection();
		HttpPost httpget = new HttpPost(ConfigProperties.UNDEPLOY + ConfigProperties.APP_NAME);
		httpget.setHeader("Cookie", auth);
		HttpResponse response = httpclient.execute(httpget);
		System.out.println("ResponseCode: "
				+ response.getStatusLine().getStatusCode());
		return readResponse(response);
	}
	
	
	protected String executeCommand(String cmd) throws Exception{
		if(cmd.equals("deploy")){
			File warPath = new File(ConfigProperties.APP_PATH);
			return deploy(warPath, ConfigProperties.APP_NAME);
		}else if(cmd.equals("start")){
			return start();
		}else if(cmd.equals("stop")){
			return stop(ConfigProperties.APP_NAME);
		}else if(cmd.equals("list")){
			return list();
		}else if(cmd.equals("undeploy")){
			return undeploy();
		}
		return null;
	}
}
