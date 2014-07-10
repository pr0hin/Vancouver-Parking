package com.ubc.cpsc310.vancouverparking.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchHistoryServiceAsync {

	void addHistory(String location, AsyncCallback<Void> callback);

	void getHistory(AsyncCallback<List<String>> callback);

}
