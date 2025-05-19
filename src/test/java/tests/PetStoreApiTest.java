package tests;

import config.TestConfig;
import io.restassured.http.ContentType;
import model.Pet;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetStoreApiTest extends TestConfig {

    static long petId;

    @Test
    @Order(1)
    public void testAddPet() {
        Pet pet = new Pet();
        pet.id = System.currentTimeMillis();
        pet.name = "Fluffy";
        pet.status = "available";
        pet.photoUrls = List.of("http://example.com/photo.jpg");

        Pet.Category category = new Pet.Category();
        category.id = 1;
        category.name = "Dogs";
        pet.category = category;

        given()
            .contentType(ContentType.JSON)
            .body(pet)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body("name", equalTo("Fluffy"))
            .body("status", equalTo("available"));

        petId = pet.id;
        System.out.println(petId);
    }

    @Test
    @Order(2)
    public void testGetPet() {
    	System.out.println(petId);
        given()
            .pathParam("petId", petId)
        .when()
            .get("/pet/{petId}")
        .then()
            .statusCode(200)
            .body("status", equalTo("available"))
            .body("name", equalTo("Fluffy"));
    }
    
    @Test
    @Order(3)
    public void testFindPetsByAvailableStatus() {
        given()
            .queryParam("status", "available")
        .when()
            .get("/pet/findByStatus")
        .then()
            .statusCode(200)
            .body("status", everyItem(equalTo("available")))
            .body("id", not(empty()));
    }

    @Test
    @Order(4)
    public void testUpdatePetStatus() {
        Pet pet = new Pet();
        pet.id = petId;
        pet.name = "Fluffy";
        pet.status = "sold";
        pet.photoUrls = List.of("http://example.com/photo.jpg");

        Pet.Category category = new Pet.Category();
        category.id = 1;
        category.name = "Dogs";
        pet.category = category;

        given()
            .contentType(ContentType.JSON)
            .body(pet)
        .when()
            .put("/pet")
        .then()
            .statusCode(200)
            .body("status", equalTo("sold"));
    }

    @Test
    @Order(5)
    public void testDeletePet() {
        given()
            .pathParam("petId", petId)
        .when()
            .delete("/pet/{petId}")
        .then()
            .statusCode(200);
    }
  
}
