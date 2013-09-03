package com.example.history.core;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.example.util.BullshitFree;
import com.example.util.BullshitFreeMethod;

public class Tracker implements ISelectionListener {

	private static final BullshitFreeMethod<ISourceReference> computeHighlightRangeSourceReference =
			BullshitFree.method(JavaEditor.class, "computeHighlightRangeSourceReference"); 

	private History history;
	
	
	public Tracker(History history) {
		this.history = history; 
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof JavaEditor) {
			JavaEditor editor = (JavaEditor) part;
			ISourceReference reference = computeHighlightRangeSourceReference.invoke(editor);
			if (reference == null) return;
			history.append(new Item(part, (IJavaElement) reference));
		}
	}


}
