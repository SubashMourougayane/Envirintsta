package bluepanther.envirinsta.InternalStorage;

import java.io.Serializable;
import java.util.List;

import bluepanther.envirinsta.Adapter.RowItem;


/**
 * Created by SUBASH.M on 11/8/2016.
 */

public class Internal_Image implements Serializable
{
    public List<RowItem> imgcontent;

    public Internal_Image(List<RowItem> imgcontent)
    {
        this.imgcontent = imgcontent;
    }
}
