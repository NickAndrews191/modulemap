package module3;

//Java utilities libraries
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

import java.lang.Object;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = true;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 6;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 3;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	public int yellow = color(255, 255, 0);
    public int blue   = color(0,0,255);
    public int green  = color(0,255,0);
    public int white  = color(255,255,255);
    public int black  = color(0,0,0);
	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	 
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	   
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	  
	   
        // Create markers for every earthquake
        for (PointFeature earthquake : earthquakes) {
        	
            markers.add(createMarker(earthquake));
        }
      
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setup method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
	
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
		int yellow = color(255, 255, 0);
	    int blue = color(0,0,255);
	    int green = color(0,255,0);
	    	   
	    if (THRESHOLD_LIGHT < mag   ) {
	    	marker.setColor(yellow);
	    	marker.setRadius(4);
	    }
	    else if (THRESHOLD_MODERATE > mag && THRESHOLD_MODERATE < 7  ) {
	    	marker.setColor(blue);
	    	marker.setRadius(6);
	    }
	    
	    else {	    	
	    	marker.setColor(green);
	    	marker.setRadius(10);
	    }
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}
    
	
	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		fill(white);
		rect(20, 60, 175, 475);
		fill(black);
		textSize(24);
		text("MAP KEY", 40, 100); 
		fill(black);
		textSize(16);
		text("less than 3", 80, 155); 
		fill(black);
		textSize(16);
		text("3 to 6 mag", 80, 205); 
		fill(black);
		textSize(16);
		text("greater than 7", 80, 255); 
		fill(yellow);
		ellipse(45, 150, 30, 30);
		fill(blue);
		ellipse(45, 200, 30, 30);
		fill(green);
		ellipse(45, 250, 30, 30);
		// Remember you can use Processing's graphics methods here
		// Remember you can use Processing's graphics methods here
	
	}
}
