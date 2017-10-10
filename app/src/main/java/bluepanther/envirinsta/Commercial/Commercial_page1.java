package bluepanther.envirinsta.Commercial;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterCommer;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.FileUtils.ImgUri;
import bluepanther.envirinsta.NGO_Grid.GPSTracker;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Timeline.Timeline;



/**
 * Created by shyam on 27/3/17.
 */

public class Commercial_page1 extends ActionBarActivity implements OnMapReadyCallback{

    FloatingActionButton fab;
    List<RowItem> rowItemsi, rowItemsa, rowItemsv, rowItemsf, rowItemst;
    Boolean go=true;
    public CustomAdapterCommer adapter;
    List<RowItem> grpcontent=new ArrayList<>();
    private Spinner mSearchableSpinner;
    private Spinner mSearchableSpinner1;
    private SimpleListAdapter mSimpleListAdapter, mSimpleListAdapter2;
    private final ArrayList<String> mStrings = new ArrayList<>();
    private final ArrayList<String> mStrings2 = new ArrayList<>();
    public static String city="",issue="";
    String [] cities = {"Select city","Agra","Ahmedabad","Bangalore","Bhopal","Chennai","Coimbatore","Delhi","Ghaziabad","Hyderbad","Indore","Jaipur","Kanpur","Kolkata","Lucknow","Ludhiana","Madurai","Mumbai","Nagpur","Patna","Pimpri-Chinchwad","Pune","Surat","Thane","Pondicherry","Vadodara","Visakapatinam"};
    Marker markerx;
    private GoogleMap mMap;
    ProgressDialog progressDialog;
    GPSTracker gps;
    LatLng mylatlang = new LatLng(0, 0);
    public String serverKey = "AIzaSyAjAjLy4ZKnku_DpOBDLoeULqfXbuyM6hw";
    public Double curlat, curlong;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;

Boolean sflag1=false,sflag2=false;
    Route route;
    String location="";
    Leg leg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commercial_page1);
        CurrentUser.ssec="A";



        fab = (FloatingActionButton) findViewById(R.id.fab);
        initListValues();
        initListValues2();
        mSimpleListAdapter = new SimpleListAdapter(this, mStrings);
        mSimpleListAdapter2 = new SimpleListAdapter(this, mStrings2);
        mSearchableSpinner = (Spinner) findViewById(R.id.SearchableSpinner);
        mSearchableSpinner.setAdapter(mSimpleListAdapter);
//        mSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
        mSearchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sflag1){
                city=mStrings.get(i-1);
                Toast.makeText(Commercial_page1.this, "Item on position " + i + " : " + mSimpleListAdapter.getItem(i) + " Selected", Toast.LENGTH_SHORT).show();
                }
                sflag1=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSearchableSpinner1 = (Spinner) findViewById(R.id.SearchableSpinner1);
        mSearchableSpinner1.setAdapter(mSimpleListAdapter2);
