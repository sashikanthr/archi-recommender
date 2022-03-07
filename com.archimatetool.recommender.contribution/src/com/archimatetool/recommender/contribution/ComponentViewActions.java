package com.archimatetool.recommender.contribution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.archimatetool.editor.ui.services.ViewManager;
import com.archimatetool.editor.views.tree.ITreeModelView;
import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IArchimateModelObject;
import com.archimatetool.model.IProperty;
import com.archimatetool.recommender.model.Recommendation;
import com.archimatetool.recommender.model.Recommender;

public class ComponentViewActions {

	private ComponentView componentView;

	private List<IAction> actions;

	public ComponentViewActions(ComponentView componentView) {
		this.componentView = componentView;
		actions = new ArrayList<>();
	}

	public void makeActions() {

		IAction revealObject = new Action(Messages.Recommendation_Action_Reveal_Object) {
			@Override
			public void run() {
				selectObjects((IStructuredSelection) componentView.getViewer().getSelection());
			}

			@Override
			public String getToolTipText() {
				return getText();
			}
		};

		actions.add(revealObject);

		IAction replaceObject = new Action(Messages.Recommendation_Action_Replace_Object) {
			@Override
			public void run() {
				replaceObjects((IStructuredSelection) componentView.getViewer().getSelection());
			}

			@Override
			public String getToolTipText() {
				return Messages.Recommendation_Action_Replace_Object_Tooltip;
			}
		};

		actions.add(replaceObject);

	}

	public List<IAction> getActions() {
		return actions;
	}

	public void selectObjects(IStructuredSelection selection) {
		if (selection != null) {
			List<IArchimateConcept> treeList = new ArrayList<IArchimateConcept>();
			for (Object recommendation : selection.toArray()) {

				if (recommendation instanceof Recommendation) {
					Object object = ((Recommendation) recommendation).getRecommendation();
					if (object instanceof IArchimateConcept) {
						treeList.add((IArchimateConcept) object);
					}
				}
			}

			if (!treeList.isEmpty()) {
				ITreeModelView view = (ITreeModelView) ViewManager.showViewPart(ITreeModelView.ID, false);
				if (view != null) {
					view.getViewer().setSelection(new StructuredSelection(treeList), true);
				}
			}

		}
	}

	public void replaceObjects(IStructuredSelection selection) {

		if (selection != null) {
			List<IArchimateConcept> treeList = new ArrayList<IArchimateConcept>();

			for (Object recommendation : selection.toArray()) {

				if (recommendation instanceof Recommendation) {
					Object object = ((Recommendation) recommendation).getRecommendation();
					if (object instanceof IArchimateConcept) {
						treeList.add((IArchimateConcept) object);
						IArchimateConcept model = getSelection();
						if (model != null && object instanceof IArchimateConcept) {
							IArchimateConcept target = ((IArchimateConcept) object);
							rename(model,target);
							mergeProperties(model, target);
						}

					}
				}
			}
		}
	}

	private IArchimateConcept getSelection() {

		Object object = componentView.getViewer().getInput();
		if (object instanceof Recommender) {
			IArchimateModelObject selection = ((Recommender) componentView.getViewer().getInput()).getSelection();
			if(selection instanceof IArchimateConcept) {
				return (IArchimateConcept)selection;
			}
		}
		return null;
	}

	private void rename(IArchimateConcept model, IArchimateConcept target) {
		model.setDocumentation(target.getDocumentation());
		model.setName(target.getName());
	}
	
	private void mergeProperties(IArchimateConcept model, IArchimateConcept target) {
		target.getProperties().forEach(p -> {
			 IProperty property = IArchimateFactory.eINSTANCE.createProperty();
		     property.setKey(p.getKey());
		     property.setValue(p.getValue());
		     model.getProperties().add(property);		     
		});
	}

}
