package com.archimatetool.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.archimatetool.model.IArchimateDiagramModel;
import com.archimatetool.model.IArchimateElement;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.IArchimateRelationship;

public class Recommender {
	
	private IArchimateModel fModel;

	
	public Recommender(IArchimateModel model) {
		fModel = model;		
	}
	
	public List<Object> showRecommendations() {
		if(fModel!=null) {
			return collectObjects();
		}
		
		return null;
	}

	private List<Object> collectObjects() {
		
		List<Object> result = new ArrayList<>();
		
        for(Iterator<EObject> iter = fModel.eAllContents(); iter.hasNext();) {
        	EObject object = iter.next();
        	if(object instanceof IArchimateModelObject) {
        	IArchimateModelObject modelObject = (IArchimateModelObject) object;
        	if(modelObject instanceof IArchimateRelationship || modelObject instanceof IArchimateElement)
            result.add(generateMockRecommendations(modelObject));
        }
        }
        
        return result;
	}
	
	private IRecord generateMockRecommendations(IArchimateModelObject modelObject) {
		return new Record(modelObject);		
	}

}
