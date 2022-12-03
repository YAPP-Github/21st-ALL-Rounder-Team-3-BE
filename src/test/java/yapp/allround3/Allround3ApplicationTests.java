package yapp.allround3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Allround3ApplicationTests {

	@Test
	void temporaryTest(){
		assertThat(4-1).isEqualTo(3);
	}

}
