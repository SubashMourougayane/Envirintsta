package bluepanther.envirinsta.NGO_Grid;

/**
 * Created by Hariharsudan on 12/17/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bluepanther.envirinsta.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    public Context context;
    public String location;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        TextView dateTime,follow;
        FloatingActionButton fab;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            follow=(TextView)itemView.findViewById(R.id.textView13);
            fab=(FloatingActionButton)itemView.findViewById(R.id.fab);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(Context context, ArrayList<DataObject> myDataset) {
        this.context=context;

        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.dateTime.setText(mDataset.get(position).getmText2());
        holder.follow.setText(mDataset.get(position).getmText3());

        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location=mDataset.get(position).getmText4();

                Intent i=new Intent(context,ShowMap.class);
                i.putExtra("loc",location);
                context.startActivity(i);
            }
        });

    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}