//        mSearchableSpinner1.setOnItemSelectedListener(mOnItemSelectedListener1);

        mSearchableSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sflag2) {
                    issue = mStrings2.get(i - 1);
                    System.out.println("City is" + city + " and issue is" + issue);
                    mMap.clear();
                    markerx = mMap.addMarker(new MarkerOptions().position(mylatlang).title("Me"));
                    new MyTask().execute();
                    Toast.makeText(Commercial_page1.this, "Item on position " + i + " : " + mSimpleListAdapter.getItem(i) + " Selected", Toast.LENGTH_SHORT).show();
                }
                sflag2 = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Commercial_page1.this,Commercial_page2.class);
                startActivity(i);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        Firebase.setAndroidContext(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId())
      {
          case R.id.logout:
              AlertDialog.Builder builder = new AlertDialog.Builder(Commercial_page1.this);
              builder.setTitle("Logout");
              builder.setMessage("Are you sure you want to logout?");
              builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                      SharedPreferences.Editor editor = pref.edit();

                      editor.putBoolean("islog",false);
                      editor.commit();
                      startActivity(new Intent(Commercial_page1.this,
                              AA_home.class));
                      Commercial_page1.this.finish();
                  }
              }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.dismiss();
                  }
              });
              AlertDialog dialog = builder.create();
              dialog.show();
      }
        return super.onOptionsItemSelected(item);
    }

    private void initListValues() {
        mStrings.add("Agra");
        mStrings.add("Ahmedabad");
        mStrings.add("Bangalore");
        mStrings.add("Bhopal");
        mStrings.add("Chennai");
        mStrings.add("Coimbatore");
        mStrings.add("Delhi");
        mStrings.add("Ghaziabad");
        mStrings.add("Hyderbad");
        mStrings.add("Indore");
        mStrings.add("Bangalore");
        mStrings.add("Jaipur");
        mStrings.add("Kanpur");
        mStrings.add("Kolkata");
        mStrings.add("Lucknow");
        mStrings.add("Ludhiana");
        mStrings.add("Madurai");
        mStrings.add("Mumbai");
        mStrings.add("Nagpur");
        mStrings.add("Patna");
        mStrings.add("Pimpri-Chinchwad");
        mStrings.add("Pune");
        mStrings.add("Surat");
        mStrings.add("Thane");
        mStrings.add("Pondicherry");
        mStrings.add("Vadodara");
        mStrings.add("Visakapatinam");
    }

    private void initListValues2() {
        mStrings2.add("Roads");
        mStrings2.add("Transport");
        mStrings2.add("Water Lines");
        mStrings2.add("Electricity");
        mStrings2.add("Bank/ATM");
        mStrings2.add("Hospital");
        mStrings2.add("Politics");
        mStrings2.add("Govt Offices");
        mStrings2.add("Sewage");
        mStrings2.add("General PWD");
        mStrings2.add("Others");


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new GPSTracker(Commercial_page1.this);
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
        markerx = mMap.addMarker(new MarkerOptions().position(mylatlang).title("Me"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylatlang));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylatlang, 12.0f));
    }
    public class MyTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            rowItemsi = new ArrayList<RowItem>();
            rowItemsa = new ArrayList<RowItem>();
            rowItemsv = new ArrayList<RowItem>();
            rowItemsf = new ArrayList<RowItem>();
            rowItemst = new ArrayList<RowItem>();
            String tmp1 = Base_url + "Classes/" + city + "/" + CurrentUser.ssec + "/Images/";
            fb_db = new Firebase(tmp1);
            rowItemsi.clear();


            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    ImageDesc images = dataSnapshot.getValue(ImageDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);
                    go = true;
                    try {

                        if (images.maincat.equals(issue)) {
                            go = true;
                        } else {
                            go = false;
                        }


                        if (go) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.picture, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Images");
                                rowItemsi.add(item);
                                getfiles();
                                CommerContent.grpcontent=new ArrayList<RowItem>(grpcontent);
                                String res = item.getAuthor() + item.getTime();
                                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(Commercial_page1.city).child("A").child("Images").child(res);

                                System.out.println("Storage refference : " + storageReference);


                                        storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                                            @Override
                                            public void onSuccess(StorageMetadata storageMetadata) {
                                                // Metadata now contains the metadata for 'images/forest.jpg'
                                                try {
                                                    Double lat = Double.parseDouble(storageMetadata.getCustomMetadata("latitude"));
                                                    Double lon = Double.parseDouble(storageMetadata.getCustomMetadata("longitude"));
                                                    String title=storageMetadata.getCustomMetadata("title");
                                                    LatLng latLng = new LatLng(lat, lon);
                                                    mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).title(title));

                                                }
                                                catch(Exception e)
                                                {

                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception)   {
                                                // Uh-oh, an error occurred!
                                            }
                                        });



                            }
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
            String tmp2 = Base_url + "Classes/" + city + "/" + CurrentUser.ssec + "/Audios/";
            fb_db = new Firebase(tmp2);
            rowItemsa.clear();

            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    Audiodesc images = dataSnapshot.getValue(Audiodesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);
                    go = true;
                    try {

                        if (images.maincat.equals(issue)) {
                            go = true;
                        } else {
                            go = false;
                        }


                        if (go) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.music, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Audios");
                                rowItemsa.add(item);
                                getfiles();
                                CommerContent.grpcontent=new ArrayList<RowItem>(grpcontent);

                            }
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
            String tmp3 = Base_url + "Classes/" + city + "/" + CurrentUser.ssec + "/Videos/";
            fb_db = new Firebase(tmp3);
            rowItemsv.clear();

            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    VideoDesc images = dataSnapshot.getValue(VideoDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);
                    go = true;
                    try {

                        if (images.maincat.equals(issue)) {
                            go = true;
                        } else {
                            go = false;
                        }


                        if (go) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.clip, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Videos");
                                rowItemsv.add(item);
                                getfiles();
                                CommerContent.grpcontent=new ArrayList<RowItem>(grpcontent);


                            }
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
            String tmp4 = Base_url + "Classes/" + city + "/" + CurrentUser.ssec + "/Files/";
            fb_db = new Firebase(tmp4);
            rowItemsf.clear();

            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    FileDesc images = dataSnapshot.getValue(FileDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);
                    go = true;
                    try {

                        if (images.maincat.equals(issue)) {
                            go = true;
                        } else {
                            go = false;
                        }


                        if (go) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.files, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Files");
                                rowItemsf.add(item);
                                getfiles();
                                CommerContent.grpcontent=new ArrayList<RowItem>(grpcontent);


                            }
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
            String tmp5 = Base_url + "Classes/" + city + "/" + CurrentUser.ssec + "/Texts/";
            fb_db = new Firebase(tmp5);
            rowItemst.clear();

            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    TextDesc images = dataSnapshot.getValue(TextDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);
                    go = true;
                    try {

                        if (images.maincat.equals(issue)) {
                            go = true;
                        } else {
                            go = false;
                        }


                        if (go) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.doc, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Text");
                                rowItemst.add(item);
                                getfiles();
                                CommerContent.grpcontent=new ArrayList<RowItem>(grpcontent);


                            }
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
            return null;
        }
    }

    public void getfiles()
    {
        grpcontent = new ArrayList<>();
        grpcontent.addAll(rowItemsi);
        grpcontent.addAll(rowItemsa);
        grpcontent.addAll(rowItemsv);
        grpcontent.addAll(rowItemsf);
        grpcontent.addAll(rowItemst);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(grpcontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });

    }
}
