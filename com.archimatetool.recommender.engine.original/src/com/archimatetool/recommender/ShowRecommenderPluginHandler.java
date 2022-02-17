package com.archimatetool.recommender;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.archimatetool.editor.ui.services.ViewManager;

public class ShowRecommenderPluginHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ViewManager.toggleViewPart(IRecommenderView.ID, true);
		return null;
	}

}
