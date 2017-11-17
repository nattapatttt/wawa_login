package wawa.labwawa;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Win 8 Pro on 13/8/2560.
 */

public class Customer
{
    @SerializedName("id")
    public long Id;

    @SerializedName("name")
    public String Name;


    @SerializedName("surname")
    public String surname;

    @SerializedName("telephone")
    public String Telephone;

    @SerializedName("email")
    public String Email;

    @SerializedName("username")
    public String Username;

    @SerializedName("password")
    public String Password;

    @SerializedName("photoUrl")
    public String PhotoUrl;



}
