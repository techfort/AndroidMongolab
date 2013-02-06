package com.techfort.mongolabandroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoLabDB {
	public static final String URL = "https://api.mongolab.com/api/1/";
	private String database;
	private String apiKey;
	private MongoLabHttpClient client;
	
	public MongoLabDB(String database, String apiKey){
		this.database = database;
		this.apiKey = apiKey;
		client = new MongoLabHttpClient();
	}
	
	// obtain collection
	public MongoLabCollection getCollection(String collection){
		return new MongoLabCollection(this.database, this.apiKey, collection);
	}
	
	// list databases
	public JSONArray listDatabases(){
		String requestUrl = URL + "databases/?apiKey=" + apiKey;
		return toJsonArray( client.get( requestUrl ) );
	}
	
	// list collections
	public JSONArray listCollections(){
		String requestUrl = URL + "databases/" + database + "/collections/?apiKey=" + apiKey;
		return toJsonArray( client.get(requestUrl) );
	}
	
	public static JSONObject toJsonObject(String json){
		try {
			return new JSONObject(json);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONArray toJsonArray(String json){
		try {
			return new JSONArray(json);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public class MongoLabCollection {
		
		private String collection;
		private String collectionUrl;

		// we don't want this constructor to be visible from outside this class
		MongoLabCollection(String db, String apikey, String collection){
			this.collection = collection;
			client = new MongoLabHttpClient();
			collectionUrl = URL + "databases/" + database + "/collections/" + collection;
		}
		
		public JSONArray listDocuments(){
			return toJsonArray( client.get(collectionUrl + "/?apiKey=" + apiKey ) );
		}
		
		public JSONObject getById(String id){
			return toJsonObject( client.get( collectionUrl + "/" + id + "?apiKey=" + apiKey ) );
		}
		
		
	}
}
