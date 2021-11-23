package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import java.util.*;

public class CachedForecaster extends Forecaster {

  private final Forecaster forecaster;
  private final Map<PlaceAndDayToForeCast, Forecast> cache =
      new LinkedHashMap<>();
  private final Queue<PlaceAndDayToForeCast> queue = new LinkedList<>();
  private static final int MAX_SIZE = 5;

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

    Forecast forecast = cache.get(placeAndDayToForeCast);
    if (forecast != null) {
      return forecast;
    } else {
      return addForecastAndReturn(region, day, placeAndDayToForeCast);
    }
  }

  private Forecast addForecastAndReturn(
      Region region, Day day, PlaceAndDayToForeCast placeAndDayToForeCast) {
    Forecast forecast = forecaster.forecastFor(region, day);
    if (queue.size() <= MAX_SIZE) {
      queue.add(placeAndDayToForeCast);
    } else {
      queue.poll();
      cache.remove(placeAndDayToForeCast);
    }
    cache.put(placeAndDayToForeCast, forecast);
    return forecast;
  }
}
