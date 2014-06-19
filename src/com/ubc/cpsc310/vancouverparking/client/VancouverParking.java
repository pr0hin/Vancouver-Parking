package com.ubc.cpsc310.vancouverparking.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.maps.gwt.client.ArrayHelper;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.MapTypeStyle;
import com.google.maps.gwt.client.MapTypeStyleFeatureType;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.Marker.ClickHandler;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;
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
	private Button filterButton = new Button();
	// Data related fields
	private List<MeterInfo> allMeters = new ArrayList<MeterInfo>();

	// Map related fields
	private GoogleMap map;
	private MarkerImage icon = MarkerImage.create("/mapIcon.png");

	private Button loadMetersButton = new Button("Load Meters"); // //////////
	private HorizontalPanel panel = new HorizontalPanel(); // //////////
	private LatLng myLatLng = LatLng.create(49.2569777, -123.123904);
	private MapOptions myOptions = MapOptions.create();
	private final Size iconsize = Size.create(5.0, 5.0);
	private Element selected = null;
	private InfoWindow lastInfoWindow = null;
	private List<Marker> markers = new ArrayList<Marker>();

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
		myOptions.setZoom(13.0);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);

		map = GoogleMap.create(Document.get().getElementById("map_canvas"),
				myOptions);

		loadMetersButton.addStyleName("loadButton"); // /////////////////
		panel.add(loadMetersButton); // ///////////////////

		// Add filter elements to filterBox
		RootPanel.get("filterBox").add(filterButton);
		RootPanel.get("filterBox").add(panel); // //////////////
		filterButton.setText("Filter to $3");
		filterButton
				.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
					public void onClick(ClickEvent event) {
						displayMeters(filterMetersByRate(3));
					}
				});

		loadMetersButton
				.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
					public void onClick(ClickEvent event) {
						meterService.loadMeters(new AsyncCallback<Void>() {
							public void onFailure(Throwable error) {
								// TODO
							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub

							}
						});
					}
				});

		getMeters();
	}

	private void getMeters() {

		meterService.getMeters(new AsyncCallback<List<MeterInfo>>() {
			public void onFailure(Throwable error) {
				// TODO
			}
			public void onSuccess(List<MeterInfo> meters) {

				System.out.println("Meters on client: "+meters.size());
				// Receiving meters from server and putting them in global
				// allMeters variable
				for (MeterInfo meter : meters) {
					allMeters.add(meter);
				}
				displayMeters(meters);
			}

		});
	}

	private void displayMeters(List<MeterInfo> meters) {


		clearMarkers();
		// Adding meters to the cellList

		cellList.setPageSize(meters.size());
		cellList.setRowCount(meters.size(), true);
		cellList.setRowData(0, meters);
		cellList.redraw();

		// Add cellList to list_view
		RootPanel.get("list_view").add(cellList);

		// creating markers and putting them in map
		icon.setScaledSize(iconsize);

		for (int i = 0; i < meters.size(); i++) {
			MeterInfo meter = meters.get(i);
			LatLng latlon = LatLng.create(meter.getLatitude(),
					meter.getLongitude());
			MarkerOptions newMarkerOpts = MarkerOptions.create();
			newMarkerOpts.setPosition(latlon);
			newMarkerOpts.setIcon(icon);
			final Marker marker = Marker.create(newMarkerOpts);
			markers.add(marker);


			final int index = i;
			// creating popup
			InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
			infowindowOpts.setContent("Meter #: "
					+ String.valueOf(meter.getNumber()));
			final InfoWindow infowindow = InfoWindow.create(infowindowOpts);

			// adding clickListener to marker
			marker.addClickListener(new ClickHandler() {
				@Override
				public void handle(MouseEvent event) {
					// code for selecting proper item in list
					Element element = cellList.getRowElement(index);
					element.scrollIntoView();
					element.focus();
					element.setClassName("selected");
					if (selected == null) {
						selected = element;
					} else {
						selected.removeClassName("selected");
						selected = element;
					}

					// code for displaying popup
					if (lastInfoWindow == null) {
						infowindow.open(map, marker);
						lastInfoWindow = infowindow;
					} else {
						infowindow.open(map, marker);
						lastInfoWindow.close();
						lastInfoWindow = infowindow;
					}
				}
			});
		}
		reloadMarkers();

	}

	public void reloadMarkers() {
		for (Marker m : markers) {
			m.setMap(map);
		}
	}

	public void clearMarkers() {
		for (Marker m : markers) {
			m.setMap((GoogleMap) null);
		}
		markers.clear();
	}


	public List<MeterInfo> filterMetersByRate(int rate) {
		List<MeterInfo> filteredMeters = new ArrayList<MeterInfo>();
		for (MeterInfo m : allMeters) {
			if (m.getRate() == rate) {
				filteredMeters.add(m);
			}
		}
		return filteredMeters;
	}

}
