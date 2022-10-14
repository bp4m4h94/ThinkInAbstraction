package model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;

@Data
public class Product {

    private String id;
    private String name;
    private String sku;
    private BigDecimal price;
    private HashSet<String> tags; // 商品標籤

}
