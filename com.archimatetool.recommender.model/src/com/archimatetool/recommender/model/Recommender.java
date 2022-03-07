package com.archimatetool.recommender.model;

import java.util.List;

import com.archimatetool.model.IArchimateModelObject;

public interface Recommender {
	
	IArchimateModelObject getSelection();

	List<? extends Recommendation> getRecommendations();
}