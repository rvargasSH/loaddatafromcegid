package sainthonore.loaddatatotouroperator.Model;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "product_master")
public class ProductMaster extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODMAST_SEQ")
    @SequenceGenerator(sequenceName = "PRODMAST_SEQ", initialValue = 1, allocationSize = 1, name = "PRODMAST_SEQ")
    private Long promId;

    @Column(nullable = false, unique = true)
    private String materialCode;

    @Column(nullable = false, unique = false)
    private String materialName;

    @Column(nullable = false, unique = false)
    private String materialBrand;

    @Column(nullable = false, unique = false)
    private String materialFamily;

    @Column(nullable = false, unique = false)
    private String materialCategory;

    @Column(nullable = false, unique = false)
    private String materialSubcategory;
}
