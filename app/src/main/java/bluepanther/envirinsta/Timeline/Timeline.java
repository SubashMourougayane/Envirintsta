package bluepanther.envirinsta.Timeline;

/**
 * Created by shyamjoval on 1/14/2017.
 */

import android.animation.TimeAnimator;
import android.app.*;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;
import com.sa90.materialarcmenu.ArcMenu;
import com.singh.daman.proprogressviews.DottedArcProgress;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.Adapter.Cred_Update;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.ContentDesc.ImageDesc2;
import bluepanther.envirinsta.FileUtils.Filepath;
import bluepanther.envirinsta.FileUtils.ImageCompressor;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.InternalStorage.Internal_Audio;
import bluepanther.envirinsta.InternalStorage.Internal_File;
import bluepanther.envirinsta.InternalStorage.Internal_Image;
import bluepanther.envirinsta.InternalStorage.Internal_Text;
import bluepanther.envirinsta.InternalStorage.Internal_Video;
import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.NGO_Grid.GPSTracker;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.Services.LocContacts;
import bluepanther.envirinsta.Signing.MainActivity;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.Travel.Travel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class Timeline extends AppCompatActivity implements GooeyMenu.GooeyMenuInterface,OnMenuItemClickListener, OnMenuItemLongClickListener{

    private ViewPager mViewPager;
    ProgressDialog progressDialogt,progressDialogi,progressDialoga,progressDialogv,progressDialogf;
    ArcMenu arcMenu, arcMenu2, arcMenu3;
    ImageView imageView;
   String imglati="0.0",imglong="0.0";
    Uri imageUri;
    Button uploadimg;
    Boolean boolt=false,booli=false,boola=false,boolv=false,boolf=false;
    File compressedFiles;
    Boolean boolt2=false,booli2=false,boola2=false,boolv2=false,boolf2=false;
    Uri selectedImageUri,compressedImage=null,compressedAudio=null,compressedVideo=null,compressedFile=null;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    String maincat="",subcat="";
    public MediaType JSON;
    ArrayList<String>imgtags;
    OkHttpClient client;
    String mystring;
    public ArrayList<String>loccontacs,locphones;

    ArrayList<String>names=new ArrayList<>();
    ArrayList<String>mobs=new ArrayList<>();

    List<String> listsub = new ArrayList<String>();

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
    List<RowItem> grpcontent;
    List<RowItem> percontent;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    protected static final int REQUEST_PICK_VIDEO = 3;
    private static final int VIDEO_CAPTURE = 4;

    private static final int PICK_FILE = 5;

    protected static final int REQUEST_PICK_AUDIO = 6;
    public static final int ACTIVITY_RECORD_SOUND = 7;

    Uri VideoUri;
    String tagmeta="";
    String path,idesc="",adesc="",vdesc="",fdesc="";
    Uri audioUri;
    Uri fileUri = null;
    String date;
    String fname;
    TextDesc textDesc;
    ImageDesc imageDesc;
    Audiodesc audiodesc;
    VideoDesc videoDesc;
    FileDesc fileDesc;
//    private static final String[] MAIN_CATEGORY = {
//            "Select Category", "Academics", "Administration", "Extra-Curriculars", "Miscellaneous"
//    };
private static final String[] MAIN_CATEGORY = {
        "Select Category", "Roads", "Transport", "Water Lines", "Electricity","Bank/ATM", "Hospital" ,
        "Politics", "Govt Offices", "Sewage" , "General PWD", "Others"
};
    String descr="",textcontents="";
    Dialog text_dialog,image_dialog,audio_dialog,video_dialog,file_dialog;



    Notification notification,notification2,notification3,notification4;

    double progress = 0.0,progress2=0.0,progress3=0.0,progress4 = 0.0;

    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    private Firebase fb_db2;

    ImageDesc img;
    ImageDesc2 img2;
    ArrayList<String> targetmems;
    TextDesc txt;
    String tar="GROUP";
    Audiodesc aud;


    ArrayList<String>tempmems;
    VideoDesc vid;
    FileDesc fil;

//    TextDesc2 txt2;
//    Audiodesc2 aud2;
//    VideoDesc2 vid2;
//    FileDesc2 fil2;
    GPSTracker gps;
    BottomSheetBehavior behavior, behavior2, behavior3, behavior4, behavior5;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;

    private LinearLayout linearLayout;

    private GooeyMenu mGooeyMenu;
    private Toast mToast;
    private SpaceNavigationView spaceNavigationView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    final int[] tabIcons = {
            R.drawable.ic_action_group_sv,
            R.drawable.ic_action_indi_sv
    };

    static Toolbar toolbar;
   static TextView counter_text;


    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    DottedArcProgress dot;

@Override
protected void onNewIntent(Intent intent)
{
    super.onNewIntent(intent);
    setIntent(intent);//must store the new intent unless getIntent() will return the old one
    Preferences.getPrefs(getApplicationContext());
    String noti=getIntent().getExtras().get("noti").toString();

    System.out.println("noti2 is"+noti);
//        switch(noti)
//        {
//            case "image":
//                date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
//                new MyTaskinoti().execute();
//                break;
//            case "audio":
//                date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
//                new MyTaskanoti().execute();
//                break;
//            case "video":
//                date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
//                new MyTaskvnoti().execute();
//                break;
//            case "file":
//                date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
//                new MyTaskfnoti().execute();
//                break;
//            case "text":
//                date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
//                new MyTasktnoti().execute();
//                break;
//        }
    date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());

    new MyTaskinoti().execute();

    new MyTaskanoti().execute();

    new MyTaskvnoti().execute();

    new MyTaskfnoti().execute();

    new MyTasktnoti().execute();

}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_frag);

//        dot = (DottedArcProgress) findViewById(R.id.dot);
//        dot.setVisibility(View.VISIBLE);

        getSupportActionBar().setTitle("Timeline");
        Firebase.setAndroidContext(Timeline.this);
