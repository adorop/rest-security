package adorop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AUTHENTICATION_TOKEN")
@Getter
@Setter
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey)
    private User user;
}
