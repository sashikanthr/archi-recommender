package com.archimatetool.recommender.contribution;

import java.util.List;

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
import com.archimatetool.recommender.model.CompletableRecommender;
import com.archimatetool.recommender.model.Recommendation;
import com.archimatetool.recommender.model.Recommender;

public class ComponentViewer extends TreeViewer {

	private String[] columnNames = { Messages.Recommendation_Name, Messages.Recommendation_Documentation,
			Messages.Recommendation_Similarity_Score, };

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
			return getChildren(parent);
		}

		@Override
		public Object[] getChildren(Object parentElement) {

			if (parentElement instanceof CompletableRecommender) {
				return ((CompletableRecommender) parentElement).getCompletableFutureRecommendations().join().toArray();
			} else if (parentElement instanceof Recommender) {
				return ((Recommender) parentElement).getRecommendations().toArray();
			}

			if (parentElement instanceof List<?>) {
				return ((List<?>) parentElement).toArray();
			}

			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
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
			
		
			if (element instanceof Recommendation && columnIndex==0) {				
				Object object = ((Recommendation) element).getRecommendation();
				if (object instanceof IArchimateConcept) {
					return ArchiLabelProvider.INSTANCE.getImage(object);
				}
			}		

			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {

			IArchimateConcept component = null;
			Recommendation recommendation = null;
			if (element instanceof Recommendation) {
				recommendation = ((Recommendation) element);
				Object object = ((Recommendation) element).getRecommendation();
				if (object instanceof IArchimateConcept) {
					component = (IArchimateConcept) object;
				}
			}

			if (recommendation != null && component != null) {

				switch (columnIndex) {

				case 0:
					return component.getName();

				case 1:
					return component.getDocumentation();

				case 2:
					return String.valueOf(recommendation.getScore());

				default:
					return null;
				}

			}

			return null;

		}

	}
	

}
