package bluepanther.envirinsta.Contacts;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Contacts.RowItem;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.Timeline.Timeline;
import bluepanther.envirinsta.Travel.Travel;


/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class Contacts2 extends AppCompatActivity implements AdapterView.OnItemClickListener, OnMenuItemLongClickListener, OnMenuItemClickListener {

    Boolean flagz;

    ProgressDialog imgprog;
    String[] member_names;
    TypedArray profile_pics;
    String[] statues;
    String result;
    String[] time;
    ImageView imgg;
    ArrayList<String> cntcheck;
    ArrayList<String> name, categ, dates;
    ArrayList<String> content;
    String maincat="Academics",subcat="Time Table";
    EditText from_date, to_date;
    List<RowItem> rowItems;
    ListView mylistview,mylistview2;
    Button download;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    int flag;
    String file1;

    public Uri filuri;
    public Boolean go=true,filtered1=false,filtered2=false;
    CustomAdapter adapter,adapter2;
    Spinner category, subcategory, author;

    String tmp1, tmp2, tmp3, tmp4, tmp5;
    String seltab = "Text";

    ArrayList<String> namei, categi, datesi, contenti, authi;
    ArrayList<String> namea, catega, datesa, contenta, autha;
    ArrayList<String> namev, categv, datesv, contentv, authv;
    ArrayList<String> namef, categf, datesf, contentf, authf;
    ArrayList<String> namet, categt, datest, contentt, autht;


    ArrayList<String> namei2, categi2, datesi2, contenti2, authi2;
    ArrayList<String> namea2, catega2, datesa2, contenta2, autha2;
    ArrayList<String> namev2, categv2, datesv2, contentv2, authv2;
    ArrayList<String> namef2, categf2, datesf2, contentf2, authf2;
    ArrayList<String> namet2, categt2, datest2, contentt2, autht2;

    String[] member_namesi;
    String[] member_namesa;
    String[] member_namesv;
    String[] member_namesf;
    String[] member_namest;

    String[] member_namesi2;
    String[] member_namesa2;
    String[] member_namesv2;
    String[] member_namesf2;
    String[] member_namest2;



    String[] statuesi;
    String[] timei;
    String[] authori;

    String[] statuesi2;
    String[] timei2;
    String[] authori2;


    String[] statuesa;
    String[] timea;
    String[] authora;

    String[] statuesa2;
    String[] timea2;
    String[] authora2;


    String[] statuesv;
    String[] timev;
    String[] authorv;

    String[] statuesv2;
    String[] timev2;
    String[] authorv2;


    String[] statuesf;
    String[] timef;
    String[] authorf;


    String[] statuesf2;
    String[] timef2;
    String[] authorf2;


    String[] statuest;
    String[] timet;
    String[] authort;


    String[] statuest2;
    String[] timet2;
    String[] authort2;


    List<RowItem> rowItemsi, rowItemsa, rowItemsv, rowItemsf, rowItemst;
    List<RowItem> rowItemsi2, rowItemsa2, rowItemsv2, rowItemsf2, rowItemst2;

    public static String[] member_name = {"Harisudhan","Shyam","Subash","Thamarai"};
    public static String[] mail = {"nadus77@gmail.com","shyamsuganth@gmail.com","subash@gmail.com","lotus@gmail.com"};
    public static String[] rank = {"Senior Manager","Executive Engineer","Assistant Surveyor","Junior Engineer"};
    public static String[] phone = {"9751551087","9878977281","9994605758","9234586798"};

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    String day1, day2, month1, month2, year1, year2, sfrom, sto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts2);

        getSupportActionBar().setTitle("Contacts");
        mylistview = (ListView) findViewById(R.id.listView);

       // mylistview2 = (ListView) findViewById(R.id.listView2);

        Firebase.setAndroidContext(this);
        fb_db = new Firebase(Base_url);

        category = (Spinner) findViewById(R.id.category);
        //subcategory = (Spinner) findViewById(R.id.subcategory);
        //author = (Spinner) findViewById(R.id.author);


        download = (Button) findViewById(R.id.download);
        Button down2=download;

//        from_da=te = (EditText) findViewById(R.id.from_date);
//        to_date = (EditText) findViewById(R.id.to_date);
//        imgg = (ImageView) findViewById(R.id.frecord);

       // ((MetaballMenu) findViewById(R.id.metaball_menu)).setMenuClickListener(this);


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sp1 = String.valueOf(category.getSelectedItem());
                filtered1=true;
                maincat=sp1;
                if (sp1.contentEquals("Roads")) {



//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter);
                }
                if (sp1.contentEquals("Transport")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter2);
                }
                if (sp1.contentEquals("Water Lines")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter3.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter3);
                }
                if (sp1.contentEquals("Bank/ATM")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter4.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter4);
                }
                if (sp1.contentEquals("Hospitals")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter5.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter5);
                }
                if (sp1.contentEquals("Politics")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter6.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter6);
                }
                if (sp1.contentEquals("Govt Offices")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter7.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter7);
                }
                if (sp1.contentEquals("Sewage")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter8 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter8.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter8);
                }
                if (sp1.contentEquals("General PWD")) {

//                    List<String> list = new ArrayList<String>();
//                    list.add("Officer Rank 1");
//                    list.add("Officer Rank 2");
//                    list.add("Officer Rank 3");
//                    list.add("Officer Rank 4");
//                    list.add("Officer Rank 5");
//                    list.add("Officer Rank 6");
//
//                    ArrayAdapter<String> dataAdapter9 = new ArrayAdapter<String>(Contacts.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter9.notifyDataSetChanged();
//                    subcategory.setAdapter(dataAdapter9);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                filtered2=true;
//                String sp1 = String.valueOf(subcategory.getSelectedItem());
//                subcat=sp1;
//
//
//            }

//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


//        from_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePicker();
//            }
//        });
//
//        to_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePicker2();
//            }
//        });
        rowItems = new ArrayList<RowItem>();
        rowItemsi = new ArrayList<RowItem>();
        rowItemsa = new ArrayList<RowItem>();
        rowItemsv = new ArrayList<RowItem>();
        rowItemsf = new ArrayList<RowItem>();
        rowItemst = new ArrayList<RowItem>();

//        rowItemsi2 = new ArrayList<RowItem>();
//        rowItemsa2 = new ArrayList<RowItem>();
//        rowItemsv2 = new ArrayList<RowItem>();
//        rowItemsf2 = new ArrayList<RowItem>();
//        rowItemst2 = new ArrayList<RowItem>();

        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);


        down2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rowItemsi.clear();
                for (int i = 0; i < member_name.length; i++) {
                    RowItem item = new RowItem(member_name[i],
                            R.drawable.contatcs, mail[i],
                            rank[i], phone[i]);
                    rowItemsi.add(item);
                    System.out.println("mangatha added" + rowItemsi);
                }
                adapter = new CustomAdapter(Contacts2.this, rowItemsi);
                mylistview.setAdapter(adapter);

