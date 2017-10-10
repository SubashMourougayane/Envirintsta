package bluepanther.envirinsta.Reports;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Repo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nihaskalam.progressbuttonlibrary.CircularProgressButton;
import com.nihaskalam.progressbuttonlibrary.OnAnimationUpdateTimeListener;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.Contacts.Contacts;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.Timeline.Timeline;

import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.Travel.Travel;
import bluepanther.envirinsta.fragment.DateRangePickerFragment;
import bluepanther.envirinsta.Adapter.CustomAdapterGR;
import bluepanther.envirinsta.Adapter.CustomAdapterIR;

/**
 * Created by shyam on 19/1/17.
 */
/*
public class Reports_new extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener,OnMenuItemClickListener, OnMenuItemLongClickListener {


    Boolean flagz;
    List<RowItem> grpcontent;
    List<RowItem> percontent;
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
    String maincat="",subcat="";
    EditText from_date, to_date;
    List<RowItem> rowItems;
    ListView mylistview2;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    int flag;
    String file1;

    public Uri filuri;
    public Boolean go=true,go2=true,filtered1=false,filtered2=false;

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



String query="";

    String day1, day2, month1, month2, year1, year2, sfrom="", sto="";

Date sfromd,stod;
    ListView mylistview;
    SearchView searchView;
    FloatingActionButton download;
String cat="Select Category";
    String cat2="Select Sub-Category";
    private TabLayout tabLayout2;
    private ViewPager viewPager2;
    final int[] tabIcons = {
            R.drawable.ic_action_group_sv,
            R.drawable.ic_action_indi_sv
    };

    LinearLayout viewB;
    private CircularProgressButton circularButton2;
    private Button setProgressBtn;
    private int progress = 0;
    private TextView percentageTV, progressAmountTV;
    public static final int sweepDuration = 5000;
    private static final String MANUAL_PROGRESS_AMOUNT_KEY = "manualProgressAmount";
    private static final String FIXED_PROGRESS_PERCENTAGE_KEY = "fixedTimeProgressPercentage";
    private static final String CONFIGURATION_CHANGE_KEY = "configurationChange";
    private int fixedTimeProgressPercentage = 0;
    private boolean hasConfigurarationChanged = false;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private static final String[] MAIN_CATEGORY = {
            "Select Category", "Academics", "Administration", "Extra-Curriculars", "Miscellaneous"
    };

    AutoCompleteTextView text;
    TextView textView,textView2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_new);
        Preferences.getPrefs(getApplicationContext());
        RepContent.grpcontent=new ArrayList<>();
        RepContent.percontent=new ArrayList<>(  );
   // searchView = (SearchView) findViewById(R.id.searchView);
        //download = (FloatingActionButton) v.findViewById(R.id.download);
    //    searchView.setQueryHint("Search View");

        //viewB = (LinearLayout) findViewById(R.id.viewB);
      //  viewB.setVisibility(View.GONE);
//        textView = (TextView) findViewById(R.id.from_date);
//        textView2 = (TextView) findViewById(R.id.to_date);
//
//        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
//
//
//        spinner.setItems(MAIN_CATEGORY);

        Firebase.setAndroidContext(this);
        fb_db = new Firebase(Base_url);

        //FloatingActionButton down2=download;



//        FloatingActionButton fab2 = (FloatingActionButton)
//                findViewById(R.id.fab2);
//
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DateRangePickerFragment dateRangePickerFragment=
//                        DateRangePickerFragment.newInstance(Reports_new.this,false);
//
//                dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");
//
//            }
//        });



        mylistview = (ListView) findViewById(R.id.listView);

//        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
//
//            }
//        });
//        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
//
//            @Override
//            public void onNothingSelected(MaterialSpinner spinner) {
//                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(Reports_new.this, query,
//                        Toast.LENGTH_LONG).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(Reports_new.this, newText,
//                        Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

//        down2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

//        circularButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);
//        circularButton2.setIndeterminateProgressMode(false);
//        circularButton2.setStrokeColor(ContextCompat.getColor(Reports_new.this, R.color.colorStroke));
//        final int factor = sweepDuration / 100;
//        circularButton2.setSweepDuration(sweepDuration);
//        circularButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                query=searchView.getQuery().toString();
////F                cat=text.getText().toString();
//
//                circularButton2.setOnAnimationUpdateTimeListener(new OnAnimationUpdateTimeListener() {
//                    @Override
//                    public void onAnimationTimeUpdate(int timeElapsed) {
//                        fixedTimeProgressPercentage = hasConfigurarationChanged ? fixedTimeProgressPercentage + timeElapsed / factor : timeElapsed / factor;
//                        hasConfigurarationChanged = false;
//                    }
//                });
//                if (circularButton2.isIdle()) {
//                    circularButton2.showProgress();
//                } else if (circularButton2.isErrorOrCompleteOrCancelled()) {
//                    circularButton2.showIdle();
//                    viewB.setVisibility(View.VISIBLE);
//                } else if (circularButton2.isProgress()) {
//                    circularButton2.showCancel();
//                } else {
//                    circularButton2.showComplete();
//                   // circularButton2.performClick();
//
//                }
//                     if(sfrom.length()>0&&sto.length()>0) {
//                    new MyTask().execute();
//                }
//                else
//                {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Reports_new.this);
//                    builder.setMessage("Please enter a valid date range")
//                            .setCancelable(false)
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//            }
//        });

        viewPager2 = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager2(viewPager2);

        tabLayout2 = (TabLayout) findViewById(R.id.tabs);
        tabLayout2.setupWithViewPager(viewPager2);
        setupTabIcons2();



        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
//        addFragment(new Timeline(), true, R.id.container);
        rowItems = new ArrayList<RowItem>();
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

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
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

        MenuObject addFav = new MenuObject("Contacts");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("Logout");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    private void initToolbar() {
//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
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

//        mToolBarTextView.setText("Soul");
//        mToolBarTextView.setTextColor(Color.WHITE);

    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
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
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
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
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");
            finish();
            startActivity(i);
        } else if (position == 2) {
            finish();
            startActivity(new Intent(this, Reports_new.class));
        } else if (position == 3) {
            finish();
            startActivity(new Intent(this, Profile.class));
        } else if (position == 4) {
            finish();
            startActivity(new Intent(this, Contacts.class));
        } else if (position == 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Reports_new.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Reports_new.this,
                                            Sign_In.class));
                                    Reports_new.this.finish();
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
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    private void changeFragment(final Fragment targetFragment) {

//        new Handler() {
//        }.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.container, targetFragment, "fragment")
//                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .commit();
//            }
//        }, 600);
    }
    private void setupViewPager2(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("In notif class tabs");
        adapter.addFragment(new Repgroup_tab(), "Group");
        adapter.addFragment(new Repindi_tab(), "Personal");

        viewPager.setAdapter(adapter);
        System.out.println("Tabset");
    }
    private void setupTabIcons2() {
        tabLayout2.getTabAt(0).setIcon(null);
        tabLayout2.getTabAt(1).setIcon(null);

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
    public void onDateRangeSelected(int startDay, int startMonth, int
            startYear, int endDay, int endMonth, int endYear) throws ParseException {
        Log.d("range : ","from:"+startDay+"-"+startMonth+"-"+startYear+" to :"+endDay+"-"+endMonth+"-"+endYear );

        String s1 = (startDay+"-"+(startMonth+1)+"-"+startYear).toString().trim();
        String s2 = (endDay+"-"+(endMonth+1)+"-"+endYear).toString().trim();
        textView.setText(s1);
        textView2.setText(s2);
        //  filter_object.mName =
        (startDay+"-"+startMonth+"-"+startYear).toString().trim();

        //  filter_object.mIsSelected = false;
        //   mArrFilter.add(filter_object);

//        mScrollViewFilter = (ScrollView)findViewById(R.id.scrollViewFilter);
//        mFlowLayoutFilter = (FlowLayout)findViewById(R.id.flowLayout);
//
//        mFilter_Adapter = new Filter_Adapter(mArrFilter);
        day1 = String.valueOf(startDay);
        month1 = String.valueOf(startMonth + 1);
        year1 = String.valueOf(startYear);
        day2 = String.valueOf(endDay);
        month2 = String.valueOf(endMonth + 1);
        year2 = String.valueOf(endYear);
        if (day1.length() == 1) {
            day1 = "0" + day1;
        }
        if (month1.length() == 1) {
            month1 = "0" + month1;
        }
        if (day2.length() == 1) {
            day2 = "0" + day2;
        }
        if (month2.length() == 1) {
            month2 = "0" + month2;
        }
        year1=String.valueOf(year1.charAt(year1.length()-2))+String.valueOf(year1.charAt(year1.length()-1));
        year2=String.valueOf(year2.charAt(year2.length()-2))+String.valueOf(year2.charAt(year2.length()-1));
        sfrom = day1 + "-" + month1 + "-" + year1 + " 00:00:00";
        sto = day2 + "-" + month2 + "-" + year2 + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        sfromd=sdf.parse(sfrom);
        stod=sdf.parse(sto);
        System.out.println("Reports from is"+sfrom+" and "+sto);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgprog = ProgressDialog.show(Reports_new.this, "Message", "Fetching Data...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.picture, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Images");
                                    rowItemsi.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.music, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Audios");
                                    rowItemsa.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.clip, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Videos");
                                    rowItemsv.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.files, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Files");
                                    rowItemsf.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.doc, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Text");
                                    rowItemst.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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


            return "SUCCESS";


        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");


                imgprog.dismiss();
                // Do things like hide the progress bar or change a TextView
            }
        }


        public boolean checkdate(String date) throws ParseException {
            System.out.println("Comparing date:" + date + "with " + sfrom + "and" + sto);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            Date strDate = sdf.parse(date);

            if (sfromd.before(strDate)&&stod.after(strDate)) {
                return true;
            }
//            if (sfrom.compareTo(date) <= 0 && date.compareTo(sto) <= 0) {
//                return true;
//            }
            if(sfrom==null||sto==null)
            {
                return true;
            }
            return false;
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
        Collections.sort(grpcontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
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
        Collections.sort(percontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
    }
}
*/


