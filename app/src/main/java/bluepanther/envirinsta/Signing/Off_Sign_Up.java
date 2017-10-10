package bluepanther.envirinsta.Signing;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bluepanther.envirinsta.Adapter.Cred_Update;
import bluepanther.envirinsta.FileUtils.ImageCompressor;
import bluepanther.envirinsta.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Off_Sign_Up extends AppCompatActivity {
CircleImageView pro_pic_upload;
    Cred_Update cred_update;
    Uri imageUri,selectedImageUri;
    EditText name,password,sclass,section,mobile,reEnterPassword;
    String usn,pass,cls,sec,mob,rep;
    Button register;
    Boolean flag=true;
    FloatingActionButton pro_pic_button;
    TextView login,namelabel,classlabel,seclabel,passlabel,repasslabel,moblabel;
    String date;
private static final int PICK_IMAGE = 1;
    ProgressDialog progressDialog;

    private static final int PICK_Camera_IMAGE = 2;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db,fb_db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_signup);

         MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2);

pro_pic_upload = (CircleImageView)findViewById(R.id.pro_pic_upload);
        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
//        sclass = (EditText)findViewById(R.id.sclass);
//        section = (EditText)findViewById(R.id.section);
        register = (Button)findViewById(R.id.registerb);
        mobile = (EditText) findViewById(R.id.mobile);
//        seclabel = (TextView) findViewById(R.id.seclabel);
        reEnterPassword = (EditText)findViewById(R.id.reEnterPassword);
        login = (TextView) findViewById(R.id.login);
        namelabel = (TextView)findViewById(R.id.namelabel);
        classlabel = (TextView)findViewById(R.id.classlabel);
        passlabel = (TextView)findViewById(R.id.passlabel);
        repasslabel = (TextView)findViewById(R.id.repasslabel);
        moblabel = (TextView)findViewById(R.id.mobilelabel);
        pro_pic_button = (FloatingActionButton)findViewById(R.id.pro_pic_button);


        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Ubuntu-R.ttf");
        login.setTypeface(typeface);
        namelabel.setTypeface(typeface);
        classlabel.setTypeface(typeface);
        passlabel.setTypeface(typeface);
        repasslabel.setTypeface(typeface);
        moblabel.setTypeface(typeface);

        ArrayList spinlist=new ArrayList();
        String str[]=getResources().getStringArray(R.array.City);
        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<str.length;i++)
        {
            list.add(str[i]);
        }
       // MaterialSpinnerAdapter adapter = new
       //         MaterialSpinnerAdapter(Sign_Up.this,list);


        spinner.setItems("Select city","Agra","Ahmedabad","Bangalore","Bhopal","Chennai","Coimbatore","Delhi","Ghaziabad","Hyderbad","Indore","Jaipur","Kanpur","Kolkata","Lucknow","Ludhiana","Madurai","Mumbai","Nagpur","Patna","Pimpri-Chinchwad","Pune","Surat","Thane","Pondicherry","Vadodara","Visakapatinam");
        spinner2.setItems("Select Category", "Roads","Transport","Water Lines","Bank/ATM","Hospitals","Politics","Govt Offices","Sewage","General PWD");

        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

       // spinner.setAdapter(adapter);
//        seclabel.setTypeface(typeface);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                                              @Override
                                              public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                                  Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                                                  cls=item;
                                              }
                                          });

        Firebase.setAndroidContext(this);
        fb_db = new Firebase(Base_url);
pro_pic_button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          selectImage();
                                      }
                                  });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Off_Sign_Up.this, Sign_In.class);
                finish();
                startActivity(intent);

            }
        });
register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        date = new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());

        usn = name.getText().toString();
        pass = password.getText().toString();

