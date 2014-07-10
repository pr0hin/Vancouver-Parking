package com.ubc.cpsc310.vancouverparking.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("history")
public interface SearchHistoryService extends RemoteService {
  public void addHistory(String history) throws NotLoggedInException;

  public List<String> getHistory() throws NotLoggedInException;
}
