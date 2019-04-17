package co.simplon.cityspringtest.service;

import java.util.List;

import co.simplon.cityspringtest.exception.CityNameNotFoundException;
import co.simplon.cityspringtest.model.City;

public interface CityService {

	public List<City> getAllCities();

	public City getCityByName(String name) throws CityNameNotFoundException;

	public City saveCity(City city);
	
	public City updateCity(City city);
	
	public void deleteCity(String name) throws CityNameNotFoundException;

}
