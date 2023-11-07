package sainthonore.loaddatatotouroperator.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sainthonore.loaddatatotouroperator.Model.ProductMaster;

@Repository
public interface ProductMasterRepository extends JpaRepository<ProductMaster, Long> {
    Optional<ProductMaster> findByMaterialCode(String materialCode);

    @Query(value = "select * from validate_categories_and_products(:storeId)", nativeQuery = true)
    Integer validateCategoriesAndProducts(Integer storeId);
}
