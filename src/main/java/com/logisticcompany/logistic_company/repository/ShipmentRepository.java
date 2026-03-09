package com.logisticcompany.logistic_company.repository;


import com.logisticcompany.logistic_company.model.Shipment;
import com.logisticcompany.logistic_company.model.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findByStatus(ShipmentStatus status);

    List<Shipment> findBySenderId(Long senderId);

    List<Shipment> findByReceiverId(Long receiverId);

    List<Shipment> findByRegisteredById(Long employeeId);

    List<Shipment> findBySender_User_Username(String username);

    @Query("""
SELECT s FROM Shipment s
WHERE (:search IS NULL OR
       LOWER(s.sender.user.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR
       LOWER(s.receiver.user.fullName) LIKE LOWER(CONCAT('%', :search, '%')))
AND (:status IS NULL OR s.status = :status)
""")
    List<Shipment> filterShipments(String search, ShipmentStatus status);

    @Query("SELECT SUM(s.price) FROM Shipment s " +
            "WHERE s.dateCreated BETWEEN :start AND :end")
    Double calculateRevenue(LocalDateTime start,
                            LocalDateTime end);
}
