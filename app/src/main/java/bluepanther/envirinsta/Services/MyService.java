package bluepanther.envirinsta.Services;

/**
 * Created by SUBASH.M on 11/8/2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import bluepanther.envirinsta.Adapter.Cred_Update;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Timeline.Group_tab;
import bluepanther.envirinsta.Timeline.Indi_tab;
import bluepanther.envirinsta.Timeline.TimeContent;
import bluepanther.envirinsta.InternalStorage.Internal_Audio;
import bluepanther.envirinsta.InternalStorage.Internal_File;
import bluepanther.envirinsta.InternalStorage.Internal_Image;
import bluepanther.envirinsta.InternalStorage.Internal_Text;
import bluepanther.envirinsta.InternalStorage.Internal_Video;

import bluepanther.envirinsta.Adapter.CustomAdapterGT;
import bluepanther.envirinsta.Adapter.CustomAdapterIT;
import bluepanther.envirinsta.Timeline.Timeline;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class MyService extends Service {

    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    Boolean flagz;
    List<RowItem> grpcontent;
    List<RowItem> percontent;
    String lastlogin;
    String tmp1, tmp2, tmp3, tmp4, tmp5;
    ArrayList<String> namei, categi, datesi, contenti, authi, nodenamei;
    ArrayList<String> namea, catega, datesa, contenta, autha, nodenamea;
    ArrayList<String> namev, categv, datesv, contentv, authv, nodenamev;
    ArrayList<String> namef, categf, datesf, contentf, authf, nodenamef;
    ArrayList<String> namet, categt, datest, contentt, autht, nodenamet;

    ArrayList<String> namei2, categi2, datesi2, contenti2, authi2, nodenamei2;
    ArrayList<String> namea2, catega2, datesa2, contenta2, autha2, nodenamea2;
    ArrayList<String> namev2, categv2, datesv2, contentv2, authv2, nodenamev2;
    ArrayList<String> namef2, categf2, datesf2, contentf2, authf2, nodenamef2;
    ArrayList<String> namet2, categt2, datest2, contentt2, autht2, nodenamet2;

    Boolean flag = true;
    List<RowItem> imgcontent = new ArrayList<>();
    List<RowItem> audcontent = new ArrayList<>();
    List<RowItem> vidcontent = new ArrayList<>();
    List<RowItem> filecontent = new ArrayList<>();
    List<RowItem> textcontent = new ArrayList<>();

    List<RowItem> imgcontent2 = new ArrayList<>();
    List<RowItem> audcontent2 = new ArrayList<>();
    List<RowItem> vidcontent2 = new ArrayList<>();
    List<RowItem> filecontent2 = new ArrayList<>();
    List<RowItem> textcontent2 = new ArrayList<>();

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

    String[] contenttarr;
    String[] statuesi2;
    String[] timei2;
    String[] authori2;


    String[] statuesa2;
    String[] timea2;
    String[] authora2;

    String[] statuesv2;
    String[] timev2;
    String[] authorv2;

    String[] statuesf2;
    String[] timef2;
    String[] authorf2;

    String[] statuest2;
    String[] timet2;
    String[] authort2;

    String[] nodei2;
    String[] nodea2;
    String[] nodev2;
    String[] nodef2;
    String[] nodet2;



    String[] statuesi;
    String[] timei;
    String[] authori;


    String[] statuesa;
    String[] timea;
    String[] authora;

    String[] statuesv;
    String[] timev;
    String[] authorv;

    String[] statuesf;
    String[] timef;
    String[] authorf;

    String[] statuest;
    String[] timet;
    String[] authort;

    String[] nodei;
    String[] nodea;
    String[] nodev;
    String[] nodef;
    String[] nodet;

    List<RowItem> rowItemsi, rowItemsa, rowItemsv, rowItemsf, rowItemst;
    List<RowItem> rowItemsi2, rowItemsa2, rowItemsv2, rowItemsf2, rowItemst2;





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String xxx = Base_url + "Accounts/";
        System.out.println("User is" + xxx);
        Firebase.setAndroidContext(this);
        fb_db = new Firebase(xxx);
        fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                    String uname = cred_update.getUsn();
                    String password = cred_update.getPass();
                    System.out.println("Checking" + uname + "and" + CurrentUser.user);
                    if ((uname.equals(CurrentUser.user))) {
                        System.out.println("subashh " + cred_update.idate);

                        new CurrentUser(uname, password, cred_update.getMobile(), cred_update.cls, cred_update.sec, cred_update.idate, cred_update.adate, cred_update.vdate, cred_update.fdate, cred_update.tdate,cred_update.type);
                        new MyTask2().execute();
                    }


                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("FIREBASE ERROR OCCOURED");
            }
        });

        System.out.println("Assigned values");


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
    @Override public void onTaskRemoved(Intent rootIntent){
       System.out.println("Service stopped now");
//        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
//
//        PendingIntent restartServicePendingIntent = PendingIntent.getService(
//                getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
//                restartServicePendingIntent);

        System.out.println("Service restarted");
        super.onTaskRemoved(rootIntent);

    }

    public class MyTask2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            rowItemsi = new ArrayList<RowItem>();
            rowItemsa = new ArrayList<RowItem>();
            rowItemsv = new ArrayList<RowItem>();
            rowItemsf = new ArrayList<RowItem>();
            rowItemst = new ArrayList<RowItem>();

            rowItemsi2 = new ArrayList<RowItem>();
            rowItemsa2 = new ArrayList<RowItem>();
            rowItemsv2 = new ArrayList<RowItem>();
            rowItemsf2 = new ArrayList<RowItem>();
            rowItemst2 = new ArrayList<RowItem>();

            // Let it continue running until it is stopped.
            //images
            tmp1 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Images/";
            fb_db = new Firebase(tmp1);
            rowItemsi.clear();
            rowItemsi2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    ImageDesc images = dataSnapshot.getValue(ImageDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);

                    try {
                        if (icheckdate(date)) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.picture, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Images");
                                    rowItemsi.add(item);

                                    Internal_Image imgobj = new Internal_Image(rowItemsi);
                                    try {

                                        List<RowItem> imgcontent;
                                        List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                        String file = Environment.getExternalStorageDirectory() + "/img.tmp";
                                        File f = new File(file);
                                        if (f.exists()) {
                                            System.out.println("FILE CREATING1");
                                            FileInputStream fis = new FileInputStream(file);
                                            ObjectInputStream ois = new ObjectInputStream(fis);
                                            imgobj = (Internal_Image) ois.readObject();
                                            ois.close(); flag=true;
                                            imgcontent = imgobj.imgcontent;
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
                                            String file1 = Environment.getExternalStorageDirectory() + "/img.tmp";
                                            FileOutputStream fos = new FileOutputStream(file1);
                                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            oos.writeObject(new Internal_Image(imgcontent));
                                            oos.close();


                                        } else {
                                            System.out.println("FILE CREATING2");
                                            String file1 = Environment.getExternalStorageDirectory() + "/img.tmp";
                                            FileOutputStream fos = new FileOutputStream(file1);
                                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            oos.writeObject(imgobj);
                                            oos.close();

                                        }

                                    } catch (Exception e) {
                                        System.out.println("EXCEPTION OCCOURED" + e);
                                    }

                                    getlocalfiles();
                                    TimeContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGT adapter = new CustomAdapterGT(MyService.this, grpcontent);
                                    Group_tab.setContent(adapter);


                                    Intent intent = new Intent(getApplicationContext(), Timeline.class);
                                    intent.putExtra("noti","image");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    PendingIntent pendingIntent = PendingIntent
                                            .getActivity(getApplicationContext(),
                                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                            .setTicker("Title")
                                            .setContentTitle("Envirinsta")
                                            .setContentText("You have new Images")
                                            .setSmallIcon(R.drawable.soul_logo)
                                            .setContentIntent(pendingIntent).getNotification();

                                    notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(1, notification);

                                } else {
                                    flagz = false;
                                    for (int i = 0; i < images.targetmems.size(); i++) {
                                        if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                            flagz = true;
                                            break;
                                        }
                                    }
                                    if (flagz) {
                                        flagz = false;
                                        RowItem item = new RowItem(images.desc,
                                                R.drawable.picture, images.maincat + " : " + images.subcat,
                                                images.date, images.user,"Images");
                                        rowItemsi2.add(item);

                                        Internal_Image imgobj = new Internal_Image(rowItemsi2);
                                        try {

                                            List<RowItem> imgcontent;
                                            List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                            String file = Environment.getExternalStorageDirectory() + "/img1.tmp";
                                            File f = new File(file);
                                            if (f.exists()) {
                                                System.out.println("FILE CREATING1");
                                                FileInputStream fis = new FileInputStream(file);
                                                ObjectInputStream ois = new ObjectInputStream(fis);
                                                imgobj = (Internal_Image) ois.readObject();
                                                ois.close(); flag=true;
                                                imgcontent = imgobj.imgcontent;
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
                                                String file1 = Environment.getExternalStorageDirectory() + "/img1.tmp";
                                                FileOutputStream fos = new FileOutputStream(file1);
                                                ObjectOutputStream oos = new ObjectOutputStream(fos);
                                                oos.writeObject(new Internal_Image(imgcontent));
                                                oos.close();


                                            } else {
                                                System.out.println("FILE CREATING2");
                                                String file1 = Environment.getExternalStorageDirectory() + "/img1.tmp";
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
                                        CustomAdapterIT adapter = new CustomAdapterIT(MyService.this, percontent);
                                        Indi_tab.setContent(adapter);


                                        Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                        intent.putExtra("noti","image");
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        PendingIntent pendingIntent = PendingIntent
                                                .getActivity(getApplicationContext(),
                                                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                                .setTicker("Title")
                                                .setContentTitle("Envirinsta")
                                                .setContentText("You have new Images")
                                                .setSmallIcon(R.drawable.soul_logo)
                                                .setContentIntent(pendingIntent).getNotification();

                                        notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                        notificationManager.notify(1, notification);
                                    }
                                }

                        }
                    }
                    catch (ParseException e) {
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

//audio
            tmp2 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Audios/";
            fb_db = new Firebase(tmp2);
            rowItemsa.clear();
            rowItemsa2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    Audiodesc images = dataSnapshot.getValue(Audiodesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);

                    try {
                        if (acheckdate(date)) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.music, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Audios");
                                rowItemsa.add(item);

                                Internal_Audio imgobj = new Internal_Audio(rowItemsa);
                                try {

                                    List<RowItem> imgcontent;
                                    List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                    String file = Environment.getExternalStorageDirectory() + "/aud.tmp";
                                    File f = new File(file);
                                    if (f.exists()) {
                                        System.out.println("FILE CREATING1");
                                        FileInputStream fis = new FileInputStream(file);
                                        ObjectInputStream ois = new ObjectInputStream(fis);
                                        imgobj = (Internal_Audio) ois.readObject();
                                        ois.close(); flag=true;
                                        imgcontent = imgobj.audiocontent;
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
                                        String file1 = Environment.getExternalStorageDirectory() + "/aud.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(new Internal_Audio(imgcontent));
                                        oos.close();


                                    } else {
                                        System.out.println("FILE CREATING2");
                                        String file1 = Environment.getExternalStorageDirectory() + "/aud.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(imgobj);
                                        oos.close();

                                    }

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION OCCOURED" + e);
                                }

                                getlocalfiles();
                                TimeContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                CustomAdapterGT adapter = new CustomAdapterGT(MyService.this, grpcontent);
                                Group_tab.setContent(adapter);


                                Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                intent.putExtra("noti","audio");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                PendingIntent pendingIntent = PendingIntent
                                        .getActivity(getApplicationContext(),
                                                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                        .setTicker("Title")
                                        .setContentTitle("Envirinsta")
                                        .setContentText("You have new Audios")
                                        .setSmallIcon(R.drawable.soul_logo)
                                        .setContentIntent(pendingIntent).getNotification();

                                notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(2, notification);


                            } else {
                                flagz = false;
                                for (int i = 0; i < images.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz = false;
                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.music, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Audios");
                                    rowItemsa2.add(item);

                                    Internal_Audio imgobj = new Internal_Audio(rowItemsa2);
                                    try {

                                        List<RowItem> imgcontent;
                                        List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                        String file = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                                        File f = new File(file);
                                        if (f.exists()) {
                                            System.out.println("FILE CREATING1");
                                            FileInputStream fis = new FileInputStream(file);
                                            ObjectInputStream ois = new ObjectInputStream(fis);
                                            imgobj = (Internal_Audio) ois.readObject();
                                            ois.close(); flag=true;
                                            imgcontent = imgobj.audiocontent;
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
                                            String file1 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
                                            FileOutputStream fos = new FileOutputStream(file1);
                                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            oos.writeObject(new Internal_Audio(imgcontent));
                                            oos.close();


                                        } else {
                                            System.out.println("FILE CREATING2");
                                            String file1 = Environment.getExternalStorageDirectory() + "/aud1.tmp";
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
                                    CustomAdapterIT adapter = new CustomAdapterIT(MyService.this, percontent);
                                    Indi_tab.setContent(adapter);


                                    Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                    intent.putExtra("noti","audio");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    PendingIntent pendingIntent = PendingIntent
                                            .getActivity(getApplicationContext(),
                                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                            .setTicker("Title")
                                            .setContentTitle("Envirinsta")
                                            .setContentText("You have new Audios")
                                            .setSmallIcon(R.drawable.soul_logo)
                                            .setContentIntent(pendingIntent).getNotification();

                                    notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(2, notification);
                                }
                            }

                        }
                    }
                    catch (ParseException e) {
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

//video
            tmp3 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Videos/";
            fb_db = new Firebase(tmp3);
            rowItemsv.clear();
            rowItemsv2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    VideoDesc images = dataSnapshot.getValue(VideoDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);

                    try {
                        if (vcheckdate(date)) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.clip, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Videos");
                                rowItemsv.add(item);

                                Internal_Video imgobj = new Internal_Video(rowItemsv);
                                try {

                                    List<RowItem> imgcontent;
                                    List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                    String file = Environment.getExternalStorageDirectory() + "/vid.tmp";
                                    File f = new File(file);
                                    if (f.exists()) {
                                        System.out.println("FILE CREATING1");
                                        FileInputStream fis = new FileInputStream(file);
                                        ObjectInputStream ois = new ObjectInputStream(fis);
                                        imgobj = (Internal_Video) ois.readObject();
                                        ois.close(); flag=true;
                                        imgcontent = imgobj.videocontent;
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
                                        String file1 = Environment.getExternalStorageDirectory() + "/vid.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(new Internal_Video(imgcontent));
                                        oos.close();


                                    } else {
                                        System.out.println("FILE CREATING2");
                                        String file1 = Environment.getExternalStorageDirectory() + "/vid.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(imgobj);
                                        oos.close();

                                    }

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION OCCOURED" + e);
                                }

                                getlocalfiles();
                                TimeContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                CustomAdapterGT adapter = new CustomAdapterGT(MyService.this, grpcontent);
                                Group_tab.setContent(adapter);


                                Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                intent.putExtra("noti","video");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                PendingIntent pendingIntent = PendingIntent
                                        .getActivity(getApplicationContext(),
                                                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                        .setTicker("Title")
                                        .setContentTitle("Envirinsta")
                                        .setContentText("You have new Videos")
                                        .setSmallIcon(R.drawable.soul_logo)
                                        .setContentIntent(pendingIntent).getNotification();

                                notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(3, notification);

                            } else {
                                flagz = false;
                                for (int i = 0; i < images.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz = false;
                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.clip, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Videos");
                                    rowItemsv2.add(item);

                                    Internal_Video imgobj = new Internal_Video(rowItemsv2);
                                    try {

                                        List<RowItem> imgcontent;
                                        List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                        String file = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                                        File f = new File(file);
                                        if (f.exists()) {
                                            System.out.println("FILE CREATING1");
                                            FileInputStream fis = new FileInputStream(file);
                                            ObjectInputStream ois = new ObjectInputStream(fis);
                                            imgobj = (Internal_Video) ois.readObject();
                                            ois.close(); flag=true;
                                            imgcontent = imgobj.videocontent;
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
                                            String file1 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
                                            FileOutputStream fos = new FileOutputStream(file1);
                                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            oos.writeObject(new Internal_Video(imgcontent));
                                            oos.close();


                                        } else {
                                            System.out.println("FILE CREATING2");
                                            String file1 = Environment.getExternalStorageDirectory() + "/vid1.tmp";
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
                                    CustomAdapterIT adapter = new CustomAdapterIT(MyService.this, percontent);
                                    Indi_tab.setContent(adapter);

                                    Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                    intent.putExtra("noti","video");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    PendingIntent pendingIntent = PendingIntent
                                            .getActivity(getApplicationContext(),
                                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                            .setTicker("Title")
                                            .setContentTitle("Envirinsta")
                                            .setContentText("You have new Videos")
                                            .setSmallIcon(R.drawable.soul_logo)
                                            .setContentIntent(pendingIntent).getNotification();

                                    notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(3, notification);
                                }
                            }

                        }
                    }
                    catch (ParseException e) {
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

//file
            tmp4 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Files/";
            fb_db = new Firebase(tmp4);
            rowItemsf.clear();
            rowItemsf2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    FileDesc images = dataSnapshot.getValue(FileDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);

                    try {
                        if (fcheckdate(date)) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.files, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Files");
                                rowItemsf.add(item);

                                Internal_File imgobj = new Internal_File(rowItemsf);
                                try {

                                    List<RowItem> imgcontent;
                                    List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                    String file = Environment.getExternalStorageDirectory() + "/file.tmp";
                                    File f = new File(file);
                                    if (f.exists()) {
                                        System.out.println("FILE CREATING1");
                                        FileInputStream fis = new FileInputStream(file);
                                        ObjectInputStream ois = new ObjectInputStream(fis);
                                        imgobj = (Internal_File) ois.readObject();
                                        ois.close(); flag=true;
                                        imgcontent = imgobj.filecontent;
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
                                        String file1 = Environment.getExternalStorageDirectory() + "/file.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(new Internal_File(imgcontent));
                                        oos.close();


                                    } else {
                                        System.out.println("FILE CREATING2");
                                        String file1 = Environment.getExternalStorageDirectory() + "/file.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(imgobj);
                                        oos.close();

                                    }

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION OCCOURED" + e);
                                }

                                getlocalfiles();
                                TimeContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                CustomAdapterGT adapter = new CustomAdapterGT(MyService.this, grpcontent);
                                Group_tab.setContent(adapter);


                                Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                intent.putExtra("noti","file");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                PendingIntent pendingIntent = PendingIntent
                                        .getActivity(getApplicationContext(),
                                                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                        .setTicker("Title")
                                        .setContentTitle("Envirinsta")
                                        .setContentText("You have new Files")
                                        .setSmallIcon(R.drawable.soul_logo)
                                        .setContentIntent(pendingIntent).getNotification();

                                notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(4, notification);

                            } else {
                                flagz = false;
                                for (int i = 0; i < images.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz = false;
                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.files, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Files");
                                    rowItemsf2.add(item);

                                    Internal_File imgobj = new Internal_File(rowItemsf2);
                                    try {

                                        List<RowItem> imgcontent;
                                        List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                        String file = Environment.getExternalStorageDirectory() + "/file1.tmp";
                                        File f = new File(file);
                                        if (f.exists()) {
                                            System.out.println("FILE CREATING1");
                                            FileInputStream fis = new FileInputStream(file);
                                            ObjectInputStream ois = new ObjectInputStream(fis);
                                            imgobj = (Internal_File) ois.readObject();
                                            ois.close(); flag=true;
                                            imgcontent = imgobj.filecontent;
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
                                            String file1 = Environment.getExternalStorageDirectory() + "/file1.tmp";
                                            FileOutputStream fos = new FileOutputStream(file1);
                                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            oos.writeObject(new Internal_File(imgcontent));
                                            oos.close();


                                        } else {
                                            System.out.println("FILE CREATING2");
                                            String file1 = Environment.getExternalStorageDirectory() + "/file1.tmp";
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
                                    CustomAdapterIT adapter = new CustomAdapterIT(MyService.this, percontent);
                                    Indi_tab.setContent(adapter);


                                    Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                    intent.putExtra("noti","file");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    PendingIntent pendingIntent = PendingIntent
                                            .getActivity(getApplicationContext(),
                                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                            .setTicker("Title")
                                            .setContentTitle("Envirinsta")
                                            .setContentText("You have new Files")
                                            .setSmallIcon(R.drawable.soul_logo)
                                            .setContentIntent(pendingIntent).getNotification();

                                    notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(4, notification);
                                }
                            }

                        }
                    }
                    catch (ParseException e) {
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

//text


            tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/";
            fb_db = new Firebase(tmp5);
            rowItemst.clear();
            rowItemst2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    TextDesc images = dataSnapshot.getValue(TextDesc.class);
                    String date = images.date;
                    String desc = images.desc;
                    System.out.println("Checking textt" + prevChildKey + " andd" + dataSnapshot.getKey() + " and " + date + " " + desc);

                    try {
                        if (tcheckdate(date)) {
                            if (images.target.equals("all")) {

                                RowItem item = new RowItem(images.desc,
                                        R.drawable.doc, images.maincat + " : " + images.subcat,
                                        images.date, images.user,"Text");
                                rowItemst.add(item);

                                Internal_Text imgobj = new Internal_Text(rowItemst);
                                try {

                                    List<RowItem> imgcontent;
                                    List<RowItem> imgcontent2 = new ArrayList<RowItem>();
                                    String file = Environment.getExternalStorageDirectory() + "/text.tmp";
                                    File f = new File(file);
                                    if (f.exists()) {
                                        System.out.println("FILE CREATING1");
                                        FileInputStream fis = new FileInputStream(file);
                                        ObjectInputStream ois = new ObjectInputStream(fis);
                                        imgobj = (Internal_Text) ois.readObject();
                                        ois.close(); flag=true;
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
                                        String file1 = Environment.getExternalStorageDirectory() + "/text.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(new Internal_Text(imgcontent));
                                        oos.close();


                                    } else {
                                        System.out.println("FILE CREATING2");
                                        String file1 = Environment.getExternalStorageDirectory() + "/text.tmp";
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                                        oos.writeObject(imgobj);
                                        oos.close();

                                    }

                                } catch (Exception e) {
                                    System.out.println("EXCEPTION OCCOURED" + e);
                                }

                                getlocalfiles();
                                TimeContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                CustomAdapterGT adapter = new CustomAdapterGT(MyService.this, grpcontent);
                                Group_tab.setContent(adapter);


                                Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                intent.putExtra("noti","text");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                PendingIntent pendingIntent = PendingIntent
                                        .getActivity(getApplicationContext(),
                                                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                        .setTicker("Title")
                                        .setContentTitle("Envirinsta")
                                        .setContentText("You have new Texts")
                                        .setSmallIcon(R.drawable.soul_logo)
                                        .setContentIntent(pendingIntent).getNotification();

                                notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(5, notification);

                            } else {
                                flagz = false;
                                for (int i = 0; i < images.targetmems.size(); i++) {
                                    if (CurrentUser.user.equals(images.targetmems.get(i))) {
                                        flagz = true;
                                        break;
                                    }
                                }
                                if (flagz) {
                                    flagz = false;
                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.doc, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Text");
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
                                            ois.close(); flag=true;
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
                                    CustomAdapterIT adapter = new CustomAdapterIT(MyService.this, percontent);
                                    Indi_tab.setContent(adapter);


                                    Intent intent = new Intent(getApplicationContext(),Timeline.class);
                                    intent.putExtra("noti","text");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    PendingIntent pendingIntent = PendingIntent
                                            .getActivity(getApplicationContext(),
                                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    android.app.Notification notification = new android.app.Notification.Builder(getApplicationContext())
                                            .setTicker("Title")
                                            .setContentTitle("Envirinsta")
                                            .setContentText("You have new Texts")
                                            .setSmallIcon(R.drawable.soul_logo)
                                            .setContentIntent(pendingIntent).getNotification();

                                    notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
                                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.notify(5, notification);
                                }
                            }

                        }
                    }
                    catch (ParseException e) {
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
    public boolean icheckdate(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        Date d1 = sdf.parse(date);
        Date d2 = sdf.parse(CurrentUser.sidate);
        if (d1.after(d2)) {
            return true;
        }
        return false;
    }

    public boolean acheckdate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        Date d1 = sdf.parse(date);
        Date d2 = sdf.parse(CurrentUser.sadate);
        if (d1.after(d2)) {
            return true;
        }
        return false;
    }

    public boolean vcheckdate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        Date d1 = sdf.parse(date);
        Date d2 = sdf.parse(CurrentUser.svdate);
        if (d1.after(d2)) {
            return true;
        }
        return false;
    }

    public boolean fcheckdate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        Date d1 = sdf.parse(date);
        Date d2 = sdf.parse(CurrentUser.sfdate);
        if (d1.after(d2)) {
            return true;
        }
        return false;
    }

    public boolean tcheckdate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        Date d1 = sdf.parse(date);
        Date d2 = sdf.parse(CurrentUser.stdate);
        if (d1.after(d2)) {
            return true;
        }
        return false;
    }
    public void getlocalfiles()
    {
        final Internal_Image imgobj;
        String filei = Environment.getExternalStorageDirectory() + "/img.tmp";
        System.out.println("FILE READING");
        FileInputStream fisi = null;
        try {
            fisi = new FileInputStream(filei);
            ObjectInputStream ois = new ObjectInputStream(fisi);
            imgobj = (Internal_Image) ois.readObject();
            ois.close();
            imgcontent = imgobj.imgcontent;
            Collections.reverse(imgcontent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final Internal_Audio audobj;
        String filea = Environment.getExternalStorageDirectory() + "/aud.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisa = null;
        try {
            fisa = new FileInputStream(filea);
            ObjectInputStream ois = new ObjectInputStream(fisa);
            audobj = (Internal_Audio) ois.readObject();
            ois.close();
            audcontent = audobj.audiocontent;
            Collections.reverse(audcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Text txtobj;
        String filet = Environment.getExternalStorageDirectory() + "/text.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fist = null;
        try {
            fist = new FileInputStream(filet);
            ObjectInputStream ois = new ObjectInputStream(fist);
            txtobj = (Internal_Text) ois.readObject();
            ois.close();
            textcontent = txtobj.textcontent;
            Collections.reverse(textcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_Video vidobj;
        String filev = Environment.getExternalStorageDirectory() + "/vid.tmp";
        System.out.println("FILE CREATING1");
        FileInputStream fisv = null;
        try {
            fisv = new FileInputStream(filev);
            ObjectInputStream ois = new ObjectInputStream(fisv);
            vidobj = (Internal_Video) ois.readObject();
            ois.close();
            vidcontent = vidobj.videocontent;
            Collections.reverse(vidcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Internal_File fileobj;
        String filef = Environment.getExternalStorageDirectory() + "/file.tmp";
        System.out.println("FILE READING");
        FileInputStream fisf = null;
        try {
            fisf = new FileInputStream(filef);
            ObjectInputStream ois = new ObjectInputStream(fisf);
            fileobj = (Internal_File) ois.readObject();
            ois.close();
            filecontent = fileobj.filecontent;
            Collections.reverse(filecontent);

        } catch (Exception e) {
            e.printStackTrace();
        }


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
        grpcontent = new ArrayList<>();
        grpcontent.addAll(textcontent);
        grpcontent.addAll(imgcontent);
        grpcontent.addAll(audcontent);
        grpcontent.addAll(vidcontent);
        grpcontent.addAll(filecontent);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(grpcontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
        percontent = new ArrayList<>();
        percontent.addAll(textcontent2);
        percontent.addAll(imgcontent2);
        percontent.addAll(audcontent2);
        percontent.addAll(vidcontent2);
        percontent.addAll(filecontent2);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(percontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });


    }
}