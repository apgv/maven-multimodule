    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <html>
    <h2>Weather forecast</h2>
    <table>
        <tr><th>Location info</th></tr>
        <tr><td>Name:</td><td>${weather.locationName}</td></tr>
        <tr><td>Type:</td><td>${weather.locationType}</td></tr>
        <tr><td>Country:</td><td>${weather.country}</td></tr>
        <tr><td>Time zone:</td><td>${weather.timeZone}</td></tr>
        <tr><td>Altitude:</td><td>${weather.altitude}</td></tr>
        <tr><td>Latitude:</td><td>${weather.latitude}</td></tr>
        <tr><td>Longitude:</td><td>${weather.longitude}</td></tr>
    </table>
    <br/>

    <h2>Forecasts</h2>
    <strong>Number of forecasts: ${weather.forecasts.size()}</strong>
    <br/>
    <br/>

    <c:forEach var="forecast" items="${weather.forecasts}">
        <table>
            <tr><th>Forecast details:</th></tr>
            <tr><td>From:</td><td>${forecast.fromTime}</td></tr>
            <tr><td>To:</td><td>${forecast.toTime}</td></tr>
            <tr><td>Description:</td><td>${forecast.description}</td></tr>
            <tr><td>Min precipitation:</td><td>${forecast.minPrecipitation}</td></tr>
            <tr><td>Max precipitation:</td><td>${forecast.maxPrecipitation}</td></tr>
            <tr><td>Temperature:</td><td>${forecast.temperature} &deg;C</td></tr>
        </table>
        <br/>
    </c:forEach>

</html>