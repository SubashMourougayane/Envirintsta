package bluepanther.envirinsta.Contacts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bluepanther.envirinsta.R;

/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView profile_pic;
        TextView member_name;
        TextView status;
        TextView time;
        TextView author;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);

            holder.member_name = (TextView) convertView.findViewById(R.id.member_name);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.author = (TextView)convertView.findViewById(R.id.author);
//            holder.member_name.setTextColor(Color.WHITE);
//            holder.status.setTextColor(Color.WHITE);
//            holder.time.setTextColor(Color.WHITE);
//            holder.author.setTextColor(Color.WHITE);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        RowItem row_pos = rowItems.get(position);

        holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
        holder.member_name.setText(row_pos.getMember_name());
        holder.status.setText(row_pos.getStatus());
        holder.time.setText(row_pos.getTime());
        holder.author.setText(row_pos.getAuthor());
        //convertView.setBackgroundColor(Color.parseColor("#FFF05A4D"));

        return convertView;
    }


}
