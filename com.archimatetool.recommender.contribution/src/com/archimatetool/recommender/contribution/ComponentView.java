package com.archimatetool.recommender.contribution;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPart;

import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.model.IArchimateModel;
import com.archimatetool.recommender.model.CompletableRecommender;
import com.archimatetool.recommender.ui.RecommenderView;

public class ComponentView extends RecommenderView {

	// TODO Setup Help ID
	private static final String HELP_ID = null;

	private ComponentViewer fViewer;

	private IArchimateModel fModel;
	
	private ComponentViewActions actions;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		Object newValue = evt.getNewValue();

		// Model Closed
		if (propertyName == IEditorModelManager.PROPERTY_MODEL_REMOVED) {
			if (fModel == newValue) {
				fModel = null;
				fViewer.setInput(null);				
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
		actions = new ComponentViewActions(this);
		actions.makeActions();
		Composite treeComp = new Composite(parent, SWT.NULL);
		treeComp.setLayout(new TreeColumnLayout());
		treeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		fViewer = new ComponentViewer(treeComp, SWT.NULL);		
		fViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				actions.selectObjects((IStructuredSelection) event.getSelection());
			}

		});	

		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);

		getSite().setSelectionProvider(fViewer);

		IEditorModelManager.INSTANCE.addPropertyChangeListener(this);

		selectionChanged(getSite().getWorkbenchWindow().getPartService().getActivePart(),
				getSite().getWorkbenchWindow().getSelectionService().getSelection());
		
		hookContextMenu();

	}
	
	
    private void hookContextMenu() {
        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        
        menuMgr.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
                fillContextMenu(manager);
            }
        });
        
        Menu menu = menuMgr.createContextMenu(getViewer().getControl());
        getViewer().getControl().setMenu(menu);        
        getSite().registerContextMenu(menuMgr, getViewer());
    }
    
    
    private void fillContextMenu(IMenuManager manager) {    
    	actions.getActions().forEach(a -> {
    		if(a.getText().equals(Messages.Recommendation_Action_Replace_Object)) {
    			IStructuredSelection selection = (IStructuredSelection) getViewer().getSelection();
    			if(selection.toArray().length > 1) {
    				a.setEnabled(false);
    			}    			
    		}
    		manager.add(a);
    		manager.add(new Separator());
    	});       
        
     }

	private void initializeRecommendations(Object object) {

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

	public void dispose() {
		super.dispose();

		// Unregister selection listener
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);

		// Unregister us as a Model Manager Listener
		IEditorModelManager.INSTANCE.removePropertyChangeListener(this);
	}

	@Override
	public TreeViewer getViewer() {
		return fViewer;
	}

}
