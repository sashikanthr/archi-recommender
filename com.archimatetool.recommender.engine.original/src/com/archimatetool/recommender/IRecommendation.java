package com.archimatetool.recommender;

import com.archimatetool.model.IArchimateModelObject;


public interface IRecommendation {
	
	double getSimilarityScore();
	
	IArchimateModelObject getObject();
	
	String getDescription();
	
	String getName();

}