public class Reports_new extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener,OnMenuItemClickListener, OnMenuItemLongClickListener {
    ArrayList<String> type,title,tcontent, dt,maincateg,subcateg,auth,ttcontent;
    int ppp=0,qqq=0,zzz=0;
    int lol=1;
    int closei=0,closea=0,closev=0,closef=0,closet=0;
    Boolean flagi=true,flaga=true,flagv=true,flagf=true,flagt=true;

    public Document document;
    private static String FILES = Environment.getExternalStorageDirectory()+"/sample.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    int cnt;
    Boolean flagz;
    int close;
    List<RowItem> grpcontent;
    List<RowItem> percontent;
    ProgressDialog imgprog;
    String[] member_names;
    TypedArray profile_pics;
    String[] statues;
    String result;
    Button rep;
    String[] time;
    ImageView imgg;
    ArrayList<String> cntcheck;
    ArrayList<String> name, categ, dates;
    ArrayList<String> content;
    String maincat="",subcat="";
    EditText from_date, to_date;
    List<RowItem> rowItems;
    ListView mylistview2;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    int flag;
    String file1;

    public Uri filuri;
    public Boolean go=true,go2=true,filtered1=false,filtered2=false;
    Spinner category, subcategory, author;

    String tmp1, tmp2, tmp3, tmp4, tmp5;
    String seltab = "Text";
    List<String> listsub = new ArrayList<String>();


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



