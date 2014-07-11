package com.ubc.cpsc310.vancouverparking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MeterServiceAsync {
	public void getMeters(AsyncCallback<MeterInfo[]> async);
	public void loadMeters(AsyncCallback<Void> callback);	
	public void removeMeters(AsyncCallback<Void> async);
}
