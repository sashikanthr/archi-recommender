package com.archimatetool.recommender;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public interface IRecord {
	
	String getName();
	
	List<IRecommendation> getRecommendations();
	
	void addRecommendation(IRecommendation recommendation);
	
	Image getImage();

}
