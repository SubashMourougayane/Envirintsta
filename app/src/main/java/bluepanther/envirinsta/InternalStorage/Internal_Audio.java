package bluepanther.envirinsta.InternalStorage;

import java.io.Serializable;
import java.util.List;

import bluepanther.envirinsta.Adapter.RowItem;


/**
 * Created by SUBASH.M on 11/8/2016.
 */

public class Internal_Audio implements Serializable
{
    public List<RowItem> audiocontent;

    public Internal_Audio(List<RowItem> audiocontent)
    {
        this.audiocontent = audiocontent;
    }
}
