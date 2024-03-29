package com.example.history.ui.views;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.internal.ui.util.SelectionUtil;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.OpenAndLinkWithEditorHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.example.history.Plugin;
import com.example.history.core.History;
import com.example.history.core.HistoryListener;
import com.example.history.core.Item;

public class CodeHistoryView extends ViewPart implements HistoryListener {

	public static final String ID = "com.example.history.ui.views.SampleView";

	private History history;
	private TableViewer viewer;

	
	public CodeHistoryView() {
		history = Plugin.getDefault().getHistory();
	}
	
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setLabelProvider(new JavaElementLabelProvider());
		viewer.setContentProvider(history);
		viewer.setInput(history);
		
		new OpenAndLinkWithEditorHelper(viewer) {

			@Override
			protected void activate(ISelection selection) {
				try {
					final Object selectedElement= SelectionUtil.getSingleElement(selection);
					if (EditorUtility.isOpenInEditor(selectedElement) != null) {
						IEditorPart editor = EditorUtility.openInEditor(selectedElement, true);
						EditorUtility.revealInEditor(editor, (IJavaElement) selectedElement);
					}
				} catch (PartInitException ex) {
					// ignore if no editor input can be found
				}
			}

			@Override
			protected void open(ISelection selection, boolean activate) {
				if (activate) activate(selection);
			}
			
		};
		
		history.addListener(this);
	}
	
	
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	
	@Override
	public void historyChanged(History history, Item item) {
		viewer.refresh();
		Table table = viewer.getTable();
		table.showItem(table.getItem(table.getItemCount() - 1));
		viewer.refresh();
	}
	
	
	@Override
	public void dispose() {
		history.removeListener(this);
	}
	
}