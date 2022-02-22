package com.archimatetool.recommender.contribution;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.archimatetool.editor.ui.services.ViewManager;

public class ComponentRecommenderPluginHandler extends AbstractHandler {
	
	
	public static String ID = "com.archimatetool.recommender.contribution.componentRecommenderView";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewManager.toggleViewPart(ID, true);
		return null;
	}

}
