package bluepanther.envirinsta.Timeline;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import bluepanther.envirinsta.Adapter.Preferences;
import bluepanther.envirinsta.Adapter.RowItem;
import bluepanther.envirinsta.Contacts.Contacts;
import bluepanther.envirinsta.ContentDesc.Audiodesc;
import bluepanther.envirinsta.ContentDesc.FileDesc;
import bluepanther.envirinsta.ContentDesc.ImageDesc;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDesc.VideoDesc;
import bluepanther.envirinsta.FileUtils.Filepath;
import bluepanther.envirinsta.FileUtils.ImageCompressor;
import bluepanther.envirinsta.InternalStorage.Internal_Audio;
import bluepanther.envirinsta.InternalStorage.Internal_File;
import bluepanther.envirinsta.InternalStorage.Internal_Image;
import bluepanther.envirinsta.InternalStorage.Internal_Text;
import bluepanther.envirinsta.InternalStorage.Internal_Video;
import bluepanther.envirinsta.Profile.Profile;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.Reports_new;
import bluepanther.envirinsta.Signing.Sign_In;

/**
 * Created by hhs on 23/2/17.
 */

public class Timeline_new extends AppCompatActivity implements GooeyMenu.GooeyMenuInterface,OnMenuItemClickListener,OnMenuItemLongClickListener {

    BottomSheetBehavior bottomSheetBehavior, bottomSheetBehavior2, bottomSheetBehavior3, bottomSheetBehavior4, bottomSheetBehavior5;

    BottomSheetDialog bottomSheetDialog, bottomSheetDialog2, bottomSheetDialog3, bottomSheetDialog4, bottomSheetDialog5;

    private GooeyMenu mGooeyMenu;
    private Toast mToast;
    private SpaceNavigationView spaceNavigationView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    final int[] tabIcons = {
            R.drawable.ic_action_group_sv,
            R.drawable.ic_action_indi_sv
    };

    String tar = "GROUP";
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;

    private static final String[] MAIN_CATEGORY = {
            "Select Category", "Roads", "Transport", "Bank/ATM", "Waterlines", "Hospitals", "Sewage", "Politics", "Govt Offices", "General PWD"
    };

    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    protected static final int REQUEST_PICK_VIDEO = 3;
    private static final int VIDEO_CAPTURE = 4;

    private static final int PICK_FILE = 5;

    protected static final int REQUEST_PICK_AUDIO = 6;
    public static final int ACTIVITY_RECORD_SOUND = 7;

    Uri VideoUri;
    String path, idesc = "", adesc = "", vdesc = "", fdesc = "";
    Uri audioUri;
    Uri fileUri = null;
    Uri imageUri;
    String date;
    String fname;
    TextDesc textDesc;
    ImageDesc imageDesc;
    Audiodesc audiodesc;
    VideoDesc videoDesc;
    FileDesc fileDesc;
    String descr = "", textcontents = "";

    Boolean boolt = false, booli = false, boola = false, boolv = false, boolf = false;
    File compressedFiles;
    Boolean boolt2 = false, booli2 = false, boola2 = false, boolv2 = false, boolf2 = false;
    Uri selectedImageUri, compressedImage = null, compressedAudio = null, compressedVideo = null, compressedFile = null;

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

    ImageDesc img;

    android.app.Notification notification,notification2,notification3,notification4;

    double progress = 0.0,progress2=0.0,progress3=0.0,progress4 = 0.0;

    ProgressDialog progressDialogt,progressDialogi,progressDialoga,progressDialogv,progressDialogf;
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        Preferences.getPrefs(getApplicationContext());
        String noti = getIntent().getExtras().get("noti").toString();

        System.out.println("noti2 is" + noti);
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
        date = new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());

        new MyTaskinoti().execute();

        new MyTaskanoti().execute();

        new MyTaskvnoti().execute();

        new MyTaskfnoti().execute();

        new MyTasktnoti().execute();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.b1));
        bottomSheetBehavior2 = BottomSheetBehavior.from(findViewById(R.id.b2));
        bottomSheetBehavior3 = BottomSheetBehavior.from(findViewById(R.id.b3));
        bottomSheetBehavior4 = BottomSheetBehavior.from(findViewById(R.id.b4));
        bottomSheetBehavior5 = BottomSheetBehavior.from(findViewById(R.id.b5));

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior4.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior5.setState(BottomSheetBehavior.STATE_HIDDEN);


