package com.archimatetool.recommender.contribution;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.recommender.connector.ConnectSubscriber;
import com.archimatetool.recommender.connector.ConnectorService;
import com.archimatetool.recommender.engine.Recommendation;
import com.archimatetool.recommender.engine.Recommender;

public class ComponentRecommender implements Recommender, ConnectSubscriber {
	
	private ConnectorService connectService;
	
	Recommendation recommendation;	
	
	IArchimateConcept concept;
	
	CompletableFuture<Recommendation> completedRecommendation;

	public ComponentRecommender(IArchimateConcept concept) {
		this.concept = concept;
		recommendation = new ComponentRecommendation(concept);
		
	}

	@Override
	public Object getRecommendations() {
		
		completedRecommendation = new CompletableFuture<>();		
		subscribe(this, concept.getId());
		return completedRecommendation;
	}

	@Override
	public void subscribe(ConnectSubscriber subscriber, String id) {
		
		ensureConnectService();
		connectService.addSubscriber(this,id);
	}

	@Override
	public void receive(String response) {
		
		List<RecommendedComponent> components = RecommendationJSONParser.parseJson(response);
		components.forEach(recommendation::addRecommendation);
		completedRecommendation.complete(recommendation);
	}
	
	void ensureConnectService() {
		if(connectService==null) {
		connectService = ConnectorService.getInstance();
		}
	}
}
