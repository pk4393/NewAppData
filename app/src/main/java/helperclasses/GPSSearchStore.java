package helperclasses;

/**
 * Created by prashantkumar on 06/11/15.
 */
public class GPSSearchStore
{
    String storeName;
    String distance;
    String storeId;

    public GPSSearchStore(String storeName, String distance,String storeId) {
        this.storeName = storeName;
        this.distance = distance;
        this.storeId=storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getDistance() {
        return distance;
    }

    public String getStoreId() {
        return storeId;
    }
}
