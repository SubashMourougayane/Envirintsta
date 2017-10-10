/*
 * Copyright 2016 L4 Digital LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bluepanther.envirinsta.Stats;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterCon;
import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.LeaderBoards.Leaderboards_tab1;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Timeline.Timeline;

public class User_stats extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {
    Firebase fb_db;
    String Base_Url = "https://envirinsta.firebaseio.com/";
    Long pieroad=0L;
    Long pietrans=0L;
    Long piewater=0L;
    Long pieelec=0L;
    Long piebank=0L;
    Long piehosp=0L;
    Long piepolitics=0L;
    Long piegovt=0L;
    Long piesewage=0L;
    Long piegpwd=0L;
    Long pieothers=0L;


    Long audcount=0L;
    Long vidcount=0L;
    Long imgcount=0L;
    Long filescount=0L;
    Long txtcount=0L;
    Long totcount=0L;
    Long posted=0L,pending=0L,resolved=0L;

    Long audres=0L,vidres=0L,imgres=0L,filesres=0L,txtres=0L;

    Long audpend=0L,vidpend=0L,imgpend=0L,filespend=0L,txtpend=0L;

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    RecyclerView mylistview;
    public static View.OnClickListener myOnClickListener;
    public static CustomAdapterCon adapter;
    static List<RowItem> shareList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    PieChart pieChart;
    HorizontalBarChart barChart;
    RadarChart radarChart;
    BarChart chart;

    ArrayList<Integer> roadstime = new ArrayList<>();
    ArrayList<Integer> transtime = new ArrayList<>();
    ArrayList<Integer> watertime = new ArrayList<>();
    ArrayList<Integer> electime = new ArrayList<>();
    ArrayList<Integer> banktime = new ArrayList<>();
    ArrayList<Integer> hosptime = new ArrayList<>();
    ArrayList<Integer> politicstime = new ArrayList<>();
    ArrayList<Integer> govttime = new ArrayList<>();
    ArrayList<Integer> gpwdtime = new ArrayList<>();
    ArrayList<Integer> otherstime = new ArrayList<>();
    ArrayList<Integer> sewagetime = new ArrayList<>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_user);
        Preferences.getPrefs(getApplicationContext());

        fb_db = new Firebase(Base_Url);
        Firebase.setAndroidContext(this);

        new MyTaska().execute();
        new MyTaskf().execute();
        new MyTaski().execute();
        new MyTaskt().execute();
        new MyTaskv().execute();

        getSupportActionBar().setTitle("Statistics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mylistview = (RecyclerView) findViewById(R.id.myListView);
//        mylistview.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(Off_stats.this);
//        mylistview.setLayoutManager(layoutManager);
//        mylistview.setItemAnimator(new DefaultItemAnimator());
//       // adapter = new CustomAdapterCon(Off_stats.this, shareList);
//
//       // mylistview.setAdapter(adapter);
//        myOnClickListener = new Off_stats.MyOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        chart = (BarChart) findViewById(R.id.chart);



        pieChart = (PieChart) findViewById(R.id.chart3);
        barChart = (HorizontalBarChart) findViewById(R.id.chart2);
        radarChart = (RadarChart) findViewById(R.id.chart4);

        pieChart.setUsePercentValues(true);


        radarchart();
        horizontalchart();
//        piedata();


        //  new MyTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void barchart() {
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.isDrawBarShadowEnabled();
        chart.invalidate();
    }


    private void radarchart() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(5f, 1));
        entries.add(new Entry(2f, 2));
        entries.add(new Entry(7f, 3));
        entries.add(new Entry(6f, 4));
        entries.add(new Entry(5f, 5));

        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "Average Resolved per week");

        dataset_comp1.setColor(Color.CYAN);
        dataset_comp1.setDrawFilled(true);

        ArrayList<RadarDataSet> dataSets = new ArrayList<RadarDataSet>();
        dataSets.add(dataset_comp1);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Issues Posted");
        labels.add("Issues Resolved");
        labels.add("Time Taken");
        labels.add("Legitimate Issues");
        labels.add("Fake Issues");
        labels.add("Commercial Remedies");


        RadarData data = new RadarData(labels, dataSets);
        radarChart.setData(data);
        radarChart.setWebLineWidthInner(0.8f);
        radarChart.setDescription("");
        //chart.setSkipWebLineCount(10);
        radarChart.invalidate();
        radarChart.animate();

    }

    private void horizontalchart() {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Issue 1");
        labels.add("Issue 2");
        labels.add("Issue 3");
        labels.add("Issue 4");
        labels.add("Issue 5");

        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(20, 0);
        valueSet2.add(v1e1);
        BarEntry v1e2 = new BarEntry(23, 1);
        valueSet2.add(v1e2);
        BarEntry v1e3 = new BarEntry(55, 2);
        valueSet2.add(v1e3);
        BarEntry v1e4 = new BarEntry(62, 3);
        valueSet2.add(v1e4);
        BarEntry v1e5 = new BarEntry(10, 4);
        valueSet2.add(v1e5);


        BarDataSet bardataset = new BarDataSet(valueSet2, "Days in which issues have been solved");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setMinimumWidth(2);


    }

    private void piedata() {
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        if(!(pieroad.equals(0L)))
        {
            yvalues.add(new Entry(pieroad, 0));

        }
        if(!(pietrans.equals(0L)))
        {
            yvalues.add(new Entry(pietrans, 1));

        }
        if(!(piewater.equals(0L)))
        {
            yvalues.add(new Entry(piewater, 2));

        }
        if(!(pieelec.equals(0L)))
        {
            yvalues.add(new Entry(pieroad, 3));

        }
        if(!(piebank.equals(0L)))
        {
            yvalues.add(new Entry(piebank, 4));

        }
        if(!(piehosp.equals(0L)))
        {
            yvalues.add(new Entry(piehosp, 5));

        }
        if(!(piepolitics.equals(0L)))
        {
            yvalues.add(new Entry(piepolitics, 6));

        }
        if(!(piegovt.equals(0L)))
        {
            yvalues.add(new Entry(piegovt, 7));

        }
        if(!(piesewage.equals(0L)))
        {
            yvalues.add(new Entry(piesewage, 8));

        }
        if(!(piegpwd.equals(0L)))
        {
            yvalues.add(new Entry(piegpwd, 9));

        }
        if(!(pieothers.equals(0L)))
        {
            yvalues.add(new Entry(pieothers, 10));

        }

//        yvalues.add(new Entry(pieroad, 0));
//        yvalues.add(new Entry(pietrans, 1));
//        yvalues.add(new Entry(piewater, 2));
//        yvalues.add(new Entry(pieelec, 3));
//        yvalues.add(new Entry(piebank, 4));
//        yvalues.add(new Entry(piehosp, 5));
//        yvalues.add(new Entry(piepolitics, 6));
//        yvalues.add(new Entry(piegovt, 7));
//        yvalues.add(new Entry(piesewage, 8));
//        yvalues.add(new Entry(piegpwd, 9));
//        yvalues.add(new Entry(pieothers, 10));

        PieDataSet dataSet = new PieDataSet(yvalues, "Issues Category");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Road");
        xVals.add("Transport");
        xVals.add("Water Lines");
        xVals.add("Electricity");
        xVals.add("Bank/ATM");
        xVals.add("Hospitals");
        xVals.add("Politics");
        xVals.add("Govt Offices");
        xVals.add("Sewage");
        xVals.add("General PWD");
        xVals.add("Others");


        PieData dataSet2 = new PieData(xVals, dataSet);
        dataSet.setValueFormatter(new PercentFormatter());
        pieChart.setData(dataSet2);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieChart.setDescription("Issues Category");
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        Long f1 = posted;
        Long f2 = pending;
        System.out.println("LOL is "+f1);
        BarEntry v1e1 = new BarEntry(f1, 0); // posted
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(f2, 1); // pending
        valueSet1.add(v1e2);
        Long f3 = f1 - f2;
        BarEntry v1e3 = new BarEntry(f3, 2); // resolved
        valueSet1.add(v1e3);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Issues");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        // dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<BarDataSet> getDataSet2() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(20, 0); // roads
        valueSet2.add(v1e1);
        BarEntry v1e2 = new BarEntry(23, 1); // Transport
        valueSet2.add(v1e2);
        BarEntry v1e3 = new BarEntry(55, 2); // Water Lines
        valueSet2.add(v1e3);
        BarEntry v1e4 = new BarEntry(62, 3); // Electricity
        valueSet2.add(v1e4);
        BarEntry v1e5 = new BarEntry(10, 4); // Bank/ATM
        valueSet2.add(v1e5);
        BarEntry v1e6 = new BarEntry(45, 5); // Hospitals
        valueSet2.add(v1e6);
        BarEntry v1e7 = new BarEntry(37, 6); // Politics
        valueSet2.add(v1e7);
        BarEntry v1e8 = new BarEntry(82, 7); // Govt Offices
        valueSet2.add(v1e8);
        BarEntry v1e9 = new BarEntry(15, 8); // Sewage
        valueSet2.add(v1e9);
        BarEntry v1e10 = new BarEntry(27, 9); // General PWD
        valueSet2.add(v1e10);
        BarEntry v1e11 = new BarEntry(7, 10); // Others
        valueSet2.add(v1e11);


        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Issues");
        barDataSet2.setColor(Color.rgb(0, 155, 0));
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);
        // dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Posted");
        xAxis.add("Pending");
        xAxis.add("Resolved");

        return xAxis;
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
//
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
            Intent i = new Intent(this, Timeline.class);
            i.putExtra("noti", "null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(User_stats.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(User_stats.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(User_stats.this, NgoAct.class));
        } else if (position == 5) {

            startActivity(new Intent(User_stats.this, User_leaderboards.class));
        } else if (position == 6) {

            startActivity(new Intent(User_stats.this, User_stats.class));
        } else if (position == 7) {

            startActivity(new Intent(User_stats.this, Contacts2.class));
        } else if (position == 8) {

            AlertDialog.Builder builder = new AlertDialog.Builder(User_stats.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog", false);
                                    editor.commit();
                                    startActivity(new Intent(User_stats.this,
                                            Sign_In.class));
                                    User_stats.this.finish();
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

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;
        ProgressDialog progressDialog;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {


        }

    }

    private class MyTaska extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            String Base_Url1 = Base_Url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Audios";
            System.out.println("URL AUD IS " + Base_Url1);
            Firebase fb_db1 = new Firebase(Base_Url1);
            fb_db1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    audcount = dataSnapshot.getChildrenCount();
                    System.out.println("AUD COUNT " + audcount);
                    totcount = totcount+audcount;
                    System.out.println("TOT COUNT " + totcount);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {

                        Audiodesc audiodesc = postSnapshot.getValue(Audiodesc.class);
                        System.out.println("Looking1"+postSnapshot.getKey());

                        System.out.println("Looking"+audiodesc.getDesc());

                        switch (audiodesc.getMaincat())
                        {
                            case "Roads":
                                pieroad++;

                                break;
                            case "Transport":
                                pietrans++;
                                break;
                            case "Water Lines":
                                piewater++;
                                break;
                            case "Electricity":
                                pieelec++;
                                break;
                            case "Bank/ATM":
                                piebank++;
                                break;
                            case "Hospital":
                                piehosp++;
                                break;
                            case "Politics":
                                piepolitics++;
                                break;
                            case "Govt Offices":
                                piegovt++;
                                break;
                            case "Sewage":
                                piesewage++;
                                break;
                            case "General PWD":
                                pieroad++;
                                break;
                            case "Others":
                                pietrans++;
                                break;
                        }
                        if(audiodesc.getResolvers().equals(" "))
                        {
                            System.out.println("RESOLVER IS"+audiodesc.getResolvers()+" and "+audpend);
                            ++audpend;


                            System.out.println("AUD PEND IS "+audpend);
                            System.out.println("TOT PEND IS "+pending);

                        }else
                        {
                            audres++;
                            resolved = resolved+audres;
                            System.out.println("AUD RES IS "+audres);
                            System.out.println("TOT RES IS "+resolved);
                        }





                    }


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }


    }
    private class MyTaskf extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            String Base_Url2 = Base_Url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Files";
            System.out.println("URL FILE IS " + Base_Url2);
            Firebase fb_db2 = new Firebase(Base_Url2);
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    filescount = dataSnapshot.getChildrenCount();
                    System.out.println("FILES COUNT " + filescount);
                    totcount = totcount+filescount;
                    System.out.println("TOT COUNT " + totcount);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        FileDesc fileDesc = postSnapshot.getValue(FileDesc.class);

                        switch (fileDesc.getMaincat())
                        {
                            case "Roads":
                                pieroad++;
                                break;
                            case "Transport":
                                pietrans++;
                                break;
                            case "Water Lines":
                                piewater++;
                                break;
                            case "Electricity":
                                pieelec++;
                                break;
                            case "Bank/ATM":
                                piebank++;
                                break;
                            case "Hospital":
                                piehosp++;
                                break;
                            case "Politics":
                                piepolitics++;
                                break;
                            case "Govt Offices":
                                piegovt++;
                                break;
                            case "Sewage":
                                piesewage++;
                                break;
                            case "General PWD":
                                pieroad++;
                                break;
                            case "Others":
                                pietrans++;
                                break;
                        }
                        if(fileDesc.getResolvers().equals(" "))
                        {
                            filespend++;
                            System.out.println("VID PEND IS "+vidpend);
                            System.out.println("TOT PEND IS "+pending);

                        }else
                        {
                            filesres++;
                            resolved = resolved+filesres;
                            System.out.println("VID RES IS "+vidres);
                            System.out.println("TOT RES IS "+resolved);
                        }





                    }


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }


    }
    private class MyTaski extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            String Base_Url3 = Base_Url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Images";
            System.out.println("URL AUD IS " + Base_Url3);
            Firebase fb_db3 = new Firebase(Base_Url3);
            fb_db3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    imgcount = dataSnapshot.getChildrenCount();
                    System.out.println("IMAGE COUNT " + imgcount);
                    totcount = totcount+imgcount;
                    System.out.println("TOT COUNT " + totcount);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        ImageDesc imageDesc= postSnapshot.getValue(ImageDesc.class);
                        String startdate,enddate;
                        switch (imageDesc.getMaincat())
                        {
                            case "Roads":
                                pieroad++;
                                 startdate=imageDesc.getDate();
                                 enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    roadstime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Transport":
                                pietrans++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    transtime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Water Lines":
                                piewater++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    watertime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Electricity":
                                pieelec++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    electime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Bank/ATM":
                                piebank++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    banktime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Hospital":
                                piehosp++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    hosptime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Politics":
                                piepolitics++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    politicstime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Govt Offices":
                                piegovt++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    govttime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Sewage":
                                piesewage++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    sewagetime.add(restime(startdate,enddate));
                                }
                                break;
                            case "General PWD":
                                pieroad++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    gpwdtime.add(restime(startdate,enddate));
                                }
                                break;
                            case "Others":
                                pieothers++;
                                startdate=imageDesc.getDate();
                                enddate = imageDesc.getResdate();
                                if(!enddate.equals(" ")) {
                                    otherstime.add(restime(startdate,enddate));
                                }
                                break;
                        }
                        if(imageDesc.getResolvers().equals(" "))
                        {
                            imgpend++;
                            System.out.println("IMG PEND IS "+imgpend);
                            System.out.println("TOT PEND IS "+pending);
                        }else
                        {
                            imgres++;
                            resolved = resolved+imgres;
                            System.out.println("IMG RES IS "+imgres);
                            System.out.println("TOT RES IS "+resolved);
                        }


                    }

                    int imgAvg = (roadstime.size()+transtime.size()
                            +watertime.size()+electime.size()+banktime.size()
                            +hosptime.size()+politicstime.size()+govttime.size()
                            +sewagetime.size()+gpwdtime.size()+otherstime.size())/11;

                    System.out.println("IMG AVG IS "+imgAvg);


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }


    }
    private class MyTaskt extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            String Base_Url4 = Base_Url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Texts";
            System.out.println("URL TEXT IS " + Base_Url4);
            Firebase fb_db4 = new Firebase(Base_Url4);
            fb_db4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    txtcount = dataSnapshot.getChildrenCount();
                    System.out.println("TEXT COUNT " + txtcount);
                    totcount = totcount+txtcount;
                    System.out.println("TOT COUNT " + totcount);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        TextDesc textDesc = postSnapshot.getValue(TextDesc.class);
                        switch (textDesc.getMaincat())
                        {
                            case "Roads":
                                pieroad++;
                                break;
                            case "Transport":
                                pietrans++;
                                break;
                            case "Water Lines":
                                piewater++;
                                break;
                            case "Electricity":
                                pieelec++;
                                break;
                            case "Bank/ATM":
                                piebank++;
                                break;
                            case "Hospital":
                                piehosp++;
                                break;
                            case "Politics":
                                piepolitics++;
                                break;
                            case "Govt Offices":
                                piegovt++;
                                break;
                            case "Sewage":
                                piesewage++;
                                break;
                            case "General PWD":
                                pieroad++;
                                break;
                            case "Others":
                                pietrans++;
                                break;
                        }
                        if(textDesc.getResolvers().equals(" "))
                        {
                            txtpend++;
                            System.out.println("TXT PEND IS "+txtpend);
                            System.out.println("TOT PEND IS "+pending);
                        }else
                        {
                            txtres++;
                            resolved = resolved+txtres;
                            System.out.println("TXT RES IS "+txtres);
                            System.out.println("TOT RES IS "+resolved);
                        }



                    }


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }


    }
    private class MyTaskv extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            String Base_Url5 = Base_Url + "Classes/" + CurrentUser.sclass + "/" + CurrentUser.ssec + "/Videos";
            System.out.println("URL VID IS " + Base_Url5);
            Firebase fb_db5 = new Firebase(Base_Url5);
            fb_db5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    vidcount = dataSnapshot.getChildrenCount();
                    System.out.println("VID COUNT " + vidcount);
                    totcount = totcount+vidcount;
                    System.out.println("TOT COUNT " + totcount);
                    posted = totcount;
                    System.out.println("posted " + posted);



                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        VideoDesc videoDesc = postSnapshot.getValue(VideoDesc.class);
                        switch (videoDesc.getMaincat())
                        {
                            case "Roads":
                                pieroad++;
                                break;
                            case "Transport":
                                pietrans++;
                                break;
                            case "Water Lines":
                                piewater++;
                                break;
                            case "Electricity":
                                pieelec++;
                                break;
                            case "Bank/ATM":
                                piebank++;
                                break;
                            case "Hospital":
                                piehosp++;
                                break;
                            case "Politics":
                                piepolitics++;
                                break;
                            case "Govt Offices":
                                piegovt++;
                                break;
                            case "Sewage":
                                piesewage++;
                                break;
                            case "General PWD":
                                pieroad++;
                                break;
                            case "Others":
                                pietrans++;
                                break;
                        }
                        if(videoDesc.getResolvers().equals(" "))
                        {
                            vidpend++;

                            System.out.println("VID PEND IS "+vidpend);
                            System.out.println("TOT PEND IS "+pending);

                            System.out.println("PENDING VALUE IS"+pending);
                        }else
                        {
                            vidres++;
                            resolved = resolved+vidres;

                            System.out.println("VID RES IS "+vidres);
                            System.out.println("TOT RES IS "+resolved);
                            System.out.println("RESOLVED VALUE IS"+resolved);

                        }




                    }

                    pending = audpend+imgpend+filespend+txtpend+vidpend;

                    System.out.println("PENDING IS $$$"+pending);
                    barchart();
                    System.out.println("PIE DATA IS "+pieroad);
                    piedata();




                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }


    }
    public int restime(String sd,String ed)
    {
        long days=0;
        try {

            Date date1 = null,date2=null;
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd-MM-yy HH:mm:ss");
            String startdate = sd;
            String enddate = ed;

                date1 = simpleDateFormat.parse(startdate);
                date2 = simpleDateFormat.parse(enddate);
                System.out.println("Date Is " + date1 + " and " + date2);
                long diff = date2.getTime() - date1.getTime();
                long sec = diff / 1000;
                long minutes = sec / 60;
                long hours = minutes / 60;
                days = hours / 24;

                System.out.println("The Diff day is " + days);
        }catch (Exception e)
        {
            System.out.println("Exception dawww"+e);
        }
        return (int)days;
    }
}
