package com.archimatetool.recommender.ui;

import java.beans.PropertyChangeListener;

import org.eclipse.help.IContextProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.part.ViewPart;

import com.archimatetool.recommender.engine.Recommender;

public abstract class RecommenderView extends ViewPart implements ISelectionListener, PropertyChangeListener, IContextProvider {
	
	abstract void initialize();
	
	abstract Viewer getViewer();
	
	abstract Recommender getRecommender();	
	

}
