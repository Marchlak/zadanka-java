public class Product implements ProductInterface {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public double getPrice(){
        return price;
    }

    @Override
    public String toString(){
        return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}
