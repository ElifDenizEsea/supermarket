package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

public interface Item {
    BigDecimal price();
    List<DiscountType> getAttachedDiscounts();
}
