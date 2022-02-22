package com.archimatetool.recommender.contribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeColumn;

import com.archimatetool.editor.ui.ArchiLabelProvider;
import com.archimatetool.editor.ui.UIUtils;
import com.archimatetool.model.IArchimateConcept;
import com.archimatetool.recommender.engine.Recommendation;

public class ComponentViewer extends TreeViewer {

	private String[] columnNames = { Messages.Recommender_Object_Name, Messages.Recommender_Object_Type,
			Messages.Recommender_Similarity_Score, };

	private int[] columnWeights = { 20, 20, 60 };

	public ComponentViewer(Composite parent, int style) {
		super(parent, style | SWT.MULTI | SWT.FULL_SELECTION);
		UIUtils.fixMacSiliconItemHeight(getTree());

		setContentProvider(new RecommenderViewerContentProvider());
		setLabelProvider(new RecommenderViewerLabelProvider());

		getTree().setHeaderVisible(true);
		getTree().setLinesVisible(true);

		TreeColumnLayout layout = (TreeColumnLayout) getControl().getParent().getLayout();

		for (int i = 0; i < columnNames.length; i++) {
			final TreeColumn column = new TreeColumn(getTree(), SWT.NONE);
			column.setText(columnNames[i]);
			column.setData(i);
			layout.setColumnData(column, new ColumnWeightData(columnWeights[i], true));
			column.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					// TODO this is used for sorting mechanism

				}
			});
		}

	}

	class RecommenderViewerContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object parent) {
			
			ArrayList<Object> result = new ArrayList<>();
			
			if(parent instanceof Recommendation) {
				Recommendation recommendation = ((Recommendation) parent);			
				Map<Object, Recommendation> selection = new HashMap<>();
				selection.put(recommendation.getSelection(),recommendation);				
				result.add(selection);
			}

			return result.toArray();
		}		

		@Override
		public Object[] getChildren(Object parentElement) {

			if (parentElement instanceof Map) {

				Recommendation recommendation = (Recommendation) ((Map) parentElement).values().toArray()[0];
				return recommendation.getResult().toArray();
			}

			if (parentElement instanceof List<?>) {
				return ((List<?>) parentElement).toArray();
			}
			
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {

			if (element instanceof Recommendation) {
				return element;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

	}

	class RecommenderViewerLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof Map && columnIndex==0) {

				IArchimateConcept concept = (IArchimateConcept) ((Map) element).keySet().toArray()[0];

				return ArchiLabelProvider.INSTANCE.getImage(concept);
			}

			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {

			switch (columnIndex) {

			case 0:
				if (element instanceof Map) {

				Object concept = ((Map) element).keySet().toArray()[0];				
					return ((IArchimateConcept) concept).getName();
				} else if (element instanceof RecommendedComponent) {
					return ((RecommendedComponent) element).getName();
				}

			case 1:
				if (element instanceof RecommendedComponent) {
					return ((RecommendedComponent) element).getType();
				}

			case 2:
				if (element instanceof RecommendedComponent) {
					return String.valueOf(((RecommendedComponent) element).getScore());
				}

			default:
				return null;
			}

		}

	}

}