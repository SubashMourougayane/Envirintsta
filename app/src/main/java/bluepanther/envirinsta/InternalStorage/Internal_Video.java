package bluepanther.envirinsta.InternalStorage;

import java.io.Serializable;
import java.util.List;

import bluepanther.envirinsta.Adapter.RowItem;


/**
 * Created by SUBASH.M on 11/8/2016.
 */

public class Internal_Video implements Serializable
{
    public List<RowItem> videocontent;

    public Internal_Video(List<RowItem> videocontent)
    {
        this.videocontent = videocontent;
    }
}
