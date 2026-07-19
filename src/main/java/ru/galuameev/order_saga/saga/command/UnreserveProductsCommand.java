package ru.galuameev.order_saga.saga.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UnreserveProductsCommand {

    private UUID userId;

    private UUID orderId;

    private UUID productId;

    private Integer productQuantity;
}
