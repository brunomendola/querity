package net.brunomendola.querity.spring.data.mongodb.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements net.brunomendola.querity.test.domain.Order<OrderItem> {
  @NonNull
  private Short year;
  @NonNull
  private Integer number;
  @NonNull
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();
}
