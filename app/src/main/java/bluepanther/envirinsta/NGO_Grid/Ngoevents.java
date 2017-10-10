package bluepanther.envirinsta.NGO_Grid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SUBASH.M on 12/18/2016.
 */

public class Ngoevents implements Serializable
{

    public String nevent;
    public String norg;
    public String npurp;
    public ArrayList<String> joiners;



    public ArrayList<String> getJoiners() {
        return joiners;
    }

    public void setJoiners(ArrayList<String> joiners) {
        this.joiners = joiners;
    }

    public String getNtime() {
        return ntime;
    }

    public void setNtime(String ntime) {
        this.ntime = ntime;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public String getNevent() {
        return nevent;
    }

    public void setNevent(String nevent) {
        this.nevent = nevent;
    }

    public String getNorg() {
        return norg;
    }

    public void setNorg(String norg) {
        this.norg = norg;
    }

    public String getNplace() {
        return nplace;
    }

    public void setNplace(String nplace) {
        this.nplace = nplace;
    }

    public String getNpurp() {
        return npurp;
    }

    public void setNpurp(String npurp) {
        this.npurp = npurp;
    }

    public String nplace;
    public String ntime;
    public String ndate;

    public Ngoevents()
    {

    }

}
