package bluepanther.envirinsta.NGO_Grid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SUBASH.M on 12/18/2016.
 */

public class newNGO implements Serializable
{
    public String ngoname;

    public String getNgoadmin() {
        return ngoadmin;
    }

    public void setNgoadmin(String ngoadmin) {
        this.ngoadmin = ngoadmin;
    }

    public String getNgoinfo() {
        return ngoinfo;
    }

    public void setNgoinfo(String ngoinfo) {
        this.ngoinfo = ngoinfo;
    }

    public String getNgoname() {
        return ngoname;
    }

    public void setNgoname(String ngoname) {
        this.ngoname = ngoname;
    }

    public String getNgopass() {
        return ngopass;
    }

    public void setNgopass(String ngopass) {
        this.ngopass = ngopass;
    }

    public String getNgopurpose() {
        return ngopurpose;
    }

    public void setNgopurpose(String ngopurpose) {
        this.ngopurpose = ngopurpose;
    }

    public String getNgouname() {
        return ngouname;
    }

    public void setNgouname(String ngouname) {
        this.ngouname = ngouname;
    }

    public String ngoadmin;
    public String ngopurpose;
    public String ngoinfo;
    public String ngouname;
    public String ngopass;
    public ArrayList<String> followers;





    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public newNGO()
    {

    }

}
