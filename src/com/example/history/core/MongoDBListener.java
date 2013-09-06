package com.example.history.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;

import com.example.util.BullshitFree;
import com.example.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDBListener implements HistoryListener {

	private static final String[] type_names = "N/A JAVA_MODEL JAVA_PROJECT PACKAGE_FRAGMENT_ROOT PACKAGE_FRAGMENT COMPILATION_UNIT CLASS_FILE TYPE FIELD METHOD INITIALIZER PACKAGE_DECLARATION IMPORT_CONTAINER IMPORT_DECLARATION LOCAL_VARIABLE TYPE_PARAMETER ANNOTATION".split(" ");
	private static final String workspace = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();


	private DBCollection coll;


	public MongoDBListener() {
		try {
			MongoClient mongo = new MongoClient("localhost",27123);
			DB db = mongo.getDB("com_example");
			coll = db.getCollection("history");
		} catch (UnknownHostException exception) {
			throw new BullshitFree(exception);
		}
	}


	@Override
	public void historyChanged(History history, Item item) {
		BasicDBObject object = new BasicDBObject();

		object.append("workspace", workspace);
		object.append("path", item.getJavaElement().getPath().toString());

		IJavaElement each = item.getJavaElement();

		object.append("name", each.getElementName());
		object.append("type", type_names[each.getElementType()]);

		List types = new ArrayList();
		List names = new ArrayList();
		while (each != null) {
			names.add(each.getElementName());
			types.add(each.getElementType());
			each = each.getParent();
		}
		Collections.reverse(types);
		Collections.reverse(names);

		object.append("fqn", Util.join(names, "/"));
		object.append("types", types);

		coll.insert(object);

	}

}
