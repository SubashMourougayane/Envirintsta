package bluepanther.envirinsta.LeaderBoards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

import bluepanther.envirinsta.Adapter.CustomAdapterCon;
import bluepanther.envirinsta.Adapter.CustomAdapterGR;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.RepContent;

/**
 * Created by shyamjoval on 1/16/2017.
 */

public class Leaderboards_tab2 extends Fragment {
    static String file1;
    static String result;
    ProgressDialog progressDialog;
    static RecyclerView mylistview;
    static Activity context;
    public  static View.OnClickListener myOnClickListener;
    private RecyclerView.LayoutManager layoutManager;
    static public String Base_url = "https://envirinsta.firebaseio.com/";
    static public Firebase fb_db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.leaderboards_tab2, container, false);
        mylistview = (RecyclerView) parentView.findViewById(R.id.myListView);
        mylistview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mylistview.setLayoutManager(layoutManager);
        mylistview.setItemAnimator(new DefaultItemAnimator());
      //  adapter = new CustomAdapterGR(getActivity(), RepContent.grpcontent);
        //Firebase.setAndroidContext(getActivity());
        //fb_db = new Firebase(Base_url);
       // mylistview.setAdapter(adapter);
        context=getActivity();
      
        
        return parentView;
    }
    public static void setContent(CustomAdapterCon adapter)
    {
        System.out.println("Setting Timeline");
        mylistview.setAdapter(adapter);
    }


}
