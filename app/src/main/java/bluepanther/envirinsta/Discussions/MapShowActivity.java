package bluepanther.envirinsta.Discussions;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bluepanther.envirinsta.NGO_Grid.GPSTracker;
import bluepanther.envirinsta.R;

/**
 * Created by hhs on 8/3/17.
 */

public class MapShowActivity extends FragmentActivity implements OnMapReadyCallback {

    LatLng latLng =null;
    Marker markerx;
    private GoogleMap mMap;
    ProgressDialog progressDialog;
    GPSTracker gps;
    LatLng mylatlang = new LatLng(0, 0);
    public String serverKey = "AIzaSyAjAjLy4ZKnku_DpOBDLoeULqfXbuyM6hw";
    public Double curlat, curlong;

    Route route;
    String location="";
    Leg leg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new GPSTracker(MapShowActivity.this);
        if (gps.canGetLocation()) {

            curlat = gps.getLatitude();
            curlong = gps.getLongitude();
            mylatlang = new LatLng(curlat, curlong);
            System.out.println("Your location is " + mylatlang);
        }
        mMap = googleMap;
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
        //    LatLng TutorialsPoint = new LatLng(21, 57);
        markerx = mMap.addMarker(new MarkerOptions().position(mylatlang).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlang));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlang, 12.0f));
        latLng=Dis_image.latLng;
        new MyTaskMap().execute();
    }

    private class MyTaskMap extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MapShowActivity.this, "Message", "Fetching Details...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            //  Accounts doc=new Accounts(date,fullname,image);


            runOnUiThread(new Runnable() {
                public void run() {



                    Boolean flag = true;

                    if (flag) {



                        progressDialog.dismiss();

                        // markerx.remove();
                        mMap.addMarker(new
                                MarkerOptions().position(latLng).title("NGO"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        GoogleDirection.withServerKey(serverKey)
                                .from(latLng)
                                .to(mylatlang)
                                .execute(new DirectionCallback() {
                                    @Override
                                    public void onDirectionSuccess(Direction direction, String rawBody) {
                                        // Do something here
                                        System.out.println("Result isss" + direction.getStatus());
                                        String status = direction.getStatus();
                                        if (status.equals(RequestResult.OK)) {
                                            System.out.println("Result is ok");
                                            try {
                                                route = direction.getRouteList().get(0);
                                                leg = route.getLegList().get(0);
                                                Info distanceInfo = leg.getDistance();
                                                Info durationInfo = leg.getDuration();
                                                String tmp1 = distanceInfo.getText();
                                                tmp1 = tmp1.substring(0, tmp1.length() - 3);
                                                String tmp2 = durationInfo.getText();


                                            } catch (Exception e) {
                                                System.out.println("Exiting:" + e);
                                            }

                                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();


                                            PolygonOptions rectOptions = new PolygonOptions();

                                            System.out.println("Lol size is" + directionPositionList.size());
                                            for (int i = 0; i < directionPositionList.size(); i = i + 25) {

                                                try {
                                                    LatLng first = directionPositionList.get(i);
                                                    LatLng second = directionPositionList.get(i + 25);
                                                    Polyline line1 = mMap.addPolyline(new PolylineOptions()
                                                            .add(first, second)
                                                            .width(5)
                                                            .color(Color.RED));
                                                    System.out.println(first + "," + second);
                                                } catch (Exception e) {

                                                }
                                            }
                                        }
                                        if (status.equals(RequestResult.NOT_FOUND)) {
                                            System.out.println("Result is not ok");
                                        }


                                    }

                                    @Override
                                    public void onDirectionFailure(Throwable t) {
                                        // Do something here
                                        System.out.println("Direction failed");
                                    }
                                });


                    } else {
                        progressDialog.dismiss();

                    }

                }
            });



            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("SUCCESS")) {

            }
            // Do things like hide the progress bar or change a TextView
        }
    }

    public List<Address> getAddress(String location) {
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addressList;
    }

}
