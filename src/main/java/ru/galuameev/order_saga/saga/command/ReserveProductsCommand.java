package ru.galuameev.order_saga.saga.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductsCommand {

    private UUID userId;

    private UUID orderId;

    private UUID productId;

    private Integer productQuantity;
}
