package com.ubc.cpsc310.vancouverparking.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("favorites")
public interface FavoritesService extends RemoteService {
  public void addMeter(Long number) throws NotLoggedInException;
  public void removeMeter(Long number) throws NotLoggedInException;
  public Long[] getFavorites() throws NotLoggedInException;
}