//        bottomSheetDialog = new BottomSheetDialog(Timeline_new.this);
//
//        View view1 = getLayoutInflater().inflate(R.layout.text_bottom, null);
//
//        bottomSheetDialog.setContentView(view1);
//
//        bottomSheetDialog.show();
//
//        bottomSheetDialog2 = new BottomSheetDialog(Timeline_new.this);
//
//        View view2 = getLayoutInflater().inflate(R.layout.image_bottom, null);
//
//        bottomSheetDialog2.setContentView(view2);
//
//        bottomSheetDialog2.show();
//
//        bottomSheetDialog3 = new BottomSheetDialog(Timeline_new.this);
//
//        View view3 = getLayoutInflater().inflate(R.layout.audio_bottom, null);
//
//        bottomSheetDialog3.setContentView(view3);
//
//        bottomSheetDialog3.show();
//
//        bottomSheetDialog4 = new BottomSheetDialog(Timeline_new.this);
//
//        View view4 = getLayoutInflater().inflate(R.layout.video_bottom, null);
//
//        bottomSheetDialog4.setContentView(view4);
//
//        bottomSheetDialog4.show();
//
//        bottomSheetDialog5 = new BottomSheetDialog(Timeline_new.this);
//
//        View view5 = getLayoutInflater().inflate(R.layout.files_bottom, null);
//
//        bottomSheetDialog5.setContentView(view5);
//
//        bottomSheetDialog5.show();


        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("GROUP", R.drawable.ic_action_group_sv));
        spaceNavigationView.addSpaceItem(new SpaceItem("INDIVIDUAL", R.drawable.ic_action_indi_sv));
        spaceNavigationView.setCentreButtonIcon(R.drawable.soul_logo);
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setBackgroundColor(getResources().getColor(R.color.bg));

        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        mGooeyMenu = (GooeyMenu) findViewById(R.id.gooey_menu);
        mGooeyMenu.setOnMenuListener(Timeline_new.this);

        // toolbar = (Toolbar) findViewById(R.id.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Timeline");


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

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
                tar = itemName;
                System.out.println("Inside indi");


            }


            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
                tar = itemName;
            }
        });

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(Timeline_new.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(Timeline_new.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });

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

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int)
                getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(Timeline_new.this);
        mMenuDialogFragment.setItemLongClickListener(Timeline_new.this);
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
        menuNumber = menuNumber % 10;
        switch (menuNumber) {
            case 1:
                selectText();
                break;
            case 2:
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
        Toast.makeText(Timeline_new.this, "Clicked at : " + menuNumber, Toast.LENGTH_SHORT).show();

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

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(Timeline_new.this, "Clicked on position: " + position,
                Toast.LENGTH_SHORT).show();

        if (position == 1) {
            // changeFragment(new Timeline());
            Intent i = new Intent(this, Timeline_new.class);
            i.putExtra("noti", "null");

            startActivity(i);
        } else if (position == 2) {

            startActivity(new Intent(Timeline_new.this, Reports_new.class));
        } else if (position == 3) {

            startActivity(new Intent(Timeline_new.this, Profile.class));
        } else if (position == 4) {

            startActivity(new Intent(Timeline_new.this, Contacts.class));
        } else if (position == 5) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putBoolean("islog", false);
                                    editor.commit();
                                    startActivity(new Intent(Timeline_new.this,
                                            Sign_In.class));
                                    Timeline_new.this.finish();
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
        Toast.makeText(Timeline_new.this, "Long clicked on position: " + position,
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
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
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

    private void selectText() {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            final EditText editText = (EditText) findViewById(R.id.editText);
            final EditText desc = (EditText) findViewById(R.id.desc);
            Button upload = (Button) findViewById(R.id.upload);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    textcontents=editText.getText().toString();
//                    descr=desc.getText().toString();
//                    if(textcontents.length()>0&&descr.length()>0&&boolt&&boolt2)
//                    {
//                        new Timeline_new.MyTaskt().execute();
//                    }
//                    else
//                    {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                        builder.setMessage("Please provide complete details")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    }
//
//                    // text_dialog.dismiss();
                }
            });
            //mShowBottomSheet.setText("Date Picker");
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            //mShowBottomSheet.setText("Date Picker");
        }
        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_text);
        spinner.setItems(MAIN_CATEGORY);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
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
                    imageUri = Timeline_new.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

    private void selectAudio() {
        final CharSequence[] items = {"Record Audio", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
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
                    startActivityForResult(Intent.createChooser(intent, "Select Audio "), REQUEST_PICK_AUDIO);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    private void selectVideo() {
        final CharSequence[] items = {"Take Video", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
        builder.setTitle("Choose video..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Video")) {
                    File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    int cnt = f.listFiles().length;
                    System.out.println("FIle cnt is " + cnt + "and they are" + f.listFiles());
                    File mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/myvideo" + Integer.toString(cnt + 1) + ".mp4");
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo" + Integer.toString(cnt + 1) + ".mp4";
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

    private void selectFiles() {
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
        if (resultCode == Timeline_new.this.RESULT_OK) {
            System.out.println("Result code is"+resultCode+"and req code is"+requestCode);
            if (requestCode == PICK_Camera_IMAGE) {
                if (resultCode == Timeline_new.this.RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;

                } else if (resultCode == Timeline_new.this.RESULT_CANCELED) {
                    Toast.makeText(Timeline_new.this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Timeline_new.this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }

                if (bottomSheetBehavior2.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    bottomSheetDialog = new BottomSheetDialog(Timeline_new.this);

                    View view1 = getLayoutInflater().inflate(R.layout.image_bottom, null);

                    bottomSheetDialog.setContentView(view1);

                    bottomSheetDialog.show();

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
                    String image_path = Filepath.getPaths(Timeline_new.this,selectedImageUri);
                    String image_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline_new.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String image_type = mime.getExtensionFromMimeType(cR.getType(selectedImageUri));
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(Timeline_new.this.getContentResolver() , compressedImage);
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
                    Button upload2 = (Button) findViewById(R.id.upload2);
                    upload2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            idesc = desc2.getText().toString();
//                            if (idesc.length() > 0 && booli && booli2) {
//                                new Timeline.MyTaski().execute();
//
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                                builder.setMessage("Please provide complete details")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//
//                                            }
//                                        });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//                            }
                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }

                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_image);

                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
            if (requestCode == PICK_IMAGE) {
                if (resultCode == Timeline_new.this.RESULT_OK) {
                    selectedImageUri = data.getData();
                } else if (resultCode == Timeline_new.this.RESULT_CANCELED) {
                    Toast.makeText(Timeline_new.this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Timeline_new.this, "Picture was not selected", Toast.LENGTH_SHORT).show();
                }

                if (bottomSheetBehavior2.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    bottomSheetDialog = new BottomSheetDialog(Timeline_new.this);

                    View view1 = getLayoutInflater().inflate(R.layout.image_bottom, null);

                    bottomSheetDialog.setContentView(view1);

                    bottomSheetDialog.show();

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
                    String image_path = Filepath.getPaths(Timeline_new.this,selectedImageUri);
                    String image_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline_new.this.getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String image_type = mime.getExtensionFromMimeType(cR.getType(selectedImageUri));
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(Timeline_new.this.getContentResolver() , compressedImage);
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
                    Button upload2 = (Button) findViewById(R.id.upload2);
                    upload2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            idesc = desc2.getText().toString();
//                            if(idesc.length()>0&&booli&&booli2)
//                            {
//                                new Timeline.MyTaski().execute();
//
//                            }
//                            else
//                            {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                                builder.setMessage("Please provide complete details")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//
//                                            }
//                                        });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_image);

                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });

            }


            if (requestCode == REQUEST_PICK_VIDEO) {


                if (data.equals(null)) {
                    Toast.makeText(Timeline_new.this, "Video was not selected", Toast.LENGTH_SHORT).show();
                } else {
                    VideoUri = data.getData();
                    Toast.makeText(Timeline_new.this, VideoUri.toString(), Toast.LENGTH_SHORT).show();
                    Filepath obj = new Filepath();
                    path =     obj.getPaths(Timeline_new.this, VideoUri);
                    System.out.println("VIDEOO" + VideoUri + " d " + VideoUri.getPath());

                    if (bottomSheetBehavior4.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior4.setState(BottomSheetBehavior.STATE_COLLAPSED);


                        bottomSheetDialog4 = new BottomSheetDialog(Timeline_new.this);

                        View view4 = getLayoutInflater().inflate(R.layout.video_bottom, null);

                        bottomSheetDialog4.setContentView(view4);

                        bottomSheetDialog4.show();

                        String video_name = getFileName(VideoUri);
                        String video_size = null;
                        try {
                            video_size = getImageSize(VideoUri).toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String video_path = Filepath.getPaths(Timeline_new.this,VideoUri);
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

//                                vdesc = desc4.getText().toString();
//                                if(vdesc.length()>0&&boolv&&boolv2)
//                                {
//                                    new Timeline.MyTaskv().execute();
//
//                                }
//                                else
//                                {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                                    builder.setMessage("Please provide complete details")
//                                            .setCancelable(false)
//                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int id) {
//
//                                                }
//                                            });
//                                    AlertDialog alert = builder.create();
//                                    alert.show();
//                                }

                            }

                        });
                        //mShowBottomSheet.setText("Date Picker");
                    } else {
                        bottomSheetBehavior4.setState(BottomSheetBehavior.STATE_HIDDEN);
                        //mShowBottomSheet.setText("Date Picker");
                    }

                    final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_video);
                    final MaterialSpinner spinner2 = (MaterialSpinner) findViewById(R.id.spinner2_video);
                    spinner.setItems(MAIN_CATEGORY);
                    spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                        }
                    });
                    spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                        @Override
                        public void onNothingSelected(MaterialSpinner spinner) {
                            Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                        }
                    });

                }
            }

            if (requestCode == VIDEO_CAPTURE) {
                if (resultCode == Timeline_new.this.RESULT_OK) {
                    Toast.makeText(Timeline_new.this, "Video has been saved to:\n" +
                            data.getData(), Toast.LENGTH_LONG).show();
                } else if (resultCode == Timeline_new.this.RESULT_CANCELED) {
                    Toast.makeText(Timeline_new.this, "Video recording cancelled.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Timeline_new.this, "Failed to record video",
                            Toast.LENGTH_LONG).show();
                }

                if (bottomSheetBehavior4.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior4.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    bottomSheetDialog4 = new BottomSheetDialog(Timeline_new.this);

                    View view4 = getLayoutInflater().inflate(R.layout.video_bottom, null);

                    bottomSheetDialog4.setContentView(view4);

                    bottomSheetDialog4.show();

                    String video_name = getFileName(VideoUri);
                    String video_size=null;
                    try {
                        video_size= getImageSize(VideoUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String video_path = Filepath.getPaths(Timeline_new.this,VideoUri);
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

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    bottomSheetBehavior4.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_video);

                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
            if (requestCode == REQUEST_PICK_AUDIO) {
                audioUri = data.getData();
                //  String path = audioUri.getPath(); // "file:///mnt/sdcard/FileName.mp3"

                Filepath obj = new Filepath();
                path = obj.getPaths(Timeline_new.this, audioUri);
                System.out.println("Path is " + audioUri + " d " + obj.getPaths(Timeline_new.this, audioUri));

                if (bottomSheetBehavior3.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    bottomSheetDialog3 = new BottomSheetDialog(Timeline_new.this);

                    View view3 = getLayoutInflater().inflate(R.layout.audio_bottom, null);

                    bottomSheetDialog3.setContentView(view3);

                    bottomSheetDialog3.show();

                    String audio_name = getFileName(audioUri);
                    String audio_size = null;
                    try {
                        audio_size = getImageSize(audioUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String audio_path = Filepath.getPaths(Timeline_new.this,audioUri);
                    String audio_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline_new.this.getContentResolver();
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

//                            adesc = desc3.getText().toString();
//                            if(adesc.length()>0&&boola&&boola2)
//                            {
//                                new Timeline.MyTaska().execute();
//
//                            }
//                            else
//                            {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                                builder.setMessage("Please provide complete details")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//
//                                            }
//                                        });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_audio);

                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();


                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
            if (requestCode == ACTIVITY_RECORD_SOUND) {
                audioUri = data.getData();
                //  String path = audioUri.getPath(); // "file:///mnt/sdcard/FileName.mp3"

                Filepath obj = new Filepath();
                path = obj.getPaths(Timeline_new.this, audioUri);
                System.out.println("Path is " + audioUri + " d " + obj.getPaths(Timeline_new.this, audioUri));

                if (bottomSheetBehavior3.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    bottomSheetDialog3 = new BottomSheetDialog(Timeline_new.this);

                    View view3 = getLayoutInflater().inflate(R.layout.audio_bottom, null);

                    bottomSheetDialog3.setContentView(view3);

                    bottomSheetDialog3.show();

                    String audio_name = getFileName(audioUri);
                    String audio_size = null;
                    try {
                        audio_size = getImageSize(audioUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String audio_path = Filepath.getPaths(Timeline_new.this,audioUri);
                    String audio_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline_new.this.getContentResolver();
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
//                            adesc = desc3.getText().toString();
//                            if(adesc.length()>0&&boola&&boola2)
//                            {
//                                new Timeline.MyTaska().execute();
//
//                            }
//                            else
//                            {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                                builder.setMessage("Please provide complete details")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//
//                                            }
//                                        });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    bottomSheetBehavior3.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }

                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_audio);

                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                    }
                });

            }

            if (requestCode == PICK_FILE) {
                fileUri = data.getData();
                System.out.println("HOLA");
                Filepath obj = new Filepath();
                path = obj.getPaths(Timeline_new.this, fileUri);
                System.out.println(fileUri + " d " + fileUri.getPath());

                if (bottomSheetBehavior5.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior5.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    bottomSheetDialog5 = new BottomSheetDialog(Timeline_new.this);

                    View view5 = getLayoutInflater().inflate(R.layout.files_bottom, null);

                    bottomSheetDialog5.setContentView(view5);

                    bottomSheetDialog5.show();

                    String file_name = getFileName(fileUri);
                    String file_size = null;
                    try {
                        file_size = getImageSize(fileUri).toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String file_path = Filepath.getPaths(Timeline_new.this,fileUri);
                    String file_date=new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date());
                    ContentResolver cR = Timeline_new.this.getContentResolver();
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
//                            fdesc = desc5.getText().toString();
//                            if(fdesc.length()>0&&boolf&&boolf2)
//                            {
//                                new Timeline.MyTaskf().execute();
//
//                            }
//                            else
//                            {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(Timeline_new.this);
//                                builder.setMessage("Please provide complete details")
//                                        .setCancelable(false)
//                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//
//                                            }
//                                        });
//                                AlertDialog alert = builder.create();
//                                alert.show();
//                            }

                        }

                    });
                    //mShowBottomSheet.setText("Date Picker");
                } else {
                    bottomSheetBehavior5.setState(BottomSheetBehavior.STATE_HIDDEN);
                    //mShowBottomSheet.setText("Date Picker");
                }
                final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner_file);

                spinner.setItems(MAIN_CATEGORY);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                    }
                });
                spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                    @Override
                    public void onNothingSelected(MaterialSpinner spinner) {
                        Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
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
//        Cursor cursor = Timeline_new.this.getContentResolver().query(uri,
//                null, null, null, null);
//        cursor.moveToFirst();
//        long size = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
//        cursor.close();
        ContentResolver cr = getContentResolver();
        InputStream is = cr.openInputStream(uri);
        int size = is.available();
        size = size / 1024;
        return (long) size;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = Timeline_new.this.getContentResolver().query(uri, null, null, null, null);
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
//            Cred_Update cred_update = new Cred_Update();
//            cred_update.setUsn(CurrentUser.user);
//            cred_update.setPass(CurrentUser.pass);
//            cred_update.setCls(CurrentUser.sclass);
//            cred_update.setSec(CurrentUser.ssec);
//            cred_update.setAdate(CurrentUser.sadate);
//            cred_update.setVdate(CurrentUser.svdate);
//            cred_update.setFdate(CurrentUser.sfdate);
//            cred_update.setTdate(CurrentUser.stdate);
//            cred_update.setIdate(date);
//            CurrentUser.sidate = date;
//
//            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


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
//            Cred_Update cred_update = new Cred_Update();
//            cred_update.setUsn(CurrentUser.user);
//            cred_update.setPass(CurrentUser.pass);
//            cred_update.setCls(CurrentUser.sclass);
//            cred_update.setSec(CurrentUser.ssec);
//            cred_update.setIdate(CurrentUser.sidate);
//            cred_update.setVdate(CurrentUser.svdate);
//            cred_update.setFdate(CurrentUser.sfdate);
//            cred_update.setTdate(CurrentUser.stdate);
//            cred_update.setAdate(date);
//            CurrentUser.sadate = date;
//
//            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }

    public void putimage()
    {
//        Timeline_new.this.runOnUiThread(new Runnable() {
//            public void run() {
//                progressDialogi = new ProgressDialog(Timeline_new.this);
//                progressDialogi.setTitle("Message");
//                progressDialogi.setMessage("Uploading Image...");
//
//                progressDialogi.setCancelable(true);
//                progressDialogi.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                progressDialogi.show();
//            }
//        });
//
//        final String imagenode = CurrentUser.user + img.getDate();
//        System.out.println("  imagenode  " + imagenode);
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode);
//
//        System.out.println("Storage refference : " + storageReference);
//
//        final NotificationManager notificationManager = (NotificationManager) Timeline_new.this.getSystemService(NOTIFICATION_SERVICE);
//        final android.app.Notification.Builder notificationBuilder = new android.app.Notification.Builder(Timeline_new.this);
//        notificationBuilder.setOngoing(true)
//                .setContentTitle("Uploading Image")
//                .setContentText("Progressed (" + (int) progress + " %)")
//                .setProgress(100, (int) progress, false)
//
//                .setSmallIcon(R.drawable.soul_logo);
//        notification = notificationBuilder.build();
//        notificationManager.notify(100, notification);
//
//        UploadTask up = storageReference.putFile(selectedImageUri);
//        up.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                System.out.println("Progress is " + progress);
//                notificationBuilder.setProgress(100, (int) progress, false).setContentText("Progressed (" + (int) progress + "%...)");
//                notification = notificationBuilder.build();
//                notificationManager.notify(100, notification);
//
//            }
//        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
//
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Images").child(imagenode).setValue(img);
//
//                notificationBuilder.setProgress(100, (int) progress, false).setContentText("Progressed (" + (int) progress + "%)").setOngoing(false).setDefaults(android.app.Notification.DEFAULT_ALL);
//                notification = notificationBuilder.build();
//                notificationManager.notify(100, notification);
//                // Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
//
//
//                Timeline_new.this.runOnUiThread(new Runnable() {
//                    public void run() {
//                        progressDialogi.dismiss();
//                        Toast.makeText(Timeline_new.this, "Image Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                });
//                Intent i2 = new Intent(Timeline_new.this, Timeline_new.class);
//                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                i2.putExtra("noti", "null");
//                finish();
//                startActivity(i2);
//
//
//            }
//        });

    }
    public void putaudio()
    {
//        Timeline_new.this.runOnUiThread(new Runnable() {
//            public void run() {
//                progressDialoga = new ProgressDialog(Timeline_new.this);
//                progressDialoga.setTitle("Message");
//                progressDialoga.setMessage("Uploding Audio...");
//
//                progressDialoga.setCancelable(true);
//                progressDialoga.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                progressDialoga.show();
//            }
//        });
//        final String audnode = CurrentUser.user + aud.getDate();
//        System.out.println("  audionode  " + audnode);
//        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(audnode);
//
//        System.out.println("Storage refference : " + storageReference2);
//
//        final NotificationManager notificationManager2 = (NotificationManager) Timeline_new.this.getSystemService(NOTIFICATION_SERVICE);
//        final android.app.Notification.Builder notificationBuilder2 = new android.app.Notification.Builder(Timeline_new.this);
//        notificationBuilder2.setOngoing(true)
//                .setContentTitle("Uploading Audio")
//                .setContentText("Progressed (" + (int) progress2 + " %)")
//                .setProgress(100, (int) progress2, false)
//                .setSmallIcon(R.drawable.soul_logo);
//        notification2 = notificationBuilder2.build();
//        notificationManager2.notify(101, notification2);
//
//        UploadTask up1 = storageReference2.putFile(audioUri);
//        up1.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                progress2 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                System.out.println("Progress is " + progress2);
//                notificationBuilder2.setProgress(100, (int) progress2, false).setContentText("Progressed (" + (int) progress2 + "%...)");
//                notification2 = notificationBuilder2.build();
//                notificationManager2.notify(101, notification2);
//
//
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Audios").child(audnode).setValue(aud);
//                notificationBuilder2.setProgress(100, (int) progress2, false).setContentText("Progressed (" + (int) progress2 + "%)").setOngoing(false).setDefaults(android.app.Notification.DEFAULT_ALL);
//                notification2 = notificationBuilder2.build();
//                notificationManager2.notify(101, notification2);
//
//
//                Timeline_new.this.runOnUiThread(new Runnable() {
//                    public void run() {
//                        progressDialoga.dismiss();
//                        Toast.makeText(Timeline_new.this, "Audio Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                });
//                Intent i3 = new Intent(Timeline_new.this, Timeline_new.class);
//                i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                i3.putExtra("noti", "null");
//                finish();
//                startActivity(i3);
//
//
//            }
//        });
    }
    public void putvideo()
    {
//        Timeline_new.this.runOnUiThread(new Runnable() {
//            public void run() {
//                progressDialogv = new ProgressDialog(Timeline_new.this);
//                progressDialogv.setTitle("Message");
//                progressDialogv.setMessage("Uploading Video...");
//
//                progressDialogv.setCancelable(true);
//                progressDialogv.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                progressDialogv.show();progressDialogv = ProgressDialog.show(Timeline_new.this, "Message", "Uploading Video...");
//            }
//        });
//
//        final String vidnode = CurrentUser.user + vid.getDate();
//        System.out.println("  videonode  " + vidnode);
//        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(vidnode);
//
//        System.out.println("Storage refference : " + storageReference3);
//
//        final NotificationManager notificationManager3 = (NotificationManager) Timeline_new.this.getSystemService(NOTIFICATION_SERVICE);
//        final android.app.Notification.Builder notificationBuilder3 = new android.app.Notification.Builder(Timeline_new.this);
//        notificationBuilder3.setOngoing(true)
//                .setContentTitle("Uploading Video")
//                .setContentText("Progressed (" + (int) progress3 + " %)")
//                .setProgress(100, (int) progress3, false)
//                .setSmallIcon(R.drawable.soul_logo);
//        notification3 = notificationBuilder3.build();
//        notificationManager3.notify(102, notification3);
//
//        UploadTask up2 = storageReference3.putFile(VideoUri);
//        up2.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
//                progress3 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                System.out.println("Progress is " + progress3);
//                notificationBuilder3.setProgress(100, (int) progress3, false).setContentText("Progressed (" + (int) progress3 + "%...)");
//                notification3 = notificationBuilder3.build();
//                notificationManager3.notify(102, notification3);
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Videos").child(vidnode).setValue(vid);
//
//                progress3 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                System.out.println("Progress is " + progress3);
//                notificationBuilder3.setProgress(100, (int) progress3, false).setContentText("Progressed (" + (int) progress3 + "%...)").setOngoing(false).setDefaults(android.app.Notification.DEFAULT_ALL);
//                notification3 = notificationBuilder3.build();
//                notificationManager3.notify(102, notification3);
//
//
//                Timeline_new.this.runOnUiThread(new Runnable() {
//                    public void run() {
//                        progressDialogv.dismiss();
//                        Toast.makeText(Timeline_new.this, "Video Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                });
//                Intent i4 = new Intent(Timeline_new.this, Timeline_new.class);
//                i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                i4.putExtra("noti", "null");
//                finish();
//                startActivity(i4);
//
//
//            }
//        });
    }
    public void putfile()
    {

//        Timeline_new.this.runOnUiThread(new Runnable() {
//            public void run() {
//                progressDialogf = new ProgressDialog(Timeline_new.this);
//                progressDialogf.setTitle("Message");
//                progressDialogf.setMessage("Uploading File...");
//
//                progressDialogf.setCancelable(true);
//                progressDialogf.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                progressDialogf.show();
//            }
//        });
//
//        final String filenode = CurrentUser.user + fil.getDate();
//        System.out.println("  filenode  " + filenode);
//        StorageReference storageReference4 = FirebaseStorage.getInstance().getReference().child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(filenode);
//
//        System.out.println("Storage refference : " + storageReference4);
//
//        final NotificationManager notificationManager4 = (NotificationManager) Timeline_new.this.getSystemService(NOTIFICATION_SERVICE);
//        final android.app.Notification.Builder notificationBuilder4 = new android.app.Notification.Builder(Timeline_new.this);
//        notificationBuilder4.setOngoing(true)
//                .setContentTitle("Uploading File")
//                .setContentText("Progressed (" + (int) progress4 + " %)")
//                .setProgress(100, (int) progress4, false)
//                .setSmallIcon(R.drawable.soul_logo);
//        notification4 = notificationBuilder4.build();
//        notificationManager4.notify(104, notification4);
//
//        UploadTask up3 = storageReference4.putFile(fileUri);
//        up3.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                progress4 = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                System.out.println("Progress is " + progress4);
//                notificationBuilder4.setProgress(100, (int) progress4, false).setContentText("Progressed (" + (int) progress4 + "%...)");
//                notification4 = notificationBuilder4.build();
//                notificationManager4.notify(104, notification4);
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Files").child(filenode).setValue(fil);
//
//                notificationBuilder4.setProgress(100, (int) progress4, false).setContentText("Progressed (" + (int) progress4 + "%)").setOngoing(false).setDefaults(android.app.Notification.DEFAULT_ALL);
//                notification4 = notificationBuilder4.build();
//                notificationManager4.notify(104, notification4);
//
//
//                Timeline_new.this.runOnUiThread(new Runnable() {
//                    public void run() {
//                        progressDialogf.dismiss();
//                        Toast.makeText(Timeline_new.this, "File Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                });
//                Intent i5 = new Intent(Timeline_new.this, Timeline_new.class);
//                i5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                i5.putExtra("noti", "null");
//                finish();
//                startActivity(i5);
//
//
//
//            }
//        });


    }
    public void puttext()
    {

//        Timeline_new.this.runOnUiThread(new Runnable() {
//            public void run() {
//                progressDialogt = new ProgressDialog(Timeline_new.this);
//                progressDialogt.setTitle("Message");
//                progressDialogt.setMessage("Uploading Text...");
//
//                progressDialogt.setCancelable(true);
//                progressDialogt.setButton(DialogInterface.BUTTON_POSITIVE, "DISMISS", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                progressDialogt.show();            }
//        });
//        fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Texts").child(CurrentUser.user+txt.getDate()).setValue(txt);
//        Timeline_new.this.runOnUiThread(new Runnable() {
//            public void run() {
//                progressDialogt.dismiss();
//                Toast.makeText(Timeline_new.this, "Text Uploaded", Toast.LENGTH_LONG).show();
//            }
//        });
//        Intent i1 = new Intent(Timeline_new.this, Timeline_new.class);
//
//        i1.putExtra("noti", "null");
//        finish();
//        startActivity(i1);

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
//            Cred_Update cred_update = new Cred_Update();
//            cred_update.setUsn(CurrentUser.user);
//            cred_update.setPass(CurrentUser.pass);
//            cred_update.setCls(CurrentUser.sclass);
//            cred_update.setSec(CurrentUser.ssec);
//            cred_update.setIdate(CurrentUser.sidate);
//            cred_update.setFdate(CurrentUser.sfdate);
//            cred_update.setAdate(CurrentUser.sadate);
//            cred_update.setTdate(CurrentUser.stdate);
//            cred_update.setVdate(date);
//            CurrentUser.svdate = date;
//
//            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


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
//            Cred_Update cred_update = new Cred_Update();
//            cred_update.setUsn(CurrentUser.user);
//            cred_update.setPass(CurrentUser.pass);
//            cred_update.setCls(CurrentUser.sclass);
//            cred_update.setSec(CurrentUser.ssec);
//            cred_update.setIdate(CurrentUser.sidate);
//            cred_update.setVdate(CurrentUser.svdate);
//            cred_update.setAdate(CurrentUser.sadate);
//            cred_update.setTdate(CurrentUser.stdate);
//            cred_update.setFdate(date);
//            CurrentUser.sfdate = date;
//
//            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);
//

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
//            Cred_Update cred_update = new Cred_Update();
//            cred_update.setUsn(CurrentUser.user);
//            cred_update.setPass(CurrentUser.pass);
//            cred_update.setCls(CurrentUser.sclass);
//            cred_update.setSec(CurrentUser.ssec);
//            cred_update.setIdate(CurrentUser.sidate);
//            cred_update.setVdate(CurrentUser.svdate);
//            cred_update.setAdate(CurrentUser.sadate);
//            cred_update.setFdate(CurrentUser.sfdate);
//            cred_update.setTdate(date);
//            CurrentUser.stdate = date;
//
//            fb_db.child("Accounts").child(CurrentUser.user).setValue(cred_update);
//            fb_db.child("Classes").child(CurrentUser.sclass).child(CurrentUser.ssec).child("Members").child(CurrentUser.user).setValue(cred_update);


            return "SUCCESS";
        }


        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }


    }


