package wooteco.subway.admin.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import wooteco.subway.admin.domain.Line;

@DataJdbcTest
class LineRepositoryTest {

	@Autowired
	private LineRepository lineRepository;

	@DisplayName("Line 생성 테스트")
	@Test
	void name() {
		Line 신분당선 = lineRepository.save(new Line("신분당선", LocalTime.of(8, 00), LocalTime.of(8, 00), 10);

		assertThat(신분당선.getId()).isNotNull();
	}

}