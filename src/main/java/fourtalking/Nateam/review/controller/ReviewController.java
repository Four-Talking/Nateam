package fourtalking.Nateam.review.controller;

import fourtalking.Nateam.review.dto.GetAllReviewDTO;
import fourtalking.Nateam.review.dto.GetReviewDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO;
import fourtalking.Nateam.review.dto.ReviewRegisterDTO.Response;
import fourtalking.Nateam.review.dto.UpdateReviewDTO;
import fourtalking.Nateam.review.service.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{gameId}")
    public ResponseEntity<ReviewRegisterDTO.Response> registerReview(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("gameId") Long gameId,
            @RequestBody @Valid ReviewRegisterDTO.Request reviewRequest) {

        Response reviewResponseDTO = reviewService.registerReview(gameId, reviewRequest);

        return ResponseEntity.ok(reviewResponseDTO);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<GetReviewDTO> getReview(
            //
            @PathVariable("reviewId") Long reviewId) {

        GetReviewDTO getReviewDTO = reviewService.getReview("khj",reviewId);

        return ResponseEntity.ok(getReviewDTO);
    }

    @GetMapping("/{gameId}/reviews")
    public ResponseEntity<List<GetAllReviewDTO>> getAllReview(@PathVariable("gameId") Long gameId
            //, @AuthenticationPrincipal UserDetailsImpl userDetails,
            ) {

        List<GetAllReviewDTO> getAllReviewDTO = reviewService.getAllReview(gameId);

        return ResponseEntity.ok(getAllReviewDTO);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewDTO.Response> updateReview(@PathVariable("reviewId") Long reviewId,
            @RequestBody @Valid UpdateReviewDTO.Request request
            //, @AuthenticationPrincipal UserDetailsImpl userDetails,
    ) {

        UpdateReviewDTO.Response response = reviewService.updateReview(reviewId, request);

        return ResponseEntity.ok(response);
    }
}
