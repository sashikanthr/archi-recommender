package com.archimatetool.recommender.contribution;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class RecommendationJSONParser {
	
	public static List<RecommendedComponent> parseJson(String body) {
		
		
		List<RecommendedComponent> recommendations = new ArrayList<>();
		JsonArray array = new JsonParser().parse(body).getAsJsonArray();
		array.forEach(element -> {
			JsonObject object = element.getAsJsonObject();
			if(object.isJsonObject() && object.has("component")) {
				RecommendedComponent component = new RecommendedComponent();
				JsonObject componentAttrib = object.get("component").getAsJsonObject();
				component.setName(componentAttrib.get("name").getAsString());				
				component.setType(componentAttrib.get("type").getAsString());
				component.setId(componentAttrib.get("id").getAsString());
				if(object.has("score")) {
					component.setScore(object.get("score").getAsDouble());
				} else {
					component.setScore(0.0);
				}
				
				recommendations.add(component);
			}
		});		
		return recommendations;
	}
	
}
