import model.Discount;
import model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BuyMoreBoxesDiscountRule extends RuleBase {

    private final int boxCount;
    private final int percentOff;

    public BuyMoreBoxesDiscountRule(int boxCount, int percentOff) {
        this.boxCount = boxCount;
        this.percentOff = percentOff;
        setName(String.format("任 %s 箱結帳 %s 折!", boxCount, percentOff));
        setNote("熱銷飲品 限時優惠");
    }

    @Override
    public List<Discount> process(List<Product> products) {
        List<Discount> discounts = new ArrayList<>();

        List<Product> match_products = new ArrayList<>();

        for (Product product:
             products) {
            match_products.add(product);

            if (match_products.size() == boxCount) {
                for (Product mp:
                     match_products) {
                    Discount discount = new Discount();
                    discount.setAmount(mp.getPrice().multiply(new BigDecimal(percentOff).divide(new BigDecimal(100)).setScale(3, RoundingMode.HALF_UP)));
                    discount.setRuleName(getName());
                    discount.setProducts(match_products);
                    discounts.add(discount);
                }
            }

        }
        return discounts;
    }
}
