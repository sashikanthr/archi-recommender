package com.archimatetool.recommender.model;

public interface Recommendation {
	
	Object getRecommendation();
	
	double getScore();
	
}