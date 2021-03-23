package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {
    private final List<Item> items;

    public Basket() {
        this.items = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * TODO: This could be a good place to apply the results of
         * the discount calculations.
         * It is not likely to be the best place to do those calculations.
         * Think about how Basket could interact with something
         * which provides that functionality.
         */
        private BigDecimal discounts() {
            BigDecimal discount = BigDecimal.ZERO;
            for (Item item : items) {
                for (DiscountType type : item.getAttachedDiscounts()) {
                    if (item instanceof ItemByUnit) {
                        ItemByUnit itemByUnit = (ItemByUnit) item;
                        if (type.equals(DiscountType.BUY_ONE_ONE_FREE) && itemByUnit.getAmount() > 1) {
                            if (itemByUnit.getAmount() % 2 == 0) {
                                discount = itemByUnit.price().divide(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(itemByUnit.getAmount()));
                            } else {
                                discount = itemByUnit.price().multiply(BigDecimal.valueOf(itemByUnit.getAmount()));
                            }
                        } else if (type.equals(DiscountType.THREE_FOR_2) && itemByUnit.getAmount() > 2) {
                            if (itemByUnit.getAmount() % 3 == 0) {
                                discount = itemByUnit.price().divide(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(itemByUnit.getAmount()));
                            } else if (itemByUnit.getAmount() % 3 == 1) {
                                discount = itemByUnit.price().divide(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(itemByUnit.getAmount()));
                            } else {
                                discount = itemByUnit.price().divide(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(itemByUnit.getAmount()));

                            }
                        } else if (type.equals(DiscountType.TWO_FOR_1)) {
                            if (itemByUnit.getAmount() % 2 == 0) {
                                discount = itemByUnit.price().subtract(BigDecimal.valueOf(1));
                            }
                        }
                    } else {

                        ItemByWeight itemByWeight = (ItemByWeight) item;
                        if (itemByWeight.getKg() % 2 == 0) {
                            discount = itemByWeight.price().divide(BigDecimal.valueOf(2)).multiply(BigDecimal.valueOf(itemByWeight.getKg()));
                        } else {

                            discount = itemByWeight.price().multiply(BigDecimal.valueOf(itemByWeight.getKg()));
                        }
                    }
                }
            }
            return discount;
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
