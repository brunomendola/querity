package net.brunomendola.querity.spring.data.jpa.domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`order`")
public class Order extends AbstractPersistable<Long> implements net.brunomendola.querity.test.domain.Order<OrderItem> {
  @ManyToOne
  private Person person;
  @NonNull
  private Short year;
  @NonNull
  private Integer number;
  @NonNull
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();
}
