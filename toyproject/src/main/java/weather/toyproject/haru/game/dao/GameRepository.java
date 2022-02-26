package weather.toyproject.haru.game.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weather.toyproject.haru.game.GameMapper;
import weather.toyproject.haru.game.domain.GameListVO;

@Repository
public class GameRepository {
	
	@Autowired
	GameMapper gameMapper;
	
	public List<GameListVO> getGameList() {
		return gameMapper.getGameList();
	}
	
}
