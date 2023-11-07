package sainthonore.loaddatatotouroperator.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sainthonore.loaddatatotouroperator.Model.ProductMaster;
import sainthonore.loaddatatotouroperator.Repository.ProductMasterRepository;

@Service
public class ProductMasterService {
    @Autowired
    ProductMasterRepository repository;

    @Transactional
    public ProductMaster saveEntity(ProductMaster productoMaster) {
        return repository.saveAndFlush(productoMaster);
    }

    @Transactional(readOnly = true)
    public Optional<ProductMaster> findByMaterialCode(String materialCode) {
        return repository.findByMaterialCode(materialCode);
    }

    @Transactional()
    public Integer validateCategoriesAndProducts(Integer storeId) {
        return repository.validateCategoriesAndProducts(storeId);
    }
}
