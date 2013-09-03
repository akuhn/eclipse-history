package com.example.plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;

import com.example.ui.views.SampleView;

public class Tracker implements ISelectionListener {

	public List history = new ArrayList();
	public Method method;
	public SampleView listener;
	
	public ISourceReference computeHighlightRangeSourceReference(JavaEditor editor) {
		try {
			if (method == null) {
				method = JavaEditor.class.getDeclaredMethod("computeHighlightRangeSourceReference");
				method.setAccessible(true);
			}
			return (ISourceReference) method.invoke(editor);
		} catch (Exception exception) {
			throw new BullshitFree(exception);
		}
	}
	
	public void subscribe(ISelectionService service) {
		
		// NOTE: Apparently only post selection listener is updated in arrow 
		// keys, but not a regular selection listener!
		
		service.addPostSelectionListener(this);
		
	}

	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof JavaEditor) {
			JavaEditor editor = (JavaEditor) part;
			ISourceReference ref = computeHighlightRangeSourceReference(editor);
			if (ref == null) return;
			if (!history.isEmpty() && ref.equals(history.get(history.size()-1))) return;
			history.add(ref);
			if (listener != null) {
				listener.viewer.refresh();
			}
		}
	}
	
}
