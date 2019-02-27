package co.simplon.cityspringtest.service;

import java.util.List;

import co.simplon.cityspringtest.model.Monument;

public interface MonumentService {

	public List<Monument> getAllCityMonuments(String cityName);

	public Monument getMonumentByCityAndName(String cityName, String name);

	public Monument saveMonumentToCity(String cityName, Monument monument);

}
