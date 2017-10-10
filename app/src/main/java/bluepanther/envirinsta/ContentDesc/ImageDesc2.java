package bluepanther.envirinsta.ContentDesc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SUBASH.M on 11/3/2016.
 */

public class ImageDesc2 implements Serializable
{

    public String user;
    public String date;
    public String desc;
    public String maincat;
    public String path;
    public String latitude;
    public String longitude;
    public String subcat;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String type;


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {

        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public ImageDesc2()
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
