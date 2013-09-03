package com.example.history.core;

import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.ui.IWorkbenchPart;

public class Item {

	@SuppressWarnings("unused")
	private IWorkbenchPart part;
	private ISourceReference reference;

	
	public Item(IWorkbenchPart part, ISourceReference reference) {
		this.part = part;
		this.reference = reference;
	}

	
	@Override
	public int hashCode() {
		return reference.hashCode();
	}

	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Item)) return false;
		return reference.equals(((Item) other).reference);
	}
	
	
	public String toString() {
		return reference.toString();
	}


	public ISourceReference getReference() {
		return reference;
	}

}
