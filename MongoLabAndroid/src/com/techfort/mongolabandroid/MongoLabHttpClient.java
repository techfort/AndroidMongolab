package com.techfort.mongolabandroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class MongoLabHttpClient {

	private String baseUrl;
	private HttpClient client;
	
	public MongoLabHttpClient(){
		client = new DefaultHttpClient();
	}
	
	private HttpResponse execute(HttpUriRequest request){
		try {
			return client.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e ){
			e.printStackTrace();
		}
		return null;
	}
	
	private String read(HttpResponse response) {
		String output = "";
		InputStream is;
		try {
			is = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while( (line= reader.readLine())!=null){
				output += line; 
			}
			
			return output;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String get(String url){ 
		HttpGet getRequest = new HttpGet(url.trim());
		return read( execute(getRequest) );
	}

	
	public String post(String url, List<NameValuePair> params) throws UnsupportedEncodingException{
		HttpPost postRequest = new HttpPost(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
		postRequest.setHeader("Content-type", "application/json");
		postRequest.setEntity(entity);
		return read( execute(postRequest) );
		
	}
	
	public String post(String url, JSONObject query) throws UnsupportedEncodingException{
		StringEntity entity = new StringEntity(query.toString());
		HttpPost postRequest = new HttpPost(url);
		postRequest.setHeader("Content-type","application/json");
		postRequest.setEntity(entity);
		return read( execute(postRequest) );
	}
	
	
	public String runCommand(String url, File query, String apiKey){
		FileEntity entity = new FileEntity(query, "text/json");
		HttpPost postRequest = new HttpPost(baseUrl + "/runCommand?apiKey=" + apiKey);
		postRequest.setEntity(entity);
		postRequest.setHeader("Content-type","application/json");
		return read(execute(postRequest));
	}
	
	public String delete(String url, String query, String apiKey){
		String requestUrl = url + query + "&apiKey=" + apiKey;
		HttpDelete delete = new HttpDelete(requestUrl.trim());
		return read ( execute( delete ) );
		
	}
	
	public String put(String url, String id){
		throw new UnsupportedOperationException("not done yet...");
	}
}
