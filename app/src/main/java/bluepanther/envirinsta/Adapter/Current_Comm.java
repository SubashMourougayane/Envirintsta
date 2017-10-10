package bluepanther.envirinsta.Adapter;

import java.io.Serializable;

/**
 * Created by Subash on 29-03-2017.
 */

public class Current_Comm implements Serializable
{
    public static String user,pass,fullname,prod;
    public  String user2,pass2,fullname2,prod2;
    public Current_Comm(){}

    public Current_Comm(String user,String pass,String fullname,String prod)
    {
        this.user = user;
        this.pass = pass;
        this.fullname = fullname;
        this.prod = prod;

        this.user2 = user;
        this.pass2 = pass;
        this.fullname2 = fullname;

        this.prod2 = prod;




    }
}
