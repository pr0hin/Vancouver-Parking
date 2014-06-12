package com.ubc.cpsc310.vancouverparking.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ubc.cpsc310.vancouverparking.server.Meter;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MeterServiceAsync {
	public void getMeters(AsyncCallback<List<MeterInfo>> async);
	//public void addMeter(AsyncCallback<Void> async);
	//public void removeMeter(MeterInfo m, AsyncCallback<Void> async);
}