fb_db=new Firebase(Base_url);
        Preferences.getPrefs(getApplicationContext());


        listsub.add("Select Sub-Category");
        listsub.add("Sub Type 1");
        listsub.add("Sub Type 2");
        listsub.add("Sub Type 3");
        listsub.add("Sub Type 4");
        listsub.add("Sub Type 5");
        listsub.add("Sub Type 6");
        listsub.add("Sub Type 7");





        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("GROUP", R.drawable.ic_action_group_sv));
        spaceNavigationView.addSpaceItem(new SpaceItem("INDIVIDUAL", R.drawable.ic_action_indi_sv));
        spaceNavigationView.setCentreButtonIcon(R.drawable.soul_logo);
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setBackgroundColor(getResources().getColor(R.color.bg));

        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        mGooeyMenu = (GooeyMenu) findViewById(R.id.gooey_menu);
        mGooeyMenu.setOnMenuListener(Timeline.this);

       // toolbar = (Toolbar) findViewById(R.id.toolbar);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Timeline");

        fragmentManager = getSupportFragmentManager();
       // initToolbar();
        initMenuFragment();

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");


                spaceNavigationView.shouldShowFullBadgeText(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
                tar=itemName;
                System.out.println("Inside indi");


            }


            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
                tar=itemName;
            }
        });

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(Timeline.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(Timeline.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
        getlocalfiles();
        TimeContent.grpcontent=new ArrayList<>(grpcontent);
        TimeContent.percontent=new ArrayList<>(percontent);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        View bottomSheet2 =findViewById(R.id.design_bottom_sheet2);
        View bottomSheet3 = findViewById(R.id.design_bottom_sheet3);
        View bottomSheet4 = findViewById(R.id.design_bottom_sheet4);
        View bottomSheet5 = findViewById(R.id.design_bottom_sheet5);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior2 = BottomSheetBehavior.from(bottomSheet2);
        behavior3 = BottomSheetBehavior.from(bottomSheet3);
        behavior4 = BottomSheetBehavior.from(bottomSheet4);
        behavior5 = BottomSheetBehavior.from(bottomSheet5);

        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior3.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior4.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior5.setState(BottomSheetBehavior.STATE_HIDDEN);



    }

    private void initToolbar() {
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mToolBarTextView = (TextView)findViewById(R.id.text_view_toolbar_title);
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

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int)
                getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        menuParams.setAnimationDuration(30);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(Timeline.this);
        mMenuDialogFragment.setItemLongClickListener(Timeline.this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("In notif class tabs");
        adapter.addFragment(new Group_tab(), "Group");
        adapter.addFragment(new Indi_tab(), "Personal");

        viewPager.setAdapter(adapter);
        System.out.println("Tabset");
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_group_sv);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_indi_sv);

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

    @Override
    public void menuOpen() {
//        showToast("Menu Open");

    }

    @Override
    public void menuClose() {
//        showToast( "Menu Close");
    }

    @Override
    public void menuItemClicked(int menuNumber) {
        menuNumber=menuNumber%10;
        switch(menuNumber)
        {
            case 1:
                selectText();
                break;
            case 2:
//                uploadimg.setClickable(true);
                selectImage();
                break;
            case 3:
                selectAudio();
                break;
            case 4:
                selectVideo();
                break;
            case 5:
                selectFiles();
                break;
        }
        showToast( "Menu item clicked : " + menuNumber);

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

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(Timeline.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(Timeline.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(Timeline.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(Timeline.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(Timeline.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(Timeline.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(Timeline.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(Timeline.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Timeline.this,
                                            AA_home.class));
                                    Timeline.this.finish();
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
        Toast.makeText(Timeline.this, "Long clicked on position: " + position,
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
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






    private void showToast(String msg){
        if(mToast!=null){
            mToast.cancel();
        }
        mToast= Toast.makeText(Timeline.this,msg,Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }
    private void selectText()
    {

        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
             final EditText editText = (EditText) findViewById(R.id.editText);
             final EditText desc = (EditText) findViewById(R.id.desc);
//            final EditText loc = (EditText) findViewById(R.id.loc);
//
//            final CheckBox auto_tag = (CheckBox) findViewById(R.id.auto_tag);
//            auto_tag.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(auto_tag.isChecked())
//                    {
//                        loc.setEnabled(false);
//                    }
//                    else
//                    {
//                        loc.setEnabled(true);
//                    }
//                }
//            });

            Button upload = (Button) findViewById(R.id.upload);
            upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //                String loc1 = loc.getText().toString();
                textcontents=editText.getText().toString();
                descr=desc.getText().toString();
                if(textcontents.length()>0&&descr.length()>0&&boolt&&boolt2)
                {
                    new MyTaskt().execute();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                    builder.setMessage("Please provide complete details")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

               // text_dialog.dismiss();
            }
        });
            //mShowBottomSheet.setText("Date Picker");
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            //mShowBottomSheet.setText("Date Picker");
        }
        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_text);
        final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_text);
        spinner.setItems(MAIN_CATEGORY);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                if(!item.equals("Select Category"))
                {
                    maincat=item;
                    boolt=true;

                }
                if(item.equals("Roads"))
                {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                    spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                }

                if(item.equals("Transport"))
                {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                    spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                }

                if(item.equals("Water Lines"))
                {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                    spinner2.setItems(listsub);
//

                }
                if(item.equals("Electricity"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("Bank/ATM"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("Hospitals"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("Politics"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("Govt Offices"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("Sewage"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("General PWD"))
                {
                    spinner2.setItems(listsub);

                }
                if(item.equals("Others"))
                {
                    spinner2.setItems(listsub);

                }

            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                if(!item.equals("Select Sub-Category"))
                {
                    subcat=item;
                    boolt2=true;

                }
            }
        });
    }



    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
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
                    imageUri = Timeline.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

    private void selectAudio()
    {
        final CharSequence[] items = { "Record Audio", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
        builder.setTitle("Choose audio..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Record Audio")) {

                    Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Audio "), REQUEST_PICK_AUDIO);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    private void selectVideo() {
        final CharSequence[] items = { "Take Video", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
        builder.setTitle("Choose video..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Video")) {
                    File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    int cnt=f.listFiles().length;
                    System.out.println("FIle cnt is "+cnt+"and they are"+f.listFiles());
                    File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/myvideo"+Integer.toString(cnt+1)+".mp4");
                    path=Environment.getExternalStorageDirectory().getAbsolutePath()+ "/myvideo"+Integer.toString(cnt+1)+".mp4";
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    VideoUri = Uri.fromFile(mediaFile);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, VideoUri);
                    startActivityForResult(intent, VIDEO_CAPTURE);


                } else if (items[item].equals("Choose from Library")) {
                    Intent gintent = new Intent();
                    gintent.setType("video/*");
                    gintent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(
                            Intent.createChooser(gintent, "Select Picture"),
                            REQUEST_PICK_VIDEO);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    private void selectFiles()
    {
        System.out.println("About to pick files");
        Intent gintent = new Intent();
        gintent.setType("file/*");
        gintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(gintent, "Select File"),
                PICK_FILE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Timeline.this.RESULT_OK) {
            System.out.println("Result code is"+resultCode+"and req code is"+requestCode);
            if (requestCode == PICK_Camera_IMAGE) {
                gps = new GPSTracker(Timeline.this);

                if (resultCode == Timeline.this.RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
                    Bitmap bm = BitmapFactory.decodeFile(Filepath.getPaths(Timeline.this,selectedImageUri));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    System.out.println("img bytes is"+encodedImage);
                    checkVision(encodedImage);


                } else if (resultCode == Timeline.this.RESULT_CANCELED) {
                    Toast.makeText(Timeline.this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Timeline.this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }

                if (behavior2.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);


                    dot = (DottedArcProgress) findViewById(R.id.dot);
                    dot.setVisibility(View.VISIBLE);
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });


                        compressedImage=Uri.fromFile(new File(ImageCompressor.with(getApplicationContext()).compress(selectedImageUri.toString())));

                    if(compressedImage==null)
                    {
                        compressedImage=selectedImageUri;
                    }
                    String image_name = getFileName(selectedImageUri);
                    String image_size = null;
                    try {
                        image_size = getImageSize(selectedImageUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String image_path = Filepath.getPaths(Timeline.this,selectedImageUri);
                    String image_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String image_type = mime.getExtensionFromMimeType(cR.getType(selectedImageUri));
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(Timeline.this.getContentResolver() , compressedImage);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    TextView textview10 = (TextView) findViewById(R.id.textView10);
                    TextView textview11 = (TextView) findViewById(R.id.textView11);
                    TextView textview12 = (TextView) findViewById(R.id.textView12);
                    TextView textview13 = (TextView) findViewById(R.id.textView13);
                    TextView textview14 = (TextView) findViewById(R.id.textView14);

                    textview10.setText(image_name);
                    textview11.setText(image_type);
                    textview12.setText(image_path);
                    textview13.setText(image_size+" Kb");
                    textview14.setText(image_date);

                    final EditText desc2 = (EditText) findViewById(R.id.desc2);
                    uploadimg = (Button) findViewById(R.id.upload2);
                    uploadimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           // dot.setVisibility(View.VISIBLE);
                            idesc = desc2.getText().toString();
                            if (idesc.length() > 0 && booli && booli2) {
                                new MyTaski().execute();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                builder.setMessage("Please provide complete details")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }

                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_image);
                final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_image);
                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Category"))
                        {
                            maincat=item;
                            booli=true;

                        }
                        if(item.equals("Roads"))
                        {
                            List<String> list = new ArrayList<String>();
//                            list.add("Select Sub-Category");
//                            list.add("Time Table");
//                            list.add("Attendance");
//                            list.add("Text Books");
//                            list.add("Test Schedule");
//                            list.add("Test Marks");
//                            list.add("Question Papers");
//                            list.add("Resources");
//                            list.add("Exam Schedule");
//                            list.add("Exam Marks");
//                            list.add("Assignments");
//                            list.add("Home Work");
//                            list.add("Record");
//                            list.add("Observation");
//                            list.add("Projects");
//                            list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                        }

                        if(item.equals("Transport"))
                        {
//                            List<String> list = new ArrayList<String>();
//                            list.add("Select Sub-Category");
//                            list.add("Registration");
//                            list.add("Fee Payment");
//                            list.add("Reminders");
//                            list.add("Uniform");
//                            list.add("Shoes / Accessories");
//                            list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                            list.add("Visitors / Special Guests");
//                            list.add("Special Lectures");
//                            list.add("Certificates");
//                            list.add("Holidays");
//                            list.add("Scholarships");
//                            list.add("Circulars");
//                            list.add("Notice Board");
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                        }

                        if(item.equals("Water Lines"))
                        {
//                            List<String> list = new ArrayList<String>();
//                            list.add("Select Sub-Category");
//                            list.add("NCC");
//                            list.add("NSS");
//                            list.add("Club Activities");
//                            list.add("Competitions");
//                            list.add("Prizes / Awards");
//                            list.add("Donations");
//                            list.add("Field Trips");
//                            list.add("Tours");
                            spinner2.setItems(listsub);
//

                        }
                        if(item.equals("Electricity"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Bank/ATM"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Hospitals"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Politics"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Govt Offices"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Sewage"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("General PWD"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Others"))
                        {
                            spinner2.setItems(listsub);

                        }

                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });
                spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Sub-Category"))
                        {
                            subcat=item;
                            booli2=true;

                        }
                    }
                });
            }
            if (requestCode == PICK_IMAGE) {
                gps = new GPSTracker(Timeline.this);

                if (resultCode == Timeline.this.RESULT_OK) {
                    selectedImageUri = data.getData();
                    Bitmap bm = BitmapFactory.decodeFile(Filepath.getPaths(Timeline.this,selectedImageUri));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    System.out.println("img bytes is"+encodedImage);
                    checkVision(encodedImage);
                } else if (resultCode == Timeline.this.RESULT_CANCELED) {
                    Toast.makeText(Timeline.this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Timeline.this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                }

                if (behavior2.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    dot = (DottedArcProgress) findViewById(R.id.dot);
                    dot.setVisibility(View.VISIBLE);
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);


                    compressedImage=Uri.fromFile(new File(ImageCompressor.with(getApplicationContext()).compress(selectedImageUri.toString())));

                    if(compressedImage==null)
                    {
                        compressedImage=selectedImageUri;
                    }
                    String image_name = getFileName(selectedImageUri);
                    String image_size = null;
                    try {
                        image_size = getImageSize(selectedImageUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String image_path = Filepath.getPaths(Timeline.this,selectedImageUri);
                    String image_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String image_type = mime.getExtensionFromMimeType(cR.getType(selectedImageUri));
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(Timeline.this.getContentResolver() , compressedImage);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    TextView textview10 = (TextView) findViewById(R.id.textView10);
                    TextView textview11 = (TextView) findViewById(R.id.textView11);
                    TextView textview12 = (TextView) findViewById(R.id.textView12);
                    TextView textview13 = (TextView) findViewById(R.id.textView13);
                    TextView textview14 = (TextView) findViewById(R.id.textView14);

                    textview10.setText(image_name);
                    textview11.setText(image_type);
                    textview12.setText(image_path);
                    textview13.setText(image_size+" Kb");
                    textview14.setText(image_date);


                    final EditText desc2 = (EditText) findViewById(R.id.desc2);
                    uploadimg = (Button) findViewById(R.id.upload2);
                    uploadimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idesc = desc2.getText().toString();
                            if(idesc.length()>0&&booli&&booli2)
                            {
                                new MyTaski().execute();

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                builder.setMessage("Please provide complete details")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_image);
                final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_image);
                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Category"))
                        {
                            maincat=item;
                            booli=true;

                        }
                        if(item.equals("Roads"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                        }

                        if(item.equals("Transport"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                        }

                        if(item.equals("Water Lines"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                            spinner2.setItems(listsub);
//

                        }
                        if(item.equals("Electricity"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Bank/ATM"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Hospitals"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Politics"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Govt Offices"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Sewage"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("General PWD"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Others"))
                        {
                            spinner2.setItems(listsub);

                        }
                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });
                spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Sub-Category"))
                        {
                            subcat=item;
                            booli2=true;

                        }
                    }
                });
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(Filepath.getPaths(Timeline.this,selectedImageUri));
                    float[] latLong = new float[2];
                    boolean hasLatLong = exif.getLatLong(latLong);
                    if (hasLatLong) {
                        System.out.println("Latitude: " + latLong[0]);
                        System.out.println("Longitude: " + latLong[1]);
                        imglati=String.valueOf(latLong[0]);
                        imglong=String.valueOf(latLong[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                System.out.println("Image props are:"+ReadExif(Filepath.getPaths(Timeline.this,selectedImageUri)));
            }


            if (requestCode == REQUEST_PICK_VIDEO) {


                if (data.equals(null)) {
                    Toast.makeText(Timeline.this, "Video was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    VideoUri = data.getData();
                    Toast.makeText(Timeline.this, VideoUri.toString(), Toast.LENGTH_SHORT).show();
                    Filepath obj = new Filepath();
                    path =     obj.getPaths(Timeline.this, VideoUri);
                    System.out.println("VIDEOO" + VideoUri + " d " + VideoUri.getPath());

                    if (behavior4.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                        behavior4.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        String video_name = getFileName(VideoUri);
                        String video_size = null;
                        try {
                            video_size = getImageSize(VideoUri).toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String video_path = Filepath.getPaths(Timeline.this,VideoUri);
                        String video_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                        String video_type = getMimeType(video_path);

                        TextView textview10 = (TextView) findViewById(R.id.textView10_video);
                        TextView textview11 = (TextView) findViewById(R.id.textView11_video);
                        TextView textview12 = (TextView) findViewById(R.id.textView12_video);
                        TextView textview13 = (TextView) findViewById(R.id.textView13_video);
                        TextView textview14 = (TextView) findViewById(R.id.textView14_video);

                        textview10.setText(video_name);
                        textview11.setText(video_type);
                        textview12.setText(video_path);
                        textview13.setText(video_size+" Kb");
                        textview14.setText(video_date);

                        final EditText desc4 = (EditText) findViewById(R.id.desc4);
                        Button upload4 = (Button) findViewById(R.id.upload4);
                        upload4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                vdesc = desc4.getText().toString();
                                if(vdesc.length()>0&&boolv&&boolv2)
                                {
                                    new MyTaskv().execute();

                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                    builder.setMessage("Please provide complete details")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }

                            }

                        });
                        //mShowBottomSheet.setText("Date Picker");
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        //mShowBottomSheet.setText("Date Picker");
                    }

                    final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_video);
                    final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_video);
                    spinner.setItems(MAIN_CATEGORY);
                    spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                            if(!item.equals("Select Category"))
                            {
                                maincat=item;
                                boolv=true;

                            }
                            if(item.equals("Roads"))
                            {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                                spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                            }

                            if(item.equals("Transport"))
                            {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                                spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                            }

                            if(item.equals("Water Lines"))
                            {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                                spinner2.setItems(listsub);
//

                            }
                            if(item.equals("Electricity"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("Bank/ATM"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("Hospitals"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("Politics"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("Govt Offices"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("Sewage"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("General PWD"))
                            {
                                spinner2.setItems(listsub);

                            }
                            if(item.equals("Others"))
                            {
                                spinner2.setItems(listsub);

                            }
                        }
                    });
                    spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                        @Override
                        public void onNothingSelected(MaterialSpinner spinner) {
                            Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                        }
                    });
                    spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                            if(!item.equals("Select Sub-Category"))
                            {
                                subcat=item;
                                boolv2=true;

                            }
                        }
                    });
                }
            }

            if (requestCode == VIDEO_CAPTURE) {
                if (resultCode == Timeline.this.RESULT_OK) {
                    Toast.makeText(Timeline.this, "Video has been saved to:\n" +
                            data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == Timeline.this.RESULT_CANCELED) {
                    Toast.makeText(Timeline.this, "Video recording cancelled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Timeline.this, "Failed to record video",
                            Toast.LENGTH_LONG).show();
                }

                if (behavior4.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior4.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    String video_name = getFileName(VideoUri);
                    String video_size=null;
                    try {
                         video_size= getImageSize(VideoUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String video_path = Filepath.getPaths(Timeline.this,VideoUri);
                    String video_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());

                    String video_type = getMimeType(video_path);

                    TextView textview10 = (TextView) findViewById(R.id.textView10_video);
                    TextView textview11 = (TextView) findViewById(R.id.textView11_video);
                    TextView textview12 = (TextView) findViewById(R.id.textView12_video);
                    TextView textview13 = (TextView) findViewById(R.id.textView13_video);
                    TextView textview14 = (TextView) findViewById(R.id.textView14_video);

                    textview10.setText(video_name);
                    textview11.setText(video_type);
                    textview12.setText(video_path);
                    textview13.setText(video_size+" Kb");
                    textview14.setText(video_date);


                    final EditText desc4 = (EditText) findViewById(R.id.desc4);
                    Button upload4 = (Button) findViewById(R.id.upload4);
                    upload4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vdesc = desc4.getText().toString();
                            if(vdesc.length()>0&&boolv&&boolv2)
                            {
                                new MyTaskv().execute();

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                builder.setMessage("Please provide complete details")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_video);
                final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_video);
                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Category"))
                        {
                            maincat=item;
                            boolv=true;

                        }
                        if(item.equals("Roads"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                        }

                        if(item.equals("Transport"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                        }

                        if(item.equals("Water Lines"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                            spinner2.setItems(listsub);
//

                        }
                        if(item.equals("Electricity"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Bank/ATM"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Hospitals"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Politics"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Govt Offices"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Sewage"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("General PWD"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Others"))
                        {
                            spinner2.setItems(listsub);

                        }
                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });
                spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Sub-Category"))
                        {
                            subcat=item;
                            boolv2=true;

                        }
                    }
                });
            }
            if (requestCode == REQUEST_PICK_AUDIO) {
                audioUri = data.getData();
                //  String path = audioUri.getPath(); // "file:///mnt/sdcard/FileName.mp3"

                Filepath obj = new Filepath();
                path = obj.getPaths(Timeline.this, audioUri);
                System.out.println("Path is " + audioUri + " d " + obj.getPaths(Timeline.this, audioUri));

                if (behavior3.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    String audio_name = getFileName(audioUri);
                    String audio_size = null;
                    try {
                        audio_size = getImageSize(audioUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String audio_path = Filepath.getPaths(Timeline.this,audioUri);
                    String audio_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String audio_type = mime.getExtensionFromMimeType(cR.getType(audioUri));

                    TextView textview10 = (TextView) findViewById(R.id.textView10_audio);
                    TextView textview11 = (TextView) findViewById(R.id.textView11_audio);
                    TextView textview12 = (TextView) findViewById(R.id.textView12_audio);
                    TextView textview13 = (TextView) findViewById(R.id.textView13_audio);
                    TextView textview14 = (TextView) findViewById(R.id.textView14_audio);

                    textview10.setText(audio_name);
                    textview11.setText(audio_type);
                    textview12.setText(audio_path);
                    textview13.setText(audio_size+" Kb");
                    textview14.setText(audio_date);

                    final EditText desc3 = (EditText) findViewById(R.id.desc3);
                    Button upload3 = (Button) findViewById(R.id.upload3);
                    upload3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            adesc = desc3.getText().toString();
                            if(adesc.length()>0&&boola&&boola2)
                            {
                                new MyTaska().execute();

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                builder.setMessage("Please provide complete details")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_audio);
                final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_audio);
                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Category"))
                        {
                            maincat=item;
//                            boola=true;            List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");

                        }
                        if(item.equals("Roads"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                        }

                        if(item.equals("Transport"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                        }

                        if(item.equals("Water Lines"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                            spinner2.setItems(listsub);
//

                        }
                        if(item.equals("Electricity"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Bank/ATM"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Hospitals"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Politics"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Govt Offices"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Sewage"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("General PWD"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Others"))
                        {
                            spinner2.setItems(listsub);

                        }
                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });
                spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Sub-Category"))
                        {
                            subcat=item;
                            boola2=true;

                        }
                    }
                });
            }
            if (requestCode == ACTIVITY_RECORD_SOUND) {
                audioUri = data.getData();
                //  String path = audioUri.getPath(); // "file:///mnt/sdcard/FileName.mp3"

                Filepath obj = new Filepath();
                path = obj.getPaths(Timeline.this, audioUri);
                System.out.println("Path is " + audioUri + " d " + obj.getPaths(Timeline.this, audioUri));

                if (behavior3.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    String audio_name = getFileName(audioUri);
                    String audio_size = null;
                    try {
                        audio_size = getImageSize(audioUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String audio_path = Filepath.getPaths(Timeline.this,audioUri);
                    String audio_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String audio_type = mime.getExtensionFromMimeType(cR.getType(audioUri));

                    TextView textview10 = (TextView) findViewById(R.id.textView10_audio);
                    TextView textview11 = (TextView) findViewById(R.id.textView11_audio);
                    TextView textview12 = (TextView) findViewById(R.id.textView12_audio);
                    TextView textview13 = (TextView) findViewById(R.id.textView13_audio);
                    TextView textview14 = (TextView) findViewById(R.id.textView14_audio);

                    textview10.setText(audio_name);
                    textview11.setText(audio_type);
                    textview12.setText(audio_path);
                    textview13.setText(audio_size+" Kb");
                    textview14.setText(audio_date);

                    final EditText desc3 = (EditText) findViewById(R.id.desc3);
                    Button upload3 = (Button) findViewById(R.id.upload3);
                    upload3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adesc = desc3.getText().toString();
                            if(adesc.length()>0&&boola&&boola2)
                            {
                                new MyTaska().execute();

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                builder.setMessage("Please provide complete details")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }

                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_audio);
                final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_audio);
                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Category"))
                        {
                            maincat=item;
                            boola=true;

                        }
                        if(item.equals("Roads"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                        }

                        if(item.equals("Transport"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                        }

                        if(item.equals("Water Lines"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                            spinner2.setItems(listsub);
//

                        }
                        if(item.equals("Electricity"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Bank/ATM"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Hospitals"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Politics"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Govt Offices"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Sewage"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("General PWD"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Others"))
                        {
                            spinner2.setItems(listsub);

                        }
                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });
                spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Sub-Category"))
                        {
                            subcat=item;
                            boola2=true;

                        }
                    }
                });
            }

            if (requestCode == PICK_FILE) {
                fileUri = data.getData();
                System.out.println("HOLA");
                Filepath obj = new Filepath();
                path = obj.getPaths(Timeline.this, fileUri);
                System.out.println(fileUri + " d " + fileUri.getPath());

                if (behavior5.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior5.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    String file_name = getFileName(fileUri);
                    String file_size = null;
                    try {
                        file_size = getImageSize(fileUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String file_path = Filepath.getPaths(Timeline.this,fileUri);
                    String file_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String file_type = mime.getExtensionFromMimeType(cR.getType(fileUri));

                    TextView textview10 = (TextView) findViewById(R.id.textView10_file);
                    TextView textview11 = (TextView) findViewById(R.id.textView11_file);
                    TextView textview12 = (TextView) findViewById(R.id.textView12_file);
                    TextView textview13 = (TextView) findViewById(R.id.textView13_file);
                    TextView textview14 = (TextView) findViewById(R.id.textView14_file);

                    textview10.setText(file_name);
                    textview11.setText(file_type);
                    textview12.setText(file_path);
                    textview13.setText(file_size+" Kb");
                    textview14.setText(file_date);

                    final EditText desc5 = (EditText) findViewById(R.id.desc5);
                    Button upload5 = (Button) findViewById(R.id.upload5);
                    upload5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fdesc = desc5.getText().toString();
                            if(fdesc.length()>0&&boolf&&boolf2)
                            {
                                new MyTaskf().execute();

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline.this);
                                builder.setMessage("Please provide complete details")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_file);
                final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_file);
                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Category"))
                        {
                            maincat=item;
                            boolf=true;

                        }
                        if(item.equals("Roads"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Time Table");
//                    list.add("Attendance");
//                    list.add("Text Books");
//                    list.add("Test Schedule");
//                    list.add("Test Marks");
//                    list.add("Question Papers");
//                    list.add("Resources");
//                    list.add("Exam Schedule");
//                    list.add("Exam Marks");
//                    list.add("Assignments");
//                    list.add("Home Work");
//                    list.add("Record");
//                    list.add("Observation");
//                    list.add("Projects");
//                    list.add("Late Attendance");
//                    String tmps[]=new String[list.size()];
//                    for(int i=0;i<list.size();i++)
//                    {
//                        tmps[i]=list.get(i);
//                    }
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter);
                        }

                        if(item.equals("Transport"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("Registration");
//                    list.add("Fee Payment");
//                    list.add("Reminders");
//                    list.add("Uniform");
//                    list.add("Shoes / Accessories");
//                    list.add("Functions (Annual Day, Sports Day, Science Exhibition)");
//                    list.add("Visitors / Special Guests");
//                    list.add("Special Lectures");
//                    list.add("Certificates");
//                    list.add("Holidays");
//                    list.add("Scholarships");
//                    list.add("Circulars");
//                    list.add("Notice Board");
                            spinner2.setItems(listsub);
//                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Reports_new.this, android.R.layout.simple_spinner_item, list);
//                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    dataAdapter2.notifyDataSetChanged();
//                    spinner2.setAdapter(dataAdapter2);
                        }

                        if(item.equals("Water Lines"))
                        {
//                    List<String> list = new ArrayList<String>();
//                    list.add("Select Sub-Category");
//                    list.add("NCC");
//                    list.add("NSS");
//                    list.add("Club Activities");
//                    list.add("Competitions");
//                    list.add("Prizes / Awards");
//                    list.add("Donations");
//                    list.add("Field Trips");
//                    list.add("Tours");
                            spinner2.setItems(listsub);
//

                        }
                        if(item.equals("Electricity"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Bank/ATM"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Hospitals"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Politics"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Govt Offices"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Sewage"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("General PWD"))
                        {
                            spinner2.setItems(listsub);

                        }
                        if(item.equals("Others"))
                        {
                            spinner2.setItems(listsub);

                        }
                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });
                spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                        if(!item.equals("Select Sub-Category"))
                        {
                            subcat=item;
                            boolf2=true;

                        }
                    }
                });
            }
        }


    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private Long getImageSize(Uri uri) throws IOException {
//        Cursor cursor = Timeline.this.getContentResolver().query(uri,
//                null, null, null, null);
//        cursor.moveToFirst();
//        long size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
//        cursor.close();
        ContentResolver cr = getContentResolver();
        InputStream is = cr.openInputStream(uri);
        int size = is.available();
        size=size/1024;
        return (long)size;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = Timeline.this.getContentResolver().query(uri, null, null, null, null);
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
        return result;
    }

    private class MyTaskt extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //     progressDialog = ProgressDialog.show(Timeline.this, "Message", "Uploading Image...");

        }

        // Timeline.this is run in a background thread
        @Override
        protected String doInBackground(String... params) {

            txt = new TextDesc();
            txt.setUser(CurrentUser.user);

            txt.setDesc(descr);
            txt.setMaincat(maincat);
            txt.setSubcat(subcat);
            txt.setText(textcontents);
            txt.setResdate(" ");
            txt.setResolvers(" ");

            txt.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));


            if(txt.getText().isEmpty())
            {
                Toast.makeText(Timeline.this,"Enter the Text",Toast.LENGTH_SHORT).show();

            }
            else if( txt.getDesc().isEmpty())
            {
                Toast.makeText(Timeline.this,"Enter The description", Toast.LENGTH_SHORT).show();

            }
            if(isNetworkAvailable()) {
                if (tar.equals("GROUP")) {
                    txt.setTarget("all");
                    txt.setTargetmems(new ArrayList<String>());
                    puttext();
                } else if (tar.equals("INDIVIDUAL")) {
                    final List<CharSequence> list = new ArrayList<CharSequence>();
                    System.out.println("Inside indi");
                    //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values
                    final ArrayList<String>phones=new ArrayList<>();
                    fb_db2 = new Firebase(Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Members/");
                    fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                                String uname = cred_update.getUsn();
                                String mob=cred_update.getMobile();
                                if (!uname.equals(CurrentUser.user)) {
                                    list.add(uname);
                                    phones.add(mob);

                                }

                            }

                            //store contacts
                            try {
                                LocContacts imgobj = null;
                                String file = Environment.getExternalStorageDirectory() + "/contacts.tmp";
                                File f = new File(file);
                                if (f.exists()) {
                                    System.out.println("FILE CREATING1");
                                    FileInputStream fis = new FileInputStream(file);
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    imgobj = (LocContacts) ois.readObject();
                                    ois.close();
                                    Boolean flag = true;
                                    loccontacs = new ArrayList<String>(imgobj.contacts);
                                    locphones= new ArrayList<String>(imgobj.phones);
                                    System.out.println("old contacts are"+loccontacs);

                                    for (int i = 0; i < list.size(); i++) {
                                        for (int j = 0; j < loccontacs.size(); j++) {
                                            if (list.get(i).toString().equals(loccontacs.get(j))) {
                                                flag = false;
                                            }

                                        }
                                        if (flag) {
                                            System.out.println("new contacts are"+list.get(i).toString());
                                            loccontacs.add(list.get(i).toString());
                                            locphones.add(phones.get(i));
                                        }
                                        flag = true;
                                    }
System.out.println("Now storing"+loccontacs+"and "+locphones);
                                    new PrintWriter(file).close();
                                    String file1 = Environment.getExternalStorageDirectory() + "/contacts.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new LocContacts(loccontacs,locphones));
                                    oos.close();


                                } else {
                                    loccontacs = new ArrayList<String>();
                                    for (int p = 0; p < list.size(); p++) {
                                        loccontacs.add(list.get(p).toString());
                                    }
                                    System.out.println("FILE CREATING2");
                                    String file1 = Environment.getExternalStorageDirectory() + "/contacts.tmp";
                                    FileOutputStream fos = new FileOutputStream(file1);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(new LocContacts(loccontacs,phones));
                                    oos.close();

                                }
                            } catch (Exception e) {
                            }


                            final CharSequence[] dialogList = list.toArray(new CharSequence[list.size()]);
                            final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline.this);
                            builderDialog.setTitle("Select Contact");
                            int count = dialogList.length;
                            boolean[] is_checked = new boolean[count];

                            // Creating multiple selection by using setMutliChoiceItem method
                            builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton, boolean isChecked) {
                                        }
                                    });

                            builderDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ListView list = ((AlertDialog) dialog).getListView();
                                            tempmems = new ArrayList<String>();
                                            // make selected item in the comma seprated string
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for (int i = 0; i < list.getCount(); i++) {
                                                boolean checked = list.isItemChecked(i);

                                                if (checked) {
                                                    tempmems.add(list.getItemAtPosition(i).toString());
                                                    if (stringBuilder.length() > 0)
                                                        stringBuilder.append(",");
                                                    stringBuilder.append(list.getItemAtPosition(i));
                                                }
                                            }

                                            if (stringBuilder.toString().trim().equals("")) {
                                                Toast.makeText(Timeline.this, "No contact selected", Toast.LENGTH_SHORT).show();

                                            } else {
                                                txt.setTarget("indi");
                                                targetmems = new ArrayList(tempmems);
                                                txt.setTargetmems(targetmems);
                                                puttext();
                                                Toast.makeText(Timeline.this, "Contacts selected", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            builderDialog.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(Timeline.this, "No contact selected", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = builderDialog.create();
                            alert.show();

                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                }
            }else
            {
                if (tar.equals("GROUP")) {

                } else if (tar.equals("INDIVIDUAL")) {

                    final ArrayList<String>tmobs=new ArrayList<>();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Timeline.this,"No network.. Going Offline",Toast.LENGTH_LONG).show();


                    final LocContacts imgobj;
                    String filei = Environment.getExternalStorageDirectory() + "/contacts.tmp";
                    System.out.println("FILE READING");
                    FileInputStream fisi = null;
                    try {
                        fisi = new FileInputStream(filei);
                        ObjectInputStream ois = new ObjectInputStream(fisi);
                        imgobj = (LocContacts) ois.readObject();
                        ois.close();
                        names = imgobj.contacts;
                        mobs=imgobj.phones;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final List<CharSequence> list = new ArrayList<CharSequence>();

                    for(int i=0;i<names.size();i++)
                    {
                        list.add(names.get(i));
                    }
                    final CharSequence[] dialogList = list.toArray(new CharSequence[list.size()]);
                    final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline.this);
                    builderDialog.setTitle("Select Contact");
                    int count = dialogList.length;
                    boolean[] is_checked = new boolean[count];

                    // Creating multiple selection by using setMutliChoiceItem method
                    builderDialog.setMultiChoiceItems(dialogList, is_checked,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton, boolean isChecked) {
                                }
                            });

                    builderDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ListView list = ((AlertDialog) dialog).getListView();
                                    tempmems = new ArrayList<String>();
                                    // make selected item in the comma seprated string
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int i = 0; i < list.getCount(); i++) {
                                        boolean checked = list.isItemChecked(i);

                                        if (checked) {
                                            tempmems.add(list.getItemAtPosition(i).toString());
                                            tmobs.add(mobs.get(i));
                                            if (stringBuilder.length() > 0)
                                                stringBuilder.append(",");
                                            stringBuilder.append(list.getItemAtPosition(i));
                                        }
                                    }

                                    if (stringBuilder.toString().trim().equals("")) {
                                        Toast.makeText(Timeline.this, "No contact selected", Toast.LENGTH_SHORT).show();

                                    } else {
                                      //  txt.setTarget("indi");
                                        targetmems = new ArrayList(tempmems);
                                      //  txt.setTargetmems(targetmems);
                                     //   puttext();
                                        sendSms(txt,targetmems,tmobs);
                                        Toast.makeText(Timeline.this, "Contacts selected", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    builderDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Timeline.this, "No contact selected", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alert = builderDialog.create();
                    alert.show();
                        }
                    });
                }
            }





