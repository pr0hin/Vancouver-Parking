package com.ubc.cpsc310.vancouverparking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FavoritesServiceAsync {

	void addMeter(Long number, AsyncCallback<Void> callback);

	void removeMeter(Long number, AsyncCallback<Void> callback);

	void getFavorites(AsyncCallback<Long[]> callback);

}
