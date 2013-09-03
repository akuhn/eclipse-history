package com.example.plugin;

import java.lang.reflect.Method;

import org.eclipse.jdt.internal.ui.infoviews.JavadocView;
import org.eclipse.jdt.internal.ui.javaeditor.CompilationUnitEditor;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

public class Example {

	int n = 0;

	void method(BundleContext bundle) throws Exception {

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {


				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				ISelectionService service = window.getSelectionService();

				service.addPostSelectionListener(new ISelectionListener() {
					public void selectionChanged(IWorkbenchPart part,
							ISelection selection) {
						try {
							if (true) return;
							System.out.println("addPostSelectionListener");
							System.out.println(part);
							System.out.println(selection);
							System.out.println(findSelectedJavaElement(part,selection));
							if (part instanceof CompilationUnitEditor) {
								Method m = JavaEditor.class.getDeclaredMethod("computeHighlightRangeSourceReference");
								m.setAccessible(true);
								System.out.println(m.invoke(part));
							}
							System.out.println(n += 1);
						} catch (Throwable e) {
							throw new RuntimeException(e);
						}
					}
				});
				
				window.getPartService().addPartListener(new IPartListener2() {
					
					@Override
					public void partVisible(IWorkbenchPartReference partRef) {
						System.out.println("partVisible: " + partRef.getPart(false));
					}
					
					@Override
					public void partOpened(IWorkbenchPartReference partRef) {
						System.out.println("partOpened: " + partRef.getPart(false));
					}
					
					@Override
					public void partInputChanged(IWorkbenchPartReference partRef) {
						System.out.println("partInputChanged: " + partRef.getPart(false));
					}
					
					@Override
					public void partHidden(IWorkbenchPartReference partRef) {
						System.out.println("partHidden: " + partRef.getPart(false));
					}
					
					@Override
					public void partDeactivated(IWorkbenchPartReference partRef) {
						System.out.println("partDeactivated: " + partRef.getPart(false));
					}
					
					@Override
					public void partClosed(IWorkbenchPartReference partRef) {
						System.out.println("partClosed: " + partRef.getPart(false));
					}
					
					@Override
					public void partBroughtToTop(IWorkbenchPartReference partRef) {
						System.out.println("partBroughtToTop: " + partRef.getPart(false));
					}
					
					@Override
					public void partActivated(IWorkbenchPartReference partRef) {
						System.out.println("partActivated: " + partRef);
					}

				});


			}
		});

	}

	public static Object findSelectedJavaElement(IWorkbenchPart part, ISelection selection) {
		return (new JavadocView() {
			Object helper(IWorkbenchPart part, ISelection selection) {
				return findSelectedJavaElement(part,selection);
			}
		}).helper(part,selection);

	}

}
