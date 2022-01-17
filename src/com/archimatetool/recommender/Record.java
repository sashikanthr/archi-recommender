package com.archimatetool.recommender;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.archimatetool.editor.ui.ArchiLabelProvider;
import com.archimatetool.model.IArchimateModelObject;

public class Record implements IRecord {	
	
	private IArchimateModelObject modelObject;
	
	private final List<IRecommendation> recommendations;
	
	public Record(IArchimateModelObject modelObject) {
		
		this.modelObject = modelObject;
		recommendations = new ArrayList<>();
		generateMockRecommendations();		
	}

	@Override
	public String getName() {
		
		return modelObject.getName();
	}

	@Override
	public List<IRecommendation> getRecommendations() {
		return recommendations;
	}

	private List<IRecommendation> generateMockRecommendations() {		
		
		addRecommendation(new Recommendation(null, 30, "Fake Description 1"));
		addRecommendation(new Recommendation(null, 20, "Fake Description 2"));
		addRecommendation(new Recommendation(null, 10, "Fake Description 3"));
		addRecommendation(new Recommendation(null, 5, "Fake Description 4"));
		
		return recommendations;
	}

	@Override
	public Image getImage() {
		return ArchiLabelProvider.INSTANCE.getImage(modelObject);
	}

	@Override
	public void addRecommendation(IRecommendation recommendation) {
		this.recommendations.add(recommendation);		
	}

}
