package weather.toyproject.haru.game;

import java.util.List;

import weather.toyproject.com.file.FileDto;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.domain.GameListVO;
import weather.toyproject.haru.user.domain.GameListDto;

//@Mapper
public interface GameMapper {
	
	
	List<GameListVO> getGameList();
	
	/**
	 * 게임의정보를리턴한다.
	 * @param String
	 * @return GameListDto
	 */
	GameListVO getGame(String gameId);
	
	
	/**
	 * 게임의이미지파일정보를 리턴한다.
	 * @param fileId
	 * @return FileDto
	 */
	FileDto getGameImageInfo(Long fileId);
	
	/**
	 * 게임정보를 DB에 입력한다.
	 * @param gameListVO
	 * @return int
	 */
	int insertGameInfo(GameListVO gameListVO);
	
	
	int updateGameInfo(GameListVO gameListVO);
	
	/**
	 * 게임이미지 파일정보를 DB에 입력한다. 
	 * @return Long 
	 * @param FileVO
	 */
	Long insertGameImageInfo(FileVO fileVO); 
	
	/**
	 * FILE_DETAIE테이블에 파일정보를 입력한다. 
	 * @return Long 
	 * @param FileVO
	 */
	Long insertGameImageDetailInfo(FileVO fileVO);
	
	
	int updateFileAttToN(Long gameCode);
	
}	
