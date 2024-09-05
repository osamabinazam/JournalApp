package pk.smartq.journalApp.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherApiResponse {

    private Location location;
    private Current current;

    @Data
    public static class Location {
        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;

        @JsonProperty("tz_id")
        private String tzId;

        @JsonProperty("localtime_epoch")
        private long localtimeEpoch;
        private String localtime;
    }

    @Data
    public static class Current {
        private String lastUpdated;
        @JsonProperty("temp_c")
        private double tempC;

        @JsonProperty("temp_f")
        private double tempF;

        @JsonProperty("is_day")
        private int isDay;
        private Condition condition;

        @JsonProperty("wind_kph")
        private double windKph;
        @JsonProperty("wind_degree")
        private int windDegree;
        @JsonProperty("wind_dir")
        private String windDir;
        private int humidity;
        private int cloud;
        @JsonProperty("feekslike_c")
        private double feelsLikeC;
        @JsonProperty("heatindex_c")
        private double heatIndexC;
        private double uv;

        @Data
        public static class Condition {
            private String text;
            private String icon;
            private int code;
        }
    }


}

