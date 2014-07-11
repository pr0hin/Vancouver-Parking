package com.ubc.cpsc310.vancouverparking.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel; //
import com.google.gwt.user.client.ui.Anchor; // 
import com.google.gwt.user.client.ui.Button; //
import com.google.gwt.user.client.ui.CheckBox; //
import com.google.gwt.user.client.ui.FlexTable; //
import com.google.gwt.user.client.ui.HorizontalPanel; //
import com.google.gwt.user.client.ui.ListBox; //
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindow.CloseClickHandler;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.Marker.ClickHandler;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Point;
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
	private String location;
	// Data related fields

	private LinkedHashMap<MeterInfo, Marker> allMeters = new LinkedHashMap<MeterInfo, Marker>();
	private List<Long> favorites = new ArrayList<Long>();

	// Map related fields
	private GoogleMap map;
	private MarkerImage icon1 = MarkerImage.create("/mapIcon1.png");
	private MarkerImage icon2 = MarkerImage.create("/mapIcon2.png");
	private MarkerImage icon3 = MarkerImage.create("/mapIcon3.png");
	private MarkerImage icon4 = MarkerImage.create("/mapIcon4.png");
	private MarkerImage icon5 = MarkerImage.create("/mapIcon5.png");
	private MarkerImage icon6 = MarkerImage.create("/mapIcon6.png");
	private MarkerImage iconFav = MarkerImage.create("/bigStar.png");
	private MarkerImage mysteryicon = MarkerImage.create("/mysteryIcon.png");
	private MarkerImage doucheicon = MarkerImage.create("/doucheIcon.png");

	private Button loadMetersButton = new Button("Load Meters");
	private Button twitterButton = new Button("Twitter Updates");
	private LatLng myLatLng = LatLng.create(49.2569777, -123.123904);
	private MapOptions myOptions = MapOptions.create();
	private final Size iconsize = Size.create(4.0, 4.0);
	private final Size starSize = Size.create(30.0, 30.0);
	private Element selected = null;
	private InfoWindow infoWindow = null;
	private final VirtualPanel fVirtualPanel = new VirtualPanel();

	// Filter UI objects
	private CheckBox checkboxOne = new CheckBox("$1");
	private CheckBox checkboxOneTwentyFive = new CheckBox("$1.25");
	private CheckBox checkboxOneFifty = new CheckBox("$1.50");
	private CheckBox checkboxTwo = new CheckBox("$2");
	private CheckBox checkboxTwoFifty = new CheckBox("$2.50");
	private CheckBox checkboxThree = new CheckBox("$3");
	private CheckBox checkboxFour = new CheckBox("$4");
	private CheckBox checkboxFive = new CheckBox("$5");

	
	private ListBox hoursBox = new ListBox(); 	
	// Search UI objects
	private AbsolutePanel searchPanel = new AbsolutePanel();
	private SuggestBox addressBox;
	private Button addressButton = new Button();
	private Geocoder geocode;
	private Marker locationmarker = Marker.create();
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	

	private CheckBox checkboxSix = new CheckBox("$6");
	private boolean showingTweets = false;
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
	private final SearchHistoryServiceAsync historyService = GWT.create(SearchHistoryService.class);

	// ON MODULE LOAD

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		addTwitterButton();

		// Setup Map Configurations
		myOptions.setZoom(13.0);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);

		map = GoogleMap.create(Document.get().getElementById("map_canvas"),
				myOptions);
		historyService.getHistory(new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> history) {
				for (int i = 0; i <history.size(); i++) {
					oracle.add(history.get(i));
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				
			}
			
		}
				);
		

		addressBox = new SuggestBox(oracle);
		setUpAddressSearch();
		
		
		// set icon size
		icon1.setScaledSize(iconsize);
		icon2.setScaledSize(iconsize);
		icon3.setScaledSize(iconsize);
		icon4.setScaledSize(iconsize);
		icon5.setScaledSize(iconsize);
		icon6.setScaledSize(iconsize);
		iconFav.setScaledSize(starSize);

		// Add filter elements to filterBox
		displayFilterElements();

		// Check login status using login service.

		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					public void onFailure(Throwable error) {
						loginServiceOnFailure(error);
					}

					public void onSuccess(LoginInfo result) {
						loginServiceOnSuccess(result);
					}

				});
	}

	private void addTwitterButton() {
		RootPanel.get("twitterBox").setVisible(false);
		twitterButton.setStyleName("loadButton");
		RootPanel.get("loadMeters").add(twitterButton);
		twitterButton.addClickHandler((new com.google.gwt.event.dom.client.ClickHandler() {
			public void onClick(ClickEvent event) {
				if (showingTweets) {
					RootPanel.get("twitterBox").setVisible(false);
					showingTweets = false;
					twitterButton.setText("Show Tweets");
				} else {
					RootPanel.get("twitterBox").setVisible(true);
					showingTweets = true;
					twitterButton.setText("Hide Tweets");
				}
			}
		}));
	}

	// RPC SERVICE METHODS
	// ========================================================================

	private void loginServiceOnSuccess(LoginInfo result) {

		favoritesService.getFavorites(new AsyncCallback<Long[]>() {
			public void onFailure(Throwable error) {
				getFavoritesServiceOnFailure();
			}

			public void onSuccess(Long[] favorites) {
				getFavoritesServiceOnSuccess(favorites);
				System.out.println("Favorite meters " + favorites.toString());
			}

		});
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
		//displayTwitterButton();
	}

	private void getFavoritesServiceOnSuccess(Long[] favorites) {
		for (Long m : favorites)
			this.favorites.add(m);
	}

	private void getFavoritesServiceOnFailure() {
		// do nothing
	}

	private void meterServiceOnSuccess(List<MeterInfo> meters) {
		System.out.println("Meters on client: " + meters.size());

		// Adding meters to the cellList
		displayCellList(meters);

		// put meters into allMeters linkedHashmap
		for (int i = 0; i < meters.size(); i++) {
			MeterInfo meter = meters.get(i);
			meter.setFavorite(favorites);
			Marker marker = meterToMarker(meter, i);
			allMeters.put(meter, marker);
		}

	}

	private void meterServiceOnFailure() {
		// do nothing
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
	
//	private void displayTwitterButton() {
//		twitterButton.setStyleName("TwitterButton");
//		RootPanel.get("loadMeters").add(twitterButton);
//		
//		final AbsolutePanel panel = new AbsolutePanel();
//		RootPanel.get("twitterBox").add(panel);
//		twitterButton
//		.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
////			public void onClick(ClickEvent event) {
////				final TwitterPopUp tPop = new TwitterPopUp();
////				panel.add(tPop);
////				tPop.show();
//				/*
//				tPop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
//					public void setPosition(int offsetWidth, int offsetHeight) {
//						int left = (Window.getClientWidth() - offsetWidth) / 3;
//						int top = (Window.getClientHeight() - offsetHeight) / 3;
//						tPop.setPopupPosition(left, top);
//					}
//				});*/
//			}
//		});
		
		
	//}
	

	private void loadMeterService() {
		meterService.loadMeters(new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				loadMeterServiceOnFailure();
			}

			@Override
			public void onSuccess(Void result) {
				loadMeterServiceOnSuccess();
			}
		});
	}

	private void loadMeterServiceOnSuccess() {
		Window.alert("Meters loaded!!");

	}

	private void loadMeterServiceOnFailure() {
		Window.alert("Error, meters not loaded");
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

		addRateHandler(checkboxOne);
		addRateHandler(checkboxOneTwentyFive);
		addRateHandler(checkboxOneFifty);
		addRateHandler(checkboxTwo);
		addRateHandler(checkboxTwoFifty);
		addRateHandler(checkboxThree);
		addRateHandler(checkboxFour);
		addRateHandler(checkboxFive);
		addRateHandler(checkboxSix);
		addHoursHandler(hoursBox);

		RootPanel.get("priceBox").add(checkboxOne);
		RootPanel.get("priceBox").add(checkboxOneTwentyFive);
		RootPanel.get("priceBox").add(checkboxOneFifty);
		RootPanel.get("priceBox").add(checkboxTwo);
		RootPanel.get("priceBox").add(checkboxTwoFifty);
		RootPanel.get("priceBox").add(checkboxThree);
		RootPanel.get("priceBox").add(checkboxFour);
		RootPanel.get("priceBox").add(checkboxFive);
		RootPanel.get("priceBox").add(checkboxSix);
		RootPanel.get("hourBox").add(hoursBox);
		hoursBox.addItem("No selection");
		hoursBox.addItem("30 mins");
		hoursBox.addItem("1 hour");
		hoursBox.addItem("2 hours");
		hoursBox.addItem("3 hours");
		hoursBox.addItem("+4 hours");
	}

	// Helper to addClickHandler to Hours ListBox
	private void addHoursHandler(final ListBox lb) {
		lb.addChangeHandler(new com.google.gwt.event.dom.client.ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				filterMeters();
				int selectedIndex = hoursBox.getSelectedIndex();
				String hoursString = hoursBox.getItemText(selectedIndex);
				System.out.println(hoursString);
			}
		});
	}

	// Helper to addClickHandler to Rate CheckBoxes
	private void addRateHandler(CheckBox cb) {
		cb.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
			public void onClick(ClickEvent event) {
				filterMeters();
			}
		});
	}

	// Helper for getting hours from listbox
	private double getHours(ListBox lb) {
		double hours = 0.0;
		int selectedIndex = lb.getSelectedIndex();
		String hoursString = lb.getItemText(selectedIndex);

		if (hoursString.equals("30 mins")) {
			hours = 0.50;
		} else if (hoursString.equals("No selection")) {
			hours = 0.0;
		} else if (hoursString.equals("1 hour")) {
			hours = 1.0;
		} else if (hoursString.equals("2 hours")) {
			hours = 2.0;
		} else if (hoursString.equals("3 hours")) {
			hours = 3.0;
		} else if (hoursString.equals("+4 hours")) {
			hours = 4.0;
		}
		return hours;
	}

	// Filters meters on map by rate
	private void filterMeters() {
		// Get data from checkboxes
		boolean cb1 = checkboxOne.getValue();
		boolean cb1_25 = checkboxOneTwentyFive.getValue();
		boolean cb1_50 = checkboxOneFifty.getValue();
		boolean cb2 = checkboxTwo.getValue();
		boolean cb2_50 = checkboxTwoFifty.getValue();
		boolean cb3 = checkboxThree.getValue();
		boolean cb4 = checkboxFour.getValue();
		boolean cb5 = checkboxFive.getValue();
		boolean cb6 = checkboxSix.getValue();

		double hours = getHours(hoursBox);
		System.out.println(hours);

		int index = 0;
		// iterate over allMeters hashmap and add to map
		Iterator<Entry<MeterInfo, Marker>> entries = allMeters.entrySet()
				.iterator();

		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			MeterInfo meter = (MeterInfo) thisEntry.getKey();
			Marker meterMarker = (Marker) thisEntry.getValue();

			if (hours == 0.0 || meter.getTimeLimit() >= hours) {
				if ((cb1 && meter.getRate() == 1.0)
						|| (cb1_25 && meter.getRate() == 1.25)
						|| (cb1_50 && meter.getRate() == 1.50)
						|| (cb2 && meter.getRate() == 2.0)
						|| (cb2_50 && meter.getRate() == 2.50)
						|| (cb3 && meter.getRate() == 3.0)
						|| (cb4 && meter.getRate() == 4.0)
						|| (cb5 && meter.getRate() == 5.0)
						|| (cb6 && meter.getRate() == 6.0)) {

					meterMarker.setVisible(true);
				} else {
					if (!cb1 && !cb1_25 && !cb1_50 && !cb2 && !cb2_50 && !cb3
							&& !cb4 && !cb5 && !cb6) {
						meterMarker.setVisible(true);
					} else {
						meterMarker.setVisible(false);
					}
				}
				;

			} else {
				meterMarker.setVisible(false);
			}
			// //cellList.getRowElement(index).setAttribute("display", "none");
			index++;
		}
	}

	private Marker meterToMarker(final MeterInfo meter, int index) {

		// Create latlon from meter
		LatLng latlon = LatLng
				.create(meter.getLatitude(), meter.getLongitude());
		// create marker
		final Marker marker = Marker.create();
		marker.setPosition(latlon);
		assignMarkertoRate(meter, marker);
		
	
		

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

	private boolean isFavorite(final MeterInfo meter) {
		// return favorites.contains(meter);
		return meter.isFavorite();

	}

	private void assignMarkertoRate(final MeterInfo meter, Marker marker) {
		if (isFavorite(meter)) {
			System.out.println(meter.getNumber());
			marker.setIcon(iconFav);
			iconFav.setAnchor(Point.create(15, 15));
			marker.setZIndex(100000);

		} else if (meter.getRate() == 0.0) {
			marker.setIcon(mysteryicon);
		} else if (meter.getRate() < 2.0) {
			marker.setIcon(icon1);
		} else if (meter.getRate() < 3.0) {
			marker.setIcon(icon2);
		} else if (meter.getRate() < 4.0) {
			marker.setIcon(icon3);
		} else if (meter.getRate() < 5.0) {
			marker.setIcon(icon4);
		} else if (meter.getRate() < 6.0) {
			marker.setIcon(icon5);
		} else if (meter.getRate() < 7.0) {
			marker.setIcon(icon6);
		} else {
			marker.setIcon(doucheicon);
		}

	}

	private void drawInfoWindow(final Marker marker,
			final MouseEvent mouseEvent, final MeterInfo meter) {

		
		
		// Close the existing info window
		if (infoWindow != null) {
			infoWindow.close();
		}

		// Check for the marker
		if (marker == null || mouseEvent == null) {
			return;
		}

		// Button and HTMLPanel init
		final FlexTable meterInfoTable = new FlexTable();
		final HorizontalPanel hp1 = new HorizontalPanel();
		final HorizontalPanel hp2 = new HorizontalPanel();
		final HorizontalPanel hp3 = new HorizontalPanel();
		final Button favoritesButton = new Button();

		Label meterNumber = new Label("Meter #: "
				+ String.valueOf(meter.getNumber()));
		meterNumber.setStyleName("meterNumberLabel");
		Label meterRate = new Label("Rate: $"
				+ String.valueOf(meter.getRate()));
		Label meterTimeInEffect = new Label("Time in Effect: "
				+ String.valueOf(meter.getTimeInEffect()));

		
		final HTMLPanel infoHTMLPanel;

		// Button Styling - Bootstrap
		if (isFavorite(meter)) {
			favoritesButton.setStyleName("unFavButton");
		} else {
			favoritesButton.setStyleName("favButton");
		}

		// button click handler
		favoritesButton
				.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if (loginInfo.isLoggedIn()) {
							favClickHandler(meter);
							assignMarkertoRate(meter, marker);
							drawInfoWindow(marker, mouseEvent, meter);
						} else {
							Window.alert("Please sign in to save favorites");
						}
					}
				});
		// HTML Panel init and adding button
		infoHTMLPanel = new HTMLPanel(marker.getTitle());
