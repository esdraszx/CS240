package layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.client.ClientModel;
import com.example.client.Colors;
import com.example.client.FilterActivity;
import com.example.client.PersonActivity;
import com.example.client.R;
import com.example.client.SearchActivity;
import com.example.client.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.EventModel;
import Model.PersonModel;


public class MyMapFragment extends Fragment  {
    private  String centeredID;
    private  Context whereICameFrom;
    private GoogleMap mMap;
    private SupportMapFragment myfrag;
    private EventModel mapsEventSelected;
    private Boolean markerClicked = false;
    private Boolean comingFromPerson = false;


    public MyMapFragment() {
        // Required empty public constructor
    }

    public void setEventId(String in){
        centeredID = in;
    }

    public void setContext(Context in){
        whereICameFrom = in;
    }


    public static MyMapFragment newInstance() {
        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setComingFromPerson(Boolean bool){
        comingFromPerson = bool;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (comingFromPerson){
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu myMenu, MenuInflater inflater){
        inflater.inflate(R.menu.activity_main, myMenu);
        super.onCreateOptionsMenu(myMenu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                Intent intentThree = new Intent(getActivity(), SearchActivity.class);
                startActivity(intentThree);
                return true;
            case R.id.filter:
                Intent intentTwo = new Intent(getActivity(), FilterActivity.class);
                startActivity(intentTwo);
                return true;
            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_map, container, false);


        myfrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        myfrag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap){
                mMap = googleMap;

                ClientModel cm = ClientModel.getInstance();

                Map<String, EventModel> events = cm.getEventMap();

                Colors myColors = Colors.getInstance();
                List<String> colorsToUse = myColors.getMyColors();

                Set<String> masterEventList = new ArraySet<String>();

                for (int i = 0; i < cm.getEventTypesForUser().size(); i++){
                    masterEventList.add(cm.getEventTypesForUser().get(i).toLowerCase());
                }

                for (int i = 0; i < cm.getEventTypesForFemaleAncestors().size(); i++){
                    masterEventList.add(cm.getEventTypesForFemaleAncestors().get(i).toLowerCase());
                }

                for (int i = 0; i < cm.getEventTypesForMaleAncestors().size(); i++){
                    masterEventList.add(cm.getEventTypesForMaleAncestors().get(i).toLowerCase());
                }

                //masterList not has all the event types
                List<String> finalListOfEventTypes = new ArrayList<String>();

                finalListOfEventTypes.addAll(masterEventList);

                cm.setAllEventTypes(finalListOfEventTypes);


                for(Map.Entry<String, EventModel> entry : events.entrySet()){
                    EventModel event = entry.getValue();

                    if(cm.isShowFemaleEvents() && cm.getPersonById(event.getPersonID()).getGender().equals("f")){
                        int indexOfColorToUse = finalListOfEventTypes.indexOf(event.getType().toLowerCase());
                        String colorHexValue = colorsToUse.get(indexOfColorToUse);

                        LatLng eventLatLong2 = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong2)
                                .icon(getMarkerIcon(colorHexValue))).setTag(event);
                    }

                    if (cm.isShowMaleEvents() && cm.getPersonById(event.getPersonID()).getGender().equals("m")){
                        int indexOfColorToUse = finalListOfEventTypes.indexOf(event.getType().toLowerCase());
                        String colorHexValue = colorsToUse.get(indexOfColorToUse);

                        LatLng eventLatLong2 = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong2)
                                .icon(getMarkerIcon(colorHexValue))).setTag(event);
                    }

                }

                EventModel eventToCenter = events.get(centeredID);
                LatLng placeToCenter = new LatLng(eventToCenter.getLatitude(), eventToCenter.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(placeToCenter));

                if (comingFromPerson){;
                    EventModel eventSelected = cm.getEventById(centeredID);

                    mapsEventSelected = eventSelected;
                    clickMarker(v);

                }


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        ClientModel cm = ClientModel.getInstance();

                        EventModel eventSelected = (EventModel) marker.getTag();

                        mapsEventSelected = eventSelected;

                        clickMarker(v);

                        Log.d("Click","was clicked");
                        return false;
                    }
                });
            } //end of onMapReady
        }); //end of get mapasync


        LinearLayout bottomOfScreen = (LinearLayout) v.findViewById(R.id.bottomOfScreen);
        bottomOfScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start person activity
                if (markerClicked){
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    Bundle mBundle  = new Bundle();

                    mBundle.putString("personID", mapsEventSelected.getPersonID());
                    intent.putExtras(mBundle);

                    startActivity(intent);
                }

            }
        });

        return v;
    }

    public void clickMarker(View v){
        ClientModel cm = ClientModel.getInstance();

        PersonModel personSelected = cm.getPersonById(mapsEventSelected.getPersonID());

        ImageView img = (ImageView) v.findViewById(R.id.iconImageView);

        if (personSelected.getGender().equals("f")){
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.femaleColor).sizeDp(40);
            img.setImageDrawable(genderIcon);
        } else {
            Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.maleColor).sizeDp(40);
            img.setImageDrawable(genderIcon);
        }

        TextView textTop = (TextView) v.findViewById(R.id.textTop);
        TextView textBottom = (TextView) v.findViewById(R.id.textBottom);

        textTop.setText(personSelected.getDescription());
        textBottom.setText(mapsEventSelected.getDescription());
        markerClicked = true;
    }


    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }



}







/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */


/*
                    if (event.getType().equals("Birth")){
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))).setTag(event);
                    }else if (event.getType().equals("Marriage")) {
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setTag(event);
                    } else if (event.getType().equals("Served Mission")){
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(event);
                    } else if (event.getType().equals("Bought house")){
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))).setTag(event);
                    } else if (event.getType().equals("Death")){
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag(event);
                    } else if (event.getType().equals("Bought a puppy")){
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTag(event);
                    } else if (event.getType().equals("Baptism")){
                        LatLng eventLatLong = new LatLng(event.getLatitude(), event.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(eventLatLong)
                                .title(event.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).setTag(event);
                    }

*/