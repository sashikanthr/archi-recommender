package com.archimatetool.recommender.engine;

import java.util.List;

public interface Recommendation {
	
	Object getSelection();
	
	List<? extends Object> getResult();
	
	<T extends Object> void addRecommendation(T t);

}
