package com.archimatetool.recommender;

import org.eclipse.ui.IViewPart;

public interface IRecommenderView extends IViewPart {
	
    String ID = "com.archimatetool.recommender.recommenderView"; //$NON-NLS-1$
    String HELP_ID = "com.archimatetool.help.recommenderViewHelp"; //$NON-NLS-1$
    String NAME = Messages.IRecommender;
    
    void initializeRecommendations();

}
