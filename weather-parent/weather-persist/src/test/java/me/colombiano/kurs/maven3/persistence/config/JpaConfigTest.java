package me.colombiano.kurs.maven3.persistence.config;

import me.colombiano.kurs.maven3.domain.WeatherLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaConfig.class)
public class JpaConfigTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testRetrieveWeatherForecast() throws Exception {
        Query query = entityManager.createQuery("select w from WeatherLocation w left join fetch w.forecasts where w.id =:id").setParameter("id", 3L);
        WeatherLocation weatherLocation = (WeatherLocation) query.getSingleResult();

        assertNotNull("WeatherLocation is null", weatherLocation);
        assertEquals("Location name is wrong", "Skotbu", weatherLocation.getLocationName());
        assertEquals("Location type is wrong", "Rural", weatherLocation.getLocationType());
        assertEquals("Country is wrong", "Norway", weatherLocation.getCountry());
        assertEquals("Time zone is wrong", "Europe/Oslo", weatherLocation.getTimeZone());
        assertEquals("Altitude is wrong", "110", weatherLocation.getAltitude());
        assertEquals("Latitude is wrong", "500", weatherLocation.getLatitude());
        assertEquals("Longitude is wrong", "600", weatherLocation.getLongitude());

        assertEquals("The number of forecasts is wrong", 1, weatherLocation.getForecasts().size());
    }
}
