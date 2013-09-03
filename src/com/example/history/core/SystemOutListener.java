package com.example.history.core;

public class SystemOutListener implements HistoryListener {

	@Override
	public void historyChanged(History history, Item item) {
		System.out.println(item);
	}

}