//                adapter2 = new CustomAdapter(Contacts.this, rowItemst2);
//                mylistview2.setAdapter(adapter2);
              //  new MyTask().execute();
            }
        });
//        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (seltab) {
//                    case "Text":
//                        final String res = rowItemst.get(position).getAuthor() + rowItemst.get(position).getTime();
//
//                        System.out.println("Downloading" + res);
//                        String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res + "/";
//                        fb_db = new Firebase(tmp5);
//                        fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                TextDesc obj = dataSnapshot.getValue(TextDesc.class);
//                                result = obj.text;
//                                System.out.println("TXT IS " + result);
//                                Intent i = new Intent(Contacts.this, txtdisp.class);
//                                i.putExtra("value", result);
//                                startActivity(i);
//                            }
//
//                            @Override
//                            public void onCancelled(FirebaseError firebaseError) {
//
//                            }
//
//                        });
//
//
//                        break;
//                    case "Image":
//
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading Image...");
//
////
////                        Toast toast =Toast.makeText(getApplicationContext(),"DOWNLOADING IMAGE",Toast.LENGTH_LONG);
////                        toast.setGravity(Gravity.CENTER,0,0);
////                        toast.show();
//
//                        final String res2 = rowItemsi.get(position).getAuthor() + rowItemsi.get(position).getTime();
//
//                        System.out.println("Downloading" + res2);
//
//                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res2);
//
//                        System.out.println("Storage refference : " + storageReference);
//
//                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                imgprog.dismiss();
//                                System.out.println("NOOB");
//                                Intent i = new Intent(Contacts.this, imgdisp.class);
//                                ImgUri.uri = uri;
//                                startActivity(i);
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad" + exception);
//                            }
//                        });
//
//                        break;
//                    case "Audio":
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading Audio...");
//
//                        final String res3 = rowItemsa.get(position).getAuthor() + rowItemsa.get(position).getTime();
//                        System.out.println("Downloading" + res3);
//
//                        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res3);
//
//                        System.out.println("Storage refference : " + storageReference2);
//
//
//                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                System.out.println("NOOB");
//                                Intent i = new Intent();
//                                i.setAction(Intent.ACTION_VIEW);
//                                i.setDataAndType(uri, "audio/*");
//                                imgprog.dismiss();
//                                startActivity(i);
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad" + exception);
//                            }
//                        });
//                        break;
//                    case "Video":
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading Video...");
//
//                        final String res4 = rowItemsv.get(position).getAuthor() + rowItemsv.get(position).getTime();
//                        System.out.println("Downloading" + res4);
//
//                        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res4);
//
//                        System.out.println("Storage refference : " + storageReference3);
//
//
//                        storageReference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                System.out.println("NOOB");
//                                Intent i = new Intent();
//                                i.setAction(Intent.ACTION_VIEW);
//                                i.setDataAndType(uri, "video/*");
//                                imgprog.dismiss();
//                                startActivity(i);
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad" + exception);
//                            }
//                        });
//                        break;
//                    case "File":
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading File...");
//
//                        final String res5 = rowItemsf.get(position).getAuthor() + rowItemsf.get(position).getTime();
//                        System.out.println("Downloading" + res5);
//
//                        final StorageReference storageReference4 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res5);
//
//                        System.out.println("Storage refference : " + storageReference4);
//
//
//                        storageReference4.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
//
//                            @Override
//                            public void onSuccess(StorageMetadata storageMetadata) {
//
//
//                                System.out.println("Type is"+storageMetadata.getContentType()+"end");
//                                String splitarr[]=storageMetadata.getContentType().split("/");
//                                System.out.println("Sharp type is"+splitarr[splitarr.length-1]);
//                               final String ftype=storageMetadata.getContentType();
//
//                                if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
//                                    file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";
//
//                                } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
//                                    file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
//                                } else if (ftype.contains("pdf")) {
//                                    file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];
//
//                                }
//                                final File files = new File(file1);
//
//                                storageReference4.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                                        // Metadata now contains the metadata for 'images/forest.jpg'
//                                        imgprog.dismiss();
//
//                                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                                        if (ftype.contains("pdf")) {
//                                            intent.setDataAndType(Uri.fromFile(files), ftype);
//                                        } else if (ftype.equals("octet-stream") || ftype.contains("text") || ftype.contains("xml")) {
//                                            intent.setDataAndType(Uri.fromFile(files), "text/plain");
//
//                                        } else if (ftype.contains("x-zip") || ftype.contains("word") || ftype.contains("msword")) {
//                                            intent.setDataAndType(Uri.fromFile(files), "application/msword");
//
//                                        } else if (ftype.equals("presentation")) {
//                                            intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");
//
//                                        } else if (ftype.equals("spreadsheet") || ftype.equals("sheet")) {
//                                            intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");
//
//                                        }
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                                        try {
//                                            startActivity(intent);
//                                        } catch (Exception e) {
//                                            System.out.println("EXCEPTION IS "+e);
//                                            Toast.makeText(Contacts.this,"Invalid File type",Toast.LENGTH_LONG).show();
//                                            // Instruct the user to install a PDF reader here, or something
//                                        }
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception exception) {
//                                        // Uh-oh, an error occurred!
//                                    }
//                                });
//
//
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad" + exception);
//                            }
//                        });
//                        break;
//                }
//            }
//        });
//        mylistview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (seltab) {
//                    case "Text":
//                        imgprog = ProgressDialog.show(Contacts.this,"Download","Downloading Text");
//
//                        final String res = rowItemst2.get(position).getAuthor() + rowItemst2.get(position).getTime();
//
//                        System.out.println("Downloading2" + res);
//                        String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res + "/";
//                        fb_db = new Firebase(tmp5);
//                        fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                TextDesc obj = dataSnapshot.getValue(TextDesc.class);
//                                result = obj.text;
//                                System.out.println("TXT IS " + result);
//                                Intent i = new Intent(Contacts.this, txtdisp.class);
//                                i.putExtra("value", result);
//                                imgprog.dismiss();
//                                startActivity(i);
//                            }
//
//                            @Override
//                            public void onCancelled(FirebaseError firebaseError) {
//
//                            }
//
//                        });
//
//
//                        break;
//                    case "Image":
//
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading Image...");
//
////
////                        Toast toast =Toast.makeText(getApplicationContext(),"DOWNLOADING IMAGE",Toast.LENGTH_LONG);
////                        toast.setGravity(Gravity.CENTER,0,0);
////                        toast.show();
//
//                        final String res2 = rowItemsi2.get(position).getAuthor() + rowItemsi2.get(position).getTime();
//
//                        System.out.println("Downloading2" + res2);
//
//                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res2);
//
//                        System.out.println("Storage refference2 : " + storageReference);
//
//                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                imgprog.dismiss();
//                                System.out.println("NOOB2");
//                                Intent i = new Intent(Contacts.this, imgdisp.class);
//                                ImgUri.uri = uri;
//                                startActivity(i);
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad2" + exception);
//                            }
//                        });
//
//                        break;
//                    case "Audio":
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading Audio...");
//
//                        final String res3 = rowItemsa2.get(position).getAuthor() + rowItemsa2.get(position).getTime();
//                        System.out.println("Downloading2" + res3);
//
//                        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(res3);
//
//                        System.out.println("Storage refference : " + storageReference2);
//
//
//                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                imgprog.dismiss();
//                                System.out.println("NOOB");
//                                Intent i = new Intent();
//                                i.setAction(Intent.ACTION_VIEW);
//                                i.setDataAndType(uri, "audio/*");
//                                startActivity(i);
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad" + exception);
//                            }
//                        });
//                        break;
//                    case "Video":
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading Video...");
//
//                        final String res4 = rowItemsv2.get(position).getAuthor() + rowItemsv2.get(position).getTime();
//                        System.out.println("Downloading2" + res4);
//
//                        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(res4);
//
//                        System.out.println("Storage refference2 : " + storageReference3);
//
//
//                        storageReference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                imgprog.dismiss();
//                                System.out.println("NOOB2");
//                                Intent i = new Intent();
//                                i.setAction(Intent.ACTION_VIEW);
//                                i.setDataAndType(uri, "video/*");
//                                startActivity(i);
//
////                                            Picasso.with(Contacts.this).load(uri).fit().centerCrop().into(imgg);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle any errors
//                                System.out.println("sad" + exception);
//                            }
//                        });
//                        break;
//                    case "File":
//                        imgprog = ProgressDialog.show(Contacts.this, "Download", "Downloading File...");
//
//                        final String res5 = rowItemsf2.get(position).getAuthor() + rowItemsf2.get(position).getTime();
//                        System.out.println("Downloading2" + res5);
//
//                        final StorageReference storageReference4 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(res5);
//
//                        System.out.println("Storage refference2 : " + storageReference4);
//
//                        storageReference4.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
//                            @Override
//                            public void onSuccess(StorageMetadata storageMetadata) {
//                                System.out.println("Type is" + storageMetadata.getContentType() + "end");
//                                String splitarr[] = storageMetadata.getContentType().split("/");
//                                System.out.println("Sharp type is" + splitarr[splitarr.length - 1]);
//                                final String ftype = storageMetadata.getContentType();
//
//                                if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword")){                                                                  file1 = Environment.getExternalStorageDirectory() + "/" + "word.docx";
//
//                                } else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml")) {
//                                    file1 = Environment.getExternalStorageDirectory() + "/" + "text.txt";
//                                } else if(ftype.contains("pdf")){
//                                    file1 = Environment.getExternalStorageDirectory() + "/" + "pdf." + splitarr[splitarr.length - 1];
//
//                                }
//                                final File files = new File(file1);
//
//                                storageReference4.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                                        if(ftype.contains("pdf")){
//                                            intent.setDataAndType(Uri.fromFile(files), ftype);
//                                        }else if(ftype.equals("octet-stream")||ftype.contains("text")||ftype.contains("xml"))
//                                        {
//                                            intent.setDataAndType(Uri.fromFile(files), "text/plain");
//
//                                        }else if(ftype.contains("x-zip")||ftype.contains("word")||ftype.contains("msword"))
//                                        {
//                                            intent.setDataAndType(Uri.fromFile(files), "application/msword");
//
//                                        }else if(ftype.equals("presentation"))
//                                        {
//                                            intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-powerpoint");
//
//                                        }
//                                        else if(ftype.equals("spreadsheet")||ftype.equals("sheet"))
//                                        {
//                                            intent.setDataAndType(Uri.fromFile(files), "application/vnd.ms-excel");
//
//                                        }
//                                        // Check what kind of file you are trying to open, by comparing the url with extensions.
//                                        // When the if condition is matched, plugin sets the correct intent (mime) type,
//                                        // so Android knew what application to use to open the file
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                                        try {
//                                            imgprog.dismiss();
//                                            startActivity(intent);
//                                        } catch (Exception e) {
//                                            System.out.println("EXCEPTION IS " + e);
//                                            imgprog.dismiss();
//                                            Toast.makeText(Contacts.this, "Invalid File type", Toast.LENGTH_LONG).show();
//                                            // Instruct the user to install a PDF reader here, or something
//                                        }
//
//                                    }
//                                });
//
//
//                            }
//                        });
//
//                        break;
//                }
//            }
//        });

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
        mMenuDialogFragment.setItemClickListener(Contacts2.this);
        mMenuDialogFragment.setItemLongClickListener(Contacts2.this);
    }

//    private void showDatePicker() {
//        DatePickerFragment date = new DatePickerFragment();
//        /**
//         * Set Up Current Date Into dialog
//         */
//        Calendar calender = Calendar.getInstance();
//        Bundle args = new Bundle();
//        args.putInt("year", calender.get(Calendar.YEAR));
//        args.putInt("month", calender.get(Calendar.MONTH));
//        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
//        date.setArguments(args);
//        /**
//         * Set Call back to capture selected date
//         */
//        date.setCallBack(ondate_from);
//        date.show(getSupportFragmentManager(), "Date Picker");
//    }
//
//    DatePickerDialog.OnDateSetListener ondate_from = new DatePickerDialog.OnDateSetListener() {
//
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//
//            from_date.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(year));
//            day1 = String.valueOf(dayOfMonth);
//            month1 = String.valueOf(monthOfYear + 1);
//            year1 = String.valueOf(year);
//            if (day1.length() == 1) {
//                day1 = "0" + day1;
//            }
//            if (month1.length() == 1) {
//                month1 = "0" + month1;
//            }
//            sfrom = year1 + "-" + month1 + "-" + day1 + " 00:00:00";
//
//        }
//    };
//
//    private void showDatePicker2() {
//        DatePickerFragment date = new DatePickerFragment();
//        /**
//         * Set Up Current Date Into dialog
//         */
//        Calendar calender = Calendar.getInstance();
//        Bundle args = new Bundle();
//        args.putInt("year", calender.get(Calendar.YEAR));
//        args.putInt("month", calender.get(Calendar.MONTH));
//        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
//        date.setArguments(args);
//        /**
//         * Set Call back to capture selected date
//         */
//        date.setCallBack(ondate_to);
//        date.show(getSupportFragmentManager(), "Date Picker");
//    }
//
//    DatePickerDialog.OnDateSetListener ondate_to = new DatePickerDialog.OnDateSetListener() {
//
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//
//            to_date.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
//                    + "-" + String.valueOf(year));
//            day2 = String.valueOf(dayOfMonth);
//            month2 = String.valueOf(monthOfYear + 1);
//            year2 = String.valueOf(year);
//            if (day2.length() == 1) {
//                day2 = "0" + day2;
//            }
//            if (month2.length() == 1) {
//                month2 = "0" + month2;
//            }
//            sto = year2 + "-" + month2 + "-" + day2 + " 23:59:59";

       // }
    //};


//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.menuitem1:
//                seltab = "Text";
//                adapter = new CustomAdapter(Contacts.this, rowItemst);
//                mylistview.setAdapter(adapter);
//                adapter2 = new CustomAdapter(Contacts.this, rowItemst2);
//                mylistview2.setAdapter(adapter2);
//                break;
//            case R.id.menuitem2:
//                seltab = "Image";
//                adapter = new CustomAdapter(Contacts.this, rowItemsi);
//                mylistview.setAdapter(adapter);
//                adapter2 = new CustomAdapter(Contacts.this, rowItemsi2);
//                mylistview2.setAdapter(adapter2);
//                break;
//            case R.id.menuitem3:
//                seltab = "Audio";
//                adapter = new CustomAdapter(Contacts.this, rowItemsa);
//                mylistview.setAdapter(adapter);
//                adapter2 = new CustomAdapter(Contacts.this, rowItemsa2);
//                mylistview2.setAdapter(adapter2);
//                break;
//            case R.id.menuitem4:
//                seltab = "Video";
//                adapter = new CustomAdapter(Contacts.this, rowItemsv);
//                mylistview.setAdapter(adapter);
//                adapter2 = new CustomAdapter(Contacts.this, rowItemsv2);
//                mylistview2.setAdapter(adapter2);
//                break;
//            case R.id.menuitem5:
//                seltab = "File";
//                adapter = new CustomAdapter(Contacts.this, rowItemsf);
//                mylistview.setAdapter(adapter);
//                adapter2 = new CustomAdapter(Contacts.this, rowItemsf2);
//                mylistview2.setAdapter(adapter2);
//                break;
//        }
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String member_name = rowItems.get(position).getMember_name();
        Toast.makeText(getApplicationContext(), "" + member_name,
                Toast.LENGTH_SHORT).show();
    }


