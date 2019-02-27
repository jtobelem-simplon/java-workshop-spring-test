package co.simplon.cityspringtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import co.simplon.cityspringtest.model.City;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryTests {

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void findCityByName() throws Exception {
		City savedCity = testEntityManager.persistFlushFind(new City("Toulouse", 31));
		City toulouse = this.cityRepo.findByName("Toulouse");
		assertThat(toulouse.getName()).isEqualTo(savedCity.getName());
		assertThat(toulouse.getDptCode()).isEqualTo(savedCity.getDptCode());
	}
}
