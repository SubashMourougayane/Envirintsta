package bluepanther.envirinsta.LeaderBoards;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterCon;
import bluepanther.envirinsta.Adapter.CustomAdapterGT;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.NGO_Grid.NgoAct;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Services.MyService;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.Timeline.GooeyMenu;
import bluepanther.envirinsta.Timeline.Group_tab;
import bluepanther.envirinsta.Timeline.Indi_tab;
import bluepanther.envirinsta.Timeline.Timeline;
import bluepanther.envirinsta.Travel.Travel;

/**
 * Created by shyam on 27/3/17.
 */

public class User_leaderboards extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    static public String Base_url1 = "https://envirinsta.firebaseio.com/Leaderboards/"+ CurrentUser.sclass+"/Users";
    static public Firebase fb_db1;
    static public String Base_url2 = "https://envirinsta.firebaseio.com/Leaderboards/"+CurrentUser.sclass+"/Resolvers";
    static public Firebase fb_db2;
    int cnt1=0,cnt2=0;
    List<RowItem> leadList1 = new ArrayList<>();
    List<RowItem> leadList2 = new ArrayList<>();

    String userslist[];
    Integer userscore[];

    String reslist[];
    Integer resscore[];

//    ArrayList<String> userslist = new ArrayList<>();
//    ArrayList<Integer> userscore = new ArrayList<>();
//
//    ArrayList<String> reslist = new ArrayList<>();
//    ArrayList<Integer> resscore = new ArrayList<>();


    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_user);

        getSupportActionBar().setTitle("Leaderboards");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Firebase.setAndroidContext(getApplicationContext());
        fb_db1 = new Firebase(Base_url1);
        fb_db2 = new Firebase(Base_url2);
        System.out.println("URL is "+ Base_url2);

        System.out.println(" EDHUKUUUUU ");

        new MyTask().execute();
new MyTask2().execute();
        // mylistview.setAdapter(adapter);
        fragmentManager = getSupportFragmentManager();
        // initToolbar();
        initMenuFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private class MyTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params)
        {
            fb_db1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userslist=new String[(int)dataSnapshot.getChildrenCount()+1];
                    userscore=new Integer[(int)dataSnapshot.getChildrenCount()+1];
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        String s = postSnapshot.getValue().toString();
                        Integer ss = Integer.parseInt(s);
                        System.out.println("List areeeeee "+postSnapshot.getKey()+";;;"+postSnapshot.getValue());
                        System.out.println("List are "+ postSnapshot.getKey());
                        userslist[cnt1]=postSnapshot.getKey();
                        userscore[cnt1]=ss;
                        System.out.println("BOWWWWW"+userslist[cnt1]+"  - - "+userscore[cnt1]);
                        cnt1++;
                    }
                    int size=cnt1;
                    if(size>1) {
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size - 1; j++) {

                                System.out.println("UserScore  is " + userscore[j] + "   " + userscore[j]);
                                if (userscore[j] < userscore[j + 1]) {
                                    int tmp = userscore[j];
                                    userscore[j] = userscore[j + 1];
                                    userscore[j + 1] = tmp;

                                    String tmp2 = userslist[j];
                                    userslist[j] = userslist[j + 1];
                                    userslist[j + 1] = tmp2;
                                }
                            }
                        }
                    }

                    for(int i=0;i<size;i++)
                    {
                        RowItem item = new RowItem(userslist[i],R.drawable.admin1, "", String.valueOf(userscore[i]),"","");
                        leadList1.add(item);
                    }
                    CustomAdapterCon adapter = new CustomAdapterCon(User_leaderboards.this, leadList1);
                    Leaderboards_tab1.setContent(adapter);

//                    new MyTask2().execute();




                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }



    }

    private class MyTask2 extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params)
        {
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    reslist=new String[(int)dataSnapshot.getChildrenCount()+1];
                    resscore=new Integer[(int)dataSnapshot.getChildrenCount()+1];
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        String s = postSnapshot.getValue().toString();
                        Integer ss = Integer.parseInt(s);
                        System.out.println("List areeeeee "+postSnapshot.getKey()+";;;"+postSnapshot.getValue());
                        System.out.println("List are "+ postSnapshot.getKey());
                        reslist[cnt2]=postSnapshot.getKey();
                        resscore[cnt2]=ss;
                        cnt2++;
                        System.out.println("BOWWWWW"+reslist+"  - - "+resscore);
                    }
                    int size=cnt2;
                    if(size>1) {
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size - 1; j++) {
                                if (resscore[j] < resscore[j + 1]) {
                                    int tmp = resscore[j];
                                    resscore[j] = resscore[j + 1];
                                    resscore[j + 1] = tmp;

                                    String tmp2 = reslist[j];
                                    reslist[j] = reslist[j + 1];
                                    reslist[j + 1] = tmp2;
                                }
                            }
                        }
                    }

                    for(int i=0;i<size;i++)
                    {
                        RowItem item = new RowItem(reslist[i],R.drawable.admin1, "", String.valueOf(resscore[i]),"","");
                        leadList2.add(item);
                    }
                    CustomAdapterCon adapter = new CustomAdapterCon(User_leaderboards.this, leadList2);
                    Leaderboards_tab2.setContent(adapter);





                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return "SUCCESS";
        }



    }
//    private class MyTask2 extends AsyncTask<String, Integer, String> {
//
//
//        @Override
//        protected String doInBackground(String... params)
//        {
//            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
//                    {
//                        String s = postSnapshot.getValue().toString();
//                        Integer ss = Integer.parseInt(s);
//                        System.out.println("List areeeeee "+postSnapshot.getKey()+";;;"+postSnapshot.getValue());
//                        System.out.println("List are "+ postSnapshot.getKey());
//                        reslist.add(postSnapshot.getKey());
//                        resscore.add(ss);
//
//                        System.out.println("BOWWWWWsss"+reslist+"  - - "+resscore);
//
//
//
//
//                    }
//
//
//
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//            });
//
//
//            return "SUCCESS";
//        }
//
//
//
//    }

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
        menuParams.setAnimationDuration(30);
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(User_leaderboards.this);
        mMenuDialogFragment.setItemLongClickListener(User_leaderboards.this);
    }
    
    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        System.out.println("In notif class tabs");
        adapter.addFragment(new Leaderboards_tab1(), "Users");
        adapter.addFragment(new Leaderboards_tab2(), "Resolvers");

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
        Toast.makeText(User_leaderboards.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(User_leaderboards.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(User_leaderboards.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(User_leaderboards.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(User_leaderboards.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(User_leaderboards.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(User_leaderboards.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(User_leaderboards.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(User_leaderboards.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(User_leaderboards.this,
                                            AA_home.class));
                                    User_leaderboards.this.finish();
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
        Toast.makeText(User_leaderboards.this, "Long clicked on position: " + position,
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(User_leaderboards.this);
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
