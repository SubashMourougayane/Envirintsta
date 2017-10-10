package bluepanther.envirinsta.NGO_Grid;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.R;

/**
 * Created by Hariharsudan on 12/19/2016.
 */

public class NGO_user_page extends AppCompatActivity {

    private RecyclerView mRecyclerView;
      ArrayList<DataObject> results = new ArrayList<DataObject>();
String finalngo1,finalngo2;
    private MyRecyclerViewAdapter2 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "NGO";
    TextView NGOname,NGOinfo;
    Button follow;
    Button likesbut;

    String ngoname,ngoinfo;
    newNGO obj;
    Firebase fb_db,fb_db2,fb_db3,fb_db5;
    private String Base_url = "https://envirinsta.firebaseio.com/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_user_page);
        ngoname = this.getIntent().getExtras().getString("NGOname");
        ngoinfo = this.getIntent().getExtras().getString("NGOinfo");

        Firebase.setAndroidContext(this);
//        getSupportActionBar().setTitle("NGO");
//        getSupportActionBar().show();

        follow = (Button) findViewById(R.id.follow);
        NGOname = (TextView)findViewById(R.id.textView10) ;
        NGOinfo = (TextView)findViewById(R.id.textView11);
        NGOname.setText(ngoname);
        NGOinfo.setText(ngoinfo);

       // likesbut = (Button)findViewById(R.id.likesbut);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(NGO_user_page.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter2(NGO_user_page.this,new ArrayList<DataObject>());
//        mRecyclerView.setAdapter(mAdapter);
        new MyTask().execute();

        new MyTask2().execute();
//
//        likesbut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new MyTaskLike().execute();
//            }
//        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Noting"+follow.getText());
                if(follow.getText().equals("Follow"))
                {

                    new MyTask3().execute();
                    follow.setText("Following");
                    Toast.makeText(NGO_user_page.this,"You are now following this NGO", Toast.LENGTH_SHORT).show();
                }

            }
        });
       mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter2
                .MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NGO_user_page.this);
                alertDialogBuilder.setTitle("Join Event");
                alertDialogBuilder
                        .setMessage("Do you want to join this event!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finalngo1=results.get(position).getmText1();
                                finalngo2=results.get(position).getmText2();
                                Toast.makeText(NGO_user_page.this,"You are now following this event", Toast.LENGTH_SHORT).show();
                                new MyTask4().execute();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    public class MyTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db = new Firebase(Base_url+"Classes/"+ CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGOevents/"+ngoname+"/");
            fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        Ngoevents ngoupdate = postSnapshot.getValue(Ngoevents.class);

                        String ngoname=ngoupdate.getNevent();
                        String ngodesc=ngoupdate.getNpurp();
                        String joins= Integer.toString(ngoupdate.getJoiners().size())+" joining";
                        String location=ngoupdate.getNplace();


                        DataObject obj = new DataObject(ngoname,ngodesc,joins,location);
                        results.add(obj);
                    }
                    mAdapter = new MyRecyclerViewAdapter2(NGO_user_page.this,results);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }
    public class MyTask2 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            final ArrayList results = new ArrayList<DataObject>();

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGO/");
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        newNGO ngoupdate = postSnapshot.getValue(newNGO.class);

                        String ngonames=ngoupdate.getNgoname();
                        if(ngonames.equals(ngoname))
                        {
                            try {
                                ArrayList<String> fols = new ArrayList<>(ngoupdate.getFollowers());
                                for (int i = 0; i < fols.size(); i++) {
                                    if (CurrentUser.user.equals(fols.get(i))) {
                                        follow.setText("Following");
                                        break;
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                                System.out.println("No followers");
                            }

                        }

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }
    public class MyTask3 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            final ArrayList results = new ArrayList<DataObject>();

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db3 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGO/");
            fb_db3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        newNGO ngoupdate = postSnapshot.getValue(newNGO.class);

                        String ngonames=ngoupdate.getNgoname();
                        if(ngonames.equals(ngoname))
                        {
                            ArrayList<String> fols=new ArrayList<>(ngoupdate.getFollowers());
                            fols.add(CurrentUser.user);
                            newNGO ngoobj=new newNGO();
                            ngoobj.setNgoname(ngoupdate.getNgoname());
                            ngoobj.setNgoadmin(ngoupdate.getNgoadmin());
                            ngoobj.setNgopurpose(ngoupdate.getNgopurpose());
                            ngoobj.setNgoinfo(ngoupdate.getNgoinfo());
                            ngoobj.setNgouname(ngoupdate.getNgouname());
                            ngoobj.setNgopass(ngoupdate.getNgopass());

                            ngoobj.setFollowers(fols);
                            fb_db3.child(ngoname).setValue(ngoobj);
                        }

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }
    public class MyTask4 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            final ArrayList results = new ArrayList<DataObject>();

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db5 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGOevents/"+ngoname+"/");
            fb_db5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        Ngoevents ngoupdate = postSnapshot.getValue(Ngoevents.class);

                        String ngoname=ngoupdate.getNevent();
                        String ngodesc=ngoupdate.getNpurp();

                        if(ngoname.equals(finalngo1))
                        {
                            ArrayList<String> fols=new ArrayList<>(ngoupdate.getJoiners());
                            fols.add(CurrentUser.user);
                            Ngoevents obj=new Ngoevents();
                            obj.setNevent(ngoupdate.getNevent());
                            obj.setNorg(ngoupdate.getNorg());
                            obj.setNpurp(ngoupdate.getNpurp());
                            obj.setNplace(ngoupdate.getNplace());
                            obj.setNtime(ngoupdate.getNtime());
                            obj.setNdate(ngoupdate.getNdate());
                            obj.setJoiners(fols);
                            fb_db5.child(finalngo1).setValue(obj);
                        }


                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }

    public class MyTaskLike extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db = new Firebase(Base_url+"User_leaderboards/NGO/"+ CurrentUser.sclass+"/"+CurrentUser.ssec+"/"+CurrentUser.user);

            fb_db.setValue(1);
//            fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot)
//                {
//
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("FIREBASE ERROR OCCOURED");
//                }
//            });

            return null;
        }
    }
}
