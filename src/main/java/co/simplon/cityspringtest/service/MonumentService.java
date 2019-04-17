package co.simplon.cityspringtest.service;

import java.util.List;

import co.simplon.cityspringtest.exception.MonumentNameNotFoundException;
import co.simplon.cityspringtest.model.Monument;

public interface MonumentService {

	public List<Monument> getAllCityMonuments(String cityName);

	public Monument getMonumentByCityAndName(String cityName, String name) throws MonumentNameNotFoundException;

	public Monument saveMonumentToCity(String cityName, Monument monument);

}
