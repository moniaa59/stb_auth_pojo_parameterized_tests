package trello;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CommonTest {

    protected static final String KEY = "yourKey";
    protected static final String TOKEN = "yourToken";

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;

    @BeforeAll
    public static void beforeAll(){
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.setContentType(ContentType.JSON);

        reqSpec = reqBuilder.build();
    }


    protected static Stream<Arguments> createOrganizationData() {

        return Stream.of(
                Arguments.of("test", "abc abc", "test company", "https://www.test.com"),
                Arguments.of("test", "abc abc", "test company", "http://www.test.com"),
                Arguments.of("test", "abc abc", "test_company", "http://www.test.com"),
                Arguments.of("test", "abc abc", "testcompany123", "http://www.test.com"),
                Arguments.of("test", "abc abc", "abc", "http://www.test.com")
        );
    }

    protected static Stream<Arguments> createOrganizationInvalidData() {
        return Stream.of(
                Arguments.of("test", "abc abc", "abc", "123"),
                Arguments.of("test", "abc abc", "abc", "www.test.com"),
                Arguments.of("test", "abc abc", "a", "https://www.test.com"),
                Arguments.of("test", "abc abc", "23", "https://www.test.com"),
                Arguments.of("test", "abc abc", "a2", "https://www.test.com"),
                Arguments.of("test", "abc abc", "Test company", "https://www.test.com"),
                Arguments.of("test", "abc abc", "ABC", "https://www.test.com"),
                Arguments.of("test", "abc abc", "test-company", "https://www.test.com"),
                Arguments.of("test", "abc abc", "test@company", "https://www.test.com")
        );
    }

    protected static Stream<Arguments> createOrganizationEmptyRequiredField() {
        return Stream.of(
                Arguments.of("", "abc abc", "test company", "https://www.test.com")
        );
    }

}
