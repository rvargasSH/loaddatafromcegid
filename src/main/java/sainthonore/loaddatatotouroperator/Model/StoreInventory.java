package sainthonore.loaddatatotouroperator.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "storeInventory")
public class StoreInventory extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOREINV_SEQ")
    @SequenceGenerator(sequenceName = "STOREINV_SEQ", initialValue = 1, allocationSize = 1, name = "STOREINV_SEQ")
    private Long stoinId;

    @Column(nullable = false, unique = true)
    private String materialCode;

    @Column(nullable = false, unique = false)
    private Integer materialStock;

    // Id from store id coming from the user brand
    @Column(nullable = true, unique = false)
    private Long stoId;
}