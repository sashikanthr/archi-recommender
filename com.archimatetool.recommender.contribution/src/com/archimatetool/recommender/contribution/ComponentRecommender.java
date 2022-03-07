package com.archimatetool.recommender.contribution;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.recommender.connector.RecommendationSubscriber;
import com.archimatetool.recommender.connector.ConnectorService;
import com.archimatetool.recommender.model.CompletableRecommender;
import com.archimatetool.recommender.model.Recommendation;

public class ComponentRecommender implements CompletableRecommender, RecommendationSubscriber {
	
	private ConnectorService connectService;
	
	IArchimateConcept concept;
	
	CompletableFuture<List<Recommendation>> completedRecommendation;
	
	List<Recommendation> recommendations;

	public ComponentRecommender(IArchimateConcept concept) {
		this.concept = concept;		
	}

	public CompletableFuture<List<Recommendation>> getCompletableFutureRecommendations() {		
		completedRecommendation = new CompletableFuture<>();		
		subscribe(this, concept.getId());
		return completedRecommendation;
	}

	@Override
	public void subscribe(RecommendationSubscriber subscriber, String id) {		
		ensureConnectService();
		connectService.addSubscriber(this,id);
	}

	@Override
	public void receive(String response) {				
		
		recommendations = RecommendationJSONParser.parseJson(response);		
		completedRecommendation.complete(recommendations);		
	}
	
	void ensureConnectService() {
		if(connectService==null) {
		connectService = ConnectorService.getInstance();
		}
	}

	@Override
	public IArchimateModelObject getSelection() {
		return concept;
	}

	@Override
	public List<Recommendation> getRecommendations() {
		return recommendations;
	}
}