//                        Intent i = new Intent(Timeline.this, Timeline.class);
//                        i.putExtra("bool1", false);
//                        startActivity(i);
//                        finish();


            //  Toast.makeText(Timeline.this,"Text Shared", Toast.LENGTH_LONG).show();


            return "SUCCESS";
        }


        // Timeline.this runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");

            }


            //    progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        public void sendSms(TextDesc txt, ArrayList<String>users, ArrayList<String>mobiles)
        {
                for(int i=0;i<users.size();i++)
                {
                    System.out.println("Sending sms to "+users.get(i)+" "+mobiles.get(i));
                    String smstext="#envi"+",user:"+txt.user+",maicat:"+txt.maincat+",subcat:"+txt.subcat+",date:"+txt.date+",desc:"+txt.desc+",content:"+txt.text;
                    String target="+91"+mobiles.get(i);

                    Intent intent = new Intent(getApplicationContext(), Timeline.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(target,"9999",smstext,pi,null);


                }
        }
    }
    private class MyTaski extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //     progressDialog = ProgressDialog.show(Timeline.this, "Message", "Uploading Image...");

        }

        // Timeline.this is run in a background thread
        @Override
        protected String doInBackground(String... params) {




            img = new ImageDesc();

            img.setUser(CurrentUser.user);

            img.setDesc(idesc);
            img.setMaincat(maincat);
            img.setSubcat(subcat);
            img.setResolvers(" ");
            img.setResdate(" ");


            img.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
            if (tar.equals("GROUP")) {
                img.setTarget("all");
                img.setTargetmems(new ArrayList<String>());
                putimage();
            } else if (tar.equals("INDIVIDUAL")) {
                final List<CharSequence> list=new ArrayList<CharSequence>();
                System.out.println("Inside indi");
                //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values
                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                            String uname = cred_update.getUsn();
                            if(!uname.equals(CurrentUser.user))
                            {
                                list.add(uname);
                            }
                        }
                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline.this);
                        builderDialog.setTitle("Select Contact");
                        int count = dialogList.length;
                        boolean[] is_checked = new boolean[count];

                        // Creating multiple selection by using setMutliChoiceItem method
                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton, boolean isChecked) {
                                    }
                                });

                        builderDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ListView list = ((AlertDialog) dialog).getListView();
                                        tempmems=new ArrayList<String>();
                                        // make selected item in the comma seprated string
                                        StringBuilder stringBuilder = new StringBuilder();
                                        for (int i = 0; i < list.getCount(); i++) {
                                            boolean checked = list.isItemChecked(i);

                                            if (checked) {
                                                tempmems.add(list.getItemAtPosition(i).toString());
                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                                stringBuilder.append(list.getItemAtPosition(i));
                                            }
                                        }

                                        if (stringBuilder.toString().trim().equals("")) {
                                            Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();

                                        } else {
                                            img.setTarget("indi");
                                            ArrayList targetmems = new ArrayList(tempmems);
                                            img.setTargetmems(targetmems);
                                            putimage();
                                            Toast.makeText(Timeline.this,"Contacts selected",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        builderDialog.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog alert = builderDialog.create();
                        alert.show();

                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }




            return "SUCCESS";
        }


        // Timeline.this runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");

            }
            //    progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }

        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = Timeline.this.getContentResolver().query(uri, null, null, null, null);
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
            return result;
        }
    }

    private class MyTaska extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //     progressDialog = ProgressDialog.show(Timeline.this, "Message", "Uploading Image...");

        }

        // Timeline.this is run in a background thread
        @Override
        protected String doInBackground(String... params) {

            aud = new Audiodesc();
            aud.setUser(CurrentUser.user);

            aud.setDesc(adesc);
            aud.setMaincat(maincat);
            aud.setSubcat(subcat);
            aud.setResolvers(" ");
            aud.setResdate(" ");



            aud.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
            if (aud.getDesc().isEmpty()) {
                Toast.makeText(Timeline.this, "Enter the Description", Toast.LENGTH_SHORT).show();

            }


            if (tar.equals("GROUP")) {
                aud.setTarget("all");
                aud.setTargetmems(new ArrayList<String>());
                putaudio();
            } else if (tar.equals("INDIVIDUAL")) {
                final List<CharSequence> list=new ArrayList<CharSequence>();
                System.out.println("Inside indi");
                //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values
                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                            String uname = cred_update.getUsn();
                            if(!uname.equals(CurrentUser.user))
                            {
                                list.add(uname);
                            }
                        }
                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline.this);
                        builderDialog.setTitle("Select Contact");
                        int count = dialogList.length;
                        boolean[] is_checked = new boolean[count];

                        // Creating multiple selection by using setMutliChoiceItem method
                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton, boolean isChecked) {
                                    }
                                });

                        builderDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ListView list = ((AlertDialog) dialog).getListView();
                                        tempmems=new ArrayList<String>();
                                        // make selected item in the comma seprated string
                                        StringBuilder stringBuilder = new StringBuilder();
                                        for (int i = 0; i < list.getCount(); i++) {
                                            boolean checked = list.isItemChecked(i);

                                            if (checked) {
                                                tempmems.add(list.getItemAtPosition(i).toString());
                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                                stringBuilder.append(list.getItemAtPosition(i));
                                            }
                                        }

                                        if (stringBuilder.toString().trim().equals("")) {
                                            Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();

                                        } else {
                                            aud.setTarget("indi");
                                            ArrayList targetmems = new ArrayList(tempmems);
                                            aud.setTargetmems(targetmems);
                                            putaudio();
                                            Toast.makeText(Timeline.this,"Contacts selected",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        builderDialog.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog alert = builderDialog.create();
                        alert.show();

                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }


            return "SUCCESS";
        }


        // Timeline.this runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS"))
                System.out.println("SUCCESS");


            //    progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }

        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = Timeline.this.getContentResolver().query(uri, null, null, null, null);
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
            return result;
        }
    }

    private class MyTaskv extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //     progressDialog = ProgressDialog.show(Timeline.this, "Message", "Uploading Image...");

        }

        // Timeline.this is run in a background thread
        @Override
        protected String doInBackground(String... params) {




            vid = new VideoDesc();
            vid.setUser(CurrentUser.user);

            vid.setDesc(vdesc);
            vid.setMaincat(maincat);
            vid.setSubcat(subcat);
            vid.setResolvers(" ");
            vid.setResdate(" ");


            vid.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));

            if (tar.equals("GROUP")) {
                vid.setTarget("all");
                vid.setTargetmems(new ArrayList<String>());
                putvideo();
            } else if (tar.equals("INDIVIDUAL")) {
                final List<CharSequence> list=new ArrayList<CharSequence>();
                System.out.println("Inside indi");
                //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values
                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                            String uname = cred_update.getUsn();
                            if(!uname.equals(CurrentUser.user))
                            {
                                list.add(uname);
                            }
                        }
                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline.this);
                        builderDialog.setTitle("Select Contact");
                        int count = dialogList.length;
                        boolean[] is_checked = new boolean[count];

                        // Creating multiple selection by using setMutliChoiceItem method
                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton, boolean isChecked) {
                                    }
                                });

                        builderDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ListView list = ((AlertDialog) dialog).getListView();
                                        tempmems=new ArrayList<String>();
                                        // make selected item in the comma seprated string
                                        StringBuilder stringBuilder = new StringBuilder();
                                        for (int i = 0; i < list.getCount(); i++) {
                                            boolean checked = list.isItemChecked(i);

                                            if (checked) {
                                                tempmems.add(list.getItemAtPosition(i).toString());
                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
                                                stringBuilder.append(list.getItemAtPosition(i));
                                            }
                                        }

                                        if (stringBuilder.toString().trim().equals("")) {
                                            Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();

                                        } else {
                                            vid.setTarget("indi");
                                            ArrayList targetmems = new ArrayList(tempmems);
                                            vid.setTargetmems(targetmems);
                                            putvideo();
                                            Toast.makeText(Timeline.this,"Contacts selected",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        builderDialog.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog alert = builderDialog.create();
                        alert.show();

                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }



            return "SUCCESS";
        }


        // Timeline.this runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");

            }

            //    progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }

        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = Timeline.this.getContentResolver().query(uri, null, null, null, null);
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
            return result;
        }
    }
    private class MyTaskf extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(Files_PU.Timeline.this, "Message", "Uploading File...");

        }

        // Timeline.this is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            try {
                fil = new FileDesc();
                fil.setUser(CurrentUser.user);

                fil.setDesc(fdesc);
                fil.setMaincat(maincat);
                fil.setSubcat(subcat);
                fil.setResdate(" ");
                fil.setResolvers(" ");
                System.out.println("Mangatha dawww");


                System.out.println("FDESC2" + fil.getDesc());

                fil.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));

                if (tar.equals("GROUP")) {
                    fil.setTarget("all");
                    fil.setTargetmems(new ArrayList<String>());
                    putfile();
                } else if (tar.equals("INDIVIDUAL")) {
                    final List<CharSequence> list=new ArrayList<CharSequence>();
                    System.out.println("Inside indi");
                    //i've added sample names to the list at the top
//                    View openDialog = (View) findViewById(R.id.openDialog);
//                    openDialog.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {

//                            // Intialize  readable sequence of char values
                    fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
                    fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
                                String uname = cred_update.getUsn();
                                if(!uname.equals(CurrentUser.user))
                                {
                                    list.add(uname);
                                }
                            }
                            final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                            final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline.this);
                            builderDialog.setTitle("Select Contact");
                            int count = dialogList.length;
                            boolean[] is_checked = new boolean[count];

                            // Creating multiple selection by using setMutliChoiceItem method
                            builderDialog.setMultiChoiceItems(dialogList, is_checked,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton, boolean isChecked) {
                                        }
                                    });

                            builderDialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ListView list = ((AlertDialog) dialog).getListView();
                                            tempmems=new ArrayList<String>();
                                            // make selected item in the comma seprated string
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for (int i = 0; i < list.getCount(); i++) {
                                                boolean checked = list.isItemChecked(i);

                                                if (checked) {
                                                    tempmems.add(list.getItemAtPosition(i).toString());
                                                    if (stringBuilder.length() > 0) stringBuilder.append(",");
                                                    stringBuilder.append(list.getItemAtPosition(i));
                                                }
                                            }

                                            if (stringBuilder.toString().trim().equals("")) {
                                                Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();

                                            } else {
                                                fil.setTarget("indi");
                                                ArrayList targetmems = new ArrayList(tempmems);
                                                fil.setTargetmems(targetmems);
                                                putfile();
                                                Toast.makeText(Timeline.this,"Contacts selected",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            builderDialog.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(Timeline.this,"No contact selected",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            AlertDialog alert = builderDialog.create();
                            alert.show();

                        }


                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });


                }






                return "SUCCESS";

            } catch (Exception e) {
//                Toast.makeText(Timeline.this, "Network Busy!!", Toast.LENGTH_LONG).show();
                //System.out.println("Error SMS "+e);

            }

            return "FAILED";
        }



        // Timeline.this runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("SUCCESS")){
                System.out.println("SUCCESS");

            }


            //   progressDialog.dismiss();
            // Do things like hide the progress bar or change a TextView
        }
        public String getFileName(Uri uri) {
            String result = null;
            if (uri.getScheme().equals("content")) {
                Cursor cursor = Timeline.this.getContentResolver().query(uri, null, null, null, null);
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
            return result;
        }
    }

