package co.simplon.cityspringtest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.simplon.cityspringtest.exception.CityNameNotFoundException;
import co.simplon.cityspringtest.exception.MonumentNameNotFoundException;
import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.model.Monument;
import co.simplon.cityspringtest.service.CityService;
import co.simplon.cityspringtest.service.MonumentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CityService cityService;

	@MockBean
	MonumentService monumentService;

	@Autowired
	private ObjectMapper objectMapper;

	JacksonTester<City> cityJacksonTester;
	JacksonTester<Monument> monumentJacksonTester;

	@Before
	public void setUp() {
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void getCities() throws Exception {
		when(this.cityService.getAllCities()).thenReturn(new ArrayList<>());

		this.mockMvc.perform(get("/api/city")).andExpect(status().isOk());
	}

	@Test
	public void getCityByName() throws Exception {
		when(this.cityService.getCityByName("paris")).thenReturn(new City("Paris", 75));

		this.mockMvc.perform(get("/api/city/paris")).andExpect(status().isOk())
				.andExpect(jsonPath("name").value("Paris")).andExpect(jsonPath("dptCode").value(75));
	}

	@Test
	public void getCityByNameNotFound() throws Exception {
		String cityName = "minas-tirith";
		when(this.cityService.getCityByName(any())).thenThrow(new CityNameNotFoundException(cityName));

		this.mockMvc.perform(get("/api/city/" + cityName)).andExpect(status().isNotFound());
	}

	@Test
	public void getCityMonuments() throws Exception {
		when(this.monumentService.getAllCityMonuments(any())).thenReturn(new ArrayList<>());

		this.mockMvc.perform(get("/api/city/Toto/monument")).andExpect(status().isOk());

	}

	/**
	 * Un test un peu basique dans lequel on teste juste le statut.
	 */
	@Test
	public void getCityMonumentByName() throws Exception {

		when(this.monumentService.getMonumentByCityAndName("Paris", "Tour Eiffel"))
				.thenReturn(new Monument("Tour Eiffel", new City("Paris", 75)));

		this.mockMvc.perform(get("/api/city/Paris/monument/Tour Eiffel")).andExpect(status().isOk());

	}

	/**
	 * Un test un peu mieux dans lequel on test aussi quelques valeurs retournées
	 * par le controleur. On peut ainsi vérifier que la sérialisation (java -->
	 * json) fonctionne bien.
	 */
	@Test
	public void getCityMonumentByName2() throws Exception {

		when(this.monumentService.getMonumentByCityAndName(any(), any()))
				.thenReturn(new Monument("Louvre", new City("Paris", 75)));

		this.mockMvc.perform(get("/api/city/paris/monument/louvre")).andExpect(status().isOk())
				.andExpect(jsonPath("name").value("Louvre")).andExpect(jsonPath("city.name").value("Paris"))
				.andExpect(jsonPath("city.dptCode").value(75));

	}

	@Test
	public void getCityMonumentByNameNotFound() throws Exception {
		when(this.monumentService.getMonumentByCityAndName("paname", "truc"))
				.thenThrow(new MonumentNameNotFoundException("paname", "truc"));

		this.mockMvc.perform(get("/api/city/paname/monument/truc")).andExpect(status().isNotFound());
	}

	/**
	 * Dans ce test on écrit le json qui permet de créer une nouvelle entité
	 */
	@Test
	public void addCity() throws Exception {
		when(this.cityService.saveCity(any())).thenReturn(new City("Toulouse", 31));

		String jsonContent = "{\"name\": \"Toulouse\", \"dptCode\": \"31\"}";

		this.mockMvc.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value("Toulouse"))
				.andExpect(jsonPath("dptCode").value(31));
	}

	/**
	 * Dans ce test on utilise JacksonTester pour générer le json qui permet de
	 * créer une nouvelle entité
	 */
	@Test
	public void addCity2() throws Exception {
		City city = new City("Toulouse", 31);
		when(this.cityService.saveCity(any())).thenReturn(city);

		String jsonContent = cityJacksonTester.write(city).getJson();

		this.mockMvc.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value(city.getName()))
				.andExpect(jsonPath("dptCode").value(city.getDptCode()));
	}

	@Test
	public void addMonumentToCity() throws Exception {
		Monument monument = new Monument("Arc de Triomphe", new City("Paris", 75));

		when(this.monumentService.saveMonumentToCity(any(), any())).thenReturn(monument);

		String jsonContent = monumentJacksonTester.write(monument).getJson();

		this.mockMvc
				.perform(post("/api/city/paris/monument").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value(monument.getName()));
	}

	@Test
	public void addMonumentToCityNotFound() throws Exception {
		when(this.monumentService.saveMonumentToCity("Minas Tirith", new Monument("Arbre blanc", null)))
				.thenReturn(null);

		this.mockMvc.perform(post("/api/city/minas-tirith/monument").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\": \"Arbre blanc\"}")).andExpect(status().isNotFound());
	}

	@Test
	public void deleteCity() throws Exception {
		doNothing().when(this.cityService).deleteCity(any());

		this.mockMvc.perform(delete("/api/city/paris")).andExpect(status().isNoContent());
	}

	@Test
	public void updateCity() throws Exception {
		City city = new City("Toulouse", 31);

		when(this.cityService.updateCity(any())).thenReturn(city);

		String jsonContent = cityJacksonTester.write(city).getJson();

		this.mockMvc.perform(put("/api/city").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value(city.getName()))
				.andExpect(jsonPath("dptCode").value(city.getDptCode()));

	}

}
