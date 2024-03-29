package fourtalking.Nateam.global.exception.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  // JWT
  INVALID_JWT_SIGNATURE_EXCEPTION(401, "잘못된 JWT 서명입니다."),
  EXPIRED_JWT_TOKEN_EXCEPTION(401, "만료된 JWT 토큰입니다."),
  UNSUPPORTED_JWT_TOKEN_EXCEPTION(401, "지원되지 않는 JWT 토큰입니다."),
  INVALID_JWT_TOKEN_EXCEPTION(401, "JWT 토큰이 잘못되었습니다"),
  NOT_REFRESH_TOKEN_EXCEPTION(401, "Refresh Token이 아닙니다."),
  NOT_MISMATCHED_REFRESH_TOKEN_EXCEPTION(401, "DB의 리프레쉬 토큰 값과 다릅니다."),
  NO_JWT_EXCEPTION(401, "이 요청은 JWT가 필요합니다."),

  // 회원
  NOT_FOUND_MEMBER_EXCEPTION(401, "회원 정보를 찾을 수 없습니다."),
  FAILED_AUTHENTICATION_EXCEPTION(401, "인증에 실패하였습니다."),
  ALREADY_EXIST_USER_NAME_EXCEPTION(409, "이미 존재하는 이름입니다."),
  ALREADY_EXIST_EMAIL_EXCEPTION(409, "이미 존재하는 이메일입니다."),
  UNAUTHORIZED_MODIFY_EXCEPTION(401, "수정할 권한이 없습니다."),
  NO_AUTHORIZATION_EXCEPTION(400, "접근 권한이 없습니다"),
  WRONG_PASSWORD_EXCEPTION(400, "틀린 비밀번호입니다."),
  INVALID_USER_ID_AND_PASSWORD(400, "입력하신 아이디와 비밀번호에 일치하는 계정이 없습니다."),
  DUPLICATE_PASSWORD_EXCEPTION(400, "최근 3번이내의 비밀번호로 변경할 수 없습니다."),

  // 게임
  NOT_FOUND_GAME_EXCEPTION(401, "게임 정보를 찾을 수 없습니다."),

  // 리뷰
  NOT_FOUND_REVIEW_EXCEPTION(401, "리뷰 정보를 찾을 수 없습니다."),
  INCONSISTENCY_USERID_EXCEPTION(401, "유저 아이디가 일치하지 않습니다."),

  // 장바구니
  NOT_FOUND_CART_IN_GAME_EXCEPTION(401, "장바구니에 해당 게임을 찾을 수 없습니다"),

  // 주문
  NOT_FOUND_ORDER_EXCEPTION(401, "해당 주문을 찾을 수 없습니다."),
  EMPTY_CART_EXCEPTION(400,"장바구니가 비어 있습니다");

  private final int status;

  private final String message;

  ErrorCode(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
