package com.example.history.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ui.IWorkbenchPart;

public class Item {

	@SuppressWarnings("unused")
	private IWorkbenchPart part;
	private IJavaElement element;

	
	public Item(IWorkbenchPart part, IJavaElement reference) {
		this.part = part;
		this.element = reference;
	}


	@Override
	public int hashCode() {
		return element.hashCode();
	}

	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Item)) return false;
		return element.equals(((Item) other).element);
	}
	
	
	public String toString() {
		return element.toString();
	}


	public IJavaElement getJavaElement() {
		return element;
	}
	
	
	public List<IJavaElement> getAncestors() {
		List<IJavaElement> ancestors = new ArrayList();
		IJavaElement each = element;
		while (each != null) {
			ancestors.add(each);
			each = each.getParent();
		}
		return ancestors;
	}

}
