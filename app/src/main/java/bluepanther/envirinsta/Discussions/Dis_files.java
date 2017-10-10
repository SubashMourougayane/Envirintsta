package bluepanther.envirinsta.Discussions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import bluepanther.envirinsta.R;

/**
 * Created by hhs on 24/2/17.
 */

public class Dis_files extends AppCompatActivity {

    TextView textView5,textView15, textView16, textView17, textView18;
    EditText editText2;
    ImageView imageView10;

    String[] descriptionData = {"One", "Two", "Three", "Four"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussions_files);
        getSupportActionBar().setTitle("Issue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        textView5 = (TextView) findViewById(R.id.textView5);
//        textView15 = (TextView) findViewById(R.id.textView15);
//        textView16 = (TextView) findViewById(R.id.textView16);
//        textView17 = (TextView) findViewById(R.id.textView17);
//        textView18 = (TextView) findViewById(R.id.textView18);
//
//        editText2 = (EditText) findViewById(R.id.editText2);
//
//        imageView10 = (ImageView) findViewById(R.id.imageView10);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
