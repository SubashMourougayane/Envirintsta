package bluepanther.envirinsta.Officials;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import bluepanther.envirinsta.R;

/**
 * Created by hhs on 24/2/17.
 */

public class Off_Dis_audio extends AppCompatActivity {

    TextView textView5,textView15, textView16, textView17,change_status;
    EditText editText2;
    SeekBar seekBar1;
    ImageView imageView10, imageView4, imageView5, imageView7, imageView8;

    String[] descriptionData = {"One", "Two", "Three", "Four"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_discussions_audio);

        change_status = (TextView) findViewById(R.id.change_status);

        final StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
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

        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items ={"Posted", "Noticed", "In Progress", "Resolved"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Off_Dis_audio.this);
                builder.setTitle("Change Status to...");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Posted")) {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);

                        } else if (items[item].equals("Noticed")) {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

                        } else if (items[item].equals("In Progress")) {
                            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

                        } else if (items[item].equals("Resolved")) {
                            // stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                            stateProgressBar.setAllStatesCompleted(true);
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
