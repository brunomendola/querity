package net.brunomendola.querity.spring.data.elasticsearch.domain;

import lombok.*;
import net.brunomendola.querity.test.domain.ProductCategory;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem implements net.brunomendola.querity.test.domain.OrderItem {
  @NonNull
  private String sku;
  @NonNull
  private ProductCategory category;
  @NonNull
  private Integer quantity;
  @NonNull
  private BigDecimal unitPrice;
  @NonNull
  private String description;
}
