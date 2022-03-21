package weather.toyproject.haru.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.domain.GameListVO;

//@Mapper
public interface GameMapper {
	
	List<GameListVO> getGameList();
	
	/**
	 * 게임정보를 DB에 입력한다. 
	 */
	
	
	/**
	 * 게임이미지 파일정보를 DB에 입력한다. 
	 * @return int 
	 */
	int insertGameImageInfo(FileVO fileVO); 
		
}
