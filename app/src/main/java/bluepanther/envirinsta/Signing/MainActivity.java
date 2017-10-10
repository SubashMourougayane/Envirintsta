package bluepanther.envirinsta.Signing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

import bluepanther.envirinsta.Commercial.Commercial_page1;
import bluepanther.envirinsta.Officials.Off_Home;
import bluepanther.envirinsta.Services.MyService;
import bluepanther.envirinsta.Timeline.Timeline;

import static bluepanther.envirinsta.AA_home.lol;


public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
// 3 if
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if(pref.getString("usertype",null).equals("user")||pref.getString("usertype",null).equals("official"))
        {
            startService(new Intent(this,MyService.class));
            Intent i=new Intent(this,Timeline.class);
            i.putExtra("noti","null");
            startActivity(i);
        }

        if(pref.getString("usertype",null).equals("commercial"))
        {
            Intent i=new Intent(this,Commercial_page1.class);
            startActivity(i);
        }


finish();
    }


}
