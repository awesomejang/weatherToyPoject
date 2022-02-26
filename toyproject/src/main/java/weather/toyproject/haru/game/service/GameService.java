package weather.toyproject.haru.game.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weather.toyproject.haru.game.dao.GameRepository;
import weather.toyproject.haru.game.domain.GameListVO;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

	private final GameRepository gameRepository;
	
	public List<GameListVO> gameList() {  
		return gameRepository.getGameList();
	}
}
