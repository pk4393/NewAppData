package helperclasses;

/**
 * Created by prashantkumar on 06/11/15.
 */
public class StoreDetail
{
    String storeName;
    String address;
    String countryName;
    String latitude;
    String longitude;

    public StoreDetail(String storeName,String address,String countryName,String latitude,String longitude)
    {
        this.storeName=storeName;
        this.address=address;
        this.countryName=countryName;
        this.latitude=latitude;
        this.longitude = longitude;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
