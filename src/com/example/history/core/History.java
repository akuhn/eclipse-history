package com.example.history.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class History implements IStructuredContentProvider {

	private Set<HistoryListener> listeners = new HashSet();
	private List<Item> list = new ArrayList();

	
	public void append(Item item) {
		if (item.equals(last())) return;
		list.add(item);
		for (HistoryListener each: listeners) {
			each.historyChanged(this, item);
		}
	}

	
	public Item last() {
		return list.isEmpty() ? null : list.get(list.size() - 1);
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
		// list.map(Item::getReference).toArray();
		ISourceReference[] elements = new ISourceReference[list.size()];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = list.get(i).getReference();
		}
		return elements;
	}

	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
}
