package com.archimatetool.recommender.connector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

public class ConnectorService {

	Map<URI, ConnectSubscriber> subscriberMap = new HashMap<>();

	private static ConnectorService connectorService;

	public static ConnectorService getInstance() {
		if (connectorService == null) {
			connectorService = new ConnectorService();

		}

		return connectorService;
	}

	public void sendResponse(URI uri, String response) {

		if (subscriberMap.containsKey(uri)) {
			subscriberMap.get(uri).receive(response);
		}

		subscriberMap.remove(uri);
	}

	public void addSubscriber(ConnectSubscriber subscriber, String id) {
		try {

			URI uri = new URIBuilder("http://localhost:8080/recommendations").addParameter("id", id).build();
			Connector connector = new ConnectorImpl(uri);
			subscriberMap.put(uri, subscriber);
			connector.setURI(uri);
			connector.send();			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
