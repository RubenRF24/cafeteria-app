package com.rubenrf.cafeteria_app;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class CafeteriaAppApplicationTests {

	@ServiceConnection
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"));

	static {
		mySQLContainer.start();
	}

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void crearProducto() {
		String crearProductoJson = """
				{
					"nombre": "Café",
					"precio": 500,
					"categoria": "BEBIDA",
					"stock": 100
				}
				""";

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertEquals(responseBodyString, "Producto #1 creado correctamente.");
	}

	@Test
	void crearCliente() {
		String crearClienteJson = """
				{
					"nombre": "Ruben Robles",
					"correo": "ruben@mail.com",
					"telefono": "+573020202022"
				}
				""";

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertEquals(responseBodyString, "Cliente #1 creado correctamente.");
	}

}
