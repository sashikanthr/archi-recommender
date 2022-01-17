package com.archimatetool.recommender;

import com.archimatetool.model.IArchimateModelObject;

public class Recommendation implements IRecommendation {
	
	private IArchimateModelObject modelObject;
	
	private double similarityScore;
	
	private String description;
	
	public Recommendation(IArchimateModelObject modelObject, double similarityScore, String description) {
		this.modelObject = modelObject;
		this.similarityScore = similarityScore;
		this.description = description;
	}

	@Override
	public double getSimilarityScore() {
		return similarityScore;
	}

	@Override
	public IArchimateModelObject getObject() {
		return modelObject;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		//TODO should be the name of the model object
		return "Fake Recommendation";
	}
	
}
