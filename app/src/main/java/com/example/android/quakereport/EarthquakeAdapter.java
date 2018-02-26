package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by benjaminfras on 10/8/17.
 */

/*
* {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link EarthQuake} objects.
* */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

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
        // set this text on the magnitude TextView
        magnitudeTextView.setText(String.valueOf(currentEarthquake.getMagnitude()));

        // Find the TextView in the list_item.xml layout with the ID location
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location);
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.locationoffset);

        // Get the location from the current Earthquake object and
        // set this text on the location TextView
        String[] location = currentEarthquake.getLocation().split("of");
        if(location.length == 1) {
            locationOffsetTextView.setText(null);
            locationOffsetTextView.setText(location[0].trim());
        } else  {
            locationOffsetTextView.setText(location[0].trim());
            locationTextView.setText(location[1].trim());
        }

        // Find the Textview in the list_item.xml layout with the ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);

        // Get the date from the current Earthquake object and
        // set this text on the date TextView
        dateTextView.setText(currentEarthquake.getDisplayDate());

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
