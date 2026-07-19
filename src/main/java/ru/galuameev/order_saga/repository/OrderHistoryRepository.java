package ru.galuameev.order_saga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.galuameev.order_saga.entity.OrderHistory;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, UUID> {

    List<OrderHistory> findByOrderIdOrderByCreatedAtAsc(UUID orderId);
}
