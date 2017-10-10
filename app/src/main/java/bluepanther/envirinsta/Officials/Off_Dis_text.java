package bluepanther.envirinsta.Officials;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import bluepanther.envirinsta.R;

/**
 * Created by hhs on 24/2/17.
 */

public class Off_Dis_text extends AppCompatActivity {

    TextView textView5,change_status,textView15, textView16, textView17, textView18;
    EditText editText2;
    ImageView imageView10;

    String[] descriptionData = {"One", "Two", "Three", "Four"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_discussions_text);

        change_status = (TextView) findViewById(R.id.change_status);
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
        final StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);

        change_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items ={"Posted", "Noticed", "In Progress", "Resolved"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Off_Dis_text.this);
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
