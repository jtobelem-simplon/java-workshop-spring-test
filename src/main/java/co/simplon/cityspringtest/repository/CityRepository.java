package co.simplon.cityspringtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.simplon.cityspringtest.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	public City findByName(String name);

}
