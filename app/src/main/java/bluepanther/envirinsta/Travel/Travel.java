package bluepanther.envirinsta.Travel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterGT;
import bluepanther.envirinsta.Adapter.CustomAdapterIT;
import bluepanther.envirinsta.Adapter.CustomAdapterTravel;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.ContentDesc.ImageDesc2;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDisp.txtdisp;
import bluepanther.envirinsta.Discussions.Dis_image;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.NGO_Grid.GPSTracker;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.NGO_Grid.ShowMap;
import bluepanther.envirinsta.Officials.Off_Dis_image;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Services.MyService;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.Timeline.Group_tab;
import bluepanther.envirinsta.Timeline.Indi_tab;
import bluepanther.envirinsta.Timeline.Timeline;
import bluepanther.envirinsta.Travel.TravelContent;

/**
 * Created by shyam on 30/3/17.
 */

public class Travel extends ActionBarActivity implements OnMapReadyCallback,OnMenuItemClickListener, OnMenuItemLongClickListener {

    private RecyclerView.LayoutManager layoutManager;
    static Marker markerx;
    static private GoogleMap mMap;
    ProgressDialog progressDialog;
    GPSTracker_Travel gps;
    LatLng mylatlang = new LatLng(0, 0);
   static public String serverKey = "AIzaSyAjAjLy4ZKnku_DpOBDLoeULqfXbuyM6hw";
    public Double curlat, curlong;
    static private String Base_url = "https://envirinsta.firebaseio.com/";
    static private Firebase fb_db;
   static List<RowItem> rowItemsi, rowItemsa, rowItemsv, rowItemsf, rowItemst;
    static ArrayList<ImageDesc2>objList;
 static   Route route;
    String location="";
    static int key=0;
    static  HashMap<String, String> hmap;
    static  Leg leg;
    static String result,file1;
    static Context context;
   static List<RowItem> grpcontent;
    static RecyclerView mylistview;
    public static CustomAdapterTravel adapter;
    public  static View.OnClickListener myOnClickListener;
    public static View.OnLongClickListener myOnLongClickListener;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel);

        TextView issues = (TextView) findViewById(R.id.issues);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Ubuntu-R.ttf");

        issues.setTypeface(typeface);
        mylistview = (RecyclerView) findViewById(R.id.myListView);
        mylistview.setHasFixedSize(true);
