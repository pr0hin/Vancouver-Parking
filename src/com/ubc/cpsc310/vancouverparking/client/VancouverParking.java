package com.ubc.cpsc310.vancouverparking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VancouverParking implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	// private final MeterServiceAsync meterService = GWT
	// .create(MeterService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button addButton = new Button("Add meter");
		final Button removeButton = new Button("Remove meter");
		final TextBox nameField = new TextBox();
		nameField.setText("Meter Number");

		// We can add style names to widgets
		addButton.addStyleName("addButton");
		removeButton.addStyleName("removeButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("addButtonContainer").add(addButton);
		RootPanel.get("removeButtonContainer").add(removeButton);
	}

}
