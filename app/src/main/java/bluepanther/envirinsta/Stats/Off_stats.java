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
import android.os.Bundle;
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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import bluepanther.envirinsta.Adapter.CustomAdapterCon;
import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.LeaderBoards.Off_leaderboards;
import bluepanther.envirinsta.Officials.Off_Home;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Reports.Reports_new_off;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Timeline.Timeline;

public class Off_stats extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    RecyclerView mylistview;
    public  static View.OnClickListener myOnClickListener;
    public static CustomAdapterCon adapter;
    static List<RowItem> shareList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_official);
        Preferences.getPrefs(getApplicationContext());
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

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 2),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);
      //  new MyTask().execute();
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
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

        MenuObject timeline = new MenuObject("Timeline");
        timeline.setResource(R.drawable.icn_1);

        MenuObject reports = new MenuObject("Reports");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        reports.setBitmap(b);

        MenuObject lb = new MenuObject("Leaderboards");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_4));
        lb.setDrawable(bd);

        MenuObject stats = new MenuObject("Stats");
        stats.setResource(R.drawable.icn_3);

        MenuObject logout = new MenuObject("Logout");
        logout.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(timeline);
        menuObjects.add(reports);
        menuObjects.add(lb);
        menuObjects.add(stats);
        menuObjects.add(logout);
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
            Intent i=new Intent(this,Off_Home.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(this, Reports_new_off.class));
        } else if (position == 3) {

            startActivity(new Intent(this, Off_leaderboards.class));
        } else if (position == 4) {

            startActivity(new Intent(this, Off_stats.class));
        } else if (position == 5) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Off_stats.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Off_stats.this,
                                            Sign_In.class));
                                    Off_stats.this.finish();
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
  }
