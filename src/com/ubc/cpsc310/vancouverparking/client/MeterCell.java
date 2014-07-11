package com.ubc.cpsc310.vancouverparking.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class MeterCell extends AbstractCell<MeterInfo> {

	/**
     * The HTML templates used to render the cell.
     */
    interface MeterCellTemplates extends SafeHtmlTemplates {
      /**
       * The template for this Cell, which includes styles and a value.
       * 
       * @param styles the styles to include in the style attribute of the div
       * @param value the safe value. Since the value type is {@link SafeHtml},
       *          it will not be escaped before including it in the template.
       *          Alternatively, you could make the value type String, in which
       *          case the value would be escaped.
       * @return a {@link SafeHtml} instance
       */
      @SafeHtmlTemplates.Template("<div class='cell'>"
    		+ "<div class='listMarkerIcon {3}'></div>"
      		+ "<span class='meter_number'>{0}</span><br>"
    		+ "<span class='meter_data'>${1} | {2} | {6}</span>"
      		+ "</div>")
      SafeHtml cell(long meterNum, double rate, String paymentType, String iconStyle
    		  , double timeStart, double timeEnd, String timeLimit);
    }
    
	/**
	 * Create a singleton instance of the templates used to render the cell.
	 */
	private static MeterCellTemplates templates = GWT
			.create(MeterCellTemplates.class);
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			MeterInfo value, SafeHtmlBuilder sb) {
		/*
		 * Always do a null check on the value. Cell widgets can pass null to
		 * cells if the underlying data contains a null, or if the data arrives
		 * out of order.
		 */
		if (value == null) {
			return;
		}

	      // Use the template to create the Cell's html.
	      long meterNum = value.getNumber();
	      double rate = value.getRate();
	      double start = value.getTieStart();
	      double end = value.getTieEnd();
	      double limit = value.getTimeLimit();
	      int limitVal = (int) limit;
	      String limitValString;
	      String paymentType;
	      String iconStyle = "";
	      if (value.isCreditCard()) {
	    	  paymentType = "Credit Card Valid";
	      } else {
	    	  paymentType = "No Credit Cards";
	      }
	      // This next line needs to be an if statement that checks some info on meter and 
	      // makes iconStyle the proper one
	      if (value.isFavorite()){
	    	  iconStyle = "markerFav";
	      }else if (rate == 0.0) {
	    	  iconStyle = "mysteryMarker";
		  } else if (rate < 2.0) {
	    	  iconStyle = "marker1";
	      } else if (rate < 3.0) {
	    	  iconStyle = "marker2";
	      } else if (rate < 4.0) {
	    	  iconStyle = "marker3";
	      } else if (rate < 5.0) {
	    	  iconStyle = "marker4";
	      } else if (rate < 6.0) {
	    	  iconStyle = "marker5";
	      } else if (rate < 7.0) {
	    	  iconStyle = "marker6";
	      } else {
	    	  iconStyle = "doucheMarker";
	      }
	      
	      if (limitVal == 0.0) {
	    	  limitValString = "No time limit";
	      } else if (limitVal == -1.0) {
	    	  limitValString = "Unknown";
	      } else if (limitVal == 30.0) {
	    	  limitValString = "30 mins max";
	      } else {
	    	  limitValString = String.valueOf(limitVal) + " hr max";
	      }
	      
	      SafeHtml rendered = templates.cell(meterNum , rate, paymentType, iconStyle,
	    		  start, end, limitValString);
	      sb.append(rendered);

	}

}
