package com.archimatetool.recommender;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.ui.IWorkbenchPart;

import com.archimatetool.editor.ui.ArchiLabelProvider;
import com.archimatetool.editor.ui.UIUtils;
import com.archimatetool.model.INameable;

public class RecommenderViewer extends TreeViewer {

	private String[] columnNames = { Messages.Recommender_Object_Type, Messages.Recommender_Similarity_Score,
			Messages.Recommender_Description };

	private int[] columnWeights = { 20, 20, 60 };

	public RecommenderViewer(Composite parent, int style) {
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
			if (columnIndex == 0) {
				return ArchiLabelProvider.INSTANCE.getImage(element);

			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			
			switch(columnIndex) {
			
			case 0 : if(element instanceof INameable) {
					return ((INameable) element).getName();
					}
					
			case 1 : return "Recommended Object";
			
			case 2: return String.valueOf(100);
				
			default: return null;
			}		

		}

	}

}
