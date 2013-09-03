package com.example.history;

import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.example.plugin"; //$NON-NLS-1$
	private static Activator plugin;
	
	public Tracker history;
	
	public Activator() {
	}

	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		history = new Tracker();
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				ISelectionService service = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
				history.subscribe(service);
			}
		});
	}

	
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}


	public static Activator getDefault() {
		return plugin;
	}

}
