package co.simplon.cityspringtest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.repository.CityRepository;

@Service
public class CityServiceImpl implements CityService {

	private CityRepository cityRepository;

	public CityServiceImpl(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Override
	public List<City> getAllCities() {
		return cityRepository.findAll();
	}

	@Override
	public City getCityByName(String name) {
		return cityRepository.findByName(NameResourceHelper.urlToName(name));
	}

	@Override
	public City saveCity(City city) {
		return cityRepository.save(city);
	}

}
