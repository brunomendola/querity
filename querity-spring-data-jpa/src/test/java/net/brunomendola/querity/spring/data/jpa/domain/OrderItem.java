package net.brunomendola.querity.spring.data.jpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import net.brunomendola.querity.test.domain.ProductCategory;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.OrderItem {
  @ManyToOne
  @NonNull
  private Order order;
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
