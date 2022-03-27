package weather.toyproject.haru.game.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.GameMapper;
import weather.toyproject.haru.game.domain.GameListVO;

@Repository
public class GameRepository {

	private final GameMapper gameMapper;
	
	public GameRepository(GameMapper gameMapper) {
		this.gameMapper = gameMapper;
	}
	
	
	public List<GameListVO> getGameList() {
		return gameMapper.getGameList();
	}
	
	/**
	 * 게임정보를 DB에 저장한다.
	 * @param gameListVO
	 * @param filePK
	 * @return int
	 */
	public int insertGameInfo(GameListVO gameListVO) throws Exception {
		return gameMapper.insertGameInfo(gameListVO);
	}
	
	/**
	 * 게임 이미지 파일을 저장하고 File PK값을 리턴한다.
	 * @param fileVO
	 * @return int 
	 */
	public Long insertGameImage(FileVO fileVO) throws Exception {
		return gameMapper.insertGameImageInfo(fileVO);
	}
	
}
