package co.simplon.cityspringtest.service;

import java.util.List;

import co.simplon.cityspringtest.model.City;

public interface CityService {

	public List<City> getAllCities();

	public City getCityByName(String name);

	public City saveCity(City city);

}
