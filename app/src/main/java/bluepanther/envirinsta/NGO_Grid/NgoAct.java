package bluepanther.envirinsta.NGO_Grid;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

import android.app.Dialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import bluepanther.envirinsta.AA_home;
import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Contacts.Contacts;
import bluepanther.envirinsta.Contacts.Contacts2;
import bluepanther.envirinsta.LeaderBoards.User_leaderboards;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Stats.User_stats;
import bluepanther.envirinsta.Timeline.Timeline;
import bluepanther.envirinsta.Travel.Travel;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class NgoAct extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private static final int PICK_IMAGE = 1;
    Notification notification,notification2,notification3,notification4;
    EditText ngoname,ngoadmin,ngopurpose,ngoinfo,ngouname,ngopass;
    public  MyRecyclerViewAdapter mAdapter;
    ImageView imageView10;
    String txtNGO,txtAdmin,txtPurpose,txtInfo,txtUsername,txtPassword;
    double progress = 0.0,progress2=0.0,progress3=0.0,progress4 = 0.0;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Dialog login;
    String selectcat,date;
   public static  ArrayList<DataObject> results = new ArrayList<DataObject>();
    String ngoimage;
    private String Base_url = "https://envirinsta.firebaseio.com/";
    private Firebase fb_db;
    private Firebase fb_db2;
    private SpaceNavigationView spaceNavigationView;
    Dialog newngo;
    ArrayList<String> targetmems;
    String tar,ntxt,npass;
    ArrayList<String> tempmems;

    ProgressBar progressBar;
    List<CharSequence> list = new ArrayList<CharSequence>();
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    final int[] tabIcons = {
            R.drawable.ic_action_club,
            R.drawable.ic_action_esch,

    };
    public String str[]={"NGO","Events"};
    public static int int_items = 2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ngo);
        Firebase.setAndroidContext(this);
        fb_db=new Firebase(Base_url);
        //progressBar = new ProgressBar(this);
        tar="GROUP";

//        getSupportActionBar().setTitle("NGO");
//        getSupportActionBar().show();
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();
        Button signinbut = (Button)findViewById(R.id.signinbut);
        Button signupbut = (Button)findViewById(R.id.signupbut);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        signinbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = new Dialog(NgoAct.this);
                // Set GUI of login screen
                login.setContentView(R.layout.login_dialog);
                login.setTitle("Login to NGO");

                // Init button of login GUI
                Button btnLogin = (Button) login.findViewById(R.id.login);
                Button btnCancel = (Button) login.findViewById(R.id.cancel);
                final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
                final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);


                // Attached listener for login GUI button
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0)
                        {
                            ntxt = txtUsername.getText().toString();
                            npass = txtPassword.getText().toString();
                            login.dismiss();
                            new MyTask4().execute();
                        }
                        else
                        {
                            Toast.makeText(NgoAct.this, "Incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login.dismiss();
                    }
                });

                // Make dialog box visible.
                login.show();
            }
        });

        signupbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newngo = new Dialog(NgoAct.this);
                // Set GUI of login screen
                newngo.setContentView(R.layout.new_ngo_dialog);
                newngo.setTitle("Start a new NGO");
                ngoname = (EditText)newngo.findViewById(R.id.txtNGO);
                System.out.println("NGONAME "+ngoname);
                ngoadmin = (EditText)newngo.findViewById(R.id.txtAdmin);
                ngopurpose = (EditText)newngo.findViewById(R.id.txtPurpose);
                ngoinfo = (EditText)newngo.findViewById(R.id.txtInfo);
                ngouname = (EditText)newngo.findViewById(R.id.txtUsername);
                ngopass = (EditText)newngo.findViewById(R.id.txtPassword);
                imageView10 = (ImageView)newngo.findViewById(R.id.imageView10);


                imageView10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_IMAGE);
                    }
                });


                Button btnLogin = (Button) newngo.findViewById(R.id.login);
                Button btnCancel = (Button) newngo.findViewById(R.id.cancel);
