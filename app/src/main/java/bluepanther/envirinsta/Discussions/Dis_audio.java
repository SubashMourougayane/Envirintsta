package bluepanther.envirinsta.Discussions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import bluepanther.envirinsta.R;

/**
 * Created by hhs on 24/2/17.
 */

public class Dis_audio extends AppCompatActivity {

    TextView textView5,textView15, textView16, textView17;
    EditText editText2;
    SeekBar seekBar1;
    ImageView imageView10, imageView4, imageView5, imageView7, imageView8;

    String[] descriptionData = {"One", "Two", "Three", "Four"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussions_audio);

        getSupportActionBar().setTitle("Issue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

//        textView5 = (TextView) findViewById(R.id.textView5);
//        textView15 = (TextView) findViewById(R.id.textView15);
//        textView16 = (TextView) findViewById(R.id.textView16);
//        textView17 = (TextView) findViewById(R.id.textView17);
//
//        editText2 = (EditText) findViewById(R.id.editText2);
//
//        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
//
//        imageView10 = (ImageView) findViewById(R.id.imageView10);
//        imageView4 = (ImageView) findViewById(R.id.imageView4);
//        imageView7 = (ImageView) findViewById(R.id.imageView7);
//        imageView5 = (ImageView) findViewById(R.id.imageView5);
//        imageView8 = (ImageView) findViewById(R.id.imageView8);
//
//        getSupportActionBar().setTitle("Discussions");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
