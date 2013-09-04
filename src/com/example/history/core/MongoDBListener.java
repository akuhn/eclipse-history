package com.example.history.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;

import com.example.util.BullshitFree;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDBListener implements HistoryListener {

	private static final String[] type_names = new String[] {
		null,
		"JAVA_MODEL",
		"JAVA_PROJECT",
		"PACKAGE_FRAGMENT_ROOT",
		"PACKAGE_FRAGMENT",
		"COMPILATION_UNIT",
		"CLASS_FILE",
		"TYPE",
		"FIELD",
		"METHOD",
		"INITIALIZER",
		"PACKAGE_DECLARATION",
		"IMPORT_CONTAINER",
		"IMPORT_DECLARATION",
		"LOCAL_VARIABLE",
		"TYPE_PARAMETER",
		"ANNOTATION"
	};


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

		object.append("workspace", ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());

		object.append("path", item.getJavaElement().getPath().toString());

		List<BasicDBObject> list = new ArrayList();
		IJavaElement each = item.getJavaElement();
		while (each != null) {
			list.add(new BasicDBObject()
					.append("name", each.getElementName())
					.append("type", type_names[each.getElementType()]));
			each = each.getParent();
		}

		object.append("name", list.get(0).get("name"));

		object.append("type", list.get(0).get("type"));

		Collections.reverse(list);

		object.append("fqn", list);

		coll.insert(object);

	}

}
