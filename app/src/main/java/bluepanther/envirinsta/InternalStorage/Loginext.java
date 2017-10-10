package bluepanther.envirinsta.InternalStorage;

import java.io.Serializable;

import bluepanther.envirinsta.Adapter.CurrentUser;

/**
 * Created by SUBASH.M on 11/14/2016.
 */

public class Loginext implements Serializable {
    public CurrentUser user;
    public Boolean islog;
    public Loginext(CurrentUser user,Boolean islog)
    {
        this.user=user;
        this.islog=islog;
    }
}
