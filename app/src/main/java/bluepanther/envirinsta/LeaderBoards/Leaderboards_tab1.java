package bluepanther.envirinsta.LeaderBoards;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.io.File;
import java.util.ArrayList;

import bluepanther.envirinsta.Adapter.CurrentUser;
import bluepanther.envirinsta.Adapter.CustomAdapterCon;
import bluepanther.envirinsta.Adapter.CustomAdapterGR;
import bluepanther.envirinsta.Adapter.CustomAdapterGT;
import bluepanther.envirinsta.ContentDesc.TextDesc;
import bluepanther.envirinsta.ContentDisp.imgdisp;
import bluepanther.envirinsta.ContentDisp.txtdisp;
import bluepanther.envirinsta.Discussions.Dis_image;
import bluepanther.envirinsta.FileUtils.ImgUri;
import bluepanther.envirinsta.R;
import bluepanther.envirinsta.Reports.RepContent;

/**
 * Created by shyamjoval on 1/16/2017.
 */

public class Leaderboards_tab1 extends Fragment {
    static String file1;
    static String result;
    ProgressDialog progressDialog;


    static RecyclerView mylistview;
    static Activity context;
    public  static View.OnClickListener myOnClickListener;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.leaderboards_tab1, container, false);
        mylistview = (RecyclerView) parentView.findViewById(R.id.myListView);
        mylistview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mylistview.setLayoutManager(layoutManager);
        mylistview.setItemAnimator(new DefaultItemAnimator());
        //adapter = new CustomAdapterGR(getActivity(), RepContent.grpcontent);




        
        return parentView;
    }
    public static void setContent(CustomAdapterCon adapter)
    {
        System.out.println("Setting Timeline");
        mylistview.setAdapter(adapter);
    }

}
