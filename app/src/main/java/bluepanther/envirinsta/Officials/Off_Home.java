package bluepanther.envirinsta.Officials;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import bluepanther.envirinsta.LeaderBoards.Leaderboards_tab1;
import bluepanther.envirinsta.LeaderBoards.Leaderboards_tab2;
import bluepanther.envirinsta.LeaderBoards.Off_leaderboards;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Reports.Reports_new_off;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Stats.Off_stats;
import bluepanther.envirinsta.Timeline.Timeline;

/**
 * Created by Hariharsudan HHS on 21-03-2017.
 */

public class Off_Home extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView mylistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_home);

        mylistview = (RecyclerView) findViewById(R.id.myListView);
        mylistview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        mylistview.setLayoutManager(layoutManager);
        mylistview.setItemAnimator(new DefaultItemAnimator());
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
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(Off_Home.this);
        mMenuDialogFragment.setItemLongClickListener(Off_Home.this);
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

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(Off_Home.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

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
            AlertDialog.Builder builder = new AlertDialog.Builder(Off_Home.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(Off_Home.this,
                                            Sign_In.class));
                                    Off_Home.this.finish();
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
        Toast.makeText(Off_Home.this, "Long clicked on position: " + position,
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(Off_Home.this);
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
