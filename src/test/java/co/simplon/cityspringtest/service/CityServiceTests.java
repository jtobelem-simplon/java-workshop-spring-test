package co.simplon.cityspringtest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import co.simplon.cityspringtest.exception.CityNameNotFoundException;
import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.repository.CityRepository;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTests {

	@Mock
	CityRepository cityRepo;

	private CityService cityService;

	@Before
	public void setUp() throws Exception {
		cityService = new CityServiceImpl(cityRepo);
	}

	@Test
	public void getAllCities() {
		given(cityRepo.findAll()).willReturn(new ArrayList<>());

		List<City> cities = cityService.getAllCities();

		assertThat(cities).isNotNull();
	}

	@Test
	public void getCityByName() throws CityNameNotFoundException {
		given(cityRepo.findByName("Paris")).willReturn(new City("Paris", 75));

		City paris = cityService.getCityByName("Paris");

		assertThat(paris.getName()).isEqualTo("Paris");
		assertThat(paris.getDptCode()).isEqualTo(75);
	}

	@Test(expected = CityNameNotFoundException.class)
	public void getCityByNameNotFound() throws CityNameNotFoundException {
		given(cityRepo.findByName("Minas Tirith")).willReturn(null);
		cityService.getCityByName("Minas Tirith");
	}

	@Test
	public void createCity() {
		City paris = new City("Paris", 75);
		given(cityRepo.save(paris)).willReturn(new City("Paris", 75));

		City savedParis = cityService.saveCity(paris);

		assertThat(savedParis.getName()).isEqualTo("Paris");
		assertThat(savedParis.getDptCode()).isEqualTo(75);
	}

}
