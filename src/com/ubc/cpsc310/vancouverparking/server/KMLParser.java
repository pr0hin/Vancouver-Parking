package com.ubc.cpsc310.vancouverparking.server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

public class KMLParser extends RemoteServiceServlet {
	private int number;
	private double latitude;
	private double longitude;
	private float rate;
	private String timeInEffect;
	private boolean creditCard;
	private String type;
	private float timeLimit;
	private Meter meter;
	private List<Meter> meters = new ArrayList<Meter>();
	List<Feature> placemarks;
	public KMLParser() {
		 try {
		 URL meters = new URL("http://data.vancouver.ca/download/kml/parking_meter_rates_and_time_limits.kmz");
		 HttpURLConnection connection = (HttpURLConnection) meters.openConnection();

		 ZipInputStream parkingmeters = new ZipInputStream(connection.getInputStream());
		 parkingmeters.getNextEntry();
		 Kml kml = Kml.unmarshal(parkingmeters);
			Document doc = (Document) kml.getFeature();

			List<StyleSelector> styles = doc.getStyleSelector();

			List<Feature> folders = doc.getFeature();

			Folder folder = (Folder) folders.get(0);
			placemarks = folder.getFeature();
		 } catch (Exception e) {
		// TODO Auto-generated catch block
		 e.printStackTrace();
		 } 
	
		
	}

	

	public List<Meter> parse() {
		for (Feature pm : placemarks) {
			number = new Integer(pm.getName());
			String description = pm.getDescription();
			parseDescription(description);
			Placemark placemark = (Placemark) pm;
			Point point = (Point) placemark.getGeometry();
			if (point != null) {
				this.latitude = point.getCoordinates().get(0).getLatitude();
				this.longitude = point.getCoordinates().get(0).getLongitude();
			} else {
				latitude = 0;
				longitude = 0;
			}
			meter = new Meter(number, type, rate, timeLimit, creditCard,
					timeInEffect, latitude, longitude);
			meters.add(meter);

		}
		return meters;

	}

	private void parseDescription(String desc) {
		String delims = "<br>";
		String[] tokens = desc.split(delims);
		Matcher headtype = Pattern.compile("Single|Twin|Single Motorbike")
				.matcher(tokens[1]);
		if (headtype.find()) {
			this.type = headtype.group(0);
		} else {
			this.type = "Unknown";
		}

		Matcher timelimit = Pattern.compile("([1-9])|30|no time limit")
				.matcher(tokens[2]);
		if (timelimit.find()) {
			if (timelimit.group(0).equals("no time limit")) {
				this.timeLimit = 0;
			} else {
				this.timeLimit = Float.parseFloat(timelimit.group(0));
			}
		} else {
			this.timeLimit = -1;
		}
		Matcher rate = Pattern.compile("[1-9]|\\s.[0-9][0-9]").matcher(
				tokens[3]);
		if (rate.find()) {

			this.rate = Float.parseFloat(rate.group(0));
		} else {
			this.rate = 0;
		}

		Matcher creditcard = Pattern.compile("CREDIT_CARD").matcher(tokens[4]);
		if (creditcard.find()) {
			this.creditCard = true;
		} else {
			this.creditCard = false;
		}
		Matcher timeineffect = Pattern
				.compile(
						"[1-9]:[0-9][0-9]\\s(P|A)?M\\sTO\\s[1-9]?[0-9]:[0-9][0-9]\\s(P|A)?M")
				.matcher(tokens[5]);
		if (timeineffect.find()) {
			this.timeInEffect = timeineffect.group(0);
		} else {
			this.timeInEffect = "Unknown";
		}

	}

	public List<Meter> getMeters() {
		return meters;
	}



	public List<Feature> getPlacemarks() {
		return placemarks;
	}

	// METHODS USED FOR TESTING PARSING
	public List<Meter> getMetersWithoutCoord() {
		List<Meter> meterswithoutcoord = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if ((meter.getLatitude() == 0) || (meter.getLongitude() == 0)) {
				meterswithoutcoord.add(meter);
				System.out.println(meter.getNumber());

			}
		}
		return meterswithoutcoord;
	}


	public List<Meter> getMetersFailingDescriptionParsing() {
		List<Meter> metersfailingdescriptionparsing = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if ((meter.getType().equals("Unknown")) || (meter.getRate() == 0)
					|| (meter.getTimeLimit() == 0) || (!meter.isCreditCard())
					|| (meter.getTimeInEffect().equals("Unknown"))) {
				metersfailingdescriptionparsing.add(meter);
				System.out.println(meter.getNumber());
			}
		}
		return metersfailingdescriptionparsing;
	}

	public List<Meter> getMetersFailingTypeParsing() {
		List<Meter> metersFailingParsing = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if (meter.getType().equals("Unknown")) {
				System.out.println(meter.getNumber());
			}
		}
		return metersFailingParsing;
	}

	public List<Meter> getMetersFailingRateParsing() {
		List<Meter> metersFailingParsing = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if (meter.getRate() == 0) {
				System.out.println(meter.getNumber());
			}
		}
		return metersFailingParsing;
	}

	public List<Meter> getMetersFailingTimeLimit() {
		List<Meter> metersFailingParsing = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if (meter.getTimeLimit() == -1) {
				System.out.println(meter.getNumber());
			}
		}
		return metersFailingParsing;
	}
	public List<Meter> getMetersFailingCreditCard() {
		List<Meter> metersFailingParsing = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if (!meter.isCreditCard()) {
				System.out.println(meter.getNumber());
			}
		}
		return metersFailingParsing;
	}

	public List<Meter> getMetersFailingTimeInEffect() {
		List<Meter> metersFailingParsing = new ArrayList<Meter>();
		for (Meter meter : meters) {
			if (meter.getTimeInEffect().equals("Unknown")) {
				System.out.println(meter.getNumber());
			}
		}
		return metersFailingParsing;
	}

}


