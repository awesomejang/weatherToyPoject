package weather.toyproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

@SpringBootTest
public class MessageTest {
	
	@Autowired
	MessageSource ms;
	
	@Test
	void GameMsgTest() {
		String message = ms.getMessage("succ.regist.game", null, null);
		Assertions.assertThat(message).isEqualTo("게임업로드에 성공했습니다.");
	}
}