context=Travel.this;
        layoutManager = new LinearLayoutManager(Travel.this);
        mylistview.setLayoutManager(layoutManager);
        mylistview.setItemAnimator(new DefaultItemAnimator());
        //mylistview.setAdapter(adapter);
        Firebase.setAndroidContext(this);
        fb_db=new Firebase(Base_url);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        key=0;

        myOnClickListener = new Travel.MyOnClickListener(Travel.this);

        fragmentManager = getSupportFragmentManager();
        // initToolbar();
        initMenuFragment();

    }

    protected void addFragment(Fragment fragment, boolean
            addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped =
                fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction =
                    fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int)
                getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        menuParams.setAnimationDuration(30);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(Travel.this);
        mMenuDialogFragment.setItemLongClickListener(Travel.this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("Timeline");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("Reports");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("My Profile");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject ngo = new MenuObject("NGO");
        ngo.setResource(R.drawable.dest);

        MenuObject leaderboards = new MenuObject("Leaderboards");
        leaderboards.setResource(R.drawable.icn_3);

        MenuObject stats = new MenuObject("Stats");
        stats.setResource(R.drawable.icn_3);

        MenuObject travel = new MenuObject("Travel");
        travel.setResource(R.drawable.icn_5);

        MenuObject addFav = new MenuObject("Contacts");
        addFav.setResource(R.drawable.icn_4);


        MenuObject block = new MenuObject("Logout");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(ngo);
        menuObjects.add(leaderboards);
        menuObjects.add(stats);
        menuObjects.add(travel);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(Travel.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(Travel.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(Travel.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(Travel.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(Travel.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(Travel.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(Travel.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(Travel.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Travel.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Travel.this,
                                            AA_home.class));
                                    Travel.this.finish();
                                }
                            })
                    .setNegativeButton("No", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(Travel.this, "Long clicked on position: " + position,
                Toast.LENGTH_SHORT).show();
    }

    private void changeFragment(final Fragment targetFragment) {

//        new Handler() {
//        }.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, targetFragment, "fragment")
//
//                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .commit();
//            }
//        }, 600);
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (getSupportFragmentManager().findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(getSupportFragmentManager(), ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Travel.this);
            builder.setMessage("Are you sure you want to exit the App?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new GPSTracker_Travel(Travel.this);
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

    }
    public static void updateLoc(final Location location)
    {
        mMap.clear();
        markerx = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title(""));
        rowItemsi = new ArrayList<RowItem>();
        rowItemsa = new ArrayList<RowItem>();
        rowItemsv = new ArrayList<RowItem>();
        rowItemsf = new ArrayList<RowItem>();
        rowItemst = new ArrayList<RowItem>();
        objList=new ArrayList<>();
        fb_db.child("Locations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    ImageDesc2 obj = postSnapshot.getValue(ImageDesc2.class);
                    System.out.println("Adding obj" + obj.getDesc());
                    objList.add(obj);
                }
               hmap = new<String,String> HashMap();

                for(int i=0;i<objList.size();i++)
                {
                    hmap.put(String.valueOf(i),objList.get(i).getLatitude()+objList.get(i).getLongitude());
                    checkLoc(location,new LatLng(Double.parseDouble(objList.get(i).getLatitude()),Double.parseDouble(objList.get(i).getLongitude())));
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public static void checkLoc(Location location,final LatLng dest)
    {

        LatLng origin=new LatLng(location.getLatitude(),location.getLongitude());
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(dest)
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
                                if(tmp1.equals("")){
                                    tmp1="0.0";
                                }
                                for(int i=0;i<objList.size();i++)
                                {
                                    if(objList.get(i).getLatitude().equals(String.valueOf(dest.latitude))&&objList.get(i).getLongitude().equals(String.valueOf(dest.longitude)))
                                    {
                                        key=i;
                                        break;
                                    }
                                }
                                //key=Integer.parseInt(hmap.get(String.valueOf(dest.latitude)+String.valueOf(dest.longitude)));
                                System.out.println("Distance of "+objList.get(key).getDesc()+" is"+tmp1+" with dest"+dest);
                                if(Double.parseDouble(tmp1)<=10.0)
                                {
                                    ImageDesc2 obj=objList.get(key);
                                    switch(obj.getType())
                                    {
                                        case "Image":
                                        RowItem item = new RowItem(obj.desc,
                                                R.drawable.picture, obj.maincat + " : " + obj.subcat,
                                                obj.date, obj.user,"Images");
                                            rowItemsi.add(item);
                                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()))).title(obj.desc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                            break;
                                        case "Audio":
                                            RowItem item2 = new RowItem(obj.desc,
                                                    R.drawable.music, obj.maincat + " : " + obj.subcat,
                                                    obj.date, obj.user,"Audios");
                                            rowItemsa.add(item2);
                                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()))).title(obj.desc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                                            break;
                                        case "Video":
                                            RowItem item3 = new RowItem(obj.desc,
                                                    R.drawable.clip, obj.maincat + " : " + obj.subcat,
                                                    obj.date, obj.user,"Videos");
                                            rowItemsv.add(item3);
                                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()))).title(obj.desc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                                            break;
                                        case "File":
                                            RowItem item4 = new RowItem(obj.desc,
                                                    R.drawable.files, obj.maincat + " : " + obj.subcat,
                                                    obj.date, obj.user,"Files");
                                            rowItemsf.add(item4);
                                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()))).title(obj.desc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                                            break;
                                        case "Text":
                                            RowItem item5 = new RowItem(obj.desc,
                                                    R.drawable.doc, obj.maincat + " : " + obj.subcat,
                                                    obj.date, obj.user,"Text");
                                            rowItemst.add(item5);
                                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(obj.getLatitude()),Double.parseDouble(obj.getLongitude()))).title(obj.desc).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                                            break;
                                    }
                                    getfiles();
                                    TravelContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterTravel adapter = new CustomAdapterTravel(context, grpcontent);
                                    Travel.setContent(adapter);
                               
                                }
                              //  key++;

                            } catch (Exception e) {
                                System.out.println("Exiting:" + e);
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
    }
    public static void getfiles()
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
    public static void setContent(CustomAdapterTravel adapter)
    {
        mylistview.setAdapter(adapter);
    }
    private  static class MyOnClickListener implements View.OnClickListener {

        private final Context context;
        ProgressDialog progressDialog;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            final View vv = v;
            int position = mylistview.getChildPosition(v);
            if (TravelContent.grpcontent.get(position).getType().equals("Images")) {
                String res = TravelContent.grpcontent.get(position).getAuthor() + TravelContent.grpcontent.get(position).getTime();
                if(CurrentUser.type.equals("User")) {
                    Intent i = new Intent(context, Dis_image.class);
                    i.putExtra("res", res);
                    i.putExtra("title", TravelContent.grpcontent.get(position).getMember_name());
                    i.putExtra("categ", TravelContent.grpcontent.get(position).getStatus());
                    i.putExtra("auth", TravelContent.grpcontent.get(position).getAuthor());
                    context.startActivity(i);
                   // Dis_image.timg=TravelContent.grpcontent.get(position).get
                }
                else
                {
                    Intent i = new Intent(context, Off_Dis_image.class);
                    i.putExtra("res", res);
                    i.putExtra("title", TravelContent.grpcontent.get(position).getMember_name());
                    i.putExtra("categ", TravelContent.grpcontent.get(position).getStatus());
                    i.putExtra("auth", TravelContent.grpcontent.get(position).getAuthor());
                    context.startActivity(i);
                }
//                progressDialog = new ProgressDialog(vv.getContext());
//                progressDialog.setTitle("Message");
//                progressDialog.setMessage("Downloading Image...");
//
//                progressDialog.setCancelable(true);
//                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                progressDialog.show();
//
//
//                final String res = TravelContent.grpcontent.get(position).getAuthor() + TravelContent.grpcontent.get(position).getTime();
//                System.out.println("Downloading" + res);
//
//                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);
//
//                System.out.println("Storage refference : " + storageReference);
//
//
//                ImgUri.ref=storageReference;
//
//                Intent i = new Intent(vv.getContext(), imgdisp.class);
//                progressDialog.dismiss();
//                vv.getContext().startActivity(i);


            }
            if (TravelContent.grpcontent.get(position).getType().equals("Audios")) {

                progressDialog = new ProgressDialog(vv.getContext());
                progressDialog.setTitle("Message");
                progressDialog.setMessage("Downloading Audio...");

                progressDialog.setCancelable(true);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialog.show();

                final String res = TravelContent.grpcontent.get(position).getAuthor() + TravelContent.grpcontent.get(position).getTime();

                System.out.println("Downloading" + res);

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res);

                System.out.println("Storage refference : " + storageReference);


                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println("NOOB");
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_VIEW);
                        i.setDataAndType(uri, "audio/*");
                        progressDialog.dismiss();
                        vv.getContext().startActivity(i);