    String query="";

    String day1, day2, month1, month2, year1, year2, sfrom="", sto="";

    Date sfromd,stod;
    ListView mylistview;
    SearchView searchView;
    FloatingActionButton download;
    String cat="Select Category";
    String cat2="Select Sub-Category";
    private TabLayout tabLayout2;
    private ViewPager viewPager2;
    final int[] tabIcons = {
            R.drawable.ic_action_group_sv,
            R.drawable.ic_action_indi_sv
    };

    LinearLayout viewB;
    private CircularProgressButton circularButton2;
    private Button setProgressBtn;
    private int progress = 0;
    private TextView percentageTV, progressAmountTV;
    public static final int sweepDuration = 5000;
    private static final String MANUAL_PROGRESS_AMOUNT_KEY = "manualProgressAmount";
    private static final String FIXED_PROGRESS_PERCENTAGE_KEY = "fixedTimeProgressPercentage";
    private static final String CONFIGURATION_CHANGE_KEY = "configurationChange";
    private int fixedTimeProgressPercentage = 0;
    private boolean hasConfigurarationChanged = false;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

//    private static final String[] MAIN_CATEGORY = {
//            "Select Category", "Academics", "Administration", "Extra-Curriculars", "Miscellaneous"
//    };
private static final String[] MAIN_CATEGORY = {
        "Select Category", "Roads", "Transport", "Water Lines", "Electricity","Bank/ATM", "Hospital" ,
        "Politics", "Govt Offices", "Sewage" , "General PWD", "Others"
};

