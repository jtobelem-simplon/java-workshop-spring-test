package co.simplon.cityspringtest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.model.Monument;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MonumentRepositoryTests {

    @Autowired
    private MonumentRepository monumentRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findMonumentByCityAndName() throws Exception {

        City savedCity = testEntityManager.persistFlushFind(
                new City("Toulouse", 31));

        Monument savedMonument = testEntityManager.persistFlushFind(
                new Monument("arc de triomphe", savedCity));

        Monument arcDeTriomphe = this.monumentRepo.findByCityNameAndName(
                "Toulouse", "arc de triomphe");

        assertThat(arcDeTriomphe.getName())
        .isEqualTo(savedMonument.getName());

        assertThat(arcDeTriomphe.getCity().getDptCode())
        .isEqualTo(savedMonument.getCity().getDptCode());

        assertThat(arcDeTriomphe.getCity().getName())
        .isEqualTo(savedMonument.getCity().getName());

    }
}