package com.ubc.cpsc310.vancouverparking.client;


import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ubc.cpsc310.vancouverparking.server.Meter;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("meter")
public interface MeterService extends RemoteService {
  public List<MeterInfo> getMeters();// throws NotLoggedInException;
  //public void addMeter();
  //public void removeMeter(MeterInfo m);
}
