package ru.galuameev.order_saga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.galuameev.order_saga.dto.OrderRq;
import ru.galuameev.order_saga.dto.OrderRs;
import ru.galuameev.order_saga.entity.Order;
import ru.galuameev.order_saga.saga.command.CancelOrderCommand;
import ru.galuameev.order_saga.saga.command.OrderCreatedCommand;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRq orderRq);

    OrderRs toResponse(Order order);

    @Mapping(target = "orderId", source = "id")
    OrderCreatedCommand toCreatedCommand(Order order);

    @Mapping(target = "orderId", source = "id")
    CancelOrderCommand toCancelCreatedCommand(OrderRs order);

}
