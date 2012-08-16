package me.colombiano.kurs.maven3.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class WeatherRetriever {
    private Resource resource = new ClassPathResource("yr-varsel-oslo.xml");

    public InputStream getOfflineWeater() throws Exception {
        return resource.getInputStream();
    }

    public InputStream getWeather(String country, String region, String municipality, String place) throws Exception {
//        http://www.yr.no/place/Norway/Akershus/Ski/Skotbu/forecast.xml
        final String url = "http://www.yr.no/place/" + country + "/" + region + "/" + municipality + "/" + place + "/forecast.xml";
        URLConnection urlConnection = new URL(url).openConnection();

        return urlConnection.getInputStream();
    }
}