    AutoCompleteTextView text;
    TextView textView,textView2;
    String[] categories={"Academics","Administration","Extra-Curriculars","Miscellaneous","LOL1","LOL 2"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_old);

        getSupportActionBar().setTitle("Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Preferences.getPrefs(getApplicationContext());
        RepContent.grpcontent=new ArrayList<>();
        RepContent.percontent=new ArrayList<>(  );
        searchView = (SearchView) findViewById(R.id.searchView);
        //download = (FloatingActionButton) v.findViewById(R.id.download);
        searchView.setQueryHint("Search View");

        listsub.add("Select Sub-Category");
        listsub.add("Sub Type 1");
        listsub.add("Sub Type 2");
        listsub.add("Sub Type 3");
        listsub.add("Sub Type 4");
        listsub.add("Sub Type 5");
        listsub.add("Sub Type 6");
        listsub.add("Sub Type 7");

        viewB = (LinearLayout) findViewById(R.id.viewB);
        //  viewB.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.from_date);
        textView2 = (TextView) findViewById(R.id.to_date);
        rep=(Button)findViewById(R.id.rep);
        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2);

        spinner.setItems(MAIN_CATEGORY);

        Firebase.setAndroidContext(this);
        fb_db = new Firebase(Base_url);

        //FloatingActionButton down2=download;
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getfiles();
                generatePdf();
            }
        });


        FloatingActionButton fab2 = (FloatingActionButton)
                findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerFragment dateRangePickerFragment=
                        DateRangePickerFragment.newInstance(Reports_new.this,false);

                dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");

            }
        });


        ArrayAdapter adapter = new
                ArrayAdapter(Reports_new.this,android.R.layout.simple_list_item_1,categories);


        mylistview = (ListView) findViewById(R.id.listView);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                if(!item.equals("Select Category"))
                {
                    maincat=item;
                    filtered1=true;
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
                    filtered2=true;
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Reports_new.this, query,
                        Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(Reports_new.this, newText,
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

//        down2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        circularButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);
        circularButton2.setIndeterminateProgressMode(false);
        circularButton2.setStrokeColor(ContextCompat.getColor(Reports_new.this, R.color.colorStroke));
        final int factor = sweepDuration / 100;
        circularButton2.setSweepDuration(sweepDuration);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                query=searchView.getQuery().toString();
//F                cat=text.getText().toString();

                circularButton2.setOnAnimationUpdateTimeListener(new OnAnimationUpdateTimeListener() {
                    @Override
                    public void onAnimationTimeUpdate(int timeElapsed) {
                        fixedTimeProgressPercentage = hasConfigurarationChanged ? fixedTimeProgressPercentage + timeElapsed / factor : timeElapsed / factor;
                        hasConfigurarationChanged = false;
                    }
                });
                if (circularButton2.isIdle()) {
                    circularButton2.showProgress();
                } else if (circularButton2.isErrorOrCompleteOrCancelled()) {
                    circularButton2.showIdle();
                    viewB.setVisibility(View.VISIBLE);
                } else if (circularButton2.isProgress()) {
                    circularButton2.showCancel();
                } else {
                    circularButton2.showComplete();
                    // circularButton2.performClick();

                }
                if(sfrom.length()>0&&sto.length()>0) {
                    new MyTask().execute();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Reports_new.this);
                    builder.setMessage("Please enter a valid date range")
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

        viewPager2 = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager2(viewPager2);

        tabLayout2 = (TabLayout) findViewById(R.id.tabs);
        tabLayout2.setupWithViewPager(viewPager2);
        setupTabIcons2();



        fragmentManager = getSupportFragmentManager();
     //   initToolbar();
        initMenuFragment();
//        addFragment(new HomeFrag(), true, R.id.container);
        rowItems = new ArrayList<RowItem>();
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

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setAnimationDuration(30);
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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



    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
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
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
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
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(Reports_new.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(Reports_new.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(Reports_new.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(Reports_new.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(Reports_new.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(Reports_new.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(Reports_new.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(Reports_new.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Reports_new.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Reports_new.this,
                                            AA_home.class));
                                    Reports_new.this.finish();
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
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }


    private void setupViewPager2(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("In notif class tabs");
        adapter.addFragment(new Repgroup_tab(), "Group");
        adapter.addFragment(new Repindi_tab(), "Personal");

        viewPager.setAdapter(adapter);
        System.out.println("Tabset");
    }
    private void setupTabIcons2() {
        tabLayout2.getTabAt(0).setIcon(null);
        tabLayout2.getTabAt(1).setIcon(null);

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
    public void onDateRangeSelected(int startDay, int startMonth, int
            startYear, int endDay, int endMonth, int endYear) throws ParseException {
        Log.d("range : ","from:"+startDay+"-"+startMonth+"-"+startYear+" to :"+endDay+"-"+endMonth+"-"+endYear );

        String s1 = (startDay+"-"+(startMonth+1)+"-"+startYear).toString().trim();
        String s2 = (endDay+"-"+(endMonth+1)+"-"+endYear).toString().trim();
        textView.setText(s1);
        textView2.setText(s2);
        //  filter_object.mName =
        (startDay+"-"+startMonth+"-"+startYear).toString().trim();

        //  filter_object.mIsSelected = false;
        //   mArrFilter.add(filter_object);

//        mScrollViewFilter = (ScrollView)findViewById(R.id.scrollViewFilter);
//        mFlowLayoutFilter = (FlowLayout)findViewById(R.id.flowLayout);
//
//        mFilter_Adapter = new Filter_Adapter(mArrFilter);
        day1 = String.valueOf(startDay);
        month1 = String.valueOf(startMonth + 1);
        year1 = String.valueOf(startYear);
        day2 = String.valueOf(endDay);
        month2 = String.valueOf(endMonth + 1);
        year2 = String.valueOf(endYear);
        if (day1.length() == 1) {
            day1 = "0" + day1;
        }
        if (month1.length() == 1) {
            month1 = "0" + month1;
        }
        if (day2.length() == 1) {
            day2 = "0" + day2;
        }
        if (month2.length() == 1) {
            month2 = "0" + month2;
        }
        year1=String.valueOf(year1.charAt(year1.length()-2))+String.valueOf(year1.charAt(year1.length()-1));
        year2=String.valueOf(year2.charAt(year2.length()-2))+String.valueOf(year2.charAt(year2.length()-1));
        sfrom = day1 + "-" + month1 + "-" + year1 + " 00:00:00";
        sto = day2 + "-" + month2 + "-" + year2 + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        sfromd=sdf.parse(sfrom);
        stod=sdf.parse(sto);
        System.out.println("Reports from is"+sfrom+" and "+sto);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgprog = ProgressDialog.show(Reports_new.this, "Message", "Fetching Data...");

        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.picture, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Images");
                                    rowItemsi.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.music, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Audios");
                                    rowItemsa.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.clip, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Videos");
                                    rowItemsv.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.files, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Files");
                                    rowItemsf.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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
                    go = true;
                    try {
                        if (checkdate(date)) {
                            if (filtered1) {
                                if (images.maincat.equals(maincat)) {
                                    go = true;
                                    if (filtered2) {
                                        if (images.subcat.equals(subcat)) {
                                            go = true;
                                        } else {
                                            go = false;
                                        }
                                    }
                                } else {
                                    go = false;
                                }
                            }
                            if (query.length() > 0) {
                                if (query.contains(desc) || desc.contains(query)) {
                                    go2 = true;
                                } else {
                                    go2 = false;
                                }
                            }


                            if (go && go2) {
                                if (images.target.equals("all")) {

                                    RowItem item = new RowItem(images.desc,
                                            R.drawable.doc, images.maincat + " : " + images.subcat,
                                            images.date, images.user,"Text");
                                    rowItemst.add(item);
                                    getfiles();
                                    RepContent.grpcontent = new ArrayList<RowItem>(grpcontent);
                                    CustomAdapterGR adapter = new CustomAdapterGR(Reports_new.this, grpcontent);
                                    Repgroup_tab.setContent(adapter);

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
                                        getfiles();
                                        RepContent.percontent = new ArrayList<RowItem>(percontent);
                                        CustomAdapterIR adapter = new CustomAdapterIR(Reports_new.this, percontent);
                                        Repindi_tab.setContent(adapter);
                                    }
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


            return "SUCCESS";


        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("SUCCESS")) {
                System.out.println("SUCCESS");


                imgprog.dismiss();
                // Do things like hide the progress bar or change a TextView
            }
        }


        public boolean checkdate(String date) throws ParseException {
            System.out.println("Comparing date:" + date + "with " + sfrom + "and" + sto);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            Date strDate = sdf.parse(date);

            if (sfromd.before(strDate)&&stod.after(strDate)) {
                return true;
            }
//            if (sfrom.compareTo(date) <= 0 && date.compareTo(sto) <= 0) {
//                return true;
//            }
            if(sfrom==null||sto==null)
            {
                return true;
            }
            return false;
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
        Collections.sort(grpcontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
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
        Collections.sort(percontent, new Comparator<RowItem>() {
            @Override
            public int compare(RowItem lhs, RowItem rhs) {
                return rhs.getTime().compareTo(lhs.getTime());
            }
        });
    }
    public void generatePdf()
    {
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILES));
            document.open();

            ppp=0;qqq=0;zzz=0;
            lol=1;
            closei=0;closea=0;closev=0;closef=0;closet=0;
            flagi=true;flaga=true;flagv=true;flagf=true;flagt=true;
            System.out.println("ENtire group is"+grpcontent);
            type=new ArrayList<>();
            title=new ArrayList<>();
            tcontent=new ArrayList<>();
            dt=new ArrayList<>();
            maincateg=new ArrayList<>();
            subcateg=new ArrayList<>();
            auth=new ArrayList<>();
            ttcontent=new ArrayList<>();
            addContent();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  void addContent() throws DocumentException, IOException {

        for(int i=0;i<grpcontent.size();i++) {

            if(grpcontent.get(i).getType().equals("Images"))
            {
                closei++;
                flagi=false;
            }
            if(grpcontent.get(i).getType().equals("Audios"))
            {
                closea++;
                flaga=false;

            }
            if(grpcontent.get(i).getType().equals("Videos"))
            {
                closev++;
                flagv=false;

            }
            if(grpcontent.get(i).getType().equals("Files"))
            {
                closef++;
                flagf=false;

            }
            if(grpcontent.get(i).getType().equals("Text"))
            {
                closet++;
                flagt=false;

            }
        }
        for(int i=0;i<grpcontent.size();i++) {
            cnt=i;
            switch(grpcontent.get(i).getType())
            {

                case "Images":
                    // itype="Image";
                    type.add("Image");
                    title.add(grpcontent.get(cnt).getMember_name());
                    String catt=grpcontent.get(cnt).getStatus();
                    String[] words=catt.split(":");
                    maincateg.add(words[0].trim());
                    subcateg.add(words[1].trim());
                    dt.add(grpcontent.get(cnt).getTime());
                    auth.add(grpcontent.get(cnt).getAuthor());
                    tcontent.add("");
                    final String res = grpcontent.get(i).getAuthor() + grpcontent.get(i).getTime();
                    System.out.println("Downloadingz" + res);

                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(res);

                    File folder = new File(Environment.getExternalStorageDirectory() + "/Soul/PdfImages");
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdirs();
                    }
                    if (success) {

                        final String file1 = Environment.getExternalStorageDirectory() + "/Soul/PdfImages/temp"+qqq+".jpg";
                        qqq++;
                        File files = new File(file1);
                        storageReference.getFile(files).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                                Toast.makeText(Reports_new.this,"Downloaded img"+lol,Toast.LENGTH_LONG).show();
                                lol++;
                                closei--;
                                if(closei==0)
                                {
                                    flagi=true;
                                    System.out.println("Image downloaded ended with"+flagi+" "+flaga+" "+flagv+" "+flagf+" "+flagt);

                                    if(flagi&&flaga&&flagv&&flagf&&flagt)
                                    {
                                        try {
                                            Toast.makeText(Reports_new.this,"Invoked frm img",Toast.LENGTH_LONG).show();
                                            System.out.println("pdff1");
                                            doPdf();
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                System.out.println("sad" + exception);
                            }
                        });
                    }



                    break;
                case "Audios":

                    type.add("Audio");
                    title.add(grpcontent.get(cnt).getMember_name());
                    String catt2=grpcontent.get(cnt).getStatus();
                    String[] words2=catt2.split(":");
                    maincateg.add(words2[0].trim());
                    subcateg.add(words2[1].trim());
                    dt.add(grpcontent.get(cnt).getTime());
                    auth.add(grpcontent.get(cnt).getAuthor());
                    tcontent.add("");



                    closea--;
                    if(closea==0)
                    {
                        flaga=true;
                        if(flagi&&flaga&&flagv&&flagf&&flagt)
                        {
                            Toast.makeText(Reports_new.this,"Invoked frm aud",Toast.LENGTH_LONG).show();
                            System.out.println("pdff2");
                            doPdf();
                        }
                    }
                    break;
                case "Videos":

                    type.add("Video");
                    title.add(grpcontent.get(cnt).getMember_name());
                    String catt3=grpcontent.get(cnt).getStatus();
                    String[] words3=catt3.split(":");
                    maincateg.add(words3[0].trim());
                    subcateg.add(words3[1].trim());
                    dt.add(grpcontent.get(cnt).getTime());
                    auth.add(grpcontent.get(cnt).getAuthor());
                    tcontent.add("");


                    closev--;
                    if(closev==0)
                    {
                        flagv=true;
                        if(flagi&&flaga&&flagv&&flagf&&flagt)
                        {
                            Toast.makeText(Reports_new.this,"Invoked frm vid",Toast.LENGTH_LONG).show();
                            System.out.println("pdff3");
                            doPdf();
                        }
                    }
                    break;
                case "Files":

                    type.add("File");
                    title.add(grpcontent.get(cnt).getMember_name());
                    String catt4=grpcontent.get(cnt).getStatus();
                    String[] words4=catt4.split(":");
                    maincateg.add(words4[0].trim());
                    subcateg.add(words4[1].trim());
                    dt.add(grpcontent.get(cnt).getTime());
                    auth.add(grpcontent.get(cnt).getAuthor());
                    tcontent.add("");

                    closef--;
                    if(closef==0)
                    {
                        flagf=true;
                        if(flagi&&flaga&&flagv&&flagf&&flagt)
                        {
                            Toast.makeText(Reports_new.this,"Invoked frm fil",Toast.LENGTH_LONG).show();
                            System.out.println("pdff4");
                            doPdf();
                        }
                    }
                    break;
                case "Text":
                    //       ttype="Text";
                    type.add("Text");
                    title.add(grpcontent.get(cnt).getMember_name());
                    String catt5=grpcontent.get(cnt).getStatus();
                    String[] words5=catt5.split(":");
                    maincateg.add(words5[0].trim());
                    subcateg.add(words5[1].trim());
                    dt.add(grpcontent.get(cnt).getTime());
                    auth.add(grpcontent.get(cnt).getAuthor());

                    final String res2 = grpcontent.get(i).getAuthor() + grpcontent.get(i).getTime();
                    String tmp5 = Base_url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts/" + res2 + "/";
                    System.out.println("ZZlol"+tmp5);
                    fb_db = new Firebase(tmp5);
                    fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TextDesc obj = dataSnapshot.getValue(TextDesc.class);
                            result = obj.text;
                            System.out.println("Text val is "+result);


                            ttcontent.add(result);
                            closet--;
                            if(closet==0)
                            {
                                flagt=true;
                                if(flagi&&flaga&&flagv&&flagf&&flagt)
                                {
                                    try {
                                        Toast.makeText(Reports_new.this,"Invoked frm txt",Toast.LENGTH_LONG).show();
                                        System.out.println("pdff5");
                                        doPdf();
                                    } catch (DocumentException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }

                    });

                    break;
            }




        }



    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    public void doPdf() throws DocumentException, IOException {
        Image img=null;
        for(int i=0;i<type.size();i++) {

            Anchor anchor = new Anchor("Content Type: " + type.get(i), catFont);
            anchor.setName("Content Type" + type.get(i));

            // Second parameter is the number of the chapter
            Chapter catPart = new Chapter(new Paragraph(anchor), 1);

            Paragraph subPara;
            Section subCatPart;
//        subCatPart.add(new Paragraph("Hello"));

            subPara = new Paragraph("Title: " + title.get(i), subFont);
            subCatPart = catPart.addSection(subPara);

            // add a list
            //createList(subCatPart);

            com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
            list.add(new ListItem("Main Category: " + maincateg.get(i)));
            list.add(new ListItem("Sub Category: " + subcateg.get(i)));
            list.add(new ListItem("Date & Time: " + dt.get(i)));
            list.add(new ListItem("Author: " + auth.get(i)));
            subCatPart.add(list);

            if(type.get(i).equals("Text")) {
                subCatPart.add(new Paragraph("Content:"+ttcontent.get(ppp++)));

            }
            else if(type.get(i).equals("Image")) {
                String file1 = Environment.getExternalStorageDirectory() + "/Soul/PdfImages/temp"+zzz+".jpg";
                zzz++;
                try {
                    img = Image.getInstance(file1);
                    System.out.println("Image prop is"+img.getInitialRotation()+"and"+img.getImageRotation()+"and"+ img.isInverted());
                    img.setAlignment(Image.MIDDLE);
                    img.scaleAbsolute(500,540);

                } catch (BadElementException e) {
                    System.out.println("Failed 1 with"+e);
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Failed 1 with"+e);

                    e.printStackTrace();
                }
                subCatPart.add(new Paragraph("Content:"));

                subCatPart.add(img);
            }
            else
            {
                subCatPart.add(new Paragraph("Content:"));

            }
            Paragraph paragraph = new Paragraph();
            addEmptyLine(paragraph, 4);
            subCatPart.add(paragraph);

            // add a table
            // createTable(subCatPart);

            // now add all this to the document
            document.add(catPart);
        }
        document.close();

    }
}