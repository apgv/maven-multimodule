package me.colombiano.kurs.maven3.service;

import no.mesan.kurs.maven3.domain.WeatherLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class WeatherService {

    @Autowired
    private WeatherRetriever weatherRetriever;
    
    @Autowired
    private WeatherParser weatherParser;

    public WeatherLocation getOfflineForecast() {
        WeatherLocation weatherLocation = null;

        try {
            InputStream weatherStream = weatherRetriever.getOfflineWeater();
            weatherLocation = weatherParser.parse(weatherStream);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return weatherLocation;
    }
}
