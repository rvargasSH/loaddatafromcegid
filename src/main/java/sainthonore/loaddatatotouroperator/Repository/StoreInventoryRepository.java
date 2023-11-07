package sainthonore.loaddatatotouroperator.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sainthonore.loaddatatotouroperator.Model.StoreInventory;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory, Long> {
    Optional<StoreInventory> findByMaterialCodeAndStoId(String materialCode, Long stoId);
}
