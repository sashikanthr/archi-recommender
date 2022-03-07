package com.archimatetool.recommender.connector;

public interface RecommendationSubscriber {
	
	void subscribe(RecommendationSubscriber subscriber, String id);
	
	void receive(String response);

}
