package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class forecastClient {
  private final Forecaster forecaster = new CachedForecaster(new Forecaster());

  private void run() {
    long startTime = System.currentTimeMillis();
    Forecast londonForecast = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
    System.out.println("London outlook: " + londonForecast.summary());
    System.out.println("London temperature: " + londonForecast.temperature());
    long firstEndTime = System.currentTimeMillis();

    long secondStartTime = System.currentTimeMillis();
    Forecast londonForecastAgain = forecaster.forecastFor(Region.LONDON, Day.MONDAY);
    System.out.println("London outlook: " + londonForecastAgain.summary());
    System.out.println("London temperature: " + londonForecastAgain.temperature());
    long secondEndTime = System.currentTimeMillis();

    System.out.printf("First Forecast: %dms\n", firstEndTime - startTime);
    System.out.printf("Second Forecast: %dms\n", secondEndTime - secondStartTime);
  }

  public static void main(String[] args) {
    new forecastClient().run();
  }
}
