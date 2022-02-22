package com.archimatetool.recommender.contribution;

import java.util.ArrayList;
import java.util.List;

import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.recommender.engine.Recommendation;

public class ComponentRecommendation implements Recommendation {
	
	private IArchimateConcept selection;
	
	private List<RecommendedComponent> recommendations;
	
	public ComponentRecommendation(IArchimateConcept concept) {
		this.selection = concept;
		recommendations = new ArrayList<>();
	}
	
	@Override
	public Object getSelection() {
		return selection;
	}

	@Override
	public List<? extends Object> getResult() {
		return recommendations;
	}

	@Override
	public <T> void addRecommendation(T t) {
		recommendations.add((RecommendedComponent) t);
	}

}
