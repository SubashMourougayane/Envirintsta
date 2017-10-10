package bluepanther.envirinsta.ContentDesc;

import java.util.ArrayList;

/**
 * Created by SUBASH.M on 11/4/2016.
 */

public class Audiodesc
{
    //testing1;;
    public String user;
    public String date;
    public String desc;
    public String maincat;

    public String getResolvers() {
        return resolvers;
    }

    public void setResolvers(String resolvers) {
        this.resolvers = resolvers;
    }

    public String getResdate() {
        return resdate;
    }

    public void setResdate(String resdate) {
        this.resdate = resdate;
    }

    public String resolvers;
    public String resdate;


    public String subcat;
    public String target;
    public ArrayList<String> targetmems;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ArrayList<String> getTargetmems() {
        return targetmems;
    }

    public void setTargetmems(ArrayList<String> targetmems) {
        this.targetmems = targetmems;
    }

    public Audiodesc()
    {

    }
    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMaincat() {
        return maincat;
    }

    public void setMaincat(String maincat) {
        this.maincat = maincat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
