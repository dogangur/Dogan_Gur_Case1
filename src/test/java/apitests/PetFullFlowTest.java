package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class PetFullFlowTest {

    public static long petId = 987654367;
    public static String petName = "dogandoggie1";

    @Test(priority = 1)
    public void createPet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String jsonBody = """
            {
              "id": %d,
              "category": {
                "id": 1,
                "name": "Dogann1"
              },
              "name": "%s",
              "photoUrls": [
                "https://example.com/dog.jpg"
              ],
              "tags": [
                {
                  "id": 1,
                  "name": "Dobby"
                }
              ],
              "status": "available"
            }
        """.formatted(petId, petName);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/pet");

        int statusCode = response.getStatusCode();
        System.out.println("POST isteği gönderildi. Status Code: " + statusCode);

        if (statusCode == 200) {
            System.out.println("✔ Pet başarıyla oluşturuldu.");
            System.out.println("➤ ID: " + response.jsonPath().getLong("id"));
            System.out.println("➤ Name: " + response.jsonPath().getString("name"));
            System.out.println("➤ Status: " + response.jsonPath().getString("status"));
        } else {
            System.err.println("❌ Pet oluşturulamadı. Hata kodu: " + statusCode);
            System.err.println("Lütfen JSON formatını ve gerekli alanları kontrol ediniz.");
        }

        Assert.assertEquals(statusCode, 200, "Pet oluşturulamadı!");
        Assert.assertEquals(response.jsonPath().getString("name"), petName);
        Assert.assertEquals(response.jsonPath().getString("status"), "available");
    }

    @Test(priority = 2)
    public void getPetById() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        Response response = given()
                .when()
                .get("/pet/" + petId);

        int statusCode = response.getStatusCode();
        System.out.println("GET isteği gönderildi. Status Code: " + statusCode);

        if (statusCode == 200) {
            System.out.println("✔ Pet başarıyla getirildi.");
            System.out.println("->  ID: " + response.jsonPath().getLong("id"));
            System.out.println("->  Name: " + response.jsonPath().getString("name"));
            System.out.println("-> Status: " + response.jsonPath().getString("status"));
        } else if (statusCode == 404) {
            System.err.println("X Pet bulunamadı! ID: " + petId);
            System.err.println("Bu ID ile oluşturulmuş bir pet olup olmadığını kontrol ediniz.");
        } else {
            System.err.println("! Beklenmeyen bir durum oluştu. Kod: " + statusCode);
        }

        Assert.assertEquals(statusCode, 200, "Pet bulunamadı!");
        Assert.assertEquals(response.jsonPath().getString("name"), petName, "Pet ismi eşleşmiyor!");
    }

    @Test(priority = 3)
    public void updatePet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";


        Response beforeUpdate = given()
                .when()
                .get("/pet/" + petId);

        String oldName = beforeUpdate.jsonPath().getString("name");
        String oldStatus = beforeUpdate.jsonPath().getString("status");
        String oldCategory = beforeUpdate.jsonPath().getString("category.name");
        String oldTag = beforeUpdate.jsonPath().getString("tags[0].name");


        String updatedName = "Post edilen ismi Put ettigim hali";
        String updatedStatus = "sold";
        String updatedCategory = "Dogan2";
        String updatedTag = "Hagrid";

        String updatedJson = """
        {
          "id": %d,
          "category": {
            "id": 1,
            "name": "%s"
          },
          "name": "%s",
          "photoUrls": [
            "https://example.com/updated.jpg"
          ],
          "tags": [
            {
              "id": 1,
              "name": "%s"
            }
          ],
          "status": "%s"
        }
    """.formatted(petId, updatedCategory, updatedName, updatedTag, updatedStatus);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when()
                .put("/pet");

        int statusCode = response.getStatusCode();
        System.out.println("PUT isteği gönderildi. Status Code: " + statusCode);

        if (statusCode == 200) {
            String newName = response.jsonPath().getString("name");
            String newStatus = response.jsonPath().getString("status");
            String newCategory = response.jsonPath().getString("category.name");
            String newTag = response.jsonPath().getString("tags[0].name");

            System.out.println("✔ Pet başarıyla güncellendi. Alan karşılaştırması:");
            System.out.println("->  İsim:       " + oldName + " ➜ " + newName);
            System.out.println("->  Status:     " + oldStatus + " ➜ " + newStatus);
            System.out.println("-> Category:   " + oldCategory + " ➜ " + newCategory);
            System.out.println("-> Tag:        " + oldTag + " ➜ " + newTag);
        } else {
            System.err.println("XPet güncellenemedi! Hata kodu: " + statusCode);
            System.err.println("Lütfen güncelleme için gönderilen alanları kontrol ediniz.");
        }

        Assert.assertEquals(statusCode, 200, "Pet güncellenemedi!");
        Assert.assertEquals(response.jsonPath().getString("name"), updatedName, "İsim güncellenemedi!");
    }

    @Test(priority = 4)
    public void deletePet() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        Response response = given()
                .when()
                .delete("/pet/" + petId);

        int statusCode = response.getStatusCode();
        System.out.println("DELETE isteği gönderildi. Status Code: " + statusCode);

        if (statusCode == 200) {
            System.out.println("✔ Pet başarıyla silindi.");
            System.out.println("-> Silinen ID: " + petId);
        } else if (statusCode == 404) {
            System.err.println("X Silinmek istenen Pet bulunamadı! ID: " + petId);
        } else {
            System.err.println("!Beklenmeyen durum. Kod: " + statusCode);
        }

        Assert.assertEquals(statusCode, 200, "Pet silinemedi!");
    }
}
