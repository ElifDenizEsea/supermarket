package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public class ItemByWeight implements Item {

    private List<DiscountType> attachedDiscounts;
    private final WeighedProduct product;
    private final BigDecimal weightInKilos;
    private double kg;

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }

    ItemByWeight(final WeighedProduct product, final BigDecimal weightInKilos) {
        this.product = product;
        this.weightInKilos = weightInKilos;
    }

    public BigDecimal price() {
        return product.pricePerKilo().multiply(weightInKilos).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<DiscountType> getAttachedDiscounts() {
        return attachedDiscounts;
    }
}