//                                            Picasso.with(Reports.this).load(uri).fit().centerCrop().into(imgg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        System.out.println("sad" + exception);
                    }
                });
            }
            if (TravelContent.grpcontent.get(position).getType().equals("Videos")) {

                progressDialog = new ProgressDialog(vv.getContext());
                progressDialog.setTitle("Message");
                progressDialog.setMessage("Downloading Video...");

                progressDialog.setCancelable(true);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialog.show();

                final String res = TravelContent.grpcontent.get(position).getAuthor() + TravelContent.grpcontent.get(position).getTime();

                System.out.println("Downloading" + res);

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res);

                System.out.println("Storage refference : " + storageReference);


                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println("NOOB");
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_VIEW);
                        i.setDataAndType(uri, "video/*");
                        progressDialog.dismiss();
                        vv.getContext().startActivity(i);

//                                            Picasso.with(Reports.this).load(uri).fit().centerCrop().into(imgg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        System.out.println("sad" + exception);
                    }
                });
            }
            if (TravelContent.grpcontent.get(position).getType().equals("Files")) {

                progressDialog = new ProgressDialog(vv.getContext());
                progressDialog.setTitle("Message");
                progressDialog.setMessage("Downloading File...");

                progressDialog.setCancelable(true);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialog.show();

                final String res = TravelContent.grpcontent.get(position).getAuthor() + TravelContent.grpcontent.get(position).getTime();

                System.out.println("Downloading" + res);

                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res);

                System.out.println("Storage refference : " + storageReference);


                storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {

                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {


                        System.out.println("Type is" + storageMetadata.getContentType() + "end");
                        String splitarr[] = storageMetadata.getContentType().split("/");
                        System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
                        final String ftype = storageMetadata.getContentType();

                        if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                            file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";

                        } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                            file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
                        } else if (ftype.contains("pdf")) {
                            file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];

                        }
                        final File files = new File(file1);