public void putimage()
{
    if(imglati.equals("0.0")|| imglong.equals("0.0")) {
        if (gps.canGetLocation()) {

            imglati = String.valueOf(gps.getLatitude());
            imglong = String.valueOf(gps.getLongitude());



        }
        else {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(Timeline.this, "Image location not tagged!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    runOnUiThread(new Runnable() {
        public void run() {
            progressDialogi = new ProgressDialog(Timeline.this);
            progressDialogi.setTitle("Message");
            progressDialogi.setMessage("Uploading Image...");

            progressDialogi.setCancelable(true);
            progressDialogi.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            progressDialogi.show();
        }
    });

    final String imagenode = CurrentUser.user + img.getDate();
    System.out.println("  imagenode  " + imagenode);


    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode);

    System.out.println("Storage refference : " + storageReference);

    final NotificationManager notificationManager = (NotificationManager) Timeline.this.getSystemService(NOTIFICATION_SERVICE);
    final Notification.Builder notificationBuilder = new Notification.Builder(Timeline.this);
    notificationBuilder.setOngoing(true)
            .setContentTitle("Uploading Image")
            .setContentText("Progressed (" + (int) progress + " %)")
            .setProgress(100, (int) progress, false)

            .setSmallIcon(R.drawable.soul_logo);
    notification = notificationBuilder.build();
    notificationManager.notify(100, notification);

    StorageMetadata metadata = new StorageMetadata.Builder()
            .setContentType("image/jpeg")
            .setCustomMetadata("latitude",imglati )
            .setCustomMetadata("longitude", imglong)
            .setCustomMetadata("Progress","1")
            .setCustomMetadata("Remedy","")
            .setCustomMetadata("Tags",tagmeta)
            .setCustomMetadata("title",img.getDesc())
            .build();


    UploadTask up = storageReference.putFile(selectedImageUri,metadata);
    up.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
            progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            System.out.println("Progress is " + progress);
            notificationBuilder.setProgress(100, (int) progress, false).setContentText("Progressed (" + (int) progress + "%...)");
            notification = notificationBuilder.build();
            notificationManager.notify(100, notification);

        }
    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

        }
    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            System.out.println("IMG VAL "+img.getResolvers());
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode).setValue(img);
            img2 = new ImageDesc2();

            img2.date = img.getDate();
            img2.desc = img.getDesc();
            img2.maincat = img.getMaincat();
            img2.user = img.getUser();
            img2.subcat = img.getSubcat();
            img2.latitude = imglati;
            img2.longitude=imglong;
            img2.setType("Image");
            img2.path ="Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Images/"+imagenode+"/";

            fb_db.child("Locations").child(imagenode).setValue(img2);

            fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int pt = Integer.parseInt(dataSnapshot.getValue().toString());
                    System.out.println("Upload point is"+pt);
                    fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).setValue(pt+100);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }});

            notificationBuilder.setProgress(100, (int) progress, false).setContentText("Progressed (" + (int) progress + "%)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
            notification = notificationBuilder.build();
            notificationManager.notify(100, notification);
           // Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();


            runOnUiThread(new Runnable() {
                public void run() {
                    progressDialogi.dismiss();
                    Toast.makeText(Timeline.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                }
            });
                                    Intent i2 = new Intent(Timeline.this, Timeline.class);
            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            i2.putExtra("noti", "null");
              finish();
                                    startActivity(i2);


        }
    });

}
    public void putaudio()
    {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialoga = new ProgressDialog(Timeline.this);
                progressDialoga.setTitle("Message");
                progressDialoga.setMessage("Uploding Audio...");

                progressDialoga.setCancelable(true);
                progressDialoga.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialoga.show();
            }
        });
        final String audnode = CurrentUser.user + aud.getDate();
        System.out.println("  audionode  " + audnode);
        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(audnode);

        System.out.println("Storage refference : " + storageReference2);

        final NotificationManager notificationManager2 = (NotificationManager) Timeline.this.getSystemService(NOTIFICATION_SERVICE);
        final Notification.Builder notificationBuilder2 = new Notification.Builder(Timeline.this);
        notificationBuilder2.setOngoing(true)
                .setContentTitle("Uploading Audio")
                .setContentText("Progressed (" + (int) progress2 + " %)")
                .setProgress(100, (int) progress2, false)
                .setSmallIcon(R.drawable.soul_logo);
        notification2 = notificationBuilder2.build();
        notificationManager2.notify(101, notification2);

        UploadTask up1 = storageReference2.putFile(audioUri);
        up1.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progress2 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Progress is " + progress2);
                notificationBuilder2.setProgress(100, (int) progress2, false).setContentText("Progressed (" + (int) progress2 + "%...)");
                notification2 = notificationBuilder2.build();
                notificationManager2.notify(101, notification2);


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(audnode).setValue(aud);
                notificationBuilder2.setProgress(100, (int) progress2, false).setContentText("Progressed (" + (int) progress2 + "%)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                notification2 = notificationBuilder2.build();
                notificationManager2.notify(101, notification2);
                fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            int pt = Integer.parseInt(dataSnapshot.getValue().toString());
                            System.out.println("Upload point is" + pt);
                            fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).setValue(pt + 100);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }});


               runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialoga.dismiss();
                        Toast.makeText(Timeline.this, "Audio Uploaded", Toast.LENGTH_LONG).show();
                    }
                });
                                    Intent i3 = new Intent(Timeline.this, Timeline.class);
                i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                i3.putExtra("noti", "null");
                    finish();
                                    startActivity(i3);


            }
        });
    }
    public void putvideo()
    {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialogv = new ProgressDialog(Timeline.this);
                progressDialogv.setTitle("Message");
                progressDialogv.setMessage("Uploading Video...");

                progressDialogv.setCancelable(true);
                progressDialogv.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialogv.show();progressDialogv = ProgressDialog.show(Timeline.this, "Message", "Uploading Video...");
            }
        });

        final String vidnode = CurrentUser.user + vid.getDate();
        System.out.println("  videonode  " + vidnode);
        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(vidnode);

        System.out.println("Storage refference : " + storageReference3);

        final NotificationManager notificationManager3 = (NotificationManager) Timeline.this.getSystemService(NOTIFICATION_SERVICE);
        final Notification.Builder notificationBuilder3 = new Notification.Builder(Timeline.this);
        notificationBuilder3.setOngoing(true)
                .setContentTitle("Uploading Video")
                .setContentText("Progressed (" + (int) progress3 + " %)")
                .setProgress(100, (int) progress3, false)
                .setSmallIcon(R.drawable.soul_logo);
        notification3 = notificationBuilder3.build();
        notificationManager3.notify(102, notification3);

        UploadTask up2 = storageReference3.putFile(VideoUri);
        up2.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                progress3 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Progress is " + progress3);
                notificationBuilder3.setProgress(100, (int) progress3, false).setContentText("Progressed (" + (int) progress3 + "%...)");
                notification3 = notificationBuilder3.build();
                notificationManager3.notify(102, notification3);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(vidnode).setValue(vid);

                fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int pt = Integer.parseInt(dataSnapshot.getValue().toString());
                        System.out.println("Upload point is"+pt);
                        fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).setValue(pt+100);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }});

                progress3 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Progress is " + progress3);
                notificationBuilder3.setProgress(100, (int) progress3, false).setContentText("Progressed (" + (int) progress3 + "%...)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                notification3 = notificationBuilder3.build();
                notificationManager3.notify(102, notification3);


             runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialogv.dismiss();
                        Toast.makeText(Timeline.this, "Video Uploaded", Toast.LENGTH_LONG).show();
                    }
                });
                                    Intent i4 = new Intent(Timeline.this, Timeline.class);
                i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                i4.putExtra("noti", "null");
                      finish();
                                    startActivity(i4);


            }
        });
    }
    public void putfile()
    {

       runOnUiThread(new Runnable() {
            public void run() {
                progressDialogf = new ProgressDialog(Timeline.this);
                progressDialogf.setTitle("Message");
                progressDialogf.setMessage("Uploading File...");

                progressDialogf.setCancelable(true);
                progressDialogf.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialogf.show();
            }
        });

        final String filenode = CurrentUser.user + fil.getDate();
        System.out.println("  filenode  " + filenode);
        StorageReference storageReference4 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(filenode);

        System.out.println("Storage refference : " + storageReference4);

        final NotificationManager notificationManager4 = (NotificationManager) Timeline.this.getSystemService(NOTIFICATION_SERVICE);
        final Notification.Builder notificationBuilder4 = new Notification.Builder(Timeline.this);
        notificationBuilder4.setOngoing(true)
                .setContentTitle("Uploading File")
                .setContentText("Progressed (" + (int) progress4 + " %)")
                .setProgress(100, (int) progress4, false)
                .setSmallIcon(R.drawable.soul_logo);
        notification4 = notificationBuilder4.build();
        notificationManager4.notify(104, notification4);

        UploadTask up3 = storageReference4.putFile(fileUri);
        up3.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                progress4 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Progress is " + progress4);
                notificationBuilder4.setProgress(100, (int) progress4, false).setContentText("Progressed (" + (int) progress4 + "%...)");
                notification4 = notificationBuilder4.build();
                notificationManager4.notify(104, notification4);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(filenode).setValue(fil);

                fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int pt = Integer.parseInt(dataSnapshot.getValue().toString());
                        System.out.println("Upload point is"+pt);
                        fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).setValue(pt+100);

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }});

                notificationBuilder4.setProgress(100, (int) progress4, false).setContentText("Progressed (" + (int) progress4 + "%)").setOngoing(false).setDefaults(Notification.DEFAULT_ALL);
                notification4 = notificationBuilder4.build();
                notificationManager4.notify(104, notification4);


               runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialogf.dismiss();
                        Toast.makeText(Timeline.this, "File Uploaded", Toast.LENGTH_LONG).show();
                    }
                });
                                    Intent i5 = new Intent(Timeline.this, Timeline.class);
                i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                i5.putExtra("noti", "null");
                       finish();
                                    startActivity(i5);



            }
        });


    }
    public void puttext()
    {

        runOnUiThread(new Runnable() {
            public void run() {
                progressDialogt = new ProgressDialog(Timeline.this);
                progressDialogt.setTitle("Message");
                progressDialogt.setMessage("Uploading Text...");

                progressDialogt.setCancelable(true);
                progressDialogt.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                progressDialogt.show();            }
        });
        fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Texts").child(CurrentUser.user+txt.getDate()).setValue(txt);


        fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int pt = Integer.parseInt(dataSnapshot.getValue().toString());
                System.out.println("Upload point is"+pt);
                fb_db.child("Leaderboards").child(CurrentUser.sclass).child("Users").child(CurrentUser.user).setValue(pt+100);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }});

       runOnUiThread(new Runnable() {
            public void run() {
                progressDialogt.dismiss();
                Toast.makeText(Timeline.this, "Text Uploaded", Toast.LENGTH_LONG).show();
            }
        });
        Intent i1 = new Intent(Timeline.this, Timeline.class);

        i1.putExtra("noti", "null");
          finish();
        startActivity(i1);

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
    private class MyTaskinoti extends AsyncTask<String, Integer, String> {

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
            //  Accounts doc=new Accounts(date,fullname,image);
            Cred_Update cred_update = new Cred_Update();
            cred_update.setUsn(CurrentUser.user);
            cred_update.setPass(CurrentUser.pass);
            cred_update.setCls(CurrentUser.sclass);
            cred_update.setSec(CurrentUser.ssec);
            cred_update.setType(CurrentUser.type);
            cred_update.setAdate(CurrentUser.sadate);
            cred_update.setVdate(CurrentUser.svdate);
            cred_update.setFdate(CurrentUser.sfdate);
            cred_update.setTdate(CurrentUser.stdate);
            cred_update.setMobile(CurrentUser.mobile);
            cred_update.setIdate(date);
            CurrentUser.sidate=date;

            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


            return "SUCCESS";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTaskanoti extends AsyncTask<String, Integer, String> {

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
            //  Accounts doc=new Accounts(date,fullname,image);
            Cred_Update cred_update = new Cred_Update();
            cred_update.setUsn(CurrentUser.user);
            cred_update.setPass(CurrentUser.pass);
            cred_update.setCls(CurrentUser.sclass);
            cred_update.setSec(CurrentUser.ssec);
            cred_update.setType(CurrentUser.type);
            cred_update.setIdate(CurrentUser.sidate);
            cred_update.setVdate(CurrentUser.svdate);
            cred_update.setFdate(CurrentUser.sfdate);
            cred_update.setTdate(CurrentUser.stdate);
            cred_update.setMobile(CurrentUser.mobile);

            cred_update.setAdate(date);
            CurrentUser.sadate=date;

            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


            return "SUCCESS";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTaskvnoti extends AsyncTask<String, Integer, String> {

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
            //  Accounts doc=new Accounts(date,fullname,image);
            Cred_Update cred_update = new Cred_Update();
            cred_update.setUsn(CurrentUser.user);
            cred_update.setPass(CurrentUser.pass);
            cred_update.setCls(CurrentUser.sclass);
            cred_update.setSec(CurrentUser.ssec);
            cred_update.setType(CurrentUser.type);
            cred_update.setIdate(CurrentUser.sidate);
            cred_update.setFdate(CurrentUser.sfdate);
            cred_update.setAdate(CurrentUser.sadate);
            cred_update.setTdate(CurrentUser.stdate);
            cred_update.setMobile(CurrentUser.mobile);

            cred_update.setVdate(date);
            CurrentUser.svdate=date;

            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);



            return "SUCCESS";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }

    private class MyTaskfnoti extends AsyncTask<String, Integer, String> {

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
            //  Accounts doc=new Accounts(date,fullname,image);
            Cred_Update cred_update = new Cred_Update();
            cred_update.setUsn(CurrentUser.user);
            cred_update.setPass(CurrentUser.pass);
            cred_update.setCls(CurrentUser.sclass);
            cred_update.setSec(CurrentUser.ssec);
            cred_update.setType(CurrentUser.type);
            cred_update.setIdate(CurrentUser.sidate);
            cred_update.setVdate(CurrentUser.svdate);
            cred_update.setAdate(CurrentUser.sadate);
            cred_update.setTdate(CurrentUser.stdate);
            cred_update.setMobile(CurrentUser.mobile);

            cred_update.setFdate(date);
            CurrentUser.sfdate=date;

            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);



            return "SUCCESS";
        }



        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class MyTasktnoti extends AsyncTask<String, Integer, String> {

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
            //  Accounts doc=new Accounts(date,fullname,image);
            Cred_Update cred_update = new Cred_Update();
            cred_update.setUsn(CurrentUser.user);
            cred_update.setPass(CurrentUser.pass);
            cred_update.setCls(CurrentUser.sclass);
            cred_update.setSec(CurrentUser.ssec);
            cred_update.setType(CurrentUser.type);
            cred_update.setIdate(CurrentUser.sidate);
            cred_update.setVdate(CurrentUser.svdate);
            cred_update.setAdate(CurrentUser.sadate);
            cred_update.setFdate(CurrentUser.sfdate);
            cred_update.setMobile(CurrentUser.mobile);

            cred_update.setTdate(date);
            CurrentUser.stdate = date;

            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    String ReadExif(String file){
        String exif="Exif: " + file;
        try {
            ExifInterface exifInterface = new ExifInterface(file);

            exif += "\nIMAGE_LENGTH: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            exif += "\nIMAGE_WIDTH: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            exif += "\n DATETIME: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            exif += "\n TAG_MAKE: " + exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            exif += "\n TAG_MODEL: " + exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            exif += "\n TAG_ORIENTATION: " + exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            exif += "\n TAG_WHITE_BALANCE: " + exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            exif += "\n TAG_FOCAL_LENGTH: " + exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            exif += "\n TAG_FLASH: " + exifInterface.getAttribute(ExifInterface.TAG_FLASH);
            exif += "\nGPS related:";
            exif += "\n TAG_GPS_DATESTAMP: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
            exif += "\n TAG_GPS_TIMESTAMP: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            exif += "\n TAG_GPS_LATITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            exif += "\n TAG_GPS_LATITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            exif += "\n TAG_GPS_LONGITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            exif += "\n TAG_GPS_LONGITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            exif += "\n TAG_GPS_PROCESSING_METHOD: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);

//            Toast.makeText(Timeline.this,
//                    exif,
//                    Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(Timeline.this,
                    e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        return exif;
    }
    public void checkVision(String image)
    {

        mystring=image;
        JSON = MediaType.parse("application/json; charset=utf-8");



//        OkHttpClient.Builder b = new OkHttpClient.Builder();
//        b.readTimeout(30, TimeUnit.SECONDS);
//        b.writeTimeout(30, TimeUnit.SECONDS);
//
//        client=b.build();


        client = new OkHttpClient.Builder()
               .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .build();

        System.out.println("strting vision");
        new MyTaskVision().execute();

    }
    private class MyTaskVision extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params)
        {
            String json ="{\"requests\":[{\"image\":{\"content\":\""+mystring+"\"},\"features\":[{\"type\":\"SAFE_SEARCH_DETECTION\"},{\"type\":\"LANDMARK_DETECTION\"},{\"type\":\"LOGO_DETECTION\"},{\"type\":\"LABEL_DETECTION\"}]}]}";
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyADtvO9_3vK3TkkHW5imY4t25AXaE8Gkis")
                    .post(body)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Vision failed "+e);

            }
            try {
                runOnUiThread(new Runnable() {
                    public void run() {
                        uploadimg.setClickable(true);
                    }
                });
//                JSONObject jobj = new JSONObject(response.body().string());
//                System.out.println("adult con"+jobj.getString("adult"));
                String tmp  = response.body().string();
                System.out.println("Final response: "+tmp);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dot.setVisibility(View.GONE);
                    }
                });
                JSONObject jsonObj = new JSONObject(tmp);
                JSONObject jsonObj2 = new JSONObject(jsonObj.getJSONArray("responses").getString(0));
                String safe = jsonObj2.getString("safeSearchAnnotation");
                JSONArray labarray=jsonObj2.getJSONArray("labelAnnotations");
            //    JSONArray logoarray=jsonObj2.getJSONArray("logoAnnotations");

                System.out.println("Imp res:"+safe);
                int index=0;
                String labels;
                imgtags=new ArrayList<>();
