package co.simplon.cityspringtest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.simplon.cityspringtest.exception.MonumentNameNotFoundException;
import co.simplon.cityspringtest.model.City;
import co.simplon.cityspringtest.model.Monument;
import co.simplon.cityspringtest.repository.CityRepository;
import co.simplon.cityspringtest.repository.MonumentRepository;

@Service
public class MonumentServiceImpl implements MonumentService {

	private MonumentRepository monumentRepository;
	private CityRepository cityRepository;

	public MonumentServiceImpl(MonumentRepository monumentRepository, CityRepository cityRepository) {
		this.monumentRepository = monumentRepository;
		this.cityRepository = cityRepository;
	}

	@Override
	public List<Monument> getAllCityMonuments(String cityName) {
		return monumentRepository.findAllByCityName(cityName);
	}

	@Override
	public Monument getMonumentByCityAndName(String cityName, String name) 
			throws MonumentNameNotFoundException {
		Monument monument = monumentRepository.findByCityNameAndName(cityName,name);
		
		if (monument != null) {
			return monument;
		}
		else {
			throw new MonumentNameNotFoundException(cityName, name);
		}
	}

	@Override
	public Monument saveMonumentToCity(String cityName, Monument monument) {
		Monument newSavedMonument = null;
		City city = cityRepository.findByName(cityName);

		if (city != null) {
			newSavedMonument = monumentRepository.save(monument);
			monument.setCity(city);
		}

		return newSavedMonument;
	}

}
