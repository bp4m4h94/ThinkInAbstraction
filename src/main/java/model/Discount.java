package model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Discount {
    private String id;
    private String ruleName;
    private List<Product> products;
    private BigDecimal amount; // 折扣金額
}
