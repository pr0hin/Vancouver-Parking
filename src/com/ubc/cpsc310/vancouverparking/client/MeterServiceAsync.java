package com.ubc.cpsc310.vancouverparking.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ubc.cpsc310.vancouverparking.server.Meter;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MeterServiceAsync {
	public void getMeters(AsyncCallback<List<Meter>> async);
	public void addMeter(Meter m, AsyncCallback<Void> async);
	public void removeMeter(Meter m, AsyncCallback<Void> async);
}
