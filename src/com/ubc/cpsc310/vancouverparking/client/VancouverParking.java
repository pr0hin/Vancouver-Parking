package com.ubc.cpsc310.vancouverparking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;




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
		
		// Initializing the map
	    LatLng myLatLng = LatLng.create(49.2569777, -123.123904);
	    MapOptions myOptions = MapOptions.create();
	    myOptions.setZoom(10.0);
	    myOptions.setCenter(myLatLng);
	    myOptions.setMapTypeId(MapTypeId.ROADMAP);
	    final GoogleMap map = GoogleMap.create(Document.get().getElementById("map_canvas"), myOptions);
	    
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
