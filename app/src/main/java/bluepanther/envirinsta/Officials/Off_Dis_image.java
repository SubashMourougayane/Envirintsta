package bluepanther.envirinsta.Officials;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterCon;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Commercial.SimpleListAdapter;
import bluepanther.envirinsta.ContentDisp.imgdisp;
import bluepanther.envirinsta.Discussions.Dis_image;
import bluepanther.envirinsta.Discussions.Img_com;
import bluepanther.envirinsta.Discussions.MapShowActivity;
import bluepanther.envirinsta.FileUtils.ImgUri;
import bluepanther.envirinsta.NGO_Grid.GPSTracker;
import bluepanther.envirinsta.NGO_Grid.NGO;
import bluepanther.envirinsta.NGO_Grid.Ngoevents;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Timeline.Timeline;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;


/**
 * Created by hhs on 24/2/17.
 */

public class Off_Dis_image extends AppCompatActivity {
    Boolean sflag1=false;
    TextView textView5,textView15, textView16, textView17,view_location;
    EditText editText2;
    ImageView imageView10, imageView6;;
    String res;
    String comment="";
    String agency;
    public static LatLng latLng =null;
    LinearLayout fake_layout;
//    private SimpleListAdapter mSimpleListAdapter;


    FloatingActionButton cmnt;

    String BaseUrl1 = "https://envirinsta.firebaseio.com/Resolvers/"+CurrentUser.sclass+"/";

    Firebase fb_dbres;

    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db,fb_db2;
    String title="",categ="",auth="";
    ArrayList<RowItem> rowItems;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView mylistview;

    private Spinner mSearchableSpinner;

    private SimpleListAdapter mSimpleListAdapter;
    private final ArrayList<String> mStrings = new ArrayList<>();


    Marker markerx;
    private GoogleMap mMap;
    ProgressDialog progressDialog;
    GPSTracker gps;
    LatLng mylatlang = new LatLng(0, 0);
    public String serverKey = "AIzaSyAjAjLy4ZKnku_DpOBDLoeULqfXbuyM6hw";
    public Double curlat, curlong;

    String[] descriptionData = {"Posted", "Noticed", "In Progress", "Resolved"};
    TextView change_status,tagsview;
    Route route;
    String location="";
    Leg leg;
    StateProgressBar stateProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_discussions_image);
        mylistview = (RecyclerView) findViewById(R.id.myListView);
        mylistview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(Off_Dis_image.this);
        mylistview.setLayoutManager(layoutManager);
        mylistview.setItemAnimator(new DefaultItemAnimator());
        Firebase.setAndroidContext(Off_Dis_image.this);
        fb_db=new Firebase(Base_url);
        System.out.println("URL IS "+BaseUrl1);
        fb_dbres = new Firebase(BaseUrl1);
        fake_layout = (LinearLayout) findViewById(R.id.fake_layout);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView15 = (TextView) findViewById(R.id.textView15);
        textView16 = (TextView) findViewById(R.id.textView16);
        textView17 = (TextView) findViewById(R.id.textView17);
        view_location = (TextView) findViewById(R.id.view_location);
        change_status = (TextView) findViewById(R.id.change_status);
tagsview=(TextView)findViewById(R.id.tags);
        editText2 = (EditText) findViewById(R.id.editText2);


        imageView10 = (ImageView) findViewById(R.id.imageView10);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        cmnt=(FloatingActionButton)findViewById(R.id.fab);