//                final EditText txtUsername = (EditText)newngo.findViewById(R.id.txtUsername);
//                final EditText txtPassword = (EditText)newngo.findViewById(R.id.txtPassword);

                // Attached listener for login GUI button
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("NGONAME "+ngoname);
                        txtNGO = ngoname.getText().toString();
                        txtAdmin = ngoadmin.getText().toString();
                        txtPurpose = ngopurpose.getText().toString();
                        txtInfo = ngoinfo.getText().toString();
                        txtUsername = ngouname.getText().toString();
                        txtPassword = ngopass.getText().toString();

                        newngo.dismiss();
                        new MyTask3().execute();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newngo.dismiss();
                    }
                });

                // Make dialog box visible.
                newngo.show();
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("Selected tab is"+tab.getText());
                TempClass.sharedValue=tab.getText().toString();
                System.out.println("After tab select shyam"+TempClass.sharedValue);
                super.onTabSelected(tab);
            }
        });

new MyTask5().execute();
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
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
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
        Toast.makeText(NgoAct.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(NgoAct.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(NgoAct.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(NgoAct.this, NgoAct.class));
        }else if (position == 5) {

            startActivity(new Intent(NgoAct.this, User_leaderboards.class));
        }
        else if (position == 6) {

            startActivity(new Intent(NgoAct.this, User_stats.class));
        }
        else if (position == 7) {

            startActivity(new Intent(NgoAct.this, Travel.class));
        }
        else if (position == 8) {

            startActivity(new Intent(NgoAct.this, Contacts2.class));
        }
        else if (position == 9) {

            AlertDialog.Builder builder = new AlertDialog.Builder(NgoAct.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog",false);
                                    editor.commit();
                                    startActivity(new Intent(NgoAct.this,
                                            AA_home.class));
                                    NgoAct.this.finish();
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
        Toast.makeText(NgoAct.this, "Long clicked on position: " + position,
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

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
       // tabLayout.getTabAt(1).setIcon(tabIcons[1]);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NGO_tab(), "NGO");
       //\ adapter.addFrag(new Events_tab(), "Events");
        viewPager.setAdapter(adapter);


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
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imageView10.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            ngoimage= Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }
    public class MyTask3 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
ArrayList<String> fols=new ArrayList<>();
            fols.add("Dummy Follower");
            newNGO ngoobj = new newNGO();
            ngoobj.setNgoname(txtNGO);
            ngoobj.setNgoadmin(txtAdmin);
            ngoobj.setNgoinfo(txtInfo);
            ngoobj.setNgopurpose(txtPurpose);
            ngoobj.setNgouname(txtUsername);
            ngoobj.setNgopass(txtPassword);

            ngoobj.setFollowers(fols);
            System.out.println("Lol"+ CurrentUser.sclass+" "+CurrentUser.ssec);

            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("NGO").child(txtNGO).setValue(ngoobj);
          //  newngo.dismiss();
            return null;
        }
    }
    public class MyTask4 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            // Validate Your login credential here than display message
//            Toast.makeText(NgoAct.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGO/");
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        newNGO ngoupdate = postSnapshot.getValue(newNGO.class);
                        String uname = ngoupdate.getNgouname();
                        String password = ngoupdate.getNgopass();

                        if((uname.equals(ntxt))&&(password.equals(npass)))
                        {
                            Intent intent = new Intent(NgoAct.this, NGO.class);
                            intent.putExtra("NGOclass",ngoupdate);
                            startActivity(intent);
                            // Redirect to dashboard / home screen.

                        }
                        else
                        {
                            Toast.makeText(NgoAct.this,"Login Failed", Toast.LENGTH_SHORT).show();
                        }



                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }
    public class MyTask5 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            results = new ArrayList<DataObject>();

            // Validate Your login credential here than display message
//            Toast.makeText(MainActivity2.this, "Login Success", Toast.LENGTH_SHORT).show();
            fb_db2 = new Firebase(Base_url+"Classes/"+ CurrentUser.sclass+"/"+CurrentUser.ssec+"/NGO/");
            fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        newNGO ngoupdate = postSnapshot.getValue(newNGO.class);
                        String uname = ngoupdate.getNgouname();
                        String password = ngoupdate.getNgopass();
                        String ngoname=ngoupdate.getNgoname();
                        String ngodesc=ngoupdate.getNgoinfo();
                        String location =ngoupdate.getNgoinfo();
                        String fols= Integer.toString(ngoupdate.getFollowers().size())+" following";


                        DataObject obj = new DataObject(ngoname,ngodesc,fols,location);
                        results.add(obj);
                    }

                    mAdapter = new MyRecyclerViewAdapter(NgoAct.this,results);
                    NGO_tab.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("FIREBASE ERROR OCCOURED");
                }
            });

            return null;
        }
    }

    }





