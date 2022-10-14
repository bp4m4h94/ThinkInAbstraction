import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Discount;
import model.Product;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    public static void main(String[] args) {
        ArrayList<Product> products = loadProduct();
        for (Product product : products) {
            System.out.printf("- %s      $%s\n", product.getName(), product.getPrice());
        }
        BigDecimal totalPrice = checkProcess(products, loadRule());
        System.out.println("total price: $" + totalPrice);

    }

    private static RuleBase[] loadRule() {
            return new BuyMoreBoxesDiscountRule[]{new BuyMoreBoxesDiscountRule(2, 12)};
    }

    /**
     * 結帳
    */
    private static BigDecimal checkProcess(List<Product> products) {

        BigDecimal total = new BigDecimal(0);
        for (Product p:
             products) {
            total = total.add(p.getPrice());
        }

        return total;

    }

    private static BigDecimal checkProcess(List<Product> products, RuleBase[] rules) {


        BigDecimal checkout_without_discount = checkProcess(products);
        List<Discount> discounts = new ArrayList<>();
        for (RuleBase rule:
             rules) {
            discounts.addAll(rule.process(products));
        }


        BigDecimal totalDiscountAmt = BigDecimal.valueOf(0);

            for (Discount discount : discounts) {
                totalDiscountAmt = totalDiscountAmt.add(discount.getAmount());
                System.out.printf("- 符合折扣 %s, 折抵 %s 元\n", discount.getRuleName(), discount.getAmount());
            }

        return checkout_without_discount.subtract(totalDiscountAmt);

    }


    private static ArrayList<Product> loadProduct() {
        try {
            String str = Files.readString(Path.of("src/main/java/product.json"));
            Type type = new TypeToken<List<Product>>() {}.getType();
            Gson gson = new Gson();
            return gson.fromJson(str, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
