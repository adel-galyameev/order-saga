package ru.galuameev.order_saga.saga.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.galuameev.order_saga.saga.command.ReserveCourierCommand;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCourierEvent extends ReserveCourierCommand {

    private boolean reserved;
}