//        getActionBar().setDisplayHomeAsUpEnabled(true);


        res=this.getIntent().getExtras().get("res").toString();
        title=this.getIntent().getExtras().get("title").toString();
        categ=this.getIntent().getExtras().get("categ").toString();
        auth=this.getIntent().getExtras().get("auth").toString();

        textView17.setText(title);
        textView16.setText(categ);
        textView15.setText(auth);

        stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);


        new MyTask().execute();
        new MyTask2().execute();


        cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment=editText2.getText().toString();
                editText2.getText().clear();
                new MyTask3().execute();
            }
        });

        view_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Off_Dis_image.this,MapShowActivity.class);

                startActivity(i);
            }
        });

        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items ={"Posted", "Noticed", "In Progress", "Resolved"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(Off_Dis_image.this);
                builder.setTitle("Change Status to...");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int item) {
                        if (items[item].equals("Posted")) {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                            new ChangeStatus(1).execute();

                        } else if (items[item].equals("Noticed")) {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                            new ChangeStatus(2).execute();

                        } else if (items[item].equals("In Progress")) {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                            new ChangeStatus(3).execute();

                        } else if (items[item].equals("Resolved")) {
                            dialog.dismiss();

                            new MyTaskres().execute();

                            // stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);

                            LayoutInflater li = LayoutInflater.from(Off_Dis_image.this);

                            final View promptsView = li.inflate(R.layout.resolved_dialog, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Off_Dis_image.this);

                            alertDialogBuilder.setView(promptsView);

                            alertDialogBuilder.setTitle("Tell us who resolved it...");

                            //dialog.dismiss();

                            mSearchableSpinner = (Spinner) promptsView.findViewById(R.id.SearchableSpinner);
                            mSearchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    try {
                                        Toast.makeText(Off_Dis_image.this, "LOL", Toast.LENGTH_LONG).show();
                                        if (sflag1) {
                                            int s = mSearchableSpinner.getSelectedItemPosition();
                                            System.out.println("JIL IS " + mStrings.get(s - 1));
                                            agency = mStrings.get(i - 1);


                                            stateProgressBar.setAllStatesCompleted(true);
                                        }
                                        sflag1 = true;
                                    }
                                    catch(Exception e){}



                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    System.out.println("Sel item is "+agency);
                                    fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Resolvers").child(agency).setValue(100);
                                    Firebase changeres = fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res).child("resolvers");
                                    changeres.setValue(agency);
                                    String  date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());

                                    Firebase changeresdate = fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res).child("resdate");
                                    changeresdate.setValue(date);




                                }
                            });

                            alertDialogBuilder.setNegativeButton("Add New", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    LayoutInflater li = LayoutInflater.from(Off_Dis_image.this);

                                    final View promptsView = li.inflate(R.layout.resolved_dialog2, null);

                                    AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(Off_Dis_image.this);

                                    alertDialogBuilder2.setView(promptsView);

                                    alertDialogBuilder2.setTitle("Add a new Agency...");


                                    final EditText new_agency = (EditText) promptsView.findViewById(R.id.new_agency);



                                    alertDialogBuilder2.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            stateProgressBar.setAllStatesCompleted(true);
                                            String agency = new_agency.getText().toString();
                                            fb_db.child("Resolvers").child(CurrentUser.sclass).child(agency).setValue(100);
                                            fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Resolvers").child(agency).setValue(100);
                                            Firebase changeres = fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res).child("resolvers");
                                            changeres.setValue(agency);
                                            String  date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());

                                            Firebase changeresdate = fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res).child("resdate");
                                            changeresdate.setValue(date);

