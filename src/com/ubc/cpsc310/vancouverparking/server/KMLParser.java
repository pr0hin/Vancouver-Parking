package com.ubc.cpsc310.vancouverparking.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.annotations.Persistent;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

public class KMLParser {
	private int number;
	private double latitude;
	private double longitude;
	private double rate;
	private String timeineffect;
	private boolean creditCard;
	private String type;
	private float timelimit;
	private Meter meter;
	private List<Meter> meters = new ArrayList<Meter>();

	public static void main(String [ ] args)
	{	
		File parkingmeters = new File(
				"/Users/rohinpatel/Desktop/310/VancouverParking/src/com/ubc/cpsc310/vancouverparking/server/parking_meter_rates_and_time_limits.kml");
		Kml kml = Kml.unmarshal(parkingmeters);
		Document doc = (Document) kml.getFeature();

		List<StyleSelector> styles = doc.getStyleSelector();
		
		List<Feature> placemarks;
		List<Feature> folders = doc.getFeature();

		Folder folder = (Folder) folders.get(0);
		placemarks = folder.getFeature();
		String desc = placemarks.get(0).getDescription();
		System.out.println(desc);
		String delims = "<br>";
		String [] tokens = desc.split(delims);
		for (int i = 1; i < tokens.length; i++) {
		System.out.println(tokens[i]);
		}
		Matcher headtype = Pattern.compile("Single|Twin").matcher(tokens[1]);
		if (headtype.find()) {
			System.out.println(headtype.group(0));
		}
		Matcher timelimit = Pattern.compile("([1-9])|30").matcher(tokens[2]);
		if (timelimit.find()) {
			System.out.println(timelimit.group(0));
		}
		Matcher hours = Pattern.compile("[$][1-9].[0-9][0-9]").matcher(tokens[3]);
		if (hours.find()) {
			
		System.out.println(hours.group(0));
		}
		
		
		
		
	}

//	private List<Meter> parse() {
//		for (Feature pm : placemarks) {
//			number = new Integer(pm.getName());
//			String description = pm.getDescription();
//			parseDescription(description);
//			Placemark placemark = (Placemark) pm;
//			Point point = (Point) placemark.getGeometry();
//			latitude = point.getCoordinates().get(0).getLatitude();
//			longitude = point.getCoordinates().get(0).getLongitude();
//
//			meter = new Meter(number);
//			meters.add(meter);
//		}
//		return null;
//	}
//
//	private void parseDescription(String description) {
//		String delims = "<br>";
//		String [] tokens = desc.split(delims);
//
//	}
}
