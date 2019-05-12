package com.discovery.sonic.recruitment;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class RESTExerciseTest {

  private static final String HOST = "http://localhost:8080";
  private static final Random RANDOM = new Random();

  @Test
  void verify_server_is_responding() {
    when()
        .get(HOST + "/ping")
        .then()
        .statusCode(200);
  }

  @Test
  void create_video_returns_new_video_id() {
    Map<String, String> requestBody = getVideoRequestBody("Big Bang Theory - Episode " + generateRandomIntWithinRange(1, 1000));

    given()
        .header("Content-Type", ContentType.JSON)
        .body(requestBody)
        .accept(ContentType.JSON)
        .when()
        .post(HOST + "/video")
        .then()
        .statusCode(201)
        .body("id", IsValidUUID.validUUID());
  }

  @Test
  void get_video_returns_correct_id_and_title() {
    String title = "Big Bang Theory - Episode " + generateRandomIntWithinRange(1, 1000);
    Map<String, String> requestBody = getVideoRequestBody(title);

    // Create video
    String videoId = given()
        .header("Content-Type", ContentType.JSON)
        .body(requestBody)
        .accept(ContentType.JSON)
        .when()
        .post(HOST + "/video")
        .then()
        .statusCode(201)
        .extract()
        .path("id");

    // Get video by id should return correct title and id
    given()
        .get(HOST + "/video/" + videoId)
        .then()
        .statusCode(200)
        .body("id", equalTo(videoId))
        .body("title", equalTo(title));
  }

  @Test
  void video_playback_info_returns_count_0() {
    Map<String, String> requestBody = getVideoRequestBody("The Walking Dead - Episode " + generateRandomIntWithinRange(1, 1000));

    // Create video
    String videoId = given()
        .header("Content-Type", ContentType.JSON)
        .body(requestBody)
        .accept(ContentType.JSON)
        .when()
        .post(HOST + "/video")
        .then()
        .statusCode(201)
        .extract()
        .path("id");

    // PlaybackInfo should return count 0
    given()
        .get(HOST + "/video/" + videoId + "/playbackInfo")
        .then()
        .statusCode(200)
        .body("count", equalTo(0));
  }

  @Test
  void get_playback_info_returns_correct_count() {
    Map<String, String> requestBody = getVideoRequestBody("Grey's Anatomy - Episode " + generateRandomIntWithinRange(1, 1000));

    // Create video
    String videoId = given()
        .header("Content-Type", ContentType.JSON)
        .body(requestBody)
        .accept(ContentType.JSON)
        .when()
        .post(HOST + "/video")
        .then()
        .statusCode(201)
        .extract()
        .path("id");

    // Report playback
    int numberOfPlaybackReports = generateRandomIntWithinRange(50, 100);
    for (int i = 0; i < numberOfPlaybackReports; i++) {
      given()
          .post(HOST + "/video/" + videoId + "/playbackReport")
          .then()
          .statusCode(200);
    }

    // Get playbackInfo should return correct count
    given()
        .get(HOST + "/video/" + videoId + "/playbackInfo")
        .then()
        .statusCode(200)
        .body("count", equalTo(numberOfPlaybackReports));
  }

  @Test
  void get_non_existing_video_returns_404() {
    String videoId = UUID.randomUUID().toString();
    given()
        .get(HOST + "/video/" + videoId)
        .then()
        .statusCode(404);
  }

  private int generateRandomIntWithinRange(int min, int max) {
    return RANDOM.nextInt((max - min) + 1) + min;
  }

  private Map<String, String> getVideoRequestBody(String title) {
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("title", title);
    return requestBody;
  }
}
