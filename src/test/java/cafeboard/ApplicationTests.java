package cafeboard;

import cafeboard.Board.BoardRequestDTO;
import cafeboard.Board.BoardResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	DatabaseCleanupPostgres databaseCleanupPostgres;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		databaseCleanupPostgres.execute();
	}

	@DisplayName("게시판을 생성한다.")
	@Test
	void createBoard() {
		BoardResponseDTO board = RestAssured
				.given().log().all()
				.contentType(ContentType.JSON)
				.body(new BoardRequestDTO("공지사항"))
				.when()
				.post("/api/boards")
				.then().log().all()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.as(BoardResponseDTO.class);

		assertThat(board.id()).isGreaterThan(0);
	}

	@DisplayName("게시판 목록을 조회한다.")
	@Test
	void findBoards() {
		// given
		// 게시판 생성
		RestAssured
				.given()
				.contentType(ContentType.JSON)
				.body(new BoardRequestDTO("공지사항"))
				.when()
				.post("/api/boards")
				.then()
				.statusCode(HttpStatus.OK.value());

		// when
		// 게시판 목록 조회
		List<BoardResponseDTO> boards = RestAssured
				.given().log().all()
				.when()
				.get("/api/boards")
				.then().log().all()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.jsonPath()
				.getList(".", BoardResponseDTO.class);

		// then
		assertThat(boards).hasSize(1);
	}

	@DisplayName("게시판 하나의 이름을 수정한다.")
	@Test
	void updateBoards() {
		// given
		// 게시판 생성
		RestAssured
				.given()
				.contentType(ContentType.JSON)
				.body(new BoardRequestDTO("공지사항"))
				.when()
				.post("/api/boards")
				.then()
				.statusCode(HttpStatus.OK.value());

		// when
		// 게시판 이름 수정
		BoardResponseDTO board = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.body(new BoardRequestDTO("자유게시판"))
				.when()
				.put("/api/boards/1")
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.as(BoardResponseDTO.class);

		// then
		assertThat(board.boardName()).isEqualTo("자유게시판");
	}

	@DisplayName("게시판 하나를 삭제한다.")
	@Test
	void deleteBoards() {
		// given
		RestAssured
				.given()
				.contentType(ContentType.JSON)
				.body(new BoardRequestDTO("공지사항"))
				.when()
				.post("/api/boards")
				.then()
				.statusCode(HttpStatus.OK.value());

		// when
		RestAssured
				.given()
				.when()
				.delete("/api/boards/1")
				.then()
				.statusCode(HttpStatus.OK.value());

		// then
		RestAssured
				.given()
				.when()
				.get("/api/boards/1")
				.then()
				.statusCode(500);
	}


}
