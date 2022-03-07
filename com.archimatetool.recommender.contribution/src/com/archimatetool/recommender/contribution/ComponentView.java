package com.archimatetool.recommender.contribution;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.help.HelpSystem;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

import com.archimatetool.editor.ArchiPlugin;
import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.recommender.model.CompletableRecommender;
import com.archimatetool.recommender.model.Recommender;
import com.archimatetool.recommender.ui.RecommenderView;

public class ComponentView extends RecommenderView {

	// TODO Setup Help ID
	private static final String HELP_ID = null;

	private ComponentViewer fViewer;

	private IArchimateModel fModel;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();

		// Model Closed
		if (propertyName == IEditorModelManager.PROPERTY_MODEL_REMOVED) {
			if (fModel == newValue) {
				fModel = null;
				fViewer.setInput(null);
				// fActionValidate.setEnabled(false); TODO - This should correspond to the
				// recommend action
			}
		}

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		if (part == this || part == null) {
			return;
		}

		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			initializeRecommendations(object);
		}
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite treeComp = new Composite(parent, SWT.NULL);
		treeComp.setLayout(new TreeColumnLayout());
		treeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		fViewer = new ComponentViewer(treeComp, SWT.NULL);
		fViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				System.out.println("In the double click listener");
			}

		});

		fViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				System.out.println("In the selection changed listener");

			}
		});

		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);

		getSite().setSelectionProvider(fViewer);

		IEditorModelManager.INSTANCE.addPropertyChangeListener(this);

		selectionChanged(getSite().getWorkbenchWindow().getPartService().getActivePart(),
				getSite().getWorkbenchWindow().getSelectionService().getSelection());

	}

	public void initializeRecommendations(Object object) {

		BusyIndicator.showWhile(null, new Runnable() {
			
			@Override
			public void run() {
				IArchimateConcept concept = getConceptObject(object);
				if (concept != null) {
					CompletableRecommender recommender = new ComponentRecommender(concept);
					fViewer.setInput(recommender);
					fViewer.expandAll();
				}
			}
		});

	}

	public void initializeRecommendations_old(Object object) {

		BusyIndicator.showWhile(null, new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				IArchimateConcept concept = getConceptObject(object);
				if (concept != null) {
					Recommender recommender = new ComponentRecommender(concept);
					Object result = recommender.getRecommendations();
					fViewer.setInput(result);
					fViewer.expandAll();					
				}
			}
		});

	}

	private IArchimateConcept getConceptObject(Object object) {

		IArchimateConcept concept = null;

		if (object instanceof IArchimateConcept) {
			concept = (IArchimateConcept) object;
		} else if (object instanceof IAdaptable) {
			concept = ((IAdaptable) object).getAdapter(IArchimateConcept.class);
		}

		return concept;
	}

	@Override
	public void setFocus() {
		if (fViewer != null) {
			fViewer.getControl().setFocus();
		}
	}

	@Override
	public String getContributorId() {
		return ArchiPlugin.PLUGIN_ID;
	}

	@Override
	public int getContextChangeMask() {
		return NONE;
	}

	@Override
	public org.eclipse.help.IContext getContext(Object target) {
		return HelpSystem.getContext(HELP_ID);
	}

	@Override
	public String getSearchExpression(Object target) {
		return Messages.Recommender;
	}

	@Override

	public void dispose() {
		super.dispose();

		// Unregister selection listener
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);

		// Unregister us as a Model Manager Listener
		IEditorModelManager.INSTANCE.removePropertyChangeListener(this);
	}

}