//    private class MyTask extends AsyncTask<String, Integer, String> {
//
//        // Runs in UI before background thread is called
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            imgprog = ProgressDialog.show(Contacts.this, "Message", "Fetching Data...");
//
//        }
//
//        // This is run in a background thread
//        @Override
//        protected String doInBackground(String... params) {
//            //images
//            tmp1 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Images/";
//            fb_db = new Firebase(tmp1);
//            fb_db.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    contenti = new ArrayList<>();
//                    namei = new ArrayList<>();
//                    categi = new ArrayList<>();
//                    datesi = new ArrayList<>();
//                    authi = new ArrayList<String>();
//                    rowItemsi.clear();
//
//                    contenti2 = new ArrayList<>();
//                    namei2 = new ArrayList<>();
//                    categi2 = new ArrayList<>();
//                    datesi2 = new ArrayList<>();
//                    authi2 = new ArrayList<String>();
//
//
//                    rowItemsi2.clear();
//                    adapter.notifyDataSetChanged();
//                    adapter2.notifyDataSetChanged();
//                    System.out.println("Entering");
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        ImageDesc images = postSnapshot.getValue(ImageDesc.class);
//                        String date = images.date;
//                        String desc = images.desc;
//                        System.out.println("Checking" + postSnapshot.getKey());
//                        go=true;
//                        if (checkdate(date)) {
//                            if (filtered1) {
//                                if (images.maincat.equals(maincat)) {
//                                    go = true;
//                                    if (filtered2) {
//                                        if (images.subcat.equals(subcat)) {
//                                            go = true;
//                                        } else {
//                                            go = false;
//                                        }
//                                    }
//                                } else {
//                                    go = false;
//                                }
//                            }go=true;
//
//                            if (go) {
//                                if (images.target.equals("all")) {
//                                    System.out.println("Image date passed:" + postSnapshot.getKey());
//                                    namei.add(images.desc);
//                                    categi.add(images.maincat + " : " + images.subcat);
//                                    datesi.add(images.date);
//                                    authi.add(images.user);
//                                    System.out.println("Added" + namei.size());
//                                    member_namesi = new String[namei.size()];
//                                    statuesi = new String[namei.size()];
//                                    timei = new String[namei.size()];
//                                    authori = new String[authi.size()];
//
//                                    for (int i = 0; i < namei.size(); i++) {
//
//
//                                        System.out.println("title is " + namei.get(i));
//                                        member_namesi[i] = namei.get(i);
//                                        statuesi[i] = categi.get(i);
//                                        timei[i] = datesi.get(i);
//                                        authori[i] = authi.get(i);
//                                    }
//
//                                } else {
//                                    flagz = false;
//                                    for (int i = 0; i < images.targetmems.size(); i++) {
//                                        if (CurrentUser.user.equals(images.targetmems.get(i))) {
//                                            flagz = true;
//                                            break;
//                                        }
//                                    }
//                                    if (flagz) {
//                                        flagz = false;
//                                        System.out.println("Image date passed:" + postSnapshot.getKey());
//                                        namei2.add(images.desc);
//                                        categi2.add(images.maincat + " : " + images.subcat);
//                                        datesi2.add(images.date);
//                                        authi2.add(images.user);
//                                        System.out.println("Added" + namei2.size());
//                                        member_namesi2 = new String[namei2.size()];
//                                        statuesi2 = new String[namei2.size()];
//                                        timei2 = new String[namei2.size()];
//                                        authori2 = new String[authi2.size()];
//                                        for (int i = 0; i < namei2.size(); i++) {
//
//
//                                            System.out.println("title is " + namei2.get(i));
//                                            member_namesi2[i] = namei2.get(i);
//                                            statuesi2[i] = categi2.get(i);
//                                            timei2[i] = datesi2.get(i);
//                                            authori2[i] = authi2.get(i);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if (namei.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesi.length);
//                        for (int i = 0; i < member_namesi.length; i++) {
//                            RowItem item = new RowItem(member_namesi[i],
//                                    R.drawable.picture, statuesi[i],
//                                    timei[i], authori[i]);
//                            rowItemsi.add(item);
//                            System.out.println("mangatha added" + rowItemsi);
//                        }
//
//                        adapter.notifyDataSetChanged();
//
//                        //  profile_pics.recycle();
//
//                    }
//                    if (namei2.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesi2.length);
//                        for (int i = 0; i < member_namesi2.length; i++) {
//                            RowItem item = new RowItem(member_namesi2[i],
//                                    R.drawable.picture, statuesi2[i],
//                                    timei2[i], authori2[i]);
//                            rowItemsi2.add(item);
//                            System.out.println("mangatha added" + rowItemsi2);
//                        }
//
//                        adapter2.notifyDataSetChanged();
//
//                        //  profile_pics.recycle();
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("FIREBASE ERROR OCCOURED");
//                }
//            });
////audio
//            tmp2 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Audios/";
//            fb_db = new Firebase(tmp2);
//            fb_db.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    contenta = new ArrayList<>();
//                    namea = new ArrayList<>();
//                    catega = new ArrayList<>();
//                    datesa = new ArrayList<>();
//                    autha = new ArrayList<String>();
//                    rowItemsa.clear();
//
//                    contenta2 = new ArrayList<>();
//                    namea2 = new ArrayList<>();
//                    catega2 = new ArrayList<>();
//                    datesa2 = new ArrayList<>();
//                    autha2 = new ArrayList<String>();
//                    rowItemsa2.clear();
//
//                    adapter.notifyDataSetChanged();
//                    adapter2.notifyDataSetChanged();
//
//                    System.out.println("Entering");
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Audiodesc audiodesc = postSnapshot.getValue(Audiodesc.class);
//                        String date = audiodesc.date;
//                        String desc = audiodesc.desc;
//                        System.out.println("Checking" + postSnapshot.getKey());
//                        go=true;
//                        if (checkdate(date)) {
//                            if (filtered1) {
//                                if (audiodesc.maincat.equals(maincat)) {
//                                    go = true;
//                                    if (filtered2) {
//                                        if (audiodesc.subcat.equals(subcat)) {
//                                            go = true;
//                                        } else {
//                                            go = false;
//                                        }
//                                    }
//                                } else {
//                                    go = false;
//                                }
//                            }go=true;
//                            if (go) {
//                                if (audiodesc.target.equals("all")) {
//
//
//                                    System.out.println("Audio date passed:" + postSnapshot.getKey());
//                                    namea.add(audiodesc.desc);
//                                    catega.add(audiodesc.maincat + " : " + audiodesc.subcat);
//                                    datesa.add(audiodesc.date);
//                                    autha.add(audiodesc.user);
//                                    System.out.println("Added" + namea.size());
//                                    member_namesa = new String[namea.size()];
//                                    statuesa = new String[namea.size()];
//                                    timea = new String[namea.size()];
//                                    authora = new String[autha.size()];
//                                    for (int i = 0; i < namea.size(); i++) {
//
//
//                                        System.out.println("title is " + namea.get(i));
//                                        member_namesa[i] = namea.get(i);
//                                        statuesa[i] = catega.get(i);
//                                        timea[i] = datesa.get(i);
//                                        authora[i] = autha.get(i);
//                                    }
//
//                                } else {
//                                    flagz = false;
//                                    for (int i = 0; i < audiodesc.targetmems.size(); i++) {
//                                        if (CurrentUser.user.equals(audiodesc.targetmems.get(i))) {
//                                            flagz = true;
//                                            break;
//                                        }
//                                    }
//                                    if (flagz) {
//                                        flagz = false;
//                                        System.out.println("Image date passed:" + postSnapshot.getKey());
//                                        namea2.add(audiodesc.desc);
//                                        catega2.add(audiodesc.maincat + " : " + audiodesc.subcat);
//                                        datesa2.add(audiodesc.date);
//                                        autha2.add(audiodesc.user);
//                                        System.out.println("Added" + namea2.size());
//                                        member_namesa2 = new String[namea2.size()];
//                                        statuesa2 = new String[namea2.size()];
//                                        timea2 = new String[namea2.size()];
//                                        authora2 = new String[autha2.size()];
//                                        for (int i = 0; i < namea2.size(); i++) {
//
//
//                                            System.out.println("title is " + namea2.get(i));
//                                            member_namesa2[i] = namea2.get(i);
//                                            statuesa2[i] = catega2.get(i);
//                                            timea2[i] = datesa2.get(i);
//                                            authora2[i] = autha2.get(i);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if (namea.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesa.length);
//                        for (int i = 0; i < member_namesa.length; i++) {
//                            RowItem item = new RowItem(member_namesa[i],
//                                    R.drawable.music, statuesa[i],
//                                    timea[i], authora[i]);
//                            rowItemsa.add(item);
//                            System.out.println("mangatha added" + rowItemsa);
//                        }
//
//                        adapter.notifyDataSetChanged();
//                        //  profile_pics.recycle();
//
//
//                    }
//                    if (namea2.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesa2.length);
//                        for (int i = 0; i < member_namesa2.length; i++) {
//                            RowItem item2 = new RowItem(member_namesa2[i],
//                                    R.drawable.picture, statuesa2[i],
//                                    timea2[i], authora2[i]);
//                            rowItemsa2.add(item2);
//                            System.out.println("mangatha added here" + rowItemsa2.get(0).getMember_name());
//                        }
//                        adapter2.notifyDataSetChanged();
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("FIREBASE ERROR OCCOURED");
//                }
//            });
////video
//            tmp3 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Videos/";
//            fb_db = new Firebase(tmp3);
//            fb_db.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    contentv = new ArrayList<>();
//                    namev = new ArrayList<>();
//                    categv = new ArrayList<>();
//                    datesv = new ArrayList<>();
//                    authv = new ArrayList<String>();
//                    rowItemsv.clear();
//
//                    contentv2 = new ArrayList<>();
//                    namev2 = new ArrayList<>();
//                    categv2 = new ArrayList<>();
//                    datesv2 = new ArrayList<>();
//                    authv2 = new ArrayList<String>();
//
//                    rowItemsv2.clear();
//
//                    adapter.notifyDataSetChanged();
//                    adapter2.notifyDataSetChanged();
//                    System.out.println("Entering");
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        VideoDesc images = postSnapshot.getValue(VideoDesc.class);
//                        String date = images.date;
//                        String desc = images.desc;
//                        System.out.println("Checking" + postSnapshot.getKey());
//                        go=true;
//                        if (checkdate(date)) {
//                            if (filtered1) {
//                                if (images.maincat.equals(maincat)) {
//                                    go = true;
//                                    if (filtered2) {
//                                        if (images.subcat.equals(subcat)) {
//                                            go = true;
//                                        } else {
//                                            go = false;
//                                        }
//                                    }
//                                } else {
//                                    go = false;
//                                }
//                            }go=true;
//                            if(go) {
//                                if (images.target.equals("all")) {
//
//                                    System.out.println("Videos date passed:" + postSnapshot.getKey());
//                                    namev.add(images.desc);
//                                    categv.add(images.maincat + " : " + images.subcat);
//                                    datesv.add(images.date);
//                                    authv.add(images.user);
//                                    System.out.println("Added" + namev.size());
//                                    member_namesv = new String[namev.size()];
//                                    statuesv = new String[namev.size()];
//                                    timev = new String[namev.size()];
//                                    authorv = new String[authv.size()];
//                                    for (int i = 0; i < namev.size(); i++) {
//
//
//                                        System.out.println("title is " + namev.get(i));
//                                        member_namesv[i] = namev.get(i);
//                                        statuesv[i] = categv.get(i);
//                                        timev[i] = datesv.get(i);
//                                        authorv[i] = authv.get(i);
//                                    }
//
//                                } else {
//                                    flagz = false;
//                                    for (int i = 0; i < images.targetmems.size(); i++) {
//                                        if (CurrentUser.user.equals(images.targetmems.get(i))) {
//                                            flagz = true;
//                                            break;
//                                        }
//                                    }
//                                    if (flagz) {
//                                        flagz = false;
//                                        System.out.println("Image date passed:" + postSnapshot.getKey());
//                                        namev2.add(images.desc);
//                                        categv2.add(images.maincat + " : " + images.subcat);
//                                        datesv2.add(images.date);
//                                        authv2.add(images.user);
//                                        System.out.println("Added" + namev2.size());
//                                        member_namesv2 = new String[namev2.size()];
//                                        statuesv2 = new String[namev2.size()];
//                                        timev2 = new String[namev2.size()];
//                                        authorv2 = new String[authv2.size()];
//                                        for (int i = 0; i < namev2.size(); i++) {
//
//
//                                            System.out.println("title is " + namev2.get(i));
//                                            member_namesv2[i] = namev2.get(i);
//                                            statuesv2[i] = categv2.get(i);
//                                            timev2[i] = datesi2.get(i);
//                                            authorv2[i] = authv2.get(i);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        //       }
//                    }
//
//                    if (namev.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesv.length);
//                        for (int i = 0; i < member_namesv.length; i++) {
//                            RowItem item = new RowItem(member_namesv[i],
//                                    R.drawable.clip, statuesv[i],
//                                    timev[i], authorv[i]);
//                            rowItemsv.add(item);
//                            System.out.println("mangatha added" + rowItemsv);
//                        }
//
//                        adapter.notifyDataSetChanged();
//                        //  profile_pics.recycle();
//
//                    }
//                    if (namev2.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesv2.length);
//                        for (int i = 0; i < member_namesv2.length; i++) {
//                            RowItem item2 = new RowItem(member_namesv2[i],
//                                    R.drawable.picture, statuesv2[i],
//                                    timev2[i], authorv2[i]);
//                            rowItemsv2.add(item2);
//                            System.out.println("mangatha added" + rowItemsv2);
//                        }
//                        adapter2.notifyDataSetChanged();
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("FIREBASE ERROR OCCOURED");
//                }
//            });
////file
//            tmp4 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Files/";
//            fb_db = new Firebase(tmp4);
//            fb_db.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    contentf = new ArrayList<>();
//                    namef = new ArrayList<>();
//                    categf = new ArrayList<>();
//                    datesf = new ArrayList<>();
//                    authf = new ArrayList<String>();
//                    rowItemsf.clear();
//
//                    contentf2 = new ArrayList<>();
//                    namef2 = new ArrayList<>();
//                    categf2 = new ArrayList<>();
//                    datesf2 = new ArrayList<>();
//                    authf2 = new ArrayList<String>();
//
//                    rowItemsf2.clear();
//                    adapter.notifyDataSetChanged();
//                    adapter2.notifyDataSetChanged();
//                    System.out.println("Entering");
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        FileDesc fileDesc = postSnapshot.getValue(FileDesc.class);
//                        String date = fileDesc.date;
//                        String desc = fileDesc.desc;
//                        System.out.println("Checking" + postSnapshot.getKey());
//                        go=true;
//                        if (checkdate(date)) {
//                            if (filtered1) {
//                                if (fileDesc.maincat.equals(maincat)) {
//                                    go = true;
//                                    if (filtered2) {
//                                        if (fileDesc.subcat.equals(subcat)) {
//                                            go = true;
//                                        } else {
//                                            go = false;
//                                        }
//                                    }
//                                } else {
//                                    go = false;
//                                }
//                            }go=true;
//                            if (go) {
//                                if (fileDesc.target.equals("all")) {
//
//                                    System.out.println("Files date passed:" + postSnapshot.getKey());
//                                    namef.add(fileDesc.desc);
//                                    categf.add(fileDesc.maincat + " : " + fileDesc.subcat);
//                                    datesf.add(fileDesc.date);
//                                    authf.add(fileDesc.user);
//                                    System.out.println("Added" + namef.size());
//                                    member_namesf = new String[namef.size()];
//                                    statuesf = new String[namef.size()];
//                                    timef = new String[namef.size()];
//                                    authorf = new String[authf.size()];
//                                    for (int i = 0; i < namef.size(); i++) {
//
//
//                                        System.out.println("title is " + namef.get(i));
//                                        member_namesf[i] = namef.get(i);
//                                        statuesf[i] = categf.get(i);
//                                        timef[i] = datesf.get(i);
//                                        authorf[i] = authf.get(i);
//                                    }
//
//                                } else {
//                                    flagz = false;
//                                    for (int i = 0; i < fileDesc.targetmems.size(); i++) {
//                                        if (CurrentUser.user.equals(fileDesc.targetmems.get(i))) {
//                                            flagz = true;
//                                            break;
//                                        }
//                                    }
//                                    if (flagz) {
//                                        flagz = false;
//                                        System.out.println("Image date passed:" + postSnapshot.getKey());
//                                        namef2.add(fileDesc.desc);
//                                        categf2.add(fileDesc.maincat + " : " + fileDesc.subcat);
//                                        datesf2.add(fileDesc.date);
//                                        authf2.add(fileDesc.user);
//                                        System.out.println("Added" + namef2.size());
//                                        member_namesf2 = new String[namef2.size()];
//                                        statuesf2 = new String[namef2.size()];
//                                        timef2 = new String[namef2.size()];
//                                        authorf2 = new String[authf2.size()];
//                                        for (int i = 0; i < namef2.size(); i++) {
//
//
//                                            System.out.println("title is " + namef2.get(i));
//                                            member_namesf2[i] = namef2.get(i);
//                                            statuesf2[i] = categf2.get(i);
//                                            timef2[i] = datesf2.get(i);
//                                            authorf2[i] = authf2.get(i);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if (namef.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesf.length);
//                        for (int i = 0; i < member_namesf.length; i++) {
//                            RowItem item = new RowItem(member_namesf[i],
//                                    R.drawable.files, statuesf[i],
//                                    timef[i], authorf[i]);
//                            rowItemsf.add(item);
//                            System.out.println("mangatha added" + rowItemsf);
//                        }
//
//                        adapter.notifyDataSetChanged();
//                        //  profile_pics.recycle();
//
//                    }
//
//                    if (namef2.size() > 0) {
//                        System.out.println("Row cnt is" + member_namesf2.length);
//                        for (int i = 0; i < member_namesf2.length; i++) {
//                            RowItem item2 = new RowItem(member_namesf2[i],
//                                    R.drawable.picture, statuesf2[i],
//                                    timef2[i], authorf2[i]);
//                            rowItemsf2.add(item2);
//                            System.out.println("mangatha added" + rowItemsf2);
//                        }
//                        adapter2.notifyDataSetChanged();
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("FIREBASE ERROR OCCOURED");
//                }
//            });
////text
//
//
//            tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/";
//            fb_db = new Firebase(tmp5);
//
//            fb_db.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    contentt = new ArrayList<>();
//                    namet = new ArrayList<>();
//                    categt = new ArrayList<>();
//                    datest = new ArrayList<>();
//                    autht = new ArrayList<String>();
//                    rowItemst.clear();
//
//
//                    namet2 = new ArrayList<>();
//                    categt2 = new ArrayList<>();
//                    datest2 = new ArrayList<>();
//                    autht2 = new ArrayList<String>();
//
//                    rowItemst2.clear();
//                    adapter.notifyDataSetChanged();
//                    adapter2.notifyDataSetChanged();
//                    System.out.println("Entering");
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        TextDesc textDesc = postSnapshot.getValue(TextDesc.class);
//                        String date = textDesc.date;
//                        String desc = textDesc.desc;
//                        System.out.println("Checking" + postSnapshot.getKey());
//                        go=true;
//                        if (checkdate(date)) {
//                            if (filtered1) {
//                                if (textDesc.maincat.equals(maincat)) {
//                                    go = true;
//                                    if (filtered2) {
//                                        if (textDesc.subcat.equals(subcat)) {
//                                            go = true;
//                                        } else {
//                                            go = false;
//                                        }
//                                    }
//                                } else {
//                                    go = false;
//                                }
//                            }go=true;
//                            if(go) {
//                                if (textDesc.target.equals("all")) {
//
//
//                                    System.out.println("Files date passed:" + postSnapshot.getKey());
//                                    namet.add(textDesc.desc);
//                                    categt.add(textDesc.maincat + " : " + textDesc.subcat);
//                                    datest.add(textDesc.date);
//                                    contentt.add(textDesc.text);
//                                    autht.add(textDesc.user);
//                                    System.out.println("Added" + namet.size());
//                                    member_namest = new String[namet.size()];
//                                    statuest = new String[namet.size()];
//                                    timet = new String[namet.size()];
//                                    authort = new String[autht.size()];
//                                    for (int i = 0; i < namet.size(); i++) {
//
//
//                                        System.out.println("title is " + namet.get(i));
//                                        member_namest[i] = namet.get(i);
//                                        statuest[i] = categt.get(i);
//                                        timet[i] = datest.get(i);
//                                        authort[i] = autht.get(i);
//
//                                    }
//
//                                } else {
//                                    flagz = false;
//                                    for (int i = 0; i < textDesc.targetmems.size(); i++) {
//                                        if (CurrentUser.user.equals(textDesc.targetmems.get(i))) {
//                                            flagz = true;
//                                            break;
//                                        }
//                                    }
//                                    if (flagz) {
//                                        flagz = false;
//                                        System.out.println("Image date passed:" + postSnapshot.getKey());
//                                        namet2.add(textDesc.desc);
//                                        categt2.add(textDesc.maincat + " : " + textDesc.subcat);
//                                        datest2.add(textDesc.date);
//                                        autht2.add(textDesc.user);
//
//                                        System.out.println("Added" + namet2.size());
//                                        member_namest2 = new String[namet2.size()];
//                                        statuest2 = new String[namet2.size()];
//                                        timet2 = new String[namet2.size()];
//                                        authort2 = new String[autht2.size()];
//                                        for (int i = 0; i < namet2.size(); i++) {
//
//
//                                            System.out.println("title is " + namet2.get(i));
//                                            member_namest2[i] = namet2.get(i);
//                                            statuest2[i] = categt2.get(i);
//                                            timet2[i] = datest2.get(i);
//                                            authort2[i] = autht2.get(i);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if (namet.size() > 0) {
//                        System.out.println("Row cnt is" + member_namest.length);
//                        for (int i = 0; i < member_namest.length; i++) {
//                            RowItem item = new RowItem(member_namest[i],
//                                    R.drawable.doc, statuest[i],
//                                    timet[i], authort[i]);
//                            rowItemst.add(item);
//                            System.out.println("mangatha added" + rowItemst);
//                        }
//
//                        adapter.notifyDataSetChanged();
//                        //  profile_pics.recycle();
//
//                    }
//                    if (namet2.size() > 0) {
//                        System.out.println("Row cnt is" + member_namest2.length);
//                        for (int i = 0; i < member_namest2.length; i++) {
//                            RowItem item = new RowItem(member_namest2[i],
//                                    R.drawable.doc, statuest2[i],
//                                    timet2[i], authort2[i]);
//                            rowItemst2.add(item);
//                            System.out.println("mangatha added" + rowItemst2);
//                        }
//                        adapter2.notifyDataSetChanged();
//
//                    }
//
//
//                    }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    System.out.println("FIREBASE ERROR OCCOURED");
//                }
//            });
//
//
//            return "SUCCESS";
//
//
//        }
//
//
//        // This runs in UI when background thread finishes
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (result.equals("SUCCESS")) {
//                System.out.println("SUCCESS");
//
//
//                imgprog.dismiss();
//                // Do things like hide the progress bar or change a TextView
//            }
//        }
//
//
//        public boolean checkdate(String date) {
//            System.out.println("Comparing date:" + date + "with " + sfrom + "and" + sto);
//            if (sfrom.compareTo(date) <= 0 && date.compareTo(sto) <= 0) {
//                return true;
//            }
//            return false;
//        }
//    }

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
        Toast.makeText(Contacts2.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(Contacts2.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(Contacts2.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(Contacts2.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(Contacts2.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(Contacts2.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(Contacts2.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(Contacts2.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Contacts2.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Contacts2.this,
                                            AA_home.class));
                                    Contacts2.this.finish();
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
        Toast.makeText(Contacts2.this, "Long clicked on position: " + position,
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(Contacts2.this);
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

}