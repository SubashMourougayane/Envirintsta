package bluepanther.envirinsta.ContentDisp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import bluepanther.envirinsta.R;


public class txtdisp extends AppCompatActivity {
    TextView tv;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.txtdisp);
        tv=(TextView)findViewById(R.id.textdisplay);

//        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/Bulletto Killa.ttf");

        value=this.getIntent().getExtras().getString("value");
        System.out.println("fdfd"+value);
        tv.setText(value);
     //   tv.setTypeface(face);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .85), (int) (height * .7));

    }
}
