package sainthonore.loaddatatotouroperator.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sainthonore.loaddatatotouroperator.Model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByStoStatus(Boolean stoStatus);
}
