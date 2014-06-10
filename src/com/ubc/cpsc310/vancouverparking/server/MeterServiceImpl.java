package com.ubc.cpsc310.vancouverparking.server;

import java.util.List;

import com.ubc.cpsc310.vancouverparking.client.MeterService;
import com.ubc.cpsc310.vancouverparking.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MeterServiceImpl extends RemoteServiceServlet implements
    MeterService {

	@Override
	public List<Meter> getMeters() {
		// TODO Auto-generated method stub
		return null;
	}

 
}
