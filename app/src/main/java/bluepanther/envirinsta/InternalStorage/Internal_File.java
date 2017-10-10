package bluepanther.envirinsta.InternalStorage;

import java.io.Serializable;
import java.util.List;

import bluepanther.envirinsta.Adapter.RowItem;


/**
 * Created by SUBASH.M on 11/8/2016.
 */

public class Internal_File implements Serializable
{
    public List<RowItem> filecontent;

    public Internal_File(List<RowItem> filecontent)
    {
        this.filecontent = filecontent;
    }
}