//                                            new MyTaskadd().execute();


                                        }
                                    });
                                    AlertDialog alertDialog2 = alertDialogBuilder2.create();
                                    alertDialog2.show();
                                    new ChangeStatus(4).execute();

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }

                    }
                });
                builder.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ex:
                Toast.makeText(Off_Dis_image.this,"Reported as fake!",Toast.LENGTH_SHORT).show();

                fake_layout.setBackgroundResource(R.drawable.fake_layout);

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }

    private class MyTaskres extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            fb_dbres.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        System.out.println("keys are "+postSnapshot.getKey());
                        mStrings.add(postSnapshot.getKey());

                    }

                    Set<String> hs = new HashSet<>();
                    hs.addAll(mStrings);
                    mStrings.clear();
                    mStrings.addAll(hs);

                    System.out.println("resolvers are "+mStrings);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSimpleListAdapter = new SimpleListAdapter(Off_Dis_image.this, mStrings);
                            mSearchableSpinner.setAdapter(mSimpleListAdapter);
                        }
                    });



                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }

    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            runOnUiThread(new Runnable() {
                public void run() {
                    progressDialog = new ProgressDialog(Off_Dis_image.this);
                    progressDialog.setTitle("Message");
                    progressDialog.setMessage("Downloading Image...");

                    progressDialog.setCancelable(true);
                    progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    progressDialog.show();
                }
            });



            System.out.println("Downloading" + res);

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

            System.out.println("Storage refference : " + storageReference);


            ImgUri.ref=storageReference;

            runOnUiThread(new Runnable() {
                public void run() {
                    Glide.with(Off_Dis_image.this)
                            .using(new FirebaseImageLoader())
                            .load(storageReference)
                            .into(imageView6);


                    storageReference.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                        @Override
                        public void onSuccess(StorageMetadata storageMetadata) {
                            // Metadata now contains the metadata for 'images/forest.jpg'
                            try {
                                Double lat = Double.parseDouble(storageMetadata.getCustomMetadata("latitude"));
                                Double lon = Double.parseDouble(storageMetadata.getCustomMetadata("longitude"));
                                int status=Integer.parseInt(storageMetadata.getCustomMetadata("Progress"));

                                latLng = new LatLng(lat, lon);
                                Dis_image.latLng=latLng;
                                switch(status)
                                {
                                    case 1:
                                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

                                        break;
                                    case 2:
                                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

                                        break;
                                    case 3:
                                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

                                        break;
                                    case 4:
                                        stateProgressBar.setAllStatesCompleted(true);
                                        break;

                                }
                                String tags=storageMetadata.getCustomMetadata("Tags");
                                String []tagarr=tags.split(",");
                                String fintag="";
                                for(int i=0;i<tagarr.length;i++)
                                {
                                    fintag=fintag+"#"+tagarr[i]+"      ";
                                }
                                tagsview.setText(fintag);
                                // new MyTaskMap().execute();
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
                    progressDialog.dismiss();

                }
            });
            runOnUiThread(new Runnable() {
                public void run() {
                    imageView6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Off_Dis_image.this, imgdisp.class);

                            startActivity(i);
                        }
                    });
                }
            });




            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }

    private class MyTask2 extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            rowItems=new ArrayList<>();
            fb_db2=new Firebase(Base_url+"Discussions/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Images/"+res);
            fb_db2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    Img_com images = dataSnapshot.getValue(Img_com.class);
                    String date = images.date;
                    String user=images.user;
                    String comment=images.comment;
                    RowItem item = new RowItem(user,
                            R.drawable.doc, comment,
                            date, "","Comment");
                    rowItems.add(item);

                    CustomAdapterCon adapter=new CustomAdapterCon(Off_Dis_image.this,rowItems);
                    mylistview.setAdapter(adapter);

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

            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }

    private class MyTask3 extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
            Img_com img = new Img_com(CurrentUser.user,date,comment);

            String imagenode = CurrentUser.user + date;

            fb_db.child("Discussions").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res).child("Comm_"+imagenode).setValue(img);

            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class ChangeStatus extends AsyncTask<String, Integer, String> {
        int status;
        public ChangeStatus(int status)
        {
            this.status=status;
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);
            StorageMetadata metadata=null;
            System.out.println("Storage refference : " + storageReference);
            switch(status) {
                case 1:
                    metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpeg")
                            .setCustomMetadata("latitude", String.valueOf(latLng.latitude))
                            .setCustomMetadata("longitude", String.valueOf(latLng.longitude))
                            .setCustomMetadata("Progress", "1")
                            .build();
                    break;
                case 2:
                    metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpeg")
                            .setCustomMetadata("latitude", String.valueOf(latLng.latitude))
                            .setCustomMetadata("longitude", String.valueOf(latLng.longitude))
                            .setCustomMetadata("Progress", "2")
                            .build();
                    break;
                case 3:
                    metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpeg")
                            .setCustomMetadata("latitude", String.valueOf(latLng.latitude))
                            .setCustomMetadata("longitude", String.valueOf(latLng.longitude))
                            .setCustomMetadata("Progress", "3")
                            .build();
                    break;
                case 4:
                    metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpeg")
                            .setCustomMetadata("latitude", String.valueOf(latLng.latitude))
                            .setCustomMetadata("longitude", String.valueOf(latLng.longitude))
                            .setCustomMetadata("Progress", "4")
                            .build();
                    break;

            }
            storageReference.updateMetadata(metadata)
                    .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                        @Override
                        public void onSuccess(StorageMetadata storageMetadata) {
                            // Updated metadata is in storageMetadata
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                        }
                    });


            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTaskadd extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
            Img_com img = new Img_com(CurrentUser.user,date,comment);

            String imagenode = CurrentUser.user + date;

            fb_db.child("Discussions").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res).child("Comm_"+imagenode).setValue(img);

            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }

}