//        cls = sclass.getText().toString();
//        sec = section.getText().toString().toUpperCase();
        sec="A";
        mob = mobile.getText().toString();
        rep = reEnterPassword.getText().toString();

        System.out.println("noob");
        boolean a = validate();
        System.out.println("noob3");
        if(a)
            new MyTask().execute();
        else
            Toast.makeText(Off_Sign_Up.this,"Registration Failed",Toast.LENGTH_SHORT).show();
    }
});
    }


    public boolean validate() {
        System.out.println("noob2");
        boolean valid = true;

        if (usn.isEmpty() || usn.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (cls.isEmpty()) {
//            sclass.setError("Enter Valid Class");
//            valid = false;
        } else {
//            sclass.setError(null);
        }
        if (sec.isEmpty()) {
//            section.setError("Enter Valid Section");
//            valid = false;
        } else {
//            section.setError(null);
        }

        if (mob.isEmpty() || mob.length()!=10) {
            mobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobile.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (rep.isEmpty() || rep.length() < 4 || rep.length() > 10 || !(rep.equals(pass))) {
            reEnterPassword.setError("Password Do not match");
            valid = false;
        } else {
           reEnterPassword.setError(null);
        }
        if(selectedImageUri==null)
        {
            valid=false;
            Toast.makeText(Off_Sign_Up.this,"Please select a profile image",Toast.LENGTH_SHORT).show();
        }

        return valid;
    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Off_Sign_Up.this, "Message", "Creating Account...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            //  Accounts doc=new Accounts(date,fullname,image);
            flag=true;
            fb_db2 = new Firebase(Base_url+"Accounts/");
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                        String uname = cred_update.getUsn();
                        System.out.println("Adding"+uname);
                        if(uname.equals(usn))
                        {
                            progressDialog.dismiss();
                            flag=false;
                            AlertDialog.Builder builder = new AlertDialog.Builder(Off_Sign_Up.this);
                            builder.setMessage("Username already exists! Please choose a different name")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                    if(flag)
                    {
                        cred_update = new Cred_Update();
                        cred_update.setUsn(usn);
                        cred_update.setPass(pass);
                        cred_update.setCls(cls);
                        cred_update.setSec(sec);
                        cred_update.setIdate(date);
                        cred_update.setAdate(date);
                        cred_update.setVdate(date);
                        cred_update.setFdate(date);
                        cred_update.setTdate(date);
                        cred_update.setType("Official");
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(cls).child(sec).child("MemberPics").child(usn);

                        System.out.println("Storage refference : " + storageReference);

                        UploadTask up = storageReference.putFile(selectedImageUri);
                        up.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                fb_db.child("Accounts").child(usn).setValue(cred_update);
                                fb_db.child("Classes").child(cls).child(sec).child("Members").child(usn).setValue(cred_update);
                                Toast.makeText(Off_Sign_Up.this,"Account Created",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Off_Sign_Up.this, Sign_In.class);
                                finish();
                                startActivity(intent);
                                progressDialog.dismiss();

                            }
                        });

                    }
                    //  progressDialog.dismiss();
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
            if(result.equals("SUCCESS")){
                System.out.println("SUCCESS");



            }
            else{
                Toast.makeText(Off_Sign_Up.this,"Failed..",Toast.LENGTH_LONG).show();
            }


            // Do things like hide the progress bar or change a TextView
        }
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Off_Sign_Up.this);
        builder.setTitle("Choose picture..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    String fileName = "new-photo-name.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                    imageUri = Off_Sign_Up.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //create new Intent
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            PICK_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {

            if (requestCode == PICK_Camera_IMAGE) {
                if (resultCode == this.RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;

                } else if (resultCode == this.RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                try {
                    Uri tmpUri=Uri.fromFile(new File(ImageCompressor.with(getApplicationContext()).compress(selectedImageUri.toString())));
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , tmpUri);
                    pro_pic_upload.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (requestCode == PICK_IMAGE) {
                if (resultCode == this.RESULT_OK) {
                    selectedImageUri = data.getData();
                } else if (resultCode == this.RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , selectedImageUri);
                    pro_pic_upload.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }}}

}
