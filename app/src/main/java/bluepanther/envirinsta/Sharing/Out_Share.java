package bluepanther.envirinsta.Sharing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bluepanther.envirinsta.Adapter.Cred_Update;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterShare;
import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.R;

/**
 * Created by SUBASH.M on 11/17/2016.
 */

public class Out_Share extends AppCompatActivity {

    static RecyclerView listView;
    static Uri imageUri;
   static Activity context;
    ProgressDialog progressDialog;
    ArrayList<String> sendlist;
    CustomAdapterShare adapter;
    private RecyclerView.LayoutManager layoutManager;
    public  static View.OnClickListener myOnClickListener;
    static List<RowItem> shareList = new ArrayList<>();
    static private String Base_url = "https://envirinsta.firebaseio.com/";
   static private Firebase fb_db,fb_db2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_share);

        getSupportActionBar().setTitle("Send to");
        Preferences.getPrefs(getApplicationContext());
        Firebase.setAndroidContext(this);
        sendlist = new ArrayList<>();
        context=Out_Share.this;
        listView = (RecyclerView) findViewById(R.id.listView);
        listView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CustomAdapterShare(Out_Share.this, shareList);
        listView.setAdapter(adapter);
        listView.setAdapter(null   );



        myOnClickListener = new Out_Share.MyOnClickListener(Out_Share.this);
        //progressDialog=new ProgressDialog(Out_Share.this);
      //  progressDialog.show(this,"Finding Contacts","Loading");


        new MyTask().execute();
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/".equals(type)) {

                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {

                handleSendImage(intent); // Handle single image being sent
            }
            else if (type.startsWith("audio/")) {

                handleSendAudio(intent); // Handle single audio being sent
            }
            else if (type.startsWith("video")) {

                handleSendVideo(intent); // Handle single video being sent
            }
            else if (type.startsWith("file/")) {

                handleSendFiles(intent); // Handle single file being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            shareList.add(new RowItem("Group",R.drawable.group,"","","",""));
            fb_db = new Firebase(Base_url+"Accounts/");
            fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                        String uname = cred_update.getUsn();
System.out.println("Adding"+uname);
                        RowItem item = new RowItem(uname,R.drawable.admin1, "", "","","");
                        shareList.add(item);
                    }
                    adapter = new CustomAdapterShare(Out_Share.this, shareList);
                    listView.setAdapter(adapter);
                  //  progressDialog.dismiss();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
         imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

    }


    void handleSendAudio(Intent intent) {
        Uri audioUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (audioUri != null) {
            // Update UI to reflect audio being shared
        }
    }


    void handleSendVideo(Intent intent) {
        Uri videoUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (videoUri != null) {
            // Update UI to reflect video being shared
        }
    }


    void handleSendFiles(Intent intent) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (fileUri != null) {
            // Update UI to reflect files being shared
        }
    }
    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }
    private  static class MyOnClickListener implements View.OnClickListener {

        private final Context context;
        ProgressDialog progressDialog;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            progressDialog = ProgressDialog.show(context, "Message", "Sharing Image...");
            String date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
            final ImageDesc imageDesc;
            imageDesc = new ImageDesc();
            imageDesc.setUser(CurrentUser.user);
            imageDesc.setDate(date);
            imageDesc.setDesc(getFileName(imageUri));
            imageDesc.setMaincat("Shared");
            imageDesc.setSubcat("Images");
            if(listView.getChildPosition(v)==0)
            {
                imageDesc.setTarget("all");
                imageDesc.setTargetmems(new ArrayList<String>());
                final String imagenode = CurrentUser.user + date;
                System.out.println("  imagenode  " + imagenode);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode);

                System.out.println("Storage refference : " + storageReference);

                UploadTask up = storageReference.putFile(imageUri);
                up.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fb_db2=new Firebase(Base_url);
                        fb_db2.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode).setValue(imageDesc);


                        Toast.makeText(context, "Image Shared", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                });
            }
            else {

                String target=shareList.get(listView.getChildPosition(v)).getMember_name();
                ArrayList<String>tar=new ArrayList<>();
                tar.add(target);
                imageDesc.setTarget("indi");
                imageDesc.setTargetmems(tar);
                final String imagenode = CurrentUser.user + date;
                System.out.println("  imagenode  " + imagenode);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode);

                System.out.println("Storage refference : " + storageReference);

                UploadTask up = storageReference.putFile(imageUri);
                up.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fb_db2=new Firebase(Base_url);
                        fb_db2.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode).setValue(imageDesc);


                        Toast.makeText(context, "Image Shared", Toast.LENGTH_LONG).show();  progressDialog.dismiss();

                    }
                });


            }
        }

        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
            try{
                if(result.length()>12)
                {
                    result=result.substring(0,10);
                }

            }
            catch(Exception e)
            {

            }
            return result;
        }
    }
}
