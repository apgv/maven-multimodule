package me.colombiano.kurs.maven3.domain;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
public class Forecast {

    @Id
    @Column(name = "forecast_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "weatherLocation_id")
    private WeatherLocation weatherLocation;

    private String fromTime;
    private String toTime;
    private String description;
    private String minPrecipitation;
    private String maxPrecipitation;
    private String temperature;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WeatherLocation getWeatherLocation() {
        return weatherLocation;
    }

    public void setWeatherLocation(WeatherLocation weatherLocation) {
        this.weatherLocation = weatherLocation;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinPrecipitation() {
        return minPrecipitation;
    }

    public void setMinPrecipitation(String minPrecipitation) {
        if (StringUtils.isBlank(minPrecipitation)) {
            this.minPrecipitation = "0";
        } else {
            this.minPrecipitation = minPrecipitation;
        }
    }

    public String getMaxPrecipitation() {
        return maxPrecipitation;
    }

    public void setMaxPrecipitation(String maxPrecipitation) {
        if (StringUtils.isBlank(maxPrecipitation)) {
            this.maxPrecipitation = "0";
        } else {
            this.maxPrecipitation = maxPrecipitation;
        }
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
