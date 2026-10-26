package ru.galuameev.order_saga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.galuameev.order_saga.dto.OrderHistoryRs;
import ru.galuameev.order_saga.entity.Order;
import ru.galuameev.order_saga.entity.OrderHistory;
import ru.galuameev.order_saga.mapper.OrderHistoryMapper;
import ru.galuameev.order_saga.repository.OrderHistoryRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryMapper orderHistoryMapper;

    public List<OrderHistoryRs> getOrderHistory(UUID orderId) {
        List<OrderHistory> history = orderHistoryRepository.findByOrderIdOrderByCreatedAtAsc(orderId);

        return history.stream()
                .map(orderHistoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void add(Order order) {
        orderHistoryRepository.save(orderHistoryMapper.toEntity(order));
    }
}
