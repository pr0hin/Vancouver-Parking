package com.ubc.cpsc310.vancouverparking.client;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindow.CloseClickHandler;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
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
	// ========================================================================

	// Login Fields
	private LoginInfo loginInfo = null;
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	// UI related fields

	private MeterCell metercell = new MeterCell();
	private CellList<MeterInfo> cellList = new CellList<MeterInfo>(metercell);
	private Button filterButton = new Button();
	// Data related fields
	private LinkedHashMap<MeterInfo,Marker> allMeters = new LinkedHashMap<MeterInfo,Marker>();

	// Map related fields
	private GoogleMap map;
	private MarkerImage icon1 = MarkerImage.create("/mapIcon1.png");
	private MarkerImage icon2 = MarkerImage.create("/mapIcon2.png");
	private MarkerImage icon3 = MarkerImage.create("/mapIcon3.png");
	private MarkerImage icon4 = MarkerImage.create("/mapIcon4.png");
	private MarkerImage icon5 = MarkerImage.create("/mapIcon5.png");
	private MarkerImage icon6 = MarkerImage.create("/mapIcon6.png");

	private Button loadMetersButton = new Button("Load Meters");
	private LatLng myLatLng = LatLng.create(49.2569777, -123.123904);
	private MapOptions myOptions = MapOptions.create();
	private final Size iconsize = Size.create(4.0, 4.0);
	private Element selected = null;
	private InfoWindow infoWindow = null;
	private final VirtualPanel fVirtualPanel = new VirtualPanel();

	// Filter UI objects
	private CheckBox checkboxOne = new CheckBox("$1");
	private CheckBox checkboxTwo = new CheckBox("$2");
	private CheckBox checkboxThree = new CheckBox("$3");
	private CheckBox checkboxFour = new CheckBox("$4");
	private CheckBox checkboxFive = new CheckBox("$5");
	
	private ListBox hoursBox = new ListBox(); 	

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
	private final FavoritesServiceAsync favoritesService = GWT
			.create(FavoritesService.class);

	// ON MODULE LOAD 
	// ========================================================================

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Setup Map Configurations
		myOptions.setZoom(13.0);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);

		map = GoogleMap.create(Document.get().getElementById("map_canvas"),
				myOptions);

		// set icon size
		icon1.setScaledSize(iconsize);
		icon2.setScaledSize(iconsize);
		icon3.setScaledSize(iconsize);
		icon4.setScaledSize(iconsize);
		icon5.setScaledSize(iconsize);
		icon6.setScaledSize(iconsize);
		
		// Add filter elements to filterBox
		displayFilterElements();

		// Check login status using login service.
