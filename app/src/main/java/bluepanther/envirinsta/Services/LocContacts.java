package bluepanther.envirinsta.Services;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shyam on 2/4/17.
 */

public class LocContacts implements Serializable
{
    public ArrayList<String>contacts,phones;
    public LocContacts(ArrayList<String> contacts,ArrayList<String>phones)
    {
        this.contacts=new ArrayList<>(contacts);
        this.phones=new ArrayList<>(phones);
    }
}
