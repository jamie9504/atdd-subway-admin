package wooteco.subway.admin.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.dto.LineRequest;
import wooteco.subway.admin.dto.LineResponse;

@RestController
public class MockLineController {

    private List<Line> lines = new ArrayList<>();

    @PostMapping("/lines")
    public ResponseEntity createLine(@RequestBody LineRequest request) {
        Line line = new Line(request.getName(), request.getStartTime(), request.getEndTime(),
            request.getIntervalTime());
        lines.add(line);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
    }

    @GetMapping("/lines")
    public List<LineResponse> getLines() {
        List<LineResponse> lineResponses = new ArrayList<>();
        for (Line line : lines) {
            lineResponses.add(LineResponse.of(line));
        }
        return lineResponses;
    }
}