<<<<<<< HEAD
	    LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
	      public void onFailure(Throwable error) { handleError(error);}

	      public void onSuccess(LoginInfo result) {
	    	getMeters();
	        loginInfo = result;
	        displayLoginInfo();
	      }
	    });
	}
	
	private void displayLoginInfo() {
		
		if(loginInfo.isLoggedIn()) {
			// If logged in, show username and logout link
        	String userName = loginInfo.getNickname();
        	String logout = loginInfo.getLogoutUrl();
        	signOutLink.setText(userName);
        	signOutLink.setHref(logout);
        	
        	RootPanel.get("loginInfo").add(signOutLink);
        	// If admin is logged in, show load meter button
        	if (loginInfo.getEmailAddress().equalsIgnoreCase("renniehaylock@gmail.com") || loginInfo.getEmailAddress().equalsIgnoreCase("rohinjpatel@gmail.com")) {
        		loadMetersButton.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
					public void onClick(ClickEvent event) {
						meterService.loadMeters(new AsyncCallback<Void>() {
							public void onFailure(Throwable error) {
								// TODO
							}
=======
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
						loginServiceOnFailure(error);
					}
>>>>>>> 4b4e153b3998cbbee33cbfcb42390ea20c885b25

					public void onSuccess(LoginInfo result) {
						loginServiceOnSuccess(result);
					}

				});
	}

	// RPC SERVICE METHODS
	// ========================================================================

	private void loginServiceOnSuccess(LoginInfo result) {
		meterService.getMeters(new AsyncCallback<List<MeterInfo>>() {
			public void onFailure(Throwable error) {
				meterServiceOnFailure();
			}

			public void onSuccess(List<MeterInfo> meters) {
				meterServiceOnSuccess(meters);
			}
		});

		loginInfo = result;
		displayLoginInfo();
	}

	private void meterServiceOnSuccess(List<MeterInfo> meters) {
		System.out.println("Meters on client: " + meters.size());

		// Adding meters to the cellList
		displayCellList(meters);

		// put meters into allMeters linkedHashmap
		for (int i = 0; i < meters.size(); i++) {
			MeterInfo meter = meters.get(i);
			Marker marker = meterToMarker(meter, i);
			allMeters.put(meter, marker);
		}

		// // Receiving meters from server and putting them in global
		// // allMeters variable
		// for (MeterInfo meter : meters) {
		// 	allMeters.add(meter);
		// }
		// displayMeters(meters);

	}

	private void meterServiceOnFailure() {
		// TODO Auto-generated method stub
	}

	private void loginServiceOnFailure(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		}
	}

	private void displayLoginInfo() {

		if (loginInfo.isLoggedIn()) {
			// If logged in, show username and logout link
			String userName = loginInfo.getNickname();
			String logout = loginInfo.getLogoutUrl();
			signOutLink.setText(userName);
			signOutLink.setHref(logout);

			RootPanel.get("loginInfo").add(signOutLink);
			// If admin is logged in, show load meter button
			if (loginInfo.getEmailAddress().equalsIgnoreCase(
					"andrefurlan@gmail.com")) {
				loadMetersButton
						.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
							public void onClick(ClickEvent event) {
								loadMeterService();
							}
						});
				// Load meter stuff
				loadMetersButton.setStyleName("loadButton");
				RootPanel.get("loadMeters").add(loadMetersButton);
			}
		} else {
			// If not logged in, show sign in link
			String login = loginInfo.getLoginUrl();
			signInLink.setHref(login);
			RootPanel.get("loginInfo").add(signInLink);
		}
	}

	private void loadMeterService() {
		meterService.loadMeters(new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				loadMeterServiceOnFailure();
			}

			@Override
			public void onSuccess(Void result) {
				loadMeterServiceOnSuccess();
				Window.alert("Meters loaded!!");
			}
		});
	}

	private void loadMeterServiceOnSuccess() {
		// TODO Auto-generated method stub

	}

	private void loadMeterServiceOnFailure() {
		// TODO Auto-generated method stub
	}


	// METER AND LIST DISPLAY METHODS
	// ========================================================================

	private void displayCellList(List<MeterInfo> meters) {
		cellList.setPageSize(meters.size());
		cellList.setRowCount(meters.size(), true);
		cellList.setRowData(0, meters);
		cellList.redraw();

		// Add cellList to list_view
		RootPanel.get("list_view").add(cellList);
	}

	private void displayFilterElements() {
		addRateHandler(checkboxOne, 1);
		addRateHandler(checkboxTwo, 2);
		addRateHandler(checkboxThree, 3);
		addRateHandler(checkboxFour, 4);
		addRateHandler(checkboxFive, 5);
		
		RootPanel.get("filterBox").add(filterButton);
		RootPanel.get("filterBox").add(checkboxOne);
		RootPanel.get("filterBox").add(checkboxTwo);
		RootPanel.get("filterBox").add(checkboxThree);
		RootPanel.get("filterBox").add(checkboxFour);
		RootPanel.get("filterBox").add(checkboxFive);
		RootPanel.get("filterBox").add(hoursBox);

		hoursBox.addItem("30 mins");
		hoursBox.addItem("1 hour");
		hoursBox.addItem("2 hours");
		hoursBox.addItem("3 hours");
		hoursBox.addItem("+4 hours");
		filterButton.setText("Filter to $3");
	}
	// Helper for displayFilterElements()
	private void addRateHandler(CheckBox cb, final int rate) {
		cb.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
		      public void onClick(ClickEvent event) {
		    		  filterMeters(rate);
		      }
		    });
	}

	private void filterMeters(int rate) {
		// Get data from checkboxes
		boolean cb1 = checkboxOne.getValue();
		boolean cb2 = checkboxTwo.getValue();
		boolean cb3 = checkboxThree.getValue();
		boolean cb4 = checkboxFour.getValue();
		boolean cb5 = checkboxFive.getValue();
		
		int index = 0;
		// iterate over allMeters hashmap and add to map
		Iterator<Entry<MeterInfo, Marker>> entries = allMeters.entrySet().iterator();

		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			MeterInfo meter = (MeterInfo) thisEntry.getKey();
			Marker meterMarker = (Marker) thisEntry.getValue();
			if ((cb1 && meter.getRate() == 1) ||
				(cb2 && meter.getRate() == 2) ||
				(cb3 && meter.getRate() == 3) ||
				(cb4 && meter.getRate() == 4) ||
				(cb5 && meter.getRate() == 5)) {
				
				meterMarker.setVisible(true);
			} else {
				meterMarker.setVisible(false);
			}
			
//			if (meter.getRate() == rate) {
//				meterMarker.setVisible(true);
//				//cellList.getRowElement(index).setAttribute("display", "inline");
//			} else {
//				meterMarker.setVisible(false);
//				//cellList.getRowElement(index).setAttribute("display", "none");
//				cellList.getRowElement(index).addClassName("invisible");
//			}
			index++;
		}
	}

	private Marker meterToMarker(final MeterInfo meter, int index) {
		
		// Create latlon from meter
		LatLng latlon = LatLng.create(meter.getLatitude(),
				meter.getLongitude());
		// create marker options
		MarkerOptions newMarkerOpts = MarkerOptions.create();
		newMarkerOpts.setPosition(latlon);
		
		if (meter.getRate() == 1.0) {
			newMarkerOpts.setIcon(icon1);
		} else if (meter.getRate() == 2.0) {
			newMarkerOpts.setIcon(icon2);
		} else if (meter.getRate() == 3.0) {
			newMarkerOpts.setIcon(icon3);
		} else if (meter.getRate() == 4.0) {
			newMarkerOpts.setIcon(icon4);
		} else if (meter.getRate() == 5.0) {
			newMarkerOpts.setIcon(icon5);
		} else if (meter.getRate() == 6.0) {
			newMarkerOpts.setIcon(icon6);
		} else {
			newMarkerOpts.setIcon(icon1);
		}
		
		
		// create marker
		final Marker marker = Marker.create(newMarkerOpts);

		final int i = index;

		// adding clickListener to marker
		marker.addClickListener(new ClickHandler() {
			@Override
			public void handle(MouseEvent event) {
				// code for selecting proper item in list
				Element element = cellList.getRowElement(i);
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
				drawInfoWindow(marker, event, meter);
			}
		});
		marker.setMap(map);
		return marker;
	}

	private void drawInfoWindow(Marker marker, MouseEvent mouseEvent,
			final MeterInfo meter) {

		// Close the existing info window
		if (infoWindow != null) {
			infoWindow.close();
		}

		// Check for the marker
		if (marker == null || mouseEvent == null) {
			return;
		}

		// Button and HTMLPanel init
		Button addToFavoritesButton = new Button();
		Label meterNumber = new Label("Meter #: "
				+ String.valueOf(meter.getNumber()));
		final HTMLPanel infoHTMLPanel;

		// Button Styling - Bootstrap
		addToFavoritesButton.setText("+");

		// button click handler
		addToFavoritesButton
				.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						favClickHandler(meter.getNumber());
					}
				});
		// HTML Panel init and adding button
		infoHTMLPanel = new HTMLPanel(marker.getTitle());
		infoHTMLPanel.add(meterNumber);
		infoHTMLPanel.add(addToFavoritesButton);

		fVirtualPanel.attach(infoHTMLPanel);

		// InfoWindow init and content set
		InfoWindowOptions options = InfoWindowOptions.create();
		options.setContent(infoHTMLPanel.getElement());
		infoWindow = InfoWindow.create(options);
		infoWindow.addCloseClickListener(new CloseClickHandler() {

			@Override
			public void handle() {
				fVirtualPanel.remove(infoHTMLPanel);

			}
		});
		infoWindow.open(map, marker);

		map.panTo(marker.getPosition());
	}

	private void favClickHandler(long meterNumber) {
		favoritesService.addMeter(meterNumber, new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				// TODO
				Window.alert("Shit!!");
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method
				Window.alert("Meter added!!");
			}
		});

		favoritesService.getFavorites(new AsyncCallback<Long[]>() {
			public void onFailure(Throwable error) {
				// TODO
				Window.alert("Shit!!");
			}

			public void onSuccess(Long[] favorites) {
				Window.alert("Yay");
				System.out.println("Favorite meters " + favorites.toString());
			}

		});

	}

	
	//this class is necessary to make the button inside the infoWindow work
	private static class VirtualPanel extends ComplexPanel {

		public void attach(Widget w) {
			w.removeFromParent();
			getChildren().add(w);
			adopt(w);
		}

		@Override
		public boolean isAttached() {
			return true;
		}
	}
}
