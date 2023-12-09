package fourtalking.Nateam.game.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fourtalking.Nateam.game.dto.GameGetDTO;
import fourtalking.Nateam.game.dto.GameModifyDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO;
import fourtalking.Nateam.game.dto.GameRegisterDTO.Request;
import fourtalking.Nateam.game.dto.GameRegisterDTO.Response;
import fourtalking.Nateam.game.repositroy.GameRepository;
import fourtalking.Nateam.global.exception.game.GameNotFoundException;
import fourtalking.Nateam.global.exception.user.NoAuthorizationException;
import fourtalking.Nateam.test.CommonTest;
import fourtalking.Nateam.test.GameTest;
import fourtalking.Nateam.user.dto.SignupDTO;
import fourtalking.Nateam.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class GameServiceTest implements CommonTest, GameTest {

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserService userService;


    @BeforeEach
    public void setup() {
        SignupDTO signRequestDTO = new SignupDTO(TEST_USER_NAME, TEST_USER_PASSWORD);
        userService.signup(signRequestDTO);
    }

    @Nested
    @DisplayName("게임 등록 테스트")
    class RegisterGame {

        @Test
        @DisplayName("게임 등록을 할 수 있다.")
        void registerGame() {
            //given
            GameRegisterDTO.Request gameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);

            // when
            Response response = gameService.registerGame(gameRequestDTO, TEST_USER_ID);

            // then
            assertEquals(TEST_GAME_NAME, response.gameName());
            assertEquals(TEST_GAME_INTRODUCTION, response.gameIntroduction());
            assertEquals(TEST_GAME_PRICE, response.gamePrice());
        }
    }

    @Nested
    @DisplayName("게임 조회 테스트")
    class GetGame {

        @Test
        @DisplayName("게임 Id를 통해 게임 정보를 조회할 수 있다.")
        void getGameById() {
            //given
            GameRegisterDTO.Request gameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            Response response = gameService.registerGame(gameRequestDTO, TEST_USER_ID);
            // when
            GameGetDTO gameGetDTO = gameService.getGameById(TEST_GAME_ID);

            // then
            assertEquals(response.gameId(), gameGetDTO.gameId());
            assertEquals(response.gameIntroduction(), gameGetDTO.gameIntroduction());
            assertEquals(response.gamePrice(), gameGetDTO.gamePrice());
            assertEquals(response.userName(), gameGetDTO.userName());
            assertEquals(response.createdDate(), gameGetDTO.createdDate());
        }

        @Test
        @DisplayName("잘못된 게임 Id의 게임 정보를 요청하면 예외가 발생한다.")
        void getGameByWrongId() {
            //given
            GameRegisterDTO.Request gameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            Response response = gameService.registerGame(gameRequestDTO, TEST_USER_ID);

            // when - then
            assertThatThrownBy(() -> gameService.getGameById(WRONG_TEST_GAME_ID)).isInstanceOf(
                    GameNotFoundException.class);
        }

        @Test
        @DisplayName("게임 전체 목록을 조회할 수 있다.")
        void getAllGame() {
            //given
            GameRegisterDTO.Request testGameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            GameRegisterDTO.Request OtherGameRequestDTO = new Request(OTRER_TEST_GAME_NAME,
                    OTRER_TEST_GAME_INTRODUCTION, OTRER_TEST_GAME_PRICE);
            GameRegisterDTO.Request AnotherGameRequestDTO = new Request(ANOTRER_TEST_GAME_NAME,
                    ANOTRER_TEST_GAME_INTRODUCTION, ANOTRER_TEST_GAME_PRICE);

            Response testResponse = gameService.registerGame(testGameRequestDTO, TEST_USER_ID);
            Response otherTestResponse = gameService.registerGame(OtherGameRequestDTO,
                    TEST_USER_ID);
            Response anotherTestResponse = gameService.registerGame(AnotherGameRequestDTO,
                    TEST_USER_ID);

            // when
            List<GameGetDTO> gameGetDTOS = gameService.gameGetDTOs();

            // then
            assertEquals(3, gameGetDTOS.size());
        }
    }

    @Nested
    @DisplayName("게임을 수정 테스트")
    class ModifyGame {

        @Test
        @DisplayName("게임을 수정할 수 있다.")
        void modifyGame() {
            //given
            GameRegisterDTO.Request testGameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            Response testResponse = gameService.registerGame(testGameRequestDTO, TEST_USER_ID);

            String modifyGameName = "Modify Game";
            String modifyGameIntroduction = "Modify Introduction";
            int modifyGamePrice = 100;

            // when
            GameModifyDTO.Request gameModifyRequestDTO = new GameModifyDTO.Request(modifyGameName,
                    modifyGameIntroduction, modifyGamePrice);
            GameModifyDTO.Response modifiedResponse = gameService.modifyGame(testResponse.gameId(),
                    gameModifyRequestDTO, TEST_USER_ID);

            // then
            assertEquals(modifyGameName, modifiedResponse.gameName());
            assertEquals(modifyGameIntroduction, modifiedResponse.gameIntroduction());
            assertEquals(modifyGamePrice, modifiedResponse.gamePrice());
        }

        @Test
        @DisplayName("게임을 등록한 사용자와 다른 사용자가 게임을 수정하려하면 예외가 발생한다.")
        void modifyGameByAnotherUser() {
            //given
            GameRegisterDTO.Request testGameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            Response testResponse = gameService.registerGame(testGameRequestDTO, TEST_USER_ID);

            // when - then
            String modifyGameName = "Modify Game";
            String modifyGameIntroduction = "Modify Introduction";
            int modifyGamePrice = 100;
            GameModifyDTO.Request gameModifyRequestDTO = new GameModifyDTO.Request(modifyGameName,
                    modifyGameIntroduction, modifyGamePrice);
            assertThatThrownBy(
                    () -> gameService.modifyGame(testResponse.gameId(), gameModifyRequestDTO,
                            TEST_ANOTHER_USER_ID)).isInstanceOf(NoAuthorizationException.class);
        }
    }

    @Nested
    @DisplayName("게임 삭제 테스트")
    class DeleteGame {

        @Test
        @DisplayName("게임을 삭제할 수 있다.")
        void deleteGame() {
            //given
            GameRegisterDTO.Request testGameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            Response response = gameService.registerGame(testGameRequestDTO, TEST_USER_ID);

            // when
            gameService.deleteGame(response.gameId(), TEST_USER_ID);

            // then
            assertTrue(gameRepository.findById(response.gameId()).isEmpty());
        }

        @Test
        @DisplayName("게임을 등록한 사용자와 다른 사용자가 게임을 삭제하려하면 예외가 발생한다.")
        void deleteGameByAnotherUser() {
            //given
            GameRegisterDTO.Request testGameRequestDTO = new Request(TEST_GAME_NAME,
                    TEST_GAME_INTRODUCTION, TEST_GAME_PRICE);
            Response response = gameService.registerGame(testGameRequestDTO, TEST_USER_ID);

            // when - then
            assertThatThrownBy(() -> gameService.deleteGame(response.gameId(),
                    TEST_ANOTHER_USER_ID)).isInstanceOf(NoAuthorizationException.class);
        }
    }


}