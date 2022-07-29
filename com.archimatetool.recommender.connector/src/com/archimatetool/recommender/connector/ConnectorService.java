package com.archimatetool.recommender.connector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

public class ConnectorService {

	private Map<URI, RecommendationSubscriber> subscriberMap = new HashMap<>();

	private static ConnectorService connectorService;
	
	private static Connector connector;

	public static ConnectorService getInstance() {
		if (connectorService == null) {
			connectorService = new ConnectorService();
			connector = new HttpConnector();
		}

		return connectorService;
	}

	public void sendResponse(URI uri, String response) {

		if (subscriberMap.containsKey(uri)) {			
			subscriberMap.get(uri).receive(response);
		}

		subscriberMap.remove(uri);
	}

	public void addSubscriber(RecommendationSubscriber subscriber, String id) {
		try {

			URI uri = new URIBuilder("http://127.0.0.1:5000/recommendations").addParameter("id", id).build();
			subscriberMap.put(uri, subscriber);
			sendToConnector(uri);								
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sendToConnector(URI uri) {	
		connector.setURI(uri);
		connector.send();	
	}
}
