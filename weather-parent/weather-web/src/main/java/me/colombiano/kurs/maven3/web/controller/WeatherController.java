package me.colombiano.kurs.maven3.web.controller;

import no.mesan.kurs.maven3.domain.WeatherLocation;
import no.mesan.kurs.maven3.persistence.WeatherRepository;
import no.mesan.kurs.maven3.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private WeatherRepository weatherRepository;

    @RequestMapping("/weather")
    public String getOfflineForecast(Model model) {
        WeatherLocation offlineWeatherLocation = weatherService.getOfflineForecast();
        model.addAttribute("weather", offlineWeatherLocation);

        return "weather";
    }

    @RequestMapping("/weather/db/{id}")
    public String getDatabaseWeather(@PathVariable Long id, Model model) {
        WeatherLocation databaseWeather = weatherRepository.findById(id);
        model.addAttribute("weather", databaseWeather);

        return "weather";
    }
}
