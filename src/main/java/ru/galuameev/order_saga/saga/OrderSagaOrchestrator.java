package ru.galuameev.order_saga.saga;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.galuameev.order_saga.dto.OrderRs;
import ru.galuameev.order_saga.dto.Status;
import ru.galuameev.order_saga.entity.Order;
import ru.galuameev.order_saga.mapper.DeliveryMapper;
import ru.galuameev.order_saga.mapper.OrderMapper;
import ru.galuameev.order_saga.mapper.ProductMapper;
import ru.galuameev.order_saga.saga.event.*;
import ru.galuameev.order_saga.service.OrderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSagaOrchestrator {

    @Lazy
    @Autowired
    private OrderService orderService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final DeliveryMapper deliveryMapper;
    @Value("${order.kafka.topic.order-created-topic}")
    private String orderCreatedTopic;
    @Value("${order.kafka.topic.cancel-order-command-topic}")
    private String cancelOrderCommandTopic;
    @Value("${order.kafka.topic.product-command-topic}")
    private String productCommandTopic;
    @Value("${order.kafka.topic.unreserve-product-command-topic}")
    private String unreserveProductCommandTopic;
    @Value("${order.kafka.topic.reserve-courier-command-topic}")
    private String reserveCourierCommandTopic;

    public void start(Order order) {
        kafkaTemplate.send(orderCreatedTopic, orderMapper.toCreatedCommand(order))
                .thenAccept(res -> {
                    log.info("order created command pablished");
                    orderService.updateStatus(order.getId(), Status.PROCESSED);
                })
                .exceptionally(ex -> {
                    log.error("stack trace", ex);
                    orderService.updateStatus(order.getId(), Status.REJECT);
                    return null;
                });
    }

    @KafkaListener(topics = "${order.kafka.topic.billing-event-topic}", containerFactory = "billingEventKafkaListenerContainerFactory")
    public void handlerBillingEvent(@Payload WithdrawEvent withdrawEvent) {
        Status status = withdrawEvent.isSuccesses() ? Status.PAID : Status.REJECT;
        orderService.updateStatus(withdrawEvent.getOrderId(), status);
        if (Status.PAID.equals(status)) {
            OrderRs order = orderService.getOrder(withdrawEvent.getOrderId());
            kafkaTemplate.send(productCommandTopic, productMapper.toCommand(order));
        }
    }

    @KafkaListener(topics = "${order.kafka.topic.product-event-topic}", containerFactory = "productEventKafkaListenerContainerFactory")
    public void handleWarehouseEvent(@Payload ReserveProductsEvent reserveProductsEvent) {
        OrderRs order = orderService.getOrder(reserveProductsEvent.getOrderId());
        if (reserveProductsEvent.isSuccesses()) {
            kafkaTemplate.send(reserveCourierCommandTopic, deliveryMapper.toCommand(order));
        } else {
            kafkaTemplate.send(cancelOrderCommandTopic, orderMapper.toCancelCreatedCommand(order));
        }
    }

    @KafkaListener(topics = "${order.kafka.topic.reserve-courier-event-topic}", containerFactory = "reserveCourierEventKafkaListenerContainerFactory")
    public void handleReserveCourier(@Payload ReserveCourierEvent reserveCourierEvent) {
        if (reserveCourierEvent.isReserved()) {
            orderService.updateStatus(reserveCourierEvent.getOrderId(), Status.COMPLETED);
        } else {
            OrderRs order = orderService.getOrder(reserveCourierEvent.getOrderId());
            kafkaTemplate.send(unreserveProductCommandTopic, productMapper.toCancelCommand(order));
        }
    }

    @KafkaListener(topics = "${order.kafka.topic.unreserve-product-event-topic}", containerFactory = "unreserveCourierEventKafkaListenerContainerFactory")
    public void handleUnreserveProductsEvent(@Payload UnreserveProductsEvent unreserveProductsEvent) {
        OrderRs order = orderService.getOrder(unreserveProductsEvent.getOrderId());
        kafkaTemplate.send(cancelOrderCommandTopic, orderMapper.toCancelCreatedCommand(order));
    }

    @KafkaListener(topics = "${order.kafka.topic.cancel-order-event-topic}", containerFactory = "cancelOrderEventKafkaListenerContainerFactory")
    public void handleWarehouseEvent(@Payload CancelOrderEvent cancelOrderEvent) {
        orderService.updateStatus(cancelOrderEvent.getOrderId(), Status.REFUNDED);
    }
}
