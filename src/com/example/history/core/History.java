package com.example.history.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class History implements IStructuredContentProvider {

	private Set<HistoryListener> listeners = new HashSet();
	private List<Item> list = new ArrayList();

	
	public void append(Item item) {
		IJavaElement element = item.getJavaElement();
		IJavaElement prev = list.isEmpty() ? null : list.get(list.size()-1).getJavaElement();
		
		Deque<IJavaElement> ancestors0 = new ArrayDeque();
		IJavaElement any = prev;
		while (any != null) {
			if (any.equals(element)) return;
			ancestors0.add(any);
			any = any.getParent();
		}
		
		Deque<IJavaElement> ancestors = new ArrayDeque();
		IJavaElement parent = element.getParent();
		while (parent != null) {
			if (ancestors0.contains(parent)) break;
			ancestors.addLast(parent);
			parent = parent.getParent();
		}
		while (!ancestors.isEmpty()) {
			list.add(new Item(null, ancestors.pollLast()));
		}
		
		list.add(item);

		for (HistoryListener each: listeners) {
			each.historyChanged(this, item);
		}
	}

	
	public void addListener(HistoryListener listener) {
		listeners.add(listener);
	}
	
	
	public void removeListener(HistoryListener listener) {
		listeners.remove(listener);
	}


	@Override
	public void dispose() {
	}


	@Override
	public Object[] getElements(Object inputElement) {
		// list.map(Item::getJavaElement).toArray();
		Object[] elements = new Object[list.size()];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = list.get(i).getJavaElement();
		}
		return elements;
	}

	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
}
