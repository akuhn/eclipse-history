package com.example.history;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.example.history.core.History;
import com.example.history.core.MongoDBListener;
import com.example.history.core.SystemOutListener;
import com.example.history.core.Tracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Plugin extends AbstractUIPlugin implements IStartup {

	public static final String PLUGIN_ID = "com.example.history";
	
	private static Plugin plugin;

	private ISelectionService service;
	private History history;
	private Tracker listener;

	
	public Plugin() {
	}


	@Override
	public void earlyStartup() {
	}
	
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		history = new History();
		history.addListener(new SystemOutListener());
		history.addListener(new MongoDBListener());
		// Subscribe to Eclipse selections...
		listener = new Tracker(history);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				service = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
				service.addPostSelectionListener(listener);
			}
		});
	}

	
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		service.removePostSelectionListener(listener);
		super.stop(context);
	}


	public static Plugin getDefault() {
		return plugin;
	}


	public History getHistory() {
		return history;
	}

}
