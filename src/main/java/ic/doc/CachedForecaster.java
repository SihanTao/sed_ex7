package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import java.util.*;

public class CachedForecaster extends Forecaster {

  private final Forecaster forecaster;
  private static final int MAX_SIZE = 5;
  private final LinkedHashMap<PlaceAndDayToForeCast, ForecastWithCreationTime> cache =
      new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(
            Map.Entry<PlaceAndDayToForeCast, ForecastWithCreationTime> eldest) {
          return size() > MAX_SIZE;
        }
      };

  public CachedForecaster(Forecaster forecaster) {
    this.forecaster = forecaster;
  }

  /*
   forecast for the REGION, DAY.
   If the information is in the cache, then read it directly.
   Otherwise, add it to the cache.
   If the number of information > MAX_SIZE, then remove the information
   from queue and cache.
  */
  @Override
  public Forecast forecastFor(Region region, Day day) {
    PlaceAndDayToForeCast placeAndDayToForeCast = new PlaceAndDayToForeCast(region, day);

    ForecastWithCreationTime forecastWithCreationTime = cache.get(placeAndDayToForeCast);
    if (forecastWithCreationTime != null) {
      if (!forecastWithCreationTime.isExpired()) {
        return forecastWithCreationTime.getForecast();
      } else {
        Forecast newForecast = forecaster.forecastFor(region, day);
        ForecastWithCreationTime forecastWithCreationTime1 =
            new ForecastWithCreationTime(newForecast);
        cache.put(placeAndDayToForeCast, forecastWithCreationTime1);
        return newForecast;
      }
    } else {
      return addForecastAndReturn(region, day, placeAndDayToForeCast);
    }
  }

  private Forecast addForecastAndReturn(
      Region region, Day day, PlaceAndDayToForeCast placeAndDayToForeCast) {
    Forecast forecast = forecaster.forecastFor(region, day);
    cache.put(placeAndDayToForeCast, new ForecastWithCreationTime(forecast));
    return forecast;
  }

  public LinkedHashMap<PlaceAndDayToForeCast, ForecastWithCreationTime> getCache() {
    return cache;
  }
}
