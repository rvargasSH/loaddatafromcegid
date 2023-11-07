package sainthonore.loaddatatotouroperator.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "store")
public class Store extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_SEQ")
    @SequenceGenerator(sequenceName = "STORE_SEQ", initialValue = 1, allocationSize = 1, name = "STORE_SEQ")
    private Long stoId;

    @Column(nullable = false, unique = true)
    private String stoName;

    @Column(nullable = false, unique = false)
    private String stoCodStore;

    @Column(nullable = true, unique = false)
    private String stoCegidId;

    @Column(nullable = true, unique = false)
    private String stoSapId;

    @Column(nullable = false, unique = false)
    private String stoAddress;

    @Column(nullable = true, unique = false)
    private Long citId;

    @Column(nullable = true, unique = false)
    private Long braId;

    @Column(nullable = false, unique = false)
    private Boolean stoStatus;

}