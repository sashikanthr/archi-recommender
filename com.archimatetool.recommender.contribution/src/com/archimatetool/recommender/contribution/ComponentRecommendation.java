package com.archimatetool.recommender.contribution;



import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.recommender.model.Recommendation;

public class ComponentRecommendation implements Recommendation {
	

	private double score;
	
	private IArchimateConcept component;
	
	public ComponentRecommendation(IArchimateConcept concept, double score) {
		
		this.component = concept;
		this.score = score;
	}


	public double getScore() {
		return score;
	}	

	@Override
	public Object getRecommendation() {
		return component;
	}
}
