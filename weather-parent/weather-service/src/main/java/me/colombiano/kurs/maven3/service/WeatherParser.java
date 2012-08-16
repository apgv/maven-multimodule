package me.colombiano.kurs.maven3.service;

import no.mesan.kurs.maven3.domain.Forecast;
import no.mesan.kurs.maven3.domain.WeatherLocation;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Node;
import nu.xom.Nodes;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherParser {

    public WeatherLocation parse(InputStream inputStream) throws Exception {
        Builder builder = new Builder();
        Document document = builder.build(inputStream);

//        Location location = new Location();
        WeatherLocation weatherLocation = new WeatherLocation();

        final Node locNode = document.query("/weatherdata/location").get(0);

        weatherLocation.setLocationName(getElementValue(locNode, "name"));
        weatherLocation.setLocationType(getElementValue(locNode, "type"));
        weatherLocation.setCountry(getElementValue(locNode, "country"));
        weatherLocation.setTimeZone(getChildElementAttribute(locNode, "timezone", "id"));
        weatherLocation.setAltitude(getChildElementAttribute(locNode, "location", "altitude"));
        weatherLocation.setLatitude(getChildElementAttribute(locNode, "location", "latitude"));
        weatherLocation.setLongitude(getChildElementAttribute(locNode, "location", "longitude"));

//        location.setName(getElementValue(locNode, "name"));
//        location.setType(getElementValue(locNode, "type"));
//        location.setCountry(getElementValue(locNode, "country"));
//        location.setTimeZone(getChildElementAttribute(locNode, "timezone", "id"));
//        location.setAltitude(getChildElementAttribute(locNode, "location", "altitude"));
//        location.setLatitude(getChildElementAttribute(locNode, "location", "latitude"));
//        location.setLongitude(getChildElementAttribute(locNode, "location", "longitude"));

        List<Forecast> forecasts = new ArrayList<Forecast>();

        final Nodes forecastsNodes = document.query("/weatherdata/forecast/tabular/time");

        for (int i = 0; i < forecastsNodes.size(); i++) {
            Node node = forecastsNodes.get(i);

            Forecast forecast = new Forecast();

            forecast.setFromTime(getCurrentElementAttribute(node, "from"));
            forecast.setToTime(getCurrentElementAttribute(node, "to"));
            forecast.setDescription(getChildElementAttribute(node, "symbol", "name"));
            forecast.setMinPrecipitation(getChildElementAttribute(node, "precipitation", "minvalue"));
            forecast.setMaxPrecipitation(getChildElementAttribute(node, "precipitation", "maxvalue"));
            forecast.setTemperature(getChildElementAttribute(node, "temperature", "value"));

            forecasts.add(forecast);
        }

//        weather.setLocation(location);
        weatherLocation.setForecasts(forecasts);

        return weatherLocation;
    }

    protected String getCurrentElementAttribute(Node node, String attributeName) {
        return node.query("@" + attributeName).get(0).getValue();
    }

    protected String getChildElementAttribute(Node node, String nodeName, String attributeName) {
        String result = "";

        final Nodes nodes = node.query(nodeName + "/@" + attributeName);

        if(nodes.size() > 0) {
            result = nodes.get(0).getValue();
        }

        return result;
    }

    protected String getElementValue(Node node, String nodeName) {
        return node.query(nodeName).get(0).getValue();
    }
}