package com.archimatetool.recommender.model;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CompletableRecommender extends Recommender {
	
	CompletableFuture<List<Recommendation>> getCompletableFutureRecommendations();

}