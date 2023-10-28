import java.io.Serializable;

public class Services implements Serializable {
    private final String name;
    private final double price;
    private String time;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }


    public String getTime() {
        return time;
    }

    public Services setTime(String time) {
        this.time = time;
        return null;
    }

    public Services(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " " + price + "р. ";
    }

}
