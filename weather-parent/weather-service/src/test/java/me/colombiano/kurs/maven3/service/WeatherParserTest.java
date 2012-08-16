package me.colombiano.kurs.maven3.service;

import no.mesan.kurs.maven3.domain.Forecast;
import no.mesan.kurs.maven3.domain.WeatherLocation;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Node;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class WeatherParserTest {

    private Resource resource = new ClassPathResource("yr-varsel-oslo.xml");

    @Test
    public void testParse() throws Exception {
        WeatherParser weatherParser = new WeatherParser();
        final WeatherLocation weatherLocation = weatherParser.parse(resource.getInputStream());

        assertNotNull("WeatherLocation can't be null", weatherLocation);

        assertEquals("Location value name is wrong", "Oslo", weatherLocation.getLocationName());
        assertEquals("Location value type is wrong", "City - large town", weatherLocation.getLocationType());
        assertEquals("Location value country is wrong", "Norway", weatherLocation.getCountry());
        assertEquals("Location value timezone is wrong", "Europe/Oslo", weatherLocation.getTimeZone());
        assertEquals("Location value altitude is wrong", "10", weatherLocation.getAltitude());
        assertEquals("Location value latitude is wrong", "59.912726542422", weatherLocation.getLatitude());
        assertEquals("Location value longitude is wrong", "10.7460923576733", weatherLocation.getLongitude());

        final Forecast forecast = weatherLocation.getForecasts().get(0);

        assertNotNull("Forecast can't be null", forecast);
        assertEquals("Forecast value from is wrong", "2012-08-07T12:00:00", forecast.getFromTime());
        assertEquals("Forecast value to is wrong", "2012-08-07T18:00:00", forecast.getToTime());
        assertEquals("Forecast value description is wrong", "Rain", forecast.getDescription());
        assertEquals("Forecast value minPrecipitation is wrong", "0.5", forecast.getMinPrecipitation());
        assertEquals("Forecast value maxPrecipitation is wrong", "2.8", forecast.getMaxPrecipitation());
        assertEquals("Forecast value temperature is wrong", "17", forecast.getTemperature());
    }

    @Test
    public void testParseWithMissingXmlAttributesInForecastData() throws Exception {
        WeatherParser weatherParser = new WeatherParser();
        final WeatherLocation weatherLocation = weatherParser.parse(resource.getInputStream());

        assertNotNull("WeatherLocation can't be null", weatherLocation);

        final Forecast forecast = weatherLocation.getForecasts().get(3);

        assertNotNull("Forecast can't be null", forecast);
        assertEquals("Forecast value from is wrong", "2012-08-08T06:00:00", forecast.getFromTime());
        assertEquals("Forecast value to is wrong", "2012-08-08T12:00:00", forecast.getToTime());
        assertEquals("Forecast value description is wrong", "Fair", forecast.getDescription());
        assertEquals("Forecast value minPrecipitation is wrong", "0", forecast.getMinPrecipitation());
        assertEquals("Forecast value maxPrecipitation is wrong", "0", forecast.getMaxPrecipitation());
        assertEquals("Forecast value temperature is wrong", "14", forecast.getTemperature());
    }

    @Test
    public void testGetCurrentElementAttribute() throws Exception {
        Builder builder = new Builder();
        Document document = builder.build(resource.getInputStream());

        final Node node = document.query("/weatherdata/forecast/tabular/time").get(0);

        WeatherParser weatherParser = new WeatherParser();

        assertEquals("value from is wrong", "2012-08-07T12:00:00", weatherParser.getCurrentElementAttribute(node, "from"));
        assertEquals("value to is wrong", "2012-08-07T18:00:00", weatherParser.getCurrentElementAttribute(node, "to"));
        assertEquals("value description is wrong", "Rain", weatherParser.getChildElementAttribute(node, "symbol", "name"));
        assertEquals("value minPrecipitation is wrong", "0.5", weatherParser.getChildElementAttribute(node, "precipitation", "minvalue"));
        assertEquals("value maxPrecipitation is wrong", "2.8", weatherParser.getChildElementAttribute(node, "precipitation", "maxvalue"));
        assertEquals("value temperature is wrong", "17", weatherParser.getChildElementAttribute(node, "temperature", "value"));
    }

    @Test
    public void testThatGetCurrentElementAttributeWontFailIfAttributeIsMissing() throws Exception {
        Builder builder = new Builder();
        Document document = builder.build(resource.getInputStream());

        final Node node = document.query("/weatherdata/forecast/tabular/time").get(3);

        WeatherParser weatherParser = new WeatherParser();

        assertEquals("value from is wrong", "2012-08-08T06:00:00", weatherParser.getCurrentElementAttribute(node, "from"));
        assertEquals("value to is wrong", "2012-08-08T12:00:00", weatherParser.getCurrentElementAttribute(node, "to"));
        assertEquals("value description is wrong", "Fair", weatherParser.getChildElementAttribute(node, "symbol", "name"));
        assertEquals("value minPrecipitation is wrong", "", weatherParser.getChildElementAttribute(node, "precipitation", "minvalue"));
        assertEquals("value maxPrecipitation is wrong", "", weatherParser.getChildElementAttribute(node, "precipitation", "maxvalue"));
        assertEquals("value temperature is wrong", "14", weatherParser.getChildElementAttribute(node, "temperature", "value"));
    }

    @Test
    public void testGetElementValue() throws Exception {
        Builder builder = new Builder();
        Document document = builder.build(resource.getInputStream());

        final Node locNode = document.query("/weatherdata/location").get(0);

        WeatherParser weatherParser = new WeatherParser();

        assertEquals("value name is wrong", "Oslo", weatherParser.getElementValue(locNode, "name"));
        assertEquals("value type is wrong", "City - large town", weatherParser.getElementValue(locNode, "type"));
        assertEquals("value country is wrong", "Norway", weatherParser.getElementValue(locNode, "country"));
    }
}
