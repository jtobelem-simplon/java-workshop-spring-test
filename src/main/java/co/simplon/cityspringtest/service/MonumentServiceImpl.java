package co.simplon.cityspringtest.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public Monument getMonumentByCityAndName(String cityName, String name) {
		return monumentRepository.findByCityNameAndName(NameResourceHelper.urlToName(cityName),
				NameResourceHelper.urlToName(name));
	}

	@Override
	public Monument saveMonumentToCity(String cityName, Monument monument) {
		Monument newSavedMonument = null;
		City city = cityRepository.findByName(NameResourceHelper.urlToName(cityName));

		if (city != null) {
			monument.setCity(city);
			newSavedMonument = monumentRepository.save(monument);
		}

		return newSavedMonument;
	}

}
