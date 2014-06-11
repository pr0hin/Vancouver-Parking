package com.ubc.cpsc310.vancouverparking.server;

import java.io.File;
import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.StyleSelector;

public class KMLParser {
	public static void main(String[] args) {

		File parkingmeters = new File(
				"/Users/rohinpatel/Desktop/310/VancouverParking/src/com/ubc/cpsc310/vancouverparking/server/parking_meter_rates_and_time_limits.kml");
		Kml kml = Kml.unmarshal(parkingmeters);
		Document doc = (Document) kml.getFeature();
		
		List<StyleSelector> styles = doc.getStyleSelector();
		List<Feature> features = doc.getFeature();
		System.out.println(kml);
		System.out.println("Hello Rohin");
		
	}
}
