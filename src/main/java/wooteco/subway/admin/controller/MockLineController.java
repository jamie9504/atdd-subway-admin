package wooteco.subway.admin.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import wooteco.subway.admin.dto.LineRequest;

@RestController
public class MockLineController {
	@PostMapping("lines")
	public ResponseEntity createLine(@RequestBody LineRequest request){
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.build();
	}
}
