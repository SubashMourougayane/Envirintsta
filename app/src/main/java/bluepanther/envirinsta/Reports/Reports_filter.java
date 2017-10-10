package bluepanther.envirinsta.Reports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bluepanther.envirinsta.R;
import bluepanther.envirinsta.fragment.DateRangePickerFragment;

/**
 * Created by hhs on 24/2/17.
 */

public class Reports_filter  extends AppCompatActivity implements DateRangePickerFragment.OnDateRangeSelectedListener,OnMenuItemClickListener, OnMenuItemLongClickListener {
    private static final String[] MAIN_CATEGORY = {
            "Select Category", "Academics", "Administration", "Extra-Curriculars", "Miscellaneous"
    };

    TextView textView, textView2;
    String day1, day2, month1, month2, year1, year2, sfrom="", sto="";
    Date sfromd,stod;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filters");

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search View");

        textView = (TextView) findViewById(R.id.from_date);
        textView2 = (TextView) findViewById(R.id.to_date);

        Button filter = (Button) findViewById(R.id.filter);

        final MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerFragment dateRangePickerFragment=
                        DateRangePickerFragment.newInstance(Reports_filter.this,false);

                dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateRangePickerFragment dateRangePickerFragment=
                        DateRangePickerFragment.newInstance(Reports_filter.this,false);

                dateRangePickerFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Reports_filter.this, query,
                        Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(Reports_filter.this, newText,
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        spinner.setItems(MAIN_CATEGORY);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onDateRangeSelected(int startDay, int startMonth, int
            startYear, int endDay, int endMonth, int endYear) throws ParseException {
        Log.d("range : ","from:"+startDay+"-"+startMonth+"-"+startYear+" to :"+endDay+"-"+endMonth+"-"+endYear );

        String s1 = (startDay+"-"+(startMonth+1)+"-"+startYear).toString().trim();
        String s2 = (endDay+"-"+(endMonth+1)+"-"+endYear).toString().trim();
        textView.setText(s1);
        textView2.setText(s2);
        //  filter_object.mName =
        (startDay+"-"+startMonth+"-"+startYear).toString().trim();

        //  filter_object.mIsSelected = false;
        //   mArrFilter.add(filter_object);

//        mScrollViewFilter = (ScrollView)findViewById(R.id.scrollViewFilter);
//        mFlowLayoutFilter = (FlowLayout)findViewById(R.id.flowLayout);
//
//        mFilter_Adapter = new Filter_Adapter(mArrFilter);
        day1 = String.valueOf(startDay);
        month1 = String.valueOf(startMonth + 1);
        year1 = String.valueOf(startYear);
        day2 = String.valueOf(endDay);
        month2 = String.valueOf(endMonth + 1);
        year2 = String.valueOf(endYear);
        if (day1.length() == 1) {
            day1 = "0" + day1;
        }
        if (month1.length() == 1) {
            month1 = "0" + month1;
        }
        if (day2.length() == 1) {
            day2 = "0" + day2;
        }
        if (month2.length() == 1) {
            month2 = "0" + month2;
        }
        year1=String.valueOf(year1.charAt(year1.length()-2))+String.valueOf(year1.charAt(year1.length()-1));
        year2=String.valueOf(year2.charAt(year2.length()-2))+String.valueOf(year2.charAt(year2.length()-1));
        sfrom = day1 + "-" + month1 + "-" + year1 + " 00:00:00";
        sto = day2 + "-" + month2 + "-" + year2 + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        sfromd=sdf.parse(sfrom);
        stod=sdf.parse(sto);
        System.out.println("Reports from is"+sfrom+" and "+sto);
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }
}
