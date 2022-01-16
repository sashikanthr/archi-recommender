package com.archimatetool.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.archimatetool.model.IArchimateModel;

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
            result.add(iter.next());       

        }
        
        return result;
	}	

}
