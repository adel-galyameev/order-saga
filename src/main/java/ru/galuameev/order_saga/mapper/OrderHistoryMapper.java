package ru.galuameev.order_saga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.galuameev.order_saga.dto.OrderHistoryRs;
import ru.galuameev.order_saga.dto.Status;
import ru.galuameev.order_saga.entity.Order;
import ru.galuameev.order_saga.entity.OrderHistory;

@Mapper(componentModel = "spring")
public interface OrderHistoryMapper {

    OrderHistoryRs toResponse(OrderHistory orderHistory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    OrderHistory toEntity(Order order);
}
