package com.ubc.cpsc310.vancouverparking.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.Size;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VancouverParking implements EntryPoint {
	// FIELDS
	// ========================
	// UI related fields

	private MeterCell metercell = new MeterCell();
	private CellList<MeterInfo> cellList = new CellList<MeterInfo>(metercell);
	private Button loadMetersButton = new Button("Load Meters");
	private Button getMetersButton = new Button("Get Meters");
	private HorizontalPanel panel = new HorizontalPanel();

	// Data related fields
	private List<MeterInfo> meters = new ArrayList<MeterInfo>();

	// Map related fields
	private GoogleMap map;
	private MarkerImage icon = MarkerImage.create("/mapIcon.png");
	private LatLng myLatLng = LatLng.create(49.2569777, -123.123904);
	private MapOptions myOptions = MapOptions.create();
	private final Size iconsize = Size.create(5.0, 5.0);
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

	private final MeterServiceAsync meterService = GWT
			.create(MeterService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		displayMap();
		panel.addStyleName("panel");
		loadMetersButton.addStyleName("loadButton");
		getMetersButton.addStyleName("getButton");
		panel.add(loadMetersButton);
		panel.add(getMetersButton);

		loadMetersButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadMeters();
			}
		});
		getMetersButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getMeters();
			}
		});
		RootPanel.get("list_view").add(panel);
		// getMeters();
	}

	private void loadMeters() {
		meterService.loadMeters(new AsyncCallback<Boolean>() {
			public void onFailure(Throwable error) {
				// TODO
			}

			public void onSuccess(Boolean res) {

			}
		});
	}

	private void getMeters() {
		meterService.getMeters(new AsyncCallback<List<MeterInfo>>() {
			public void onFailure(Throwable error) {
				// TODO
			}

			public void onSuccess(List<MeterInfo> meters) {
				if (meters != null)
					displayMeters(meters);
			}

		});
	}

	private void displayMeters(List<MeterInfo> meters) {

		// Adding meters to the cellList
		cellList.setPageSize(meters.size());
		cellList.setRowCount(meters.size(), true);
		cellList.setRowData(0, meters);

		// Add cellList to list_view
		RootPanel.get("list_view").add(cellList);
		// creating markers and putting them in map
		icon.setScaledSize(iconsize);
		for (MeterInfo meter : meters) {
			LatLng latlon = LatLng.create(meter.getLatitude(),
					meter.getLongitude());
			MarkerOptions newMarkerOpts = MarkerOptions.create();

			newMarkerOpts.setPosition(latlon);
			newMarkerOpts.setMap(map);
			newMarkerOpts.setIcon(icon);
			;
			newMarkerOpts.setTitle("I am a marker");
			Marker.create(newMarkerOpts);
		}

	}

	private void displayMap() {

		// Initializing the map

		myOptions.setZoom(10.0);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		map = GoogleMap.create(Document.get().getElementById("map_canvas"),
				myOptions);
	}

	public void plotMeters() {
		// Add implementation here
	}

	public void reloadList() {
		// Add implementation here
	}

}
