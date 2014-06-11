package com.ubc.cpsc310.vancouverparking.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.MapTypeStyle;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.Size;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VancouverParking implements EntryPoint {
	
	private MeterCell metercell = new MeterCell();
	private CellList<MeterInfo> cellList = new CellList<MeterInfo>(metercell);
	private GoogleMap map;
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
		
		// Some test list of meters to put into the cellList
		List<MeterInfo> meters = new ArrayList<MeterInfo>();
		for (int i = 0; i<200;i++) {
			MeterInfo meter = new MeterInfo(378625 + i);
			meter.setCreditCard(true);
			if (i%2 == 0) {
				meter.setRate(2.00);
			}
			meter.setType("Public");
			double latitude = 49.2569777 - (Math.random() * 0.1);
			double longitude = -123.123904 - (Math.random() * 0.1);
			meter.setLatitude(latitude);
			meter.setLongitude(longitude);
			meters.add(meter);
		}
		
		
		// Adding meters to the cellList
		cellList.setPageSize(meters.size());
		cellList.setRowCount(meters.size(), true);
		cellList.setRowData(0, meters);
		
		// Add cellList to list_view
		RootPanel.get("list_view").add(cellList);
		
		// Initializing the map
	    LatLng myLatLng = LatLng.create(49.2569777, -123.123904);
	    MapOptions myOptions = MapOptions.create();
	    myOptions.setZoom(10.0);
	    myOptions.setCenter(myLatLng);
	    myOptions.setMapTypeId(MapTypeId.ROADMAP);
	    map = GoogleMap.create(Document.get().getElementById("map_canvas"), myOptions);
	    
	    // creating markers and putting them in map
	    MarkerImage icon = MarkerImage.create("/mapIcon.png");
	    Size size = Size.create(5.0, 5.0);
	    icon.setScaledSize(size);
	    for (MeterInfo meter: meters) {
	    	LatLng latlon = LatLng.create(meter.getLatitude(), meter.getLongitude());
	    	MarkerOptions newMarkerOpts = MarkerOptions.create();
	    	
	    	 
		    newMarkerOpts.setPosition(latlon);
		    newMarkerOpts.setMap(map);
		    newMarkerOpts.setIcon(icon);;
		    newMarkerOpts.setTitle("I am a marker");
		    Marker.create(newMarkerOpts);
	    }
	    
	    
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
