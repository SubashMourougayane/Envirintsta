package bluepanther.envirinsta.Adapter;

import java.io.Serializable;

/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class RowItem implements Serializable {
    private String member_name;
    private int profile_pic_id;
    private String status;
    private String time;
    private String author;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RowItem(String member_name, int profile_pic_id, String status,
                   String time, String author,String type) {

        this.member_name = member_name;
        this.profile_pic_id = profile_pic_id;
        this.status = status;
        this.time = time;
        this.author=author;
        this.type=type;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public int getProfile_pic_id() {
        return profile_pic_id;
    }

    public void setProfile_pic_id(int profile_pic_id) {
        this.profile_pic_id = profile_pic_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
