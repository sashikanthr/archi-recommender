package com.archimatetool.recommender.contribution;

import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.util.ArchimateModelUtils;
import com.archimatetool.recommender.model.Recommendation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

public class RecommendationJSONParser {

	public static List<Recommendation> parseJson(String body) {

		List<Recommendation> recommendations = new ArrayList<>();
		JsonElement parsedBody = new JsonParser().parse(body);
		if (parsedBody != null && parsedBody.isJsonArray()) {
			JsonArray array = parsedBody.getAsJsonArray();
			array.forEach(element -> {
				JsonObject object = element.getAsJsonObject();
				if (object.isJsonObject() && object.has("component")) {

					JsonObject componentAttrib = object.get("component").getAsJsonObject();
					String compId = null;
					String modelId = null;
					if (componentAttrib != null) {
						if(componentAttrib.get("id")!=null) compId = componentAttrib.get("id").getAsString();
						if(componentAttrib.get("modelId")!=null) modelId = componentAttrib.get("modelId").getAsString();
					}
					double score;

					if (object.has("score")) {
						score = object.get("score").getAsDouble();
					} else {
						score = 0.0;
					}

					if (modelId != null && !modelId.isEmpty() && compId!=null && !compId.isEmpty()) {
						IArchimateModel model = findModelById(modelId.trim());
						if (model != null) {
							IArchimateConcept concept = findComponent(model, compId);
							if (concept == null) {
								concept = findComponent(model, compId.replaceFirst("id-", ""));
							}

							Recommendation component = new ComponentRecommendation(concept, score);
							recommendations.add(component);
						}
					}

				}
			});
		}
		return recommendations;
	}

	private static IArchimateModel findModelById(String id) {

		Optional<IArchimateModel> model = IEditorModelManager.INSTANCE.getModels().stream()
				.filter(m -> m.getId().equals(id)).findFirst();
		if (model.isPresent()) {
			return model.get();
		}

		return null;
	}

	private static IArchimateConcept findComponent(IArchimateModel model, String id) {
		EObject object = ArchimateModelUtils.getObjectByID(model, id);
		if (object != null && object instanceof IArchimateConcept) {
			return (IArchimateConcept) object;
		}

		return null;
	}

}
