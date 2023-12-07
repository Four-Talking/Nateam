package fourtalking.Nateam.cartgame.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CartGameRegisterDTO(@Min(1) @Max(99) Integer orderCount) {

}

