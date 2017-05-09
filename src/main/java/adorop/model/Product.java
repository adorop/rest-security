package adorop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
public class Product {
    @Id
    private Long id;
    private String title;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey)
    private User owner;
}
