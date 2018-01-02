package sort;

/**
 * Created by xiaoni on 2017/12/22.
 * 用于排序的类
 */
public class GoodsPrice implements Comparable<GoodsPrice>{
    public GoodsPrice(){}

    public GoodsPrice(String name, long price) {
        this.name = name;
        this.price = price;
    }

    String name;
    long price;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public int compareTo(GoodsPrice o) {
        if(this.price > o.price) {
            return 1;
        }else if(this.price < o.price) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
