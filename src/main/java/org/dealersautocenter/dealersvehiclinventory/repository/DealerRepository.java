    package org.dealersautocenter.dealersvehiclinventory.repository;

    import org.dealersautocenter.dealersvehiclinventory.domain.Entity.Dealer;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;


    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    public interface DealerRepository extends JpaRepository<Dealer, UUID> {
    Optional<Dealer> findByIdAndTenantId(UUID id,String tenantId);
    Page<Dealer> findByTenantId(String tenantId, Pageable pageable);
    @Query("SELECT d.subscriptionType, COUNT(d) FROM Dealer d WHERE d.tenantId = :tenantId GROUP BY d.subscriptionType")
    List<Object[]> countBySubscription(@Param("tenantId") String tenantId);
    boolean existsById(UUID id);
    }
