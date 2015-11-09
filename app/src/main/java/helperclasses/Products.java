package helperclasses;

/**
 * Created by prashantkumar on 09/11/15.
 */
public class Products
{
    String productName;
    String productSku;
    String productId;
    String productRegularPrice;
    String productSalePrice;
    String imageUrl;

    public Products(String productName, String productSku,String productId,String productRegularPrice,String productSalePrice,String imageUrl) {
        this.productName = productName;
        this.productSku = productSku;
        this.productId=productId;
        this.productRegularPrice = productRegularPrice;
        this.productSalePrice=productSalePrice;
        this.imageUrl=imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductSku() {
        return productSku;
    }

    public String getProductRegularPrice() {
        return productRegularPrice;
    }

    public String getProductSalePrice() {
        return productSalePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductId() {
        return productId;
    }
}
