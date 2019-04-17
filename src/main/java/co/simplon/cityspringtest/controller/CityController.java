package co.simplon.cityspringtest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.cityspringtest.exception.CityNameNotFoundException;
import co.simplon.cityspringtest.exception.MonumentNameNotFoundException;
import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.model.Monument;
import co.simplon.cityspringtest.service.CityService;
import co.simplon.cityspringtest.service.MonumentService;

@RestController
@RequestMapping("/api/city")
public class CityController {

	private CityService cityService;
	private MonumentService monumentService;

	public CityController(CityService cityService, MonumentService monumentService) {
		this.cityService = cityService;
		this.monumentService = monumentService;
	}

	@GetMapping
	public List<City> getCities() {
		return cityService.getAllCities();
	}

	@GetMapping("/{cityName}")
	public ResponseEntity<City> getCityByName(@PathVariable String cityName) {
		
		try {
			return ResponseEntity.ok(cityService.getCityByName(cityName));
		} 
		
		catch (CityNameNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		
	}

	@GetMapping("/{cityName}/monument")
	public List<Monument> getCityMonuments(@PathVariable String cityName) {
		return monumentService.getAllCityMonuments(cityName);
	}

	@GetMapping("/{cityName}/monument/{monumentName}")
	public ResponseEntity<Monument> getCityMonumentByName(@PathVariable String cityName,
			@PathVariable String monumentName) {
		
		try {
			Monument monument = monumentService.getMonumentByCityAndName(cityName, monumentName);
			return ResponseEntity.ok(monument);
		} 
		
		catch (MonumentNameNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<City> createCity(@RequestBody City newCity) {
		City newSavedCity = cityService.saveCity(newCity);
		return ResponseEntity.ok(newSavedCity);

	}

	@PostMapping("/{cityName}/monument")
	public ResponseEntity<Monument> createCityMonument(@PathVariable String cityName,
			@RequestBody Monument newMonument) {
		Monument newSavedMonument = this.monumentService.saveMonumentToCity(cityName, newMonument);

		if (newSavedMonument != null) {
			return ResponseEntity.ok(newSavedMonument);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping()
	public ResponseEntity<City> updateCity(@RequestBody City city) {
		City updated = cityService.updateCity(city);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{cityName}")
	public ResponseEntity<City> deleteCity(@PathVariable String cityName) {
		try {
			cityService.deleteCity(cityName);
			return ResponseEntity.noContent().build();
			
		} catch (CityNameNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
