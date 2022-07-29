package com.archimatetool.recommender.ui;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.ui.IWorkbenchPage;

import com.archimatetool.editor.preferences.IPreferenceConstants;

public abstract class RecommenderPreferenceAggregator extends PreferencePage implements IWorkbenchPage, IPreferenceConstants  {

	public abstract void addPreference();

}
