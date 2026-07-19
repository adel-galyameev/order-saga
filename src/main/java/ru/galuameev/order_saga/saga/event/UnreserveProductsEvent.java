package ru.galuameev.order_saga.saga.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.galuameev.order_saga.saga.command.UnreserveProductsCommand;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UnreserveProductsEvent extends UnreserveProductsCommand {

    private boolean isSuccesses;
}
