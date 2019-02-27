package co.simplon.cityspringtest.controller;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.model.Monument;
import co.simplon.cityspringtest.service.CityService;
import co.simplon.cityspringtest.service.MonumentService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CityControllerTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CityService cityService;

	@MockBean
	MonumentService monumentService;

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
		when(this.cityService.getCityByName(any())).thenReturn(null);

		this.mockMvc.perform(get("/api/city/minas-tirith")).andExpect(status().isNotFound());
	}

	@Test
	public void getCityMonuments() throws Exception {
		fail();
		// TODO
	}

	@Test
	public void getCityMonumentByName() throws Exception {
		fail();
		// TODO
	}

	@Test
	public void getCityMonumentByNameNotFound() throws Exception {
		when(this.monumentService.getMonumentByCityAndName(any(), any())).thenReturn(null);

		this.mockMvc.perform(get("/api/city/paris/monument/tour-de-babel")).andExpect(status().isNotFound());
	}

	@Test
	public void addCity() throws Exception {
		when(this.cityService.saveCity(any())).thenReturn(new City("Toulouse", 31));

		this.mockMvc
				.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"name\": \"Toulouse\", \"dptCode\": \"31\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value("Toulouse"))
				.andExpect(jsonPath("dptCode").value(31));
	}

	@Test
	public void addMonumentToCity() throws Exception {
		when(this.monumentService.saveMonumentToCity(any(), any()))
				.thenReturn(new Monument("Arc de Triomphe", new City("Paris", 75)));

		this.mockMvc
				.perform(post("/api/city/paris/monument").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"name\": \"Arc de Triomphe\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value("Arc de Triomphe"));
	}

	@Test
	public void addMonumentToCityNotFound() throws Exception {
		when(this.monumentService.saveMonumentToCity("Minas Tirith", new Monument("Arbre blanc", null)))
				.thenReturn(null);

		this.mockMvc.perform(post("/api/city/minas-tirith/monument").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"name\": \"Arbre blanc\"}")).andExpect(status().isNotFound());
	}

}
