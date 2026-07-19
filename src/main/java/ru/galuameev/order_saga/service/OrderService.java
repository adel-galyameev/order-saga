package ru.galuameev.order_saga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.galuameev.order_saga.dto.OrderRq;
import ru.galuameev.order_saga.dto.OrderRs;
import ru.galuameev.order_saga.dto.Status;
import ru.galuameev.order_saga.entity.Order;
import ru.galuameev.order_saga.mapper.OrderMapper;
import ru.galuameev.order_saga.repository.OrderRepository;
import ru.galuameev.order_saga.saga.OrderSagaOrchestrator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderHistoryService orderHistoryService;
    private final OrderSagaOrchestrator orderSagaOrchestrator;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderRs createOrder(OrderRq orderRq) {
        Order order = orderMapper.toEntity(orderRq);
        order.setStatus(Status.CREATED);

        order = orderRepository.save(order);
        orderHistoryService.add(order);
        orderSagaOrchestrator.start(order);
        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderRs getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        return orderMapper.toResponse(order);
    }

    @Transactional
    public void updateStatus(UUID orderId, Status orderStatus) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(orderStatus);
            orderHistoryService.add(order);
        });
    }
}
