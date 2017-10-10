package bluepanther.envirinsta.Timeline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import bluepanther.envirinsta.R;

/**
 * Created by Hariharsudan on 11/6/2016.
 */

public class SpaceView extends AppCompatActivity {

    private SpaceNavigationView spaceNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_view);
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("GROUP", R.drawable.ic_action_group_sv));
        spaceNavigationView.addSpaceItem(new SpaceItem("INDIVIDUAL", R.drawable.ic_action_indi_sv));
        spaceNavigationView.shouldShowFullBadgeText(true);
        spaceNavigationView.setBackgroundColor(getResources().getColor(R.color.bg));
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                spaceNavigationView.shouldShowFullBadgeText(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
            }
        });

        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(SpaceView.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(SpaceView.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }


}
