package ic.doc;

import com.weather.*;

import java.time.LocalTime;
import java.util.Objects;

public class PlaceAndDayToForeCast {
  private final Region region;
  private final Day day;

  private final LocalTime time;

  public PlaceAndDayToForeCast(Region region, Day day) {
    this.region = region;
    this.day = day;
    this.time = LocalTime.now();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlaceAndDayToForeCast that = (PlaceAndDayToForeCast) o;
    return Objects.equals(region, that.region) && day == that.day;
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, day);
  }
}
