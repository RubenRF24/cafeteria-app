package com.rubenrf.cafeteria_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;

import com.rubenrf.cafeteria_app.dto.cliente.DatosListadoCliente;
import com.rubenrf.cafeteria_app.dto.producto.DatosListadoProducto;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class CafeteriaAppApplicationTests {

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

		RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201);

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

		RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201);

	}

	@Test
	void obtenerCliente() {
		String crearClienteJson = """
				{
					"nombre": "Ruben Robles",
					"correo": "ruben@mail.com",
					"telefono": "+573020202022"
				}
				""";
		DatosListadoCliente cliente = RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().as(DatosListadoCliente.class);

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/clientes/" + cliente.id())
				.then()
				.log().all()
				.statusCode(200);

	}

	@Test
	void obtenerProducto() {
		String crearProductoJson = """
				{
					"nombre": "Café",
					"precio": 500,
					"categoria": "BEBIDA",
					"stock": 100
				}
				""";
		DatosListadoProducto producto = RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().as(DatosListadoProducto.class);

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/productos/" + producto.id())
				.then()
				.log().all()
				.statusCode(200);
	}

	@Test
	void obtenerClientes() {
		String crearClienteJson = """
				{
					"nombre": "Ruben Robles",
					"correo": "ruben@mail.com",
					"telefono": "+573020202022"
				}
				""";

		String crearClienteJson2 = """
				{
					"nombre": "Diego Montes",
					"correo": "diego@mail.com",
					"telefono": "+573112001122"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201);

		RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson2)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201);

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/clientes")
				.then()
				.log().all()
				.statusCode(200);

	}

	@Test
	void obtenerClientesVacio() {

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/clientes")
				.then()
				.log().all()
				.statusCode(204);

	}

	@Test
	void obtenerProductos() {
		String crearProductoJson = """
				{
					"nombre": "Café",
					"precio": 500,
					"categoria": "BEBIDA",
					"stock": 100
				}
				""";

		String crearProductoJson2 = """
				{
					"nombre": "Coca Cola 400ml",
					"precio": 2500,
					"categoria": "BEBIDA",
					"stock": 100
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201);

		RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson2)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201);

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/productos")
				.then()
				.log().all()
				.statusCode(200);
	}

	@Test
	void obtenerProductosVacio() {

		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/productos")
				.then()
				.log().all()
				.statusCode(204);

	}

	@Test
	void actualizarCliente() {
		String crearClienteJson = """
				{
					"nombre": "Ruben Robles",
					"correo": "ruben@mail.com",
					"telefono": "+573020202022"
				}
				""";

		DatosListadoCliente cliente = RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().as(DatosListadoCliente.class);

		String actualizarClienteJson = """
				{
					"nombre": "Ruben Fontalvo",
					"correo": "ruben2@mail.com",
					"telefono": "+573124400022"
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(actualizarClienteJson)
				.when()
				.put("/api/clientes/" + cliente.id())
				.then()
				.log().all()
				.statusCode(200);

	}

	@Test
	void actualizarProducto() {
		String crearProductoJson = """
				{
					"nombre": "Café",
					"precio": 500,
					"categoria": "BEBIDA",
					"stock": 100
				}
				""";

		DatosListadoProducto producto = RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().as(DatosListadoProducto.class);

		String actualizarProductoJson = """
				{
					"nombre": "Café Sello Rojo",
					"precio": 1500,
					"categoria": "BEBIDA",
					"stock": 1000
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(actualizarProductoJson)
				.when()
				.put("/api/productos/" + producto.id())
				.then()
				.log().all()
				.statusCode(200);

	}

	@Test
	void eliminarCliente() {
		String crearClienteJson = """
				{
					"nombre": "Ruben Robles",
					"correo": "ruben@mail.com",
					"telefono": "+573020202022"
				}
				""";

		DatosListadoCliente cliente = RestAssured.given()
				.contentType("application/json")
				.body(crearClienteJson)
				.when()
				.post("/api/clientes")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().as(DatosListadoCliente.class);

		RestAssured.given()
				.contentType("application/json")
				.when()
				.delete("/api/clientes/" + cliente.id())
				.then()
				.log().all()
				.statusCode(204);

	}

	@Test
	void eliminarProducto() {
		String crearProductoJson = """
				{
					"nombre": "Café",
					"precio": 500,
					"categoria": "BEBIDA",
					"stock": 100
				}
				""";

		DatosListadoProducto producto = RestAssured.given()
				.contentType("application/json")
				.body(crearProductoJson)
				.when()
				.post("/api/productos")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().as(DatosListadoProducto.class);

		RestAssured.given()
				.contentType("application/json")
				.when()
				.delete("/api/productos/" + producto.id())
				.then()
				.log().all()
				.statusCode(204);

	}

}
