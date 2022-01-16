package com.archimatetool.recommender;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

import com.archimatetool.editor.ArchiPlugin;
import com.archimatetool.editor.model.IEditorModelManager;
import com.archimatetool.model.IArchimateModel;

public class RecommenderView extends ViewPart implements IRecommenderView, ISelectionListener, PropertyChangeListener, IContextProvider, ITabbedPropertySheetPageContributor {
	
	private RecommenderViewer fViewer;
	
	private IArchimateModel fModel;
	
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        Object newValue = evt.getNewValue();
        
        // Model Closed
        if(propertyName == IEditorModelManager.PROPERTY_MODEL_REMOVED) {
            if(fModel == newValue) {
                fModel = null;
                fViewer.setInput(null);
                //fActionValidate.setEnabled(false); TODO - This should correspond to the recommend action
            }
        }
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		
		if(part == this || part == null) {
			return;
		}
		
		IArchimateModel model = part.getAdapter(IArchimateModel.class);
		
		if(model!=null) {
			fModel = model;
		}
		
		initializeRecommendations();
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Composite treeComp = new Composite(parent, SWT.NULL);
		treeComp.setLayout(new TreeColumnLayout());
		treeComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		fViewer = new RecommenderViewer(treeComp, SWT.NULL);
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
		
		selectionChanged(getSite().getWorkbenchWindow().getPartService().getActivePart(), getSite().getWorkbenchWindow().getSelectionService().getSelection());
		
		initializeRecommendations();		
		
	}

	public void initializeRecommendations() {
		
		BusyIndicator.showWhile(null, new Runnable() {
			
			@Override
			public void run() {
				Recommender recommender = new Recommender(fModel);
				List<Object> result = recommender.showRecommendations();
				fViewer.setInput(result);
				fViewer.expandAll();
			}
		});
		
	}

	@Override
	public void setFocus() {
		if(fViewer != null) {
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
	public IContext getContext(Object target) {
		return HelpSystem.getContext(HELP_ID);
	}

	@Override
	public String getSearchExpression(Object target) {
		return Messages.IRecommender;
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
