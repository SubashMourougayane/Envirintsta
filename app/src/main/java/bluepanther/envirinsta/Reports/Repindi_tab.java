package bluepanther.envirinsta.Reports;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.FileUtils.ImgUri;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDisp.imgdisp;
import bluepanther.envirinsta.Adapter.CustomAdapterIR;
import bluepanther.envirinsta.ContentDisp.txtdisp;

/**
 * Created by shyamjoval on 1/16/2017.
 */

public class Repindi_tab extends Fragment {
   static String file1;
    static String result;
    ProgressDialog progressDialog;
    public  static View.OnClickListener myOnClickListener;
    private RecyclerView.LayoutManager layoutManager;
    static RecyclerView mylistview2;
    public CustomAdapterIR adapter2;
    static public String Base_url = "https://envirinsta.firebaseio.com/";
    static public Firebase fb_db;
    static Activity context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.indi_tab, container, false);
        mylistview2 = (RecyclerView) parentView.findViewById(R.id.myListView2);
        mylistview2.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mylistview2.setLayoutManager(layoutManager);
        mylistview2.setItemAnimator(new DefaultItemAnimator());
        adapter2 = new CustomAdapterIR(getActivity(), RepContent.percontent);
        Firebase.setAndroidContext(getActivity());
        fb_db = new Firebase(Base_url);
        mylistview2.setAdapter(adapter2);
        
        context=getActivity();
        myOnClickListener = new Repindi_tab.MyOnClickListener(getActivity());


      
        return parentView;
    }
    public static void setContent(CustomAdapterIR adapter)
    {
        mylistview2.setAdapter(adapter);
    }  private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;
        ProgressDialog progressDialog;
        private MyOnClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onClick(View v) {

            final View vv = v;
            int position = mylistview2.getChildPosition(v);
            if (RepContent.percontent.get(position).getType().equals("Images")) {

                progressDialog = new ProgressDialog(vv.getContext());
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


                final String res = RepContent.percontent.get(position).getAuthor() + RepContent.percontent.get(position).getTime();
                System.out.println("Downloading" + res);

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                System.out.println("Storage refference : " + storageReference);

                ImgUri.ref=storageReference;
                Intent i = new Intent(vv.getContext(), imgdisp.class);
progressDialog.dismiss();
                vv.getContext().startActivity(i);


            }
            if (RepContent.percontent.get(position).getType().equals("Audios")) {

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
                final String res = RepContent.percontent.get(position).getAuthor() + RepContent.percontent.get(position).getTime();

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
            if (RepContent.percontent.get(position).getType().equals("Videos")) {

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
                final String res = RepContent.percontent.get(position).getAuthor() + RepContent.percontent.get(position).getTime();

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
            if (RepContent.percontent.get(position).getType().equals("Files")) {

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
                final String res = RepContent.percontent.get(position).getAuthor() + RepContent.percontent.get(position).getTime();

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
            if (RepContent.percontent.get(position).getType().equals("Text")) {

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
                final String res = RepContent.percontent.get(position).getAuthor() + RepContent.percontent.get(position).getTime();

                System.out.println("Downloading" + res);
                String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res + "/";
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
