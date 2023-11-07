package sainthonore.loaddatatotouroperator.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sainthonore.loaddatatotouroperator.Model.StoreInventory;
import sainthonore.loaddatatotouroperator.Repository.StoreInventoryRepository;

@Service
public class StoreInventoryService {
    @Autowired
    StoreInventoryRepository repository;

    @Transactional
    public StoreInventory saveEntity(StoreInventory entity) {
        return repository.saveAndFlush(entity);
    }

    @Transactional(readOnly = true)
    public Optional<StoreInventory> findByMaterialCodeAndStoId(String materialCode, Long storeId) {
        return repository.findByMaterialCodeAndStoId(materialCode, storeId);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
