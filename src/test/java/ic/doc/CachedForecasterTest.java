package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CachedForecasterTest {
  private Forecaster forecaster = new Forecaster();
  private CachedForecaster cachedForecaster = new CachedForecaster(forecaster);
  private LinkedHashMap<PlaceAndDayToForeCast, ForecastWithCreationTime> cache = cachedForecaster.getCache();;

  @Test
  public void canCacheWithinMaxSize() {
    assertTrue(cache.isEmpty());
    Forecast forecast1 = cachedForecaster.forecastFor(Region.LONDON, Day.TUESDAY);
    assertEquals(
        forecast1, cache.get(new PlaceAndDayToForeCast(Region.LONDON, Day.TUESDAY)).getForecast());
    assertEquals(1, cache.size());

    Forecast forecast2 = cachedForecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);
    assertEquals(2, cache.size());
    assertEquals(
        forecast2,
        cache.get(new PlaceAndDayToForeCast(Region.EDINBURGH, Day.MONDAY)).getForecast());

    Forecast forecast3 = cachedForecaster.forecastFor(Region.WALES, Day.THURSDAY);
    assertEquals(3, cache.size());
    assertEquals(
        forecast3, cache.get(new PlaceAndDayToForeCast(Region.WALES, Day.THURSDAY)).getForecast());

    Forecast forecast4 = cachedForecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
    assertEquals(4, cache.size());
    assertEquals(
        forecast4,
        cache.get(new PlaceAndDayToForeCast(Region.BIRMINGHAM, Day.FRIDAY)).getForecast());

    Forecast forecast5 = cachedForecaster.forecastFor(Region.NORTH_ENGLAND, Day.SUNDAY);
    assertEquals(
        forecast5,
        cache.get(new PlaceAndDayToForeCast(Region.NORTH_ENGLAND, Day.SUNDAY)).getForecast());
    assertEquals(5, cache.size());
  }

  @Test
  public void canEvictWhenFull() {
    assertTrue(cache.isEmpty());
    Forecast forecast1 = cachedForecaster.forecastFor(Region.LONDON, Day.TUESDAY);
    assertEquals(
            forecast1, cache.get(new PlaceAndDayToForeCast(Region.LONDON, Day.TUESDAY)).getForecast());
    assertEquals(1, cache.size());

    Forecast forecast2 = cachedForecaster.forecastFor(Region.EDINBURGH, Day.MONDAY);
    assertEquals(2, cache.size());
    assertEquals(
            forecast2,
            cache.get(new PlaceAndDayToForeCast(Region.EDINBURGH, Day.MONDAY)).getForecast());

    Forecast forecast3 = cachedForecaster.forecastFor(Region.WALES, Day.THURSDAY);
    assertEquals(3, cache.size());
    assertEquals(
            forecast3, cache.get(new PlaceAndDayToForeCast(Region.WALES, Day.THURSDAY)).getForecast());

    Forecast forecast4 = cachedForecaster.forecastFor(Region.BIRMINGHAM, Day.FRIDAY);
    assertEquals(4, cache.size());
    assertEquals(
            forecast4,
            cache.get(new PlaceAndDayToForeCast(Region.BIRMINGHAM, Day.FRIDAY)).getForecast());

    Forecast forecast5 = cachedForecaster.forecastFor(Region.NORTH_ENGLAND, Day.SUNDAY);
    assertEquals(
            forecast5,
            cache.get(new PlaceAndDayToForeCast(Region.NORTH_ENGLAND, Day.SUNDAY)).getForecast());
    assertEquals(5, cache.size());


    Forecast forecast6 = cachedForecaster.forecastFor(Region.SOUTH_EAST_ENGLAND, Day.SATURDAY);
    assertEquals(
            forecast6,
            cache
                    .get(new PlaceAndDayToForeCast(Region.SOUTH_EAST_ENGLAND, Day.SATURDAY))
                    .getForecast());
    assertEquals(5, cache.size());
    Forecast forecast7 = cachedForecaster.forecastFor(Region.SOUTH_WEST_ENGLAND, Day.WEDNESDAY);
    assertEquals(
            forecast7,
            cache
                    .get(new PlaceAndDayToForeCast(Region.SOUTH_WEST_ENGLAND, Day.WEDNESDAY))
                    .getForecast());
    assertEquals(5, cache.size());
  }
}
