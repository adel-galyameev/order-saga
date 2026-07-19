package ru.galuameev.order_saga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.galuameev.order_saga.dto.OrderRs;
import ru.galuameev.order_saga.saga.command.ReserveCourierCommand;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(target = "orderId", source = "id")
    ReserveCourierCommand toCommand(OrderRs order);
}