//                                System.out.println("NOOB");
//                                Intent intent = new Intent();
//                                intent.setAction(Intent.ACTION_VIEW);
//                                intent.setDataAndType(uri,"file/*");
//                                startActivity(intent);
                        storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                // Metadata now contains the metadata for 'images/forest.jpg'
                                progressDialog.dismiss();

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                if (ftype.contains("pdf")) {
                                    intent.setDataAndType(Uri.fromFile(files), ftype);
                                } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
                                    intent.setDataAndType(Uri.fromFile(files), "text/plain");

                                } else if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
                                    intent.setDataAndType(Uri.fromFile(files), "application/msword");

                                } else if (ftype.equals("presentation")) {
                                    intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");

                                } else if (ftype.equals("spreadsheet") || ftype.equals("sheet")) {
                                    intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");

                                }

                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                try {
                                    vv.getContext().startActivity(intent);
                                } catch (Exception e) {
                                    System.out.println("EXCEPTION IS " + e);
                                    Toast.makeText(vv.getContext(), "Invalid File type", Toast.LENGTH_LONG).show();
                                    // Instruct the user to install a PDF reader here, or something
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Uh-oh, an error occurred!
                            }
                        });


//                                            Picasso.with(Reports.this).load(uri).fit().centerCrop().into(imgg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        System.out.println("sad" + exception);
                    }
                });
            }
            if (TravelContent.grpcontent.get(position).getType().equals("Text")) {

                progressDialog = new ProgressDialog(vv.getContext());
                progressDialog.setTitle("Message");
                progressDialog.setMessage("Downloading Text...");

                progressDialog.setCancelable(true);
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialog.show();

                final String res = TravelContent.grpcontent.get(position).getAuthor() + TravelContent.grpcontent.get(position).getTime();

                System.out.println("Downloading" + res);
                String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res + "/";
                System.out.println("ZZlol"+tmp5);
                fb_db = new Firebase(tmp5);
                fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TextDesc obj = dataSnapshot.getValue(TextDesc.class);
                        result = obj.text;
                        System.out.println("TXT IS " + result);
                        Intent i = new Intent(vv.getContext(), txtdisp.class);
                        i.putExtra("value", result);
                        progressDialog.dismiss();
                        vv.getContext().startActivity(i);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }

                });


            }

        }

    }
}
