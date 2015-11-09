package helperclasses;

/**
 * Created by prashantkumar on 05/11/15.
 */
public class Category
{
    String categoryName;
    String categoryId;
    public Category(String categoryName,String categoryId)
    {
        this.categoryName=categoryName;
        this.categoryId=categoryId;
    }
    public String getName()
    {
        return categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }
}
