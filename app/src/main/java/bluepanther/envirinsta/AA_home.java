package bluepanther.envirinsta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.Current_Comm;
import bluepanther.envirinsta.Signing.Comer_Sign_Up;
import bluepanther.envirinsta.Signing.MainActivity;
import bluepanther.envirinsta.Signing.Off_Sign_Up;
import bluepanther.envirinsta.Signing.Sign_In;
import bluepanther.envirinsta.Signing.Sign_Up;

/**
 * Created by Hariharsudan HHS on 21-03-2017.
 */

public class AA_home extends AppCompatActivity {

    public static String lol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if(pref.getBoolean("islog",false))
        {
            if(pref.getString("usertype",null).equals("commercial"))
            {
                new Current_Comm(pref.getString("user", null), pref.getString("pass", null),pref.getString("com_fname",null),pref.getString("com_prod",null));
            }
            else {
                new CurrentUser(pref.getString("user", null), pref.getString("pass", null), pref.getString("mobile",null),pref.getString("sclass", null), pref.getString("ssec", null), pref.getString("idate", null), pref.getString("adate", null), pref.getString("vdate", null), pref.getString("fdate", null), pref.getString("tdate", null), pref.getString("type", null));
            }
            Intent intent = new Intent(AA_home.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.aa_home);

        final TextView user = (TextView) findViewById(R.id.user);
        final TextView official = (TextView) findViewById(R.id.official);
        final ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);
        final ImageView imageView5 = (ImageView) findViewById(R.id.imageView5);
        final ImageView imageView7 = (ImageView) findViewById(R.id.imageView7);
        final TextView comm = (TextView) findViewById(R.id.comm);
        final Button next = (Button) findViewById(R.id.next);
        next.setClickable(false);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView4.setImageResource(R.drawable.user);
                imageView5.setImageResource(R.drawable.official2);
                imageView7.setImageResource(R.drawable.comm);
                user.setTextColor(getResources().getColor(R.color.colorPrimary));
                official.setTextColor(getResources().getColor(R.color.pb_grey));
                comm.setTextColor(getResources().getColor(R.color.pb_grey));
                next.setClickable(true);
                 lol = "user";


            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView5.setImageResource(R.drawable.official);
                imageView4.setImageResource(R.drawable.user2);
                imageView7.setImageResource(R.drawable.comm);
                official.setTextColor(getResources().getColor(R.color.colorPrimary));
                user.setTextColor(getResources().getColor(R.color.pb_grey));
                comm.setTextColor(getResources().getColor(R.color.pb_grey));
                next.setClickable(true);
                lol="official";

            }
        });

        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lol = "commercial";
                imageView7.setImageResource(R.drawable.com);
                imageView5.setImageResource(R.drawable.official2);
                imageView4.setImageResource(R.drawable.user2);
                comm.setTextColor(getResources().getColor(R.color.colorPrimary));
                user.setTextColor(getResources().getColor(R.color.pb_grey));
                official.setTextColor(getResources().getColor(R.color.pb_grey));
                next.setClickable(true);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("usertype",lol);
                editor.commit();
                if(lol=="user")
                {
                    Intent i = new Intent(AA_home.this, Sign_In.class);
                    startActivity(i);
                }
                if(lol=="official")
                {
                    Intent i = new Intent(AA_home.this, Sign_In.class);
                    startActivity(i);
                }
                if(lol.equals("commercial"))
                {
                    Intent i = new Intent(AA_home.this, Sign_In.class);
                    startActivity(i);
                }


            }
        });
    }
}
