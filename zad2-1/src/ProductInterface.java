import java.io.Serializable;

public interface ProductInterface extends Serializable {
    int getId();
    String getName();
    double getPrice();
}
