package bluepanther.envirinsta.Adapter;

import java.io.Serializable;

/**
 * Created by SUBASH.M on 11/3/2016.
 */

public class CurrentUser implements Serializable
{
    public static String user,pass,sclass,ssec,sidate,sadate,svdate,sfdate,stdate,type,mobile;
    public  String user2,pass2,sclass2,ssec2,sidate2,sadate2,svdate2,sfdate2,stdate2,type2,mobile2;
public CurrentUser()
{

}
    public CurrentUser(String user,String pass,String mobile,String sclass,String ssec,String sidate,String sadate,String svdate,String sfdate,String stdate,String type)
    {
        this.user = user;
        this.sclass = sclass;
        this.ssec = ssec;
        this.sidate=sidate;
        this.sadate=sadate;
        this.svdate=svdate;
        this.sfdate=sfdate;
        this.stdate=stdate;
        this.pass=pass;
        this.type=type;
        this.mobile=mobile;

        this.user2 = user;
        this.sclass2 = sclass;
        this.ssec2 = ssec;
        this.sidate2=sidate;
        this.sadate2=sadate;
        this.svdate2=svdate;
        this.sfdate2=sfdate;
        this.stdate2=stdate;
        this.pass2=pass;
        this.type2=type;
        this.mobile2=mobile;
    }

}
//CurrentUser.user;