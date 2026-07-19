package ru.galuameev.order_saga.saga.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.galuameev.order_saga.saga.command.CancelOrderCommand;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderEvent extends CancelOrderCommand {

    private boolean isSuccesses;
}
