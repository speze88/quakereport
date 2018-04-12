package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by benjaminfras on 10/8/17.
 */

/*
* {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link EarthQuake} objects.
* */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();
    private static final String LOCATION_DELIMITER = "of ";

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context       The current context. Used to inflate the layout file.
     * @param earthquakes   A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position      The position in the list of data that should be displayed in the
     *                      list item view.
     * @param convertView   The recycled view to populate.
     * @param parent        The parent ViewGroup that is used for inflation.
     * @return              The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID magnitude
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Get the magnitude from the current Earthquake object and
        // set formatted magnitude value on the magnitude TextView
        magnitudeTextView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        // Find the TextView in the list_item.xml layout with the ID location
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset);

        // Get the location from the current Earthquake object and
        // and split it up in location offset and primary location
        String primaryLocation;
        String locationOffset;
        if(currentEarthquake.getLocation().contains(LOCATION_DELIMITER)) {
            String[] locationParts = currentEarthquake.getLocation().split(LOCATION_DELIMITER);
            locationOffset = locationParts[0] + LOCATION_DELIMITER;
            primaryLocation = locationParts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = currentEarthquake.getLocation();
        }

        // Set the location text fields accordingly
        locationOffsetTextView.setText(locationOffset);
        locationTextView.setText(primaryLocation);

        // Find the Textview in the list_item.xml layout with the ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);

        // Get the date from the current Earthquake object and
        // set this text on the date TextView
        dateTextView.setText(new SimpleDateFormat("MMM d, yyyy").format(currentEarthquake.getDate()));

        // Find the Textview in the list_item.xml layout with the ID date
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);

        // Get the date from the current Earthquake object and
        // set this text on the date TextView
        timeTextView.setText(new SimpleDateFormat("HH:mm").format(currentEarthquake.getDate()));

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     *
     * @param earthquakeLocation
     * @return Returns an array with locationOffset and location
     */
    private String[] subEarthquakeLocaction(String earthquakeLocation) {
        String[] splitLocation = new String[2];

        int charPos = earthquakeLocation.indexOf(LOCATION_DELIMITER);
        if(charPos > 0) {
            charPos+=2;
            splitLocation[0] = earthquakeLocation.substring(0, charPos).trim();
            splitLocation[1] = earthquakeLocation.substring(charPos, earthquakeLocation.length()).trim();
        } else {
            splitLocation[0] = getContext().getString(R.string.near_the);
            splitLocation[1] = earthquakeLocation;
        }
        return splitLocation;
    }

    /**
     * Return a formatted magnitude string showing 1 decimal place (i. e. "1.2")
     * @param magnitude
     * @return
     */
    private String formatMagnitude(Double magnitude) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId = 0;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch(magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10:
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
