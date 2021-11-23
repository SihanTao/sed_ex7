package ic.doc;

import com.weather.Forecast;

import java.util.Date;

public class ForecastWithCreationTime {
  private final Forecast forecast;
  private final Date creationTime;
  private final int HOUR = 60 * 60 * 1000;

  public ForecastWithCreationTime(Forecast forecast) {
    this.forecast = forecast;
    this.creationTime = new Date(System.currentTimeMillis());
  }

  public Forecast getForecast() {
    return forecast;
  }

  public boolean isExpired() {
    Date currentTime = new Date(System.currentTimeMillis());
    return currentTime.getTime() - creationTime.getTime() > HOUR;
  }
}
