package bluepanther.envirinsta.Profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Adapter.Cred_Update;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.Contacts.Contacts;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.Adapter.RowItemPro;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.Adapter.CustomAdapterGP;
import bluepanther.envirinsta.Adapter.CustomAdapterIP;
import bluepanther.envirinsta.Signing.Sign_Up;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.Timeline.Timeline;
import bluepanther.envirinsta.Travel.Travel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shyam on 21/1/17.
 */

public class Profile extends AppCompatActivity implements
        OnMenuItemClickListener, OnMenuItemLongClickListener {

    int imgpt,audpt,vidpt,filespt,textpt,totpt;
    TextView proname;
    ListView listView;
    ProgressDialog imgprog;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    TextView points;
    String tmp1, tmp2, tmp3, tmp4, tmp5;
    List<RowItemPro> grpcontent;
    List<RowItemPro> percontent;
    ArrayList<String> namei, categi, datesi, contenti, authi,owni;
    ArrayList<String> namea, catega, datesa, contenta, autha,owna;
    ArrayList<String> namev, categv, datesv, contentv, authv,ownv;
    ArrayList<String> namef, categf, datesf, contentf, authf,ownf;
    ArrayList<String> namet, categt, datest, contentt, autht,ownt;


    ArrayList<String> namei2, categi2, datesi2, contenti2, authi2,owni2;
    ArrayList<String> namea2, catega2, datesa2, contenta2, autha2,owna2;
    ArrayList<String> namev2, categv2, datesv2, contentv2, authv2,ownv2;
    ArrayList<String> namef2, categf2, datesf2, contentf2, authf2,ownf2;
    ArrayList<String> namet2, categt2, datest2, contentt2, autht2,ownt2;

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

    String[] owneri;
    String[] ownera;
    String[] ownerv;
    String[] ownerf;
    String[] ownert;

    String[] owneri2;
    String[] ownera2;
    String[] ownerv2;
    String[] ownerf2;
    String[] ownert2;


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


    List<RowItemPro> rowItemsi, rowItemsa, rowItemsv, rowItemsf, rowItemst;
    List<RowItemPro> rowItemsi2, rowItemsa2, rowItemsv2, rowItemsf2, rowItemst2;

    Boolean go;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private TabLayout tabLayout3;
    CircleImageView propic;
    private ViewPager viewPager3;
    final int[] tabIcons = {
            R.drawable.ic_action_group_sv,
            R.drawable.ic_action_indi_sv
    };
    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_new);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProContent.grpcontent=new ArrayList<>();
        ProContent.percontent=new ArrayList<>();
        Preferences.getPrefs(getApplicationContext());

        proname=(TextView)findViewById(R.id.user_profile_name);
        propic=(CircleImageView)findViewById(R.id.user_profile_photo);
        proname.setText(CurrentUser.user);
        listView=(ListView)findViewById(R.id.listView);
        viewPager3 = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager3(viewPager3);

        points = (TextView)findViewById(R.id.points);
        tabLayout3 = (TabLayout) findViewById(R.id.tabs);
        tabLayout3.setupWithViewPager(viewPager3);
        setupTabIcons3();

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
        new MyTaskPic().execute();
        new MyTask().execute();
        new MyTaskPt().execute();





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setupViewPager3(ViewPager viewPager) {
        ViewPagerAdapter adapter = new
                ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("In notif class tabs");
        adapter.addFragment(new Progroup_tab(), "Group");
        adapter.addFragment(new Proindi_tab(), "Personal");

        viewPager.setAdapter(adapter);
        System.out.println("Tabset");
    }
    private void setupTabIcons3() {
        tabLayout3.getTabAt(0).setIcon(null);
        tabLayout3.getTabAt(1).setIcon(null);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
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

    private void initToolbar() {
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mToolBarTextView = (TextView)
//                findViewById(R.id.text_view_toolbar_title);
//        setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            //getSupportActionBar().setTitle("Soul Timeline");
//        }
//        mToolbar.setNavigationIcon(null);
////        mToolbar.setNavigationIcon(R.drawable.btn_back);
////        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                onBackPressed();
////            }
////        });
//
//        mToolBarTextView.setText("Soul");
//        mToolBarTextView.setTextColor(Color.WHITE);

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
                if
                        (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) ==
                        null) {
                    mMenuDialogFragment.show(fragmentManager,
                            ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        System.out.println("Prog status is"+imgprog.isShowing());
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            System.out.println("menu dismissed");
            mMenuDialogFragment.dismiss();
        }
        else
        {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(Profile.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(Profile.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(Profile.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(Profile.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(Profile.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(Profile.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(Profile.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(Profile.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Profile.this,
                                            AA_home.class));
                                    Profile.this.finish();
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
        Toast.makeText(this, "Long clicked on position: " + position,
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

    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgprog = new ProgressDialog(Profile.this);
            imgprog.setTitle("Message");
            imgprog.setMessage("Fetching Data...");

            imgprog.setCancelable(true);
            imgprog.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            imgprog.show();

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            rowItemsi = new ArrayList<RowItemPro>();
            rowItemsa = new ArrayList<RowItemPro>();
            rowItemsv = new ArrayList<RowItemPro>();
            rowItemsf = new ArrayList<RowItemPro>();
            rowItemst = new ArrayList<RowItemPro>();

            rowItemsi2 = new ArrayList<RowItemPro>();
            rowItemsa2 = new ArrayList<RowItemPro>();
            rowItemsv2 = new ArrayList<RowItemPro>();
            rowItemsf2 = new ArrayList<RowItemPro>();
            rowItemst2 = new ArrayList<RowItemPro>();
            //images
            tmp1 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Images/";
            fb_db = new Firebase(tmp1);
            rowItemsi.clear();
            rowItemsi2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    ImageDesc textDesc = dataSnapshot.getValue(ImageDesc.class);
                    String date = textDesc.date;
                    String desc = textDesc.desc;
                    System.out.println("Checking textt" +prevChildKey+" andd"+ dataSnapshot.getKey()+" and "+date+" "+desc);
                    go=false;
                    if (textDesc.getUser().equals(CurrentUser.user)) {

                        go = true;
                    }
                    if(go) {
                        if (textDesc.target.equals("all")) {

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.picture, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date,"To: Group","Images",textDesc.user);
                            rowItemsi.add(item);
                            getfiles();
                            ProContent.grpcontent=new ArrayList<RowItemPro>(grpcontent);
                            CustomAdapterGP adapter = new CustomAdapterGP(Profile.this, grpcontent);
                            Progroup_tab.setContent(adapter);

                        } else {
                            String tmp="To: ";
                            for(int i=0;i<textDesc.targetmems.size();i++)
                            {

                                tmp=tmp+textDesc.targetmems.get(i)+",";
                            }

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.picture, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date, tmp,"Images",textDesc.user);
                            rowItemsi2.add(item);
                            getfiles();
                            ProContent.percontent=new ArrayList<RowItemPro>(percontent);
                            CustomAdapterIP adapter = new CustomAdapterIP(Profile.this,percontent);
                            Proindi_tab.setContent(adapter);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

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
                    Audiodesc textDesc = dataSnapshot.getValue(Audiodesc.class);
                    String date = textDesc.date;
                    String desc = textDesc.desc;
                    System.out.println("Checking textt" +prevChildKey+" andd"+ dataSnapshot.getKey()+" and "+date+" "+desc);
                    go=false;
                    if (textDesc.getUser().equals(CurrentUser.user)) {

                        go = true;
                    }
                    if(go) {
                        if (textDesc.target.equals("all")) {

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.music, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date,"To: Group","Audios",textDesc.user);
                            rowItemsa.add(item);
                            getfiles();
                            ProContent.grpcontent=new ArrayList<RowItemPro>(grpcontent);
                            CustomAdapterGP adapter = new CustomAdapterGP(Profile.this, grpcontent);
                            Progroup_tab.setContent(adapter);

                        } else {
                            String tmp="To: ";
                            for(int i=0;i<textDesc.targetmems.size();i++)
                            {

                                tmp=tmp+textDesc.targetmems.get(i)+",";
                            }

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.music, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date, tmp,"Audios",textDesc.user);
                            rowItemsa2.add(item);
                            getfiles();
                            ProContent.percontent=new ArrayList<RowItemPro>(percontent);
                            CustomAdapterIP adapter = new CustomAdapterIP(Profile.this,percontent);
                            Proindi_tab.setContent(adapter);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

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
                    VideoDesc textDesc = dataSnapshot.getValue(VideoDesc.class);
                    String date = textDesc.date;
                    String desc = textDesc.desc;
                    System.out.println("Checking textt" +prevChildKey+" andd"+ dataSnapshot.getKey()+" and "+date+" "+desc);
                    go=false;
                    if (textDesc.getUser().equals(CurrentUser.user)) {

                        go = true;
                    }
                    if(go) {
                        if (textDesc.target.equals("all")) {

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.clip, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date,"To: Group","Videos",textDesc.user);
                            rowItemsv.add(item);
                            getfiles();
                            ProContent.grpcontent=new ArrayList<RowItemPro>(grpcontent);
                            CustomAdapterGP adapter = new CustomAdapterGP(Profile.this, grpcontent);
                            Progroup_tab.setContent(adapter);

                        } else {
                            String tmp="To: ";
                            for(int i=0;i<textDesc.targetmems.size();i++)
                            {

                                tmp=tmp+textDesc.targetmems.get(i)+",";
                            }

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.clip, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date, tmp,"Videos",textDesc.user);
                            rowItemsv2.add(item);
                            getfiles();
                            ProContent.percontent=new ArrayList<RowItemPro>(percontent);
                            CustomAdapterIP adapter = new CustomAdapterIP(Profile.this,percontent);
                            Proindi_tab.setContent(adapter);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

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
                    FileDesc fileDesc = dataSnapshot.getValue(FileDesc.class);
                    String date = fileDesc.date;
                    String desc = fileDesc.desc;
                    System.out.println("Checking textt" +prevChildKey+" andd"+ dataSnapshot.getKey()+" and "+date+" "+desc);
                    go=false;
                    if (fileDesc.getUser().equals(CurrentUser.user)) {

                        go = true;
                    }
                    if(go) {
                        if (fileDesc.target.equals("all")) {

                            RowItemPro item = new RowItemPro(fileDesc.desc,
                                    R.drawable.files, fileDesc.maincat + " : " + fileDesc.subcat,
                                    fileDesc.date,"To: Group","Files",fileDesc.user);
                            rowItemsf.add(item);
                            getfiles();
                            ProContent.grpcontent=new ArrayList<RowItemPro>(grpcontent);
                            CustomAdapterGP adapter = new CustomAdapterGP(Profile.this, grpcontent);
                            Progroup_tab.setContent(adapter);

                        } else {
                            String tmp="To: ";
                            for(int i=0;i<fileDesc.targetmems.size();i++)
                            {

                                tmp=tmp+fileDesc.targetmems.get(i)+",";
                            }

                            RowItemPro item = new RowItemPro(fileDesc.desc,
                                    R.drawable.files, fileDesc.maincat + " : " + fileDesc.subcat,
                                    fileDesc.date, tmp,"Files",fileDesc.user);
                            rowItemsf2.add(item);
                            getfiles();
                            ProContent.percontent=new ArrayList<RowItemPro>(percontent);
                            CustomAdapterIP adapter = new CustomAdapterIP(Profile.this,percontent);
                            Proindi_tab.setContent(adapter);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });

            tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/";
            fb_db = new Firebase(tmp5);

            rowItemst.clear();
            rowItemst2.clear();
            fb_db.addChildEventListener(new ChildEventListener() {
                @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    TextDesc textDesc = dataSnapshot.getValue(TextDesc.class);
                    String date = textDesc.date;
                    String desc = textDesc.desc;
                    System.out.println("Checking textt" +prevChildKey+" andd"+ dataSnapshot.getKey()+" and "+date+" "+desc);
                    go=false;
                    if (textDesc.getUser().equals(CurrentUser.user)) {

                        go = true;
                    }
                    if(go) {
                        if (textDesc.target.equals("all")) {

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.doc, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date,"To: Group","Text",textDesc.user);
                            rowItemst.add(item);
                            getfiles();
                            ProContent.grpcontent=new ArrayList<RowItemPro>(grpcontent);
                            CustomAdapterGP adapter = new CustomAdapterGP(Profile.this, grpcontent);
                            Progroup_tab.setContent(adapter);

                        } else {
                            String tmp="To: ";
                            for(int i=0;i<textDesc.targetmems.size();i++)
                            {

                                tmp=tmp+textDesc.targetmems.get(i)+",";
                            }

                            RowItemPro item = new RowItemPro(textDesc.desc,
                                    R.drawable.doc, textDesc.maincat + " : " + textDesc.subcat,
                                    textDesc.date, tmp,"Text",textDesc.user);
                            rowItemst2.add(item);
                            getfiles();
                            ProContent.percontent=new ArrayList<RowItemPro>(percontent);
                            CustomAdapterIP adapter = new CustomAdapterIP(Profile.this,percontent);
                            Proindi_tab.setContent(adapter);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

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
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");



                // Do things like hide the progress bar or change a TextView
            }
        }


    }
    public void getfiles()
    {
        grpcontent = new ArrayList<>();
        grpcontent.addAll(rowItemsi);
        grpcontent.addAll(rowItemsa);
        grpcontent.addAll(rowItemsv);
        grpcontent.addAll(rowItemsf);
        grpcontent.addAll(rowItemst);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(grpcontent, new Comparator<RowItemPro>() {
            @Override
            public int compare(RowItemPro lhs, RowItemPro rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
        percontent = new ArrayList<>();
        percontent.addAll(rowItemsi2);
        percontent.addAll(rowItemsa2);
        percontent.addAll(rowItemsv2);
        percontent.addAll(rowItemsf2);
        percontent.addAll(rowItemst2);
        System.out.println("Grp list size is:"+grpcontent.size());
        Collections.sort(percontent, new Comparator<RowItemPro>() {
            @Override
            public int compare(RowItemPro lhs, RowItemPro rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
    }
    private class MyTaskPic extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("MemberPics").child(CurrentUser.user);

            System.out.println("Storage refference : " + storageReference);
//            Glide.with(getApplicationContext())
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(propic);
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgprog.dismiss();
                            System.out.println("NOOB");
                            Picasso.with(getApplicationContext()).load(uri).fit().centerCrop().into(propic);
//                                            Picasso.with(Reports.this).load(uri).fit().centerCrop().into(imgg);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            System.out.println("sad" + exception);
                        }
                    });
            return null;
        }
    }

    private class MyTaskPt extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            fb_db = new Firebase(Base_url);

            fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int pt = Integer.parseInt(dataSnapshot.getValue().toString());

                    points.setText("Credit Points "+pt);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }});
            return null;
        }
    }


}
