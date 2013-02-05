package com.techfort.mongolabandroid;


public class MongoLabCollection {
	private String baseUrl;
	private String apiKey;
	private MongoLabHttpClient client;
	private String url;

	
	public MongoLabCollection(String apikey, String collection){
		baseUrl = MongoLabDB.URL;
		apiKey = apikey;
		client = new MongoLabHttpClient();
		this.url = baseUrl  + "/collections/" + collection + "?apiKey=" + apiKey;
	}
	
	
	
	
	
}
