package com.eCommerce.MyECommerce.Repositories;

import com.eCommerce.MyECommerce.Domain.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findByOrderId(UUID orderId);

    List<OrderEntity> getAllOrdersByStatus(String orderStatus);
}
