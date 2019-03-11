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

import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.model.Monument;
import co.simplon.cityspringtest.repository.CityRepository;
import co.simplon.cityspringtest.repository.MonumentRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MonumentServiceTests {

	@Mock
	MonumentRepository monumentRepo;

	@Mock
	CityRepository cityRepo;

	private MonumentService monumentService;

	@Before
	public void setUp() throws Exception {
		monumentService = new MonumentServiceImpl(monumentRepo, cityRepo);
	}

	@Test
	public void getAllCityMonuments() {
		given(monumentRepo.findAll()).willReturn(new ArrayList<>());

		List<Monument> monuments = monumentService.getAllCityMonuments("Paris");

		assertThat(monuments).isNotNull();
	}

	@Test
	public void getMonumentByCityAndName() {
		given(monumentRepo.findByCityNameAndName("Paris", "Tour Eiffel"))
				.willReturn(new Monument("Louvre", new City("Paris", 75)));

		Monument louvre = monumentService.getMonumentByCityAndName("Paris", "Tour Eiffel");

		assertThat(louvre.getName()).isEqualTo("Louvre");
		assertThat(louvre.getCity().getDptCode()).isEqualTo(75);
	}

	@Test
	public void getMonumentByCityAndNameNotFound() {
		given(monumentRepo.findByCityNameAndName("Paris", "Tour de Pise")).willReturn(null);

		Monument notFoundMonument = monumentService.getMonumentByCityAndName("Paris", "Tour de Pise");

		assertThat(notFoundMonument).isNull();
	}

	@Test
	public void saveMonumentToCity() {
		Monument louvre = new Monument("louvre", new City("Paris", 75));
		given(monumentRepo.save(louvre)).willReturn(new Monument("louvre", new City("Paris", 75)));
		given(cityRepo.findByName("Paris")).willReturn(new City("Paris", 75));

		Monument savedLouvre = monumentService.saveMonumentToCity("Paris", louvre);

		assertThat(savedLouvre.getName()).isEqualTo("louvre");
		assertThat(savedLouvre.getCity().getDptCode()).isEqualTo(75);
	}
}