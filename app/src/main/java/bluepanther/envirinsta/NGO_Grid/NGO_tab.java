package bluepanther.envirinsta.NGO_Grid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.R;

/**
 * Created by Hariharsudan on 11/2/2016.
 */

public class NGO_tab extends android.support.v4.app.Fragment {

    private static RecyclerView mRecyclerView;
    private  MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "NGOTab";
    public Firebase fb_db2;
    String tab,finalngo1,finalngo2;
    //ArrayList<DataObject> results = new ArrayList<DataObject>();
    private String Base_url = "https://envirinsta.firebaseio.com/";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ngo_tab, container, false);

        Firebase.setAndroidContext(getContext());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getActivity(),new ArrayList<DataObject>());
//        mRecyclerView.setAdapter(mAdapter);

        TempClass.sharedValue="NGO";
        tab=TempClass.sharedValue;



        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);

                try{AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Visit this NGO page?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    System.out.println("Ngo is"+NgoAct.results.get(position).getmText1());
                                    finalngo1=NgoAct.results.get(position).getmText1();
                                    finalngo2=NgoAct.results.get(position).getmText2();
                                    Intent intent = new Intent(getActivity(), NGO_user_page.class);
                                    intent.putExtra("NGOname",finalngo1);
                                    intent.putExtra("NGOinfo",finalngo2);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                catch(Exception e)
                {
                    System.out.println("Exception "+e);
                }


            }

        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    public static void setAdapter(MyRecyclerViewAdapter adapter)
    {
        mRecyclerView.setAdapter(adapter);
    }
}