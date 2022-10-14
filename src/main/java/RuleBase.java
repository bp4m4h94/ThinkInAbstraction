import lombok.Data;
import model.Discount;
import model.Product;

import java.util.List;

/**
 * 1. input -> output
 * 2. implements each rules
 * 3. discount in order if the transaction meets multiple rules.
 */
@Data
public abstract class RuleBase {
    private String id;
    private String name;
    private String note;

    public abstract List<Discount> process(List<Product> products);
}
