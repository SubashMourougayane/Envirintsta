package bluepanther.envirinsta.NGO_Grid;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.R;

/**
 * Created by Hariharsudan on 12/17/2016.
 */

public class NGO extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    newNGO ngoobj;
    ImageView imageView10;
    private RecyclerView mRecyclerView;
    String eventimage;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "NGO";
    Button create_event;
    String ename1,pname1,plname1,tname1,dname1,oname1;
    public Firebase fb_db2,fb_db;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo_page);
        ngoobj=(newNGO)this.getIntent().getExtras().get("NGOclass");

        Firebase.setAndroidContext(this);
        TextView title = (TextView)findViewById(R.id.textView10);
        TextView info = (TextView)findViewById(R.id.textView11);

        title.setText(ngoobj.getNgoname());
        info.setText(ngoobj.getNgoinfo());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(NGO.this,new ArrayList<DataObject>());
//        mRecyclerView.setAdapter(mAdapter);


        create_event = (Button) findViewById(R.id.create_event);
        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog event = new Dialog(NGO.this);
                // Set GUI of login screen
                event.setContentView(R.layout.new_event_dialog);
                event.setTitle("Create an Event");

                // Init button of login GUI
                Button create = (Button) event.findViewById(R.id.create);
                Button cancel = (Button) event.findViewById(R.id.cancel);

                final EditText ename=(EditText)event.findViewById(R.id.txtEvent);
                final EditText oname=(EditText)event.findViewById(R.id.txtOrganizer);
                final EditText pname=(EditText)event.findViewById(R.id.txtPurpose);
                final EditText plname=(EditText)event.findViewById(R.id.txtPlace);
                final EditText tname=(EditText)event.findViewById(R.id.txtTime);
                final EditText dname=(EditText)event.findViewById(R.id.txtDate);
                imageView10 = (ImageView)event.findViewById(R.id.imageView10);
                imageView10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_IMAGE);
                    }
                });

                // Attached liste  ner for login GUI button
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ename1=ename.getText().toString();
                        oname1=oname.getText().toString();
                        pname1=pname.getText().toString();
                        plname1=plname.getText().toString();
                        tname1=tname.getText().toString();
                        dname1=dname.getText().toString();
                        ArrayList<String> joins = new ArrayList<String>();
                        joins.add("dummy");
                        Ngoevents obj=new Ngoevents();
                        obj.setNevent(ename1);
                        obj.setNorg(oname1);
                        obj.setNpurp(pname1);
                        obj.setNplace(plname1);
                        obj.setNtime(tname1);
                        obj.setNdate(dname1);
                        obj.setJoiners(joins);
                        fb_db2=new Firebase(Base_url);
                        fb_db2.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("NGOevents").child(ngoobj.getNgoname()).child(ename1).setValue(obj);
event.dismiss();

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        event.dismiss();
                    }
                });

                // Make dialog box visible.
                event.show();
            }
        });
        new MyTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView10.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            eventimage= Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);

            }
        });
    }


    public class MyTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            final ArrayList results = new ArrayList<DataObject>();

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db = new Firebase(Base_url+"Classes/"+ CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGOevents/"+ngoobj.getNgoname()+"/");
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
                    mAdapter = new MyRecyclerViewAdapter(NGO.this,results);
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
}