package nextstep.subway.path.dto;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.subway.station.domain.Station;

public class PathResponse {
    private final List<StationResponse> stations;
    private final int distance;
    private final int fare;

    public PathResponse(List<StationResponse> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public static PathResponse of(List<Station> routes, int distance, int fare) {
        List<StationResponse> stations = routes.stream()
                .map(StationResponse::from)
                .collect(Collectors.toList());
        return new PathResponse(stations, distance, fare);
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
