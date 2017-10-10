package bluepanther.envirinsta.Timeline;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.database.StaleDataException;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.content.ContextCompat;
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
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.io.PrintWriter;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;


        import bluepanther.envirinsta.Adapter.CustomAdapterIT;
        import bluepanther.envirinsta.Adapter.CurrentUser;
        import bluepanther.envirinsta.Adapter.RowItem;
        import bluepanther.envirinsta.FileUtils.ImgUri;
        import bluepanther.envirinsta.InternalStorage.Internal_Audio;
        import bluepanther.envirinsta.InternalStorage.Internal_File;
        import bluepanther.envirinsta.InternalStorage.Internal_Image;
        import bluepanther.envirinsta.InternalStorage.Internal_Text;
        import bluepanther.envirinsta.InternalStorage.Internal_Video;
        import bluepanther.envirinsta.R;
        import bluepanther.envirinsta.ContentDesc.TextDesc;
        import bluepanther.envirinsta.ContentDisp.imgdisp;
        import bluepanther.envirinsta.ContentDisp.txtdisp;
        import bluepanther.envirinsta.Services.MyService;

        import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by shyamjoval on 1/16/2017.
 */

public class Indi_tab extends Fragment {
    static int counter = 0;
    static String file1;
    static String result;
    List<RowItem> grpcontent;
    List<RowItem> percontent;
    ProgressDialog progressDialog;
    private RecyclerView.LayoutManager layoutManager;
    public static boolean is_in_action_mode = false;
    static RecyclerView mylistview2;
    public static CustomAdapterIT adapter2;
    static public  String Base_url = "https://envirinsta.firebaseio.com/";
    static public Firebase fb_db;
    public  static View.OnClickListener myOnClickListener;
    public static View.OnLongClickListener myOnLongClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.indi_tab, container, false);
        mylistview2 = (RecyclerView) parentView.findViewById(R.id.myListView2);
        mylistview2.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mylistview2.setLayoutManager(layoutManager);
        mylistview2.setItemAnimator(new DefaultItemAnimator());
        adapter2 = new CustomAdapterIT(getActivity(), TimeContent.percontent);
        Firebase.setAndroidContext(getActivity());
        fb_db = new Firebase(Base_url);
        mylistview2.setAdapter(adapter2);

        myOnClickListener = new Indi_tab.MyOnClickListener(getActivity());

        readSms();
        return parentView;
    }

    public static void setContent(CustomAdapterIT adapter)
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
            if (TimeContent.percontent.get(position).getType().equals("Images")) {

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

                final String res = TimeContent.percontent.get(position).getAuthor() + TimeContent.percontent.get(position).getTime();
                System.out.println("Downloading" + res);

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                System.out.println("Storage refference : " + storageReference);
                ImgUri.ref=storageReference;
                Intent i = new Intent(vv.getContext(), imgdisp.class);
                progressDialog.dismiss();
                vv.getContext().startActivity(i);


            }
            if (TimeContent.percontent.get(position).getType().equals("Audios")) {

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

                final String res = TimeContent.percontent.get(position).getAuthor() + TimeContent.percontent.get(position).getTime();

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
            if (TimeContent.percontent.get(position).getType().equals("Videos")) {

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

                final String res = TimeContent.percontent.get(position).getAuthor() + TimeContent.percontent.get(position).getTime();

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
            if (TimeContent.percontent.get(position).getType().equals("Files")) {

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
                final String res = TimeContent.percontent.get(position).getAuthor() + TimeContent.percontent.get(position).getTime();

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
            if (TimeContent.percontent.get(position).getType().equals("Text")) {

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
                final String res = TimeContent.percontent.get(position).getAuthor() + TimeContent.percontent.get(position).getTime();

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
            if (TimeContent.percontent.get(position).getType().contains("Textsms")) {

                result=TimeContent.percontent.get(position).getType().split(":")[1];
                Intent i = new Intent(vv.getContext(), txtdisp.class);
                i.putExtra("value", result);
//                        progressDialog.dismiss();
                vv.getContext().startActivity(i);

            }

        }
    }
    public void readSms() {
        try {
            Boolean ifsms = false;
            String smsdesc = "", smsmaincat = "", smssubcat = "", smsdate = "", smsuser = "", smscont = "";
            System.out.println("Gona read");
            List<SMSData> smsList = new ArrayList<SMSData>();

            Uri uri = Uri.parse("content://sms/inbox");
            Cursor c = getActivity().getContentResolver().query(uri, null, null, null, null);
            getActivity().startManagingCursor(c);

            // Read the sms data and store it in the list
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    SMSData sms = new SMSData();
                    sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                    sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
//                        smsList.add(sms);

                    System.out.println("SMS IS $$" + sms.getBody() + " and " + sms.getNumber());
                    String smstxt = sms.getBody();
                    if (smstxt.contains("#envi")) {
                        ifsms = true;
                        System.out.println("Reading now"+smstxt);
                        String pairs[] = smstxt.split(",");
                        smsuser = pairs[1].split(":")[1];
                        smsmaincat = pairs[2].split(":")[1];
                        smssubcat = pairs[3].split(":")[1];
                        String tmp[]=pairs[4].split(":");
                        smsdate =tmp[1]+":"+tmp[2]+":"+tmp[3];
                        smsdesc = pairs[5].split(":")[1];
                        smscont = pairs[6].split(":")[1];
                        List rowItemst2 = new ArrayList<RowItem>();
                        RowItem item = new RowItem(smsdesc,
                                R.drawable.doc, smsmaincat + " : " + smssubcat,
                                smsdate, smsuser, "Textsms:" + smscont);
                        rowItemst2.add(item);

                        Internal_Text imgobj = new Internal_Text(rowItemst2);
                        try {

                            List<RowItem> imgcontent;
                            List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                            String file = Environment.getExternalStorageDirectory() + "/text1.tmp";
                            File f = new File(file);
                            if (f.exists()) {
                                System.out.println("FILE CREATING1");
                                FileInputStream fis = new FileInputStream(file);
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                imgobj = (Internal_Text) ois.readObject();
                                ois.close();

                                Boolean flag=true;
                                imgcontent = imgobj.textcontent;
                                for (int j = 0; j < imgcontent.size(); j++) {
                                    if (imgcontent.get(j).getMember_name().equals(item.getMember_name())&&imgcontent.get(j).getTime().equals(item.getTime())) {
                                        flag = false;

                                    }
                                }
                                if (flag) {
                                    imgcontent.add(item);
                                }
                                flag = true;


                                for (int k = 0; k < imgcontent.size(); k++) {
                                    System.out.println("List iteams are" + imgcontent.get(k).getMember_name());
                                }
                                new PrintWriter(file).close();
                                String file1 = Environment.getExternalStorageDirectory() + "/text1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(new Internal_Text(imgcontent));
                                oos.close();


                            } else {
                                System.out.println("FILE CREATING2");
                                String file1 = Environment.getExternalStorageDirectory() + "/text1.tmp";
                                FileOutputStream fos = new FileOutputStream(file1);
                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                oos.writeObject(imgobj);
                                oos.close();

                            }

                        } catch (Exception e) {
                            System.out.println("EXCEPTION OCCOURED" + e);
                        }

                        getlocalfiles();
                        TimeContent.percontent = new ArrayList<RowItem>(percontent);
                        CustomAdapterIT adapter = new CustomAdapterIT(getActivity(), percontent);
                        Indi_tab.setContent(adapter);


//        Intent intent = new Intent(getActivity(),Timeline.class);
//        intent.putExtra("noti","text");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent
//                .getActivity(getActivity(),
//                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        android.app.Notification notification = new android.app.Notification.Builder(getActivity())
//                .setTicker("Title")
//                .setContentTitle("Envirinsta")
//                .setContentText("You have new Texts")
//                .setSmallIcon(R.drawable.soul_logo)
//                .setContentIntent(pendingIntent).getNotification();
//
//        notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
//        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(5, notification);

                    }


                    c.moveToNext();
                }
            }
            // c.close();



        }catch (Exception e){}
    }
    public void getlocalfiles()
    {

        List<RowItem> imgcontent2 = new ArrayList<>();
        List<RowItem> audcontent2 = new ArrayList<>();
        List<RowItem> vidcontent2 = new ArrayList<>();
        List<RowItem> filecontent2 = new ArrayList<>();
        List<RowItem> textcontent2 = new ArrayList<>();





        final Internal_Image imgobj2;
        String filei2 = Environment.getExternalStorageDirectory() + "/img1.tmp";
        System.out.println("FILE READING");
        FileInputStream fisi2 = null;
        try {
            fisi2 = new FileInputStream(filei2);
            ObjectInputStream ois = new ObjectInputStream(fisi2);
            imgobj2 = (Internal_Image) ois.readObject();
            ois.close();
            imgcontent2 = imgobj2.imgcontent;
            Collections.reverse(imgcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Audio audobj2;
        String filea2 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisa2 = null;
        try {
            fisa2 = new FileInputStream(filea2);
            ObjectInputStream ois = new ObjectInputStream(fisa2);
            audobj2 = (Internal_Audio) ois.readObject();
            ois.close();
            audcontent2 = audobj2.audiocontent;
            Collections.reverse(audcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Text txtobj2;
        String filet2 = Environment.getExternalStorageDirectory() + "/text1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fist2 = null;
        try {
            fist2 = new FileInputStream(filet2);
            ObjectInputStream ois = new ObjectInputStream(fist2);
            txtobj2 = (Internal_Text) ois.readObject();
            ois.close();
            textcontent2 = txtobj2.textcontent;
            Collections.reverse(textcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Video vidobj2;
        String filev2 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisv2 = null;
        try {
            fisv2 = new FileInputStream(filev2);
            ObjectInputStream ois = new ObjectInputStream(fisv2);
            vidobj2 = (Internal_Video) ois.readObject();
            ois.close();
            vidcontent2 = vidobj2.videocontent;
            Collections.reverse(vidcontent2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_File fileobj2;
        String filef2 = Environment.getExternalStorageDirectory() + "/file1.tmp";
        System.out.println("FILE READING");
        FileInputStream fisf2 = null;
        try {
            fisf2 = new FileInputStream(filef2);
            ObjectInputStream ois = new ObjectInputStream(fisf2);
            fileobj2 = (Internal_File) ois.readObject();
            ois.close();
            filecontent2 = fileobj2.filecontent;
            Collections.reverse(filecontent2);


        } catch (Exception e) {
            e.printStackTrace();
        }

        percontent = new ArrayList<>();
        percontent.addAll(textcontent2);
        percontent.addAll(imgcontent2);
        percontent.addAll(audcontent2);
        percontent.addAll(vidcontent2);
        percontent.addAll(filecontent2);
//        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(percontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });


    }

}

