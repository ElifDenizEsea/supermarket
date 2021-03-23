package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public class ItemByUnit implements Item {

    private List<DiscountType> attachedDiscounts;
    private final Product product;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    ItemByUnit(final Product product) {
        this.product = product;
    }

    public BigDecimal price() {
        return product.pricePerUnit();
    }

    @Override
    public List<DiscountType> getAttachedDiscounts() {
        return attachedDiscounts;
    }
}
