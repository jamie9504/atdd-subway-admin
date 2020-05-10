package wooteco.subway.admin.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

public class Line {

    @Id
    private Long id;
    private String name;
    private String color;
    private LocalTime startTime;
    private LocalTime endTime;
    private int intervalTime;
    @MappedCollection(idColumn = "line", keyColumn = "sequence")
    private List<LineStation> lineStations = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Line() {
    }

    public Line(Long id, String name, String color, LocalTime startTime, LocalTime endTime,
        int intervalTime) {
        this.name = name;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Line(String name, String color, LocalTime startTime, LocalTime endTime,
        int intervalTime) {
        this(null, name, color, startTime, endTime, intervalTime);
    }

    public void update(Line line) {
        if (line.getName() != null) {
            this.name = line.getName();
        }
        if (line.getStartTime() != null) {
            this.startTime = line.getStartTime();
        }
        if (line.getEndTime() != null) {
            this.endTime = line.getEndTime();
        }
        if (line.getIntervalTime() != 0) {
            this.intervalTime = line.getIntervalTime();
        }
        if (line.getColor() != null) {
            this.color = line.getColor();
        }
        if (line.getLineStations() != null) {
            this.lineStations = new ArrayList<>(line.getLineStations());
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void addLineStation(LineStation requestLineStation) {
        checkPreStation(requestLineStation);
        int index = lineStations.stream()
            .filter(lineStation -> lineStation.isPreStationBy(requestLineStation))
            .map(lineStation -> lineStations.indexOf(lineStation) + 1)
            .findAny()
            .orElse(0);

        lineStations.add(index, requestLineStation);
        adjustPreStationId(index + 1, requestLineStation.getStationId());
    }

    private void checkPreStation(LineStation requestLineStation) {
        Long preStationId = requestLineStation.getPreStationId();
        if (preStationId != null && hasNotPreStation(preStationId)) {
            throw new IllegalArgumentException("이전역을 찾을 수 없습니다.");
        }
    }

    private boolean hasNotPreStation(Long preStationId) {
        return lineStations.stream()
            .noneMatch(lineStation -> lineStation.getStationId().equals(preStationId));
    }

    private void adjustPreStationId(int nextLineStationIndex, Long stationId) {
        if (nextLineStationIndex < lineStations.size()) {
            LineStation nextStation = lineStations.get(nextLineStationIndex);
            nextStation.updatePreStationId(stationId);
        }
    }

    public void removeLineStationById(Long stationId) {
        int index = lineStations.stream()
            .filter(lineStation -> lineStation.isSameStationId(stationId))
            .map(lineStation -> lineStations.indexOf(lineStation))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("해당 지하철이 없습니다."));

        LineStation removeLineStation = lineStations.remove(index);
        adjustPreStationId(index, removeLineStation.getPreStationId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public String getColor() {
        return color;
    }

    public List<LineStation> getLineStations() {
        return lineStations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Long> getLineStationsId() {
        return lineStations.stream()
            .map(LineStation::getStationId)
            .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return "Line{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", color='" + color + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", intervalTime=" + intervalTime +
            ", lineStations=" + lineStations +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
