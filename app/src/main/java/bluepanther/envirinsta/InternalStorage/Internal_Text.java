package bluepanther.envirinsta.InternalStorage;

import java.io.Serializable;
import java.util.List;

import bluepanther.envirinsta.Adapter.RowItem;


/**
 * Created by SUBASH.M on 11/8/2016.
 */

public class Internal_Text implements Serializable
{
    public List<RowItem> textcontent;

    public Internal_Text(List<RowItem> textcontent)
    {
        this.textcontent = textcontent;
    }
}
