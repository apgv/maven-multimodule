package me.colombiano.kurs.maven3.persistence.repo;

import me.colombiano.kurs.maven3.domain.WeatherLocation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class WeatherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WeatherLocation findById(long id) {
        return entityManager.createQuery("select w from WeatherLocation w left join fetch w.forecasts where w.id =:id", WeatherLocation.class).setParameter("id", id).getSingleResult();
    }

    public void save(WeatherLocation weatherLocation) {
        entityManager.persist(weatherLocation);
    }
}