//    private class MyTaskt extends AsyncTask<String, Integer, String> {
//
//        // Runs in UI before background thread is called
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //     progressDialog = ProgressDialog.show(Timeline_new.this, "Message", "Uploading Image...");
//
//        }
//
//        // Timeline_new.this is run in a background thread
//        @Override
//        protected String doInBackground(String... params) {
//
//            txt = new TextDesc();
//            txt.setUser(CurrentUser.user);
//
//            txt.setDesc(descr);
//            txt.setMaincat(maincat);
//            txt.setSubcat(subcat);
//            txt.setText(textcontents);
//            txt.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
//            if(txt.getText().isEmpty())
//            {
//                Toast.makeText(Timeline_new.this,"Enter the Text",Toast.LENGTH_SHORT).show();
//
//            }
//            else if( txt.getDesc().isEmpty())
//            {
//                Toast.makeText(Timeline_new.this,"Enter The description", Toast.LENGTH_SHORT).show();
//
//            }
//            if(tar.equals("GROUP"))
//            {
//                txt.setTarget("all");
//                txt.setTargetmems(new ArrayList<String>());
//                puttext();
//            }
//            else if(tar.equals("INDIVIDUAL"))
//            {
//                final List<CharSequence> list=new ArrayList<CharSequence>();
//                System.out.println("Inside indi");
//                //i've added sample names to the list at the top
////                    View openDialog = (View) findViewById(R.id.openDialog);
////                    openDialog.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
//
////                            // Intialize  readable sequence of char values
//                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
//                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
//                            String uname = cred_update.getUsn();
//                            if(!uname.equals(CurrentUser.user))
//                            {
//                                list.add(uname);
//                            }
//
//                        }
//                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
//                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline_new.this);
//                        builderDialog.setTitle("Select Contact");
//                        int count = dialogList.length;
//                        boolean[] is_checked = new boolean[count];
//
//                        // Creating multiple selection by using setMutliChoiceItem method
//                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
//                                new DialogInterface.OnMultiChoiceClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int whichButton, boolean isChecked) {
//                                    }
//                                });
//
//                        builderDialog.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        ListView list = ((AlertDialog) dialog).getListView();
//                                        tempmems=new ArrayList<String>();
//                                        // make selected item in the comma seprated string
//                                        StringBuilder stringBuilder = new StringBuilder();
//                                        for (int i = 0; i < list.getCount(); i++) {
//                                            boolean checked = list.isItemChecked(i);
//
//                                            if (checked) {
//                                                tempmems.add(list.getItemAtPosition(i).toString());
//                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
//                                                stringBuilder.append(list.getItemAtPosition(i));
//                                            }
//                                        }
//
//                                        if (stringBuilder.toString().trim().equals("")) {
//                                            Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//
//                                        } else {
//                                            txt.setTarget("indi");
//                                            targetmems=new ArrayList(tempmems);
//                                            txt.setTargetmems(targetmems);
//                                            puttext();
//                                            Toast.makeText(Timeline_new.this,"Contacts selected",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                        builderDialog.setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        AlertDialog alert = builderDialog.create();
//                        alert.show();
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//
//
//
//
//            }
//
////                        Intent i = new Intent(Timeline_new.this, Timeline.class);
////                        i.putExtra("bool1", false);
////                        startActivity(i);
////                        finish();
//
//
//            //  Toast.makeText(Timeline_new.this,"Text Shared", Toast.LENGTH_LONG).show();
//
//
//            return "SUCCESS";
//        }
//
//
//        // Timeline_new.this runs in UI when background thread finishes
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (result.equals("SUCCESS")) {
//                System.out.println("SUCCESS");
//
//            }
//
//
//            //    progressDialog.dismiss();
//            // Do things like hide the progress bar or change a TextView
//        }
//    }
//    private class MyTaski extends AsyncTask<String, Integer, String> {
//
//        // Runs in UI before background thread is called
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //     progressDialog = ProgressDialog.show(Timeline_new.this, "Message", "Uploading Image...");
//
//        }
//
//        // Timeline_new.this is run in a background thread
//        @Override
//        protected String doInBackground(String... params) {
//
//
//
//
//            img = new ImageDesc();
//            img.setUser(CurrentUser.user);
//
//            img.setDesc(idesc);
//            img.setMaincat(maincat);
//            img.setSubcat(subcat);
//
//
//            img.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
//            if (tar.equals("GROUP")) {
//                img.setTarget("all");
//                img.setTargetmems(new ArrayList<String>());
//                putimage();
//            } else if (tar.equals("INDIVIDUAL")) {
//                final List<CharSequence> list=new ArrayList<CharSequence>();
//                System.out.println("Inside indi");
//                //i've added sample names to the list at the top
////                    View openDialog = (View) findViewById(R.id.openDialog);
////                    openDialog.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
//
////                            // Intialize  readable sequence of char values
//                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
//                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
//                            String uname = cred_update.getUsn();
//                            if(!uname.equals(CurrentUser.user))
//                            {
//                                list.add(uname);
//                            }
//                        }
//                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
//                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline_new.this);
//                        builderDialog.setTitle("Select Contact");
//                        int count = dialogList.length;
//                        boolean[] is_checked = new boolean[count];
//
//                        // Creating multiple selection by using setMutliChoiceItem method
//                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
//                                new DialogInterface.OnMultiChoiceClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int whichButton, boolean isChecked) {
//                                    }
//                                });
//
//                        builderDialog.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        ListView list = ((AlertDialog) dialog).getListView();
//                                        tempmems=new ArrayList<String>();
//                                        // make selected item in the comma seprated string
//                                        StringBuilder stringBuilder = new StringBuilder();
//                                        for (int i = 0; i < list.getCount(); i++) {
//                                            boolean checked = list.isItemChecked(i);
//
//                                            if (checked) {
//                                                tempmems.add(list.getItemAtPosition(i).toString());
//                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
//                                                stringBuilder.append(list.getItemAtPosition(i));
//                                            }
//                                        }
//
//                                        if (stringBuilder.toString().trim().equals("")) {
//                                            Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//
//                                        } else {
//                                            img.setTarget("indi");
//                                            ArrayList targetmems = new ArrayList(tempmems);
//                                            img.setTargetmems(targetmems);
//                                            putimage();
//                                            Toast.makeText(Timeline_new.this,"Contacts selected",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                        builderDialog.setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        AlertDialog alert = builderDialog.create();
//                        alert.show();
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//
//
//            }
//
//
//
//
//            return "SUCCESS";
//        }
//
//
//        // Timeline_new.this runs in UI when background thread finishes
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (result.equals("SUCCESS")) {
//                System.out.println("SUCCESS");
//
//            }
//            //    progressDialog.dismiss();
//            // Do things like hide the progress bar or change a TextView
//        }
//
//        public String getFileName(Uri uri) {
//            String result = null;
//            if (uri.getScheme().equals("content")) {
//                Cursor cursor = Timeline_new.this.getContentResolver().query(uri, null, null, null, null);
//                try {
//                    if (cursor != null && cursor.moveToFirst()) {
//                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                    }
//                } finally {
//                    cursor.close();
//                }
//            }
//            if (result == null) {
//                result = uri.getPath();
//                int cut = result.lastIndexOf('/');
//                if (cut != -1) {
//                    result = result.substring(cut + 1);
//                }
//            }
//            return result;
//        }
//    }
//
//    private class MyTaska extends AsyncTask<String, Integer, String> {
//
//        // Runs in UI before background thread is called
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //     progressDialog = ProgressDialog.show(Timeline_new.this, "Message", "Uploading Image...");
//
//        }
//
//        // Timeline_new.this is run in a background thread
//        @Override
//        protected String doInBackground(String... params) {
//
//            aud = new Audiodesc();
//            aud.setUser(CurrentUser.user);
//
//            aud.setDesc(adesc);
//            aud.setMaincat(maincat);
//            aud.setSubcat(subcat);
//
//            aud.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
//            if (aud.getDesc().isEmpty()) {
//                Toast.makeText(Timeline_new.this, "Enter the Description", Toast.LENGTH_SHORT).show();
//
//            }
//
//
//            if (tar.equals("GROUP")) {
//                aud.setTarget("all");
//                aud.setTargetmems(new ArrayList<String>());
//                putaudio();
//            } else if (tar.equals("INDIVIDUAL")) {
//                final List<CharSequence> list=new ArrayList<CharSequence>();
//                System.out.println("Inside indi");
//                //i've added sample names to the list at the top
////                    View openDialog = (View) findViewById(R.id.openDialog);
////                    openDialog.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
//
////                            // Intialize  readable sequence of char values
//                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
//                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
//                            String uname = cred_update.getUsn();
//                            if(!uname.equals(CurrentUser.user))
//                            {
//                                list.add(uname);
//                            }
//                        }
//                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
//                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline_new.this);
//                        builderDialog.setTitle("Select Contact");
//                        int count = dialogList.length;
//                        boolean[] is_checked = new boolean[count];
//
//                        // Creating multiple selection by using setMutliChoiceItem method
//                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
//                                new DialogInterface.OnMultiChoiceClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int whichButton, boolean isChecked) {
//                                    }
//                                });
//
//                        builderDialog.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        ListView list = ((AlertDialog) dialog).getListView();
//                                        tempmems=new ArrayList<String>();
//                                        // make selected item in the comma seprated string
//                                        StringBuilder stringBuilder = new StringBuilder();
//                                        for (int i = 0; i < list.getCount(); i++) {
//                                            boolean checked = list.isItemChecked(i);
//
//                                            if (checked) {
//                                                tempmems.add(list.getItemAtPosition(i).toString());
//                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
//                                                stringBuilder.append(list.getItemAtPosition(i));
//                                            }
//                                        }
//
//                                        if (stringBuilder.toString().trim().equals("")) {
//                                            Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//
//                                        } else {
//                                            aud.setTarget("indi");
//                                            ArrayList targetmems = new ArrayList(tempmems);
//                                            aud.setTargetmems(targetmems);
//                                            putaudio();
//                                            Toast.makeText(Timeline_new.this,"Contacts selected",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                        builderDialog.setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        AlertDialog alert = builderDialog.create();
//                        alert.show();
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//
//
//            }
//
//
//            return "SUCCESS";
//        }
//
//
//        // Timeline_new.this runs in UI when background thread finishes
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (result.equals("SUCCESS"))
//                System.out.println("SUCCESS");
//
//
//            //    progressDialog.dismiss();
//            // Do things like hide the progress bar or change a TextView
//        }



//    private class MyTaskv extends AsyncTask<String, Integer, String> {
//
//        // Runs in UI before background thread is called
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //     progressDialog = ProgressDialog.show(Timeline_new.this, "Message", "Uploading Image...");
//
//        }
//
//        // Timeline_new.this is run in a background thread
//        @Override
//        protected String doInBackground(String... params) {
//
//
//
//
//            vid = new VideoDesc();
//            vid.setUser(CurrentUser.user);
//
//            vid.setDesc(vdesc);
//            vid.setMaincat(maincat);
//            vid.setSubcat(subcat);
//
//
//            vid.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
//
//            if (tar.equals("GROUP")) {
//                vid.setTarget("all");
//                vid.setTargetmems(new ArrayList<String>());
//                putvideo();
//            } else if (tar.equals("INDIVIDUAL")) {
//                final List<CharSequence> list=new ArrayList<CharSequence>();
//                System.out.println("Inside indi");
//                //i've added sample names to the list at the top
////                    View openDialog = (View) findViewById(R.id.openDialog);
////                    openDialog.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
//
////                            // Intialize  readable sequence of char values
//                fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
//                fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
//                            String uname = cred_update.getUsn();
//                            if(!uname.equals(CurrentUser.user))
//                            {
//                                list.add(uname);
//                            }
//                        }
//                        final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
//                        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline_new.this);
//                        builderDialog.setTitle("Select Contact");
//                        int count = dialogList.length;
//                        boolean[] is_checked = new boolean[count];
//
//                        // Creating multiple selection by using setMutliChoiceItem method
//                        builderDialog.setMultiChoiceItems(dialogList, is_checked,
//                                new DialogInterface.OnMultiChoiceClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int whichButton, boolean isChecked) {
//                                    }
//                                });
//
//                        builderDialog.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        ListView list = ((AlertDialog) dialog).getListView();
//                                        tempmems=new ArrayList<String>();
//                                        // make selected item in the comma seprated string
//                                        StringBuilder stringBuilder = new StringBuilder();
//                                        for (int i = 0; i < list.getCount(); i++) {
//                                            boolean checked = list.isItemChecked(i);
//
//                                            if (checked) {
//                                                tempmems.add(list.getItemAtPosition(i).toString());
//                                                if (stringBuilder.length() > 0) stringBuilder.append(",");
//                                                stringBuilder.append(list.getItemAtPosition(i));
//                                            }
//                                        }
//
//                                        if (stringBuilder.toString().trim().equals("")) {
//                                            Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//
//                                        } else {
//                                            vid.setTarget("indi");
//                                            ArrayList targetmems = new ArrayList(tempmems);
//                                            vid.setTargetmems(targetmems);
//                                            putvideo();
//                                            Toast.makeText(Timeline_new.this,"Contacts selected",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                        builderDialog.setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        AlertDialog alert = builderDialog.create();
//                        alert.show();
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//
//
//            }
//
//
//
//            return "SUCCESS";
//        }
//

        // Timeline_new.this runs in UI when background thread finishes
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (result.equals("SUCCESS")) {
//                System.out.println("SUCCESS");
//
//            }
//
//            //    progressDialog.dismiss();
//            // Do things like hide the progress bar or change a TextView
//        }

//    private class MyTaskf extends AsyncTask<String, Integer, String> {
//
//        // Runs in UI before background thread is called
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // progressDialog = ProgressDialog.show(Files_PU.Timeline_new.this, "Message", "Uploading File...");
//
//        }
//
//        // Timeline_new.this is run in a background thread
//        @Override
//        protected String doInBackground(String... params) {
//            // get the string from params, which is an array
//            try {
//                fil = new FileDesc();
//                fil.setUser(CurrentUser.user);
//
//                fil.setDesc(fdesc);
//                fil.setMaincat(maincat);
//                fil.setSubcat(subcat);
//                System.out.println("Mangatha dawww");
//
//
//                System.out.println("FDESC2" + fil.getDesc());
//
//                fil.setDate(new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()));
//
//                if (tar.equals("GROUP")) {
//                    fil.setTarget("all");
//                    fil.setTargetmems(new ArrayList<String>());
//                    putfile();
//                } else if (tar.equals("INDIVIDUAL")) {
//                    final List<CharSequence> list=new ArrayList<CharSequence>();
//                    System.out.println("Inside indi");
//                    //i've added sample names to the list at the top
////                    View openDialog = (View) findViewById(R.id.openDialog);
////                    openDialog.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
//
////                            // Intialize  readable sequence of char values
//                    fb_db2 = new Firebase(Base_url+"Classes/"+CurrentUser.sclass+"/"+CurrentUser.ssec+"/Members/");
//                    fb_db2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                                Cred_Update cred_update = postSnapshot.getValue(Cred_Update.class);
//                                String uname = cred_update.getUsn();
//                                if(!uname.equals(CurrentUser.user))
//                                {
//                                    list.add(uname);
//                                }
//                            }
//                            final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
//                            final AlertDialog.Builder builderDialog = new AlertDialog.Builder(Timeline_new.this);
//                            builderDialog.setTitle("Select Contact");
//                            int count = dialogList.length;
//                            boolean[] is_checked = new boolean[count];
//
//                            // Creating multiple selection by using setMutliChoiceItem method
//                            builderDialog.setMultiChoiceItems(dialogList, is_checked,
//                                    new DialogInterface.OnMultiChoiceClickListener() {
//                                        public void onClick(DialogInterface dialog,
//                                                            int whichButton, boolean isChecked) {
//                                        }
//                                    });
//
//                            builderDialog.setPositiveButton("OK",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                            ListView list = ((AlertDialog) dialog).getListView();
//                                            tempmems=new ArrayList<String>();
//                                            // make selected item in the comma seprated string
//                                            StringBuilder stringBuilder = new StringBuilder();
//                                            for (int i = 0; i < list.getCount(); i++) {
//                                                boolean checked = list.isItemChecked(i);
//
//                                                if (checked) {
//                                                    tempmems.add(list.getItemAtPosition(i).toString());
//                                                    if (stringBuilder.length() > 0) stringBuilder.append(",");
//                                                    stringBuilder.append(list.getItemAtPosition(i));
//                                                }
//                                            }
//
//                                            if (stringBuilder.toString().trim().equals("")) {
//                                                Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//
//                                            } else {
//                                                fil.setTarget("indi");
//                                                ArrayList targetmems = new ArrayList(tempmems);
//                                                fil.setTargetmems(targetmems);
//                                                putfile();
//                                                Toast.makeText(Timeline_new.this,"Contacts selected",Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//
//                            builderDialog.setNegativeButton("Cancel",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Toast.makeText(Timeline_new.this,"No contact selected",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                            AlertDialog alert = builderDialog.create();
//                            alert.show();
//
//                        }
//
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    });
//
//
//                }
//
//
//
//
//
//
//                return "SUCCESS";
//
//            } catch (Exception e) {
////                Toast.makeText(Timeline_new.this, "Network Busy!!", Toast.LENGTH_LONG).show();
//                //System.out.println("Error SMS "+e);
//
//            }
//
//            return "FAILED";
//        }
//
//
//
//        // Timeline_new.this runs in UI when background thread finishes
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if(result.equals("SUCCESS")){
//                System.out.println("SUCCESS");
//
//            }
}
