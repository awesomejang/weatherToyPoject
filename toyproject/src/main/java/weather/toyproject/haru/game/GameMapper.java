package weather.toyproject.haru.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import weather.toyproject.haru.game.domain.GameListVO;

@Mapper
public interface GameMapper {
	
	List<GameListVO> gameList();
	
}
