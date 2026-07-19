package ru.galuameev.order_saga.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.galuameev.order_saga.dto.OrderHistoryRs;
import ru.galuameev.order_saga.dto.OrderRq;
import ru.galuameev.order_saga.dto.OrderRs;
import ru.galuameev.order_saga.service.OrderHistoryService;
import ru.galuameev.order_saga.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderControllerApi {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;

    @Override
    public ResponseEntity<OrderRs> createOrder(OrderRq orderRq) {
        return ResponseEntity.ok(orderService.createOrder(orderRq));
    }

    @Override
    public ResponseEntity<OrderRs> getOrder(UUID orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @Override
    public ResponseEntity<List<OrderHistoryRs>> getOrderHistory(UUID orderId) {
        return ResponseEntity.ok(orderHistoryService.getOrderHistory(orderId));
    }
}
