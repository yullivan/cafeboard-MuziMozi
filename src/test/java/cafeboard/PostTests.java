package cafeboard;

import cafeboard.Board.BoardRequestDTO;
import cafeboard.Board.BoardResponseDTO;
import cafeboard.Post.PostCreateResponseDTO;
import cafeboard.Post.PostRequestDTO;
import cafeboard.Post.PostResponseDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTests extends TestSetting{

    @DisplayName("게시글을 생성한다.")
    @Test
    void createBoard() {
        // 게시판 생성
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new BoardRequestDTO("공지사항"))
                .when()
                .post("/api/boards")
                .then()
                .statusCode(HttpStatus.OK.value());

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        assertThat(post.id()).isGreaterThan(0);
    }

    @DisplayName("게시글 목록을 조회한다.")
    @Test
    void findPosts() {
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

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // when
        // 게시글 목록 조회
        List<PostResponseDTO> posts = RestAssured
                .given().log().all()
                .queryParam("boardId", 1L)
                .when()
                .get("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", PostResponseDTO.class);

        // then
        assertThat(posts).hasSize(1);
    }

    @DisplayName("게시글 하나를 수정한다.")
    @Test
    void updatePosts() {
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

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // when
        // 게시판 이름 수정
        PostResponseDTO updatePost = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("수정 제목", "수정 내용", 1L))
                .when()
                .put("/api/posts/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostResponseDTO.class);

        // then
        assertThat(updatePost.title()).isEqualTo("수정 제목");
    }

    @DisplayName("게시글 하나를 삭제한다.")
    @Test
    void deletePosts() {
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

        // 게시글 생성
        PostCreateResponseDTO post = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new PostRequestDTO("제목", "내용", 1L))
                .when()
                .post("/api/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PostCreateResponseDTO.class);

        // when
        RestAssured
                .given()
                .when()
                .delete("/api/posts/1")
                .then()
                .statusCode(HttpStatus.OK.value());

        // then
        RestAssured
                .given()
                .when()
                .get("/api/posts/1")
                .then()
                .statusCode(500);
    }
}