//		infoHTMLPanel.add(meterNumber);

		if (loginInfo.isLoggedIn()) {
			hp1.add(favoritesButton);
		}
		hp1.add(meterNumber);
		meterInfoTable.setWidget(0, 0, hp1);
		meterInfoTable.setWidget(1, 0, meterRate);
		meterInfoTable.setWidget(2, 0, meterTimeInEffect);
		hp1.setStyleName("infoWindowFirstRow");
		meterInfoTable.setCellPadding(2);
		
		infoHTMLPanel.add(meterInfoTable);
		
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

	private void favClickHandler(MeterInfo meter) {
		if (!isFavorite(meter)) {
			meter.setFavorite(true);
			favoritesService.addMeter(meter.getNumber(),
					new AsyncCallback<Void>() {
						public void onFailure(Throwable error) {
						}

						@Override
						public void onSuccess(Void result) {
						}
					});

		} else {
			meter.setFavorite(false);
			favoritesService.removeMeter(meter.getNumber(),
					new AsyncCallback<Void>() {
						public void onFailure(Throwable error) {
						}

						@Override
						public void onSuccess(Void result) {
						}
					});

		}
	}

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

	private void setUpAddressSearch() {
		addressBox.setStyleName("textbox");
		addressBox.setText("Search for an address");
		
		addressBox.getValueBox().addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addressBox.setFocus(true);
				if ((addressBox.isEnabled()) && (addressBox.getText().equals("Search for an address"))) {
					addressBox.setText("");
				}
			}
		});
		addressButton.setStyleName("searchbutton");
		addressButton.setText("search");
		addressBox.addKeyPressHandler(new com.google.gwt.event.dom.client.KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if ( (!addressBox.isSuggestionListShowing()) && (event.getCharCode()==KeyCodes.KEY_ENTER) ) {
					findlocation();
				}
				
			}
			
		});


		addressButton.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() { 	
			
			@Override
		public void onClick(ClickEvent event) {
				findlocation();
				
				
			
		}}); 
		
		searchPanel.add(addressBox);
		searchPanel.add(addressButton);
		searchPanel.setStyleName("search");
		
		RootPanel.get("search").add(searchPanel);
	}



	public void findlocation() {
		
		location = addressBox.getText().toUpperCase().trim();

		

		geocode = Geocoder.create();

		if (location.equals("")) {
			Window.alert("Please enter a valid address");
		} else {
			
		
			GeocoderRequest request = GeocoderRequest.create();
			LatLng ne = LatLng.create(49.302265, -122.902679);
			LatLng sw = LatLng.create(49.216910, -123.238449);
			request.setRegion("ca");
			request.setBounds(LatLngBounds.create(ne, sw));
		
			request.setAddress(location);
			geocode.geocode(request, new Geocoder.Callback() {
				
				@Override
				public void handle(JsArray<GeocoderResult> a, GeocoderStatus b) {
					
				
					if (b == GeocoderStatus.OK) {
						
						historyService.addHistory(location, new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable error) {
								// TODO
								Window.alert("Shit!!");
							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method
							
							}
						});
						oracle.add(location);
						
						GeocoderResult result = a.shift();
						LatLng latlng = result.getGeometry().getLocation();
						
						
						locationmarker.setPosition(latlng);
						locationmarker.setMap(map);
						map.panTo(latlng);
						map.setZoom(16);
						
						}
					}
				
			});
			
		}
	}


}
