package fourtalking.Nateam.order.dto;

import java.time.LocalDateTime;

public record OrderGameGetAllDTO(
    Long orderId,
    int orderTotalPrice,
    String userName,
    LocalDateTime createdTime,
    Long gameId,
    String gameName,
    int gamePrice,
    int orderCount
) {

}
