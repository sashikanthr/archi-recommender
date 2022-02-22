package com.archimatetool.recommender.ui;

import java.beans.PropertyChangeListener;
import org.eclipse.help.IContextProvider;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public abstract class RecommenderView extends ViewPart implements ISelectionListener, PropertyChangeListener, IContextProvider, ITabbedPropertySheetPageContributor {

}
