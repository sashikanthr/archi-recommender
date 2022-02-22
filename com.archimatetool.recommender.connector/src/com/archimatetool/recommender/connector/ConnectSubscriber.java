package com.archimatetool.recommender.connector;

public interface ConnectSubscriber {
	
	void subscribe(ConnectSubscriber subscriber, String id);
	
	void receive(String response);

}
