package jsonplaceholder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTest {

    @Test
    public void createNewUser(){

        User user = new User();
        user.setName("Monika Test");
        user.setUsername("Monika");
        user.setEmail("abc@gmail.com");
        user.setPhone("123456789");
        user.setWebsite("www.abc.pl");

        Geo geo = new Geo();
        geo.setLat("-12.345");
        geo.setLng("34.34");

        Address address = new Address();
        address.setCity("Miasto");
        address.setStreet("Test Street");
        address.setSuite("Apt 1");
        address.setZipcode("34-453");
        address.setGeo(geo);

        user.setAddress(address);

        Company company = new Company();
        company.setBs("dfg");
        company.setCatchPhrase("fgh sdf");
        company.setName("Company Test");

        user.setCompany(company);


        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString("username")).isEqualTo(user.getUsername());

    }
}
