package co.simplon.cityspringtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.simplon.cityspringtest.model.Monument;

public interface MonumentRepository extends JpaRepository<Monument, Long> {

	public List<Monument> findAllByCityName(String cityName);
	
	public Monument findByCityNameAndName(String cityName, String monumentName);

}
