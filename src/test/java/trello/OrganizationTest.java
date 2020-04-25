package trello;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganizationTest extends CommonTest {

    @DisplayName("Create organization with valid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationData")
    public void createOrganization(String displayName, String desc, String name, String website) {

//        Organization organization = new Organization();
//        organization.setDisplayName(displayName);
//        organization.setDesc(desc);
//        organization.setName(name);
//        organization.setWebsite(website);

        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", displayName)
                .queryParam("name", name)
                .queryParam("desc", desc)
                .queryParam("website", website)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isEqualTo(displayName);

        final String organizationId = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete("https://api.trello.com/1/organizations/" + organizationId)
                .then()
                .statusCode(200);

    }

    @DisplayName("Create organization with invalid data")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationInvalidData")
    public void createOrganizationWithInvalidData(String dispName, String description, String companyName, String wwww) {
        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", dispName)
                .queryParam("name", companyName)
                .queryParam("website", wwww)
                .queryParam("desc", description)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(HttpStatus.SC_OK)
                //there should be status code 400 but Trello has lack of validation
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        final String organizationIdWrongValidation = json.getString("id");

        given()
                .spec(reqSpec)
                .when()
                .delete("https://api.trello.com/1/organizations/" + organizationIdWrongValidation)
                .then()
                .statusCode(200);
    }


    @DisplayName("Create organization with empty required field")
    @ParameterizedTest(name = "Display name: {0}, desc: {1}, name: {2}, website: {3}")
    @MethodSource("createOrganizationEmptyRequiredField")
    public void createOrganizationWithEmptyRequiredField(String dispName, String description, String companyName, String wwww) {
        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", dispName)
                .queryParam("name", companyName)
                .queryParam("website", wwww)
                .queryParam("desc", description)
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertThat(json.getString("displayName")).isNull();
    }

}
