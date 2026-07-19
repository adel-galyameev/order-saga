package ru.galuameev.order_saga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.galuameev.order_saga.dto.OrderRs;
import ru.galuameev.order_saga.saga.command.ReserveProductsCommand;
import ru.galuameev.order_saga.saga.command.UnreserveProductsCommand;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "orderId", source = "id")
    ReserveProductsCommand toCommand(OrderRs order);

    @Mapping(target = "orderId", source = "id")
    UnreserveProductsCommand toCancelCommand(OrderRs order);

}