try {
    while ((labels = labarray.getString(index)) != null) {
        String val = new JSONObject(labels).getString("description");
        imgtags.add(val);
        tagmeta = tagmeta + val + ",";
        System.out.println("Imp res2:" + val);
        index++;
    }
}catch(Exception e){}


            JSONObject sobj=new JSONObject(safe);
                String adult;

                adult = sobj.getString("adult");

                System.out.println("axecalls "+adult);

                if(adult.contains("VERY_UNLIKELY")||adult.contains("UNLIKELY"))
                {
                    System.out.println("$$$ U R NOT UPLOADING A ADULT CONTENT IMAGE ");
                }else if(adult.contains("POSSIBLE")||adult.contains("LIKELY")||adult.contains("VERY_LIKELY"))
                {

                    System.out.println("$$$ U R UPLOADING A ADULT CONTENT IMAGE ");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            uploadimg.setClickable(false);
                            Toast.makeText(Timeline.this,"Explicit Adult Content Detected!!",Toast.LENGTH_LONG).show();
                        }
                    });

                }

                String spoof = sobj.getString("spoof");

                System.out.println("axecalls "+spoof);

                if(spoof.contains("VERY_UNLIKELY")||spoof.contains("UNLIKELY"))
                {
                    System.out.println("$$$ U R NOT UPLOADING A SPOOF CONTENT IMAGE ");
                }else if(spoof.contains("POSSIBLE")||adult.contains("LIKELY")||spoof.contains("VERY_LIKELY"))
                {
                    System.out.println("$$$ U R UPLOADING A SPOOF CONTENT IMAGE ");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            uploadimg.setClickable(false);
                            Toast.makeText(Timeline.this,"Explicit Spoof Content Detected!!",Toast.LENGTH_LONG).show();
                        }
                    });


                }

                String medical = sobj.getString("medical");

                System.out.println("axecalls "+medical);

                if(medical.contains("VERY_UNLIKELY")||medical.contains("UNLIKELY"))
                {
                    System.out.println("$$$ U R NOT UPLOADING A MEDICAL CONTENT IMAGE ");
                }else if(medical.contains("POSSIBLE")||medical.contains("LIKELY")||medical.contains("VERY_LIKELY"))
                {
                    System.out.println("$$$ U R UPLOADING A MEDICAL CONTENT IMAGE ");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            uploadimg.setClickable(false);
                            Toast.makeText(Timeline.this,"Explicit Medical Content Detected!!",Toast.LENGTH_LONG).show();
                        }
                    });

                }

                String violence = sobj.getString("violence");

                System.out.println("axecalls "+violence);

                if(violence.contains("VERY_UNLIKELY")||violence.contains("UNLIKELY"))
                {
                    System.out.println("$$$ U R NOT UPLOADING A VIOLENCE CONTENT IMAGE ");
                }else if(violence.contains("POSSIBLE")||violence.contains("LIKELY")||violence.contains("VERY_LIKELY"))
                {
                    System.out.println("$$$ U R UPLOADING A VIOLENCE CONTENT IMAGE ");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            uploadimg.setClickable(false);
                            Toast.makeText(Timeline.this,"Explicit Violent Content Detected!!",Toast.LENGTH_LONG).show();
                        }
                    });


                }





            } catch (Exception e) {
                System.out.println("Exceptionn 1 is"+e);
                e.printStackTrace();
            }





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
