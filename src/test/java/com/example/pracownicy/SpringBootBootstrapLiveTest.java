package com.example.pracownicy;

import com.example.pracownicy.domain.Pracownik;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;

public class SpringBootBootstrapLiveTest {

    private static final String API_PATH = "http://localhost:8080/api/pracownicy";

    @Test
    public void when_get_all_pracownicy_then_ok() {
        Response response = RestAssured.get(API_PATH);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void when_get_pracownik_by_nazwa_then_ok() {
        Pracownik pracownik = creataRandomPracownik();
        createPracownikAsUri(pracownik);
        Response response = RestAssured.get(API_PATH + "/nazwa/" + pracownik.getNazwa());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(List.class).size()).isGreaterThan(0);
    }

    @Test
    public void when_get_created_pracownik_by_id_then_ok() {
        Pracownik pracownik = creataRandomPracownik();
        String address = createPracownikAsUri(pracownik);
        Response response = RestAssured.get(address);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat( (String)response.jsonPath().get("nazwa") ).isEqualTo(pracownik.getNazwa());
    }

    @Test
    public void when_get_not_exist_pracownik_by_id_then_not_found() {
        Response response = RestAssured.get(API_PATH + "/" + randomNumeric(4));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void when_create_new_pracownik_then_created() {
        Pracownik pracownik = creataRandomPracownik();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pracownik)
                .post(API_PATH);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void when_invalid_pracownik_then_error() {
        Pracownik pracownik = creataRandomPracownik();
        pracownik.setNazwa(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pracownik)
                .post(API_PATH);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void when_update_created_pracownik_then_updated() {
        Pracownik pracownik = creataRandomPracownik();
        String location = createPracownikAsUri(pracownik);
        pracownik.setId(Long.parseLong(location.split("/api/pracownicy/")[1]));
        pracownik.setNazwa("nowa nazwa");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pracownik)
                .put(location);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        response = RestAssured.get(location);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat((String)response.jsonPath().get("nazwa")).isEqualTo("nowa nazwa");
    }

    @Test
    public void when_delete_created_pracownik_then_ok() {
        Pracownik pracownik = creataRandomPracownik();
        String location = createPracownikAsUri(pracownik);
        Response response = RestAssured.delete(location);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        response = RestAssured.get(location);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    private Pracownik creataRandomPracownik() {
        Pracownik pracownik = new Pracownik();
        pracownik.setNazwa(randomAlphabetic(20));
        pracownik.setNumerKontaktowy(randomNumeric(6));
        pracownik.setPensja( (double)(new Random().nextInt(1000000)) / 100 );
        return pracownik;
    }

    private String createPracownikAsUri(Pracownik pracownik) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pracownik)
                .post(API_PATH);
        return API_PATH + "/" + response.jsonPath().get("id");
    }

}
