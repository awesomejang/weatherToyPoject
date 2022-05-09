package weather.toyproject.haru.game.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import weather.toyproject.com.file.FileDto;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.GameMapper;
import weather.toyproject.haru.game.domain.GameListVO;
import weather.toyproject.haru.user.domain.GameListDto;

@Repository
public class GameRepository {

	private final GameMapper gameMapper;
	
	public GameRepository(GameMapper gameMapper) {
		this.gameMapper = gameMapper;
	}
	
	/**
	 * 게임목록리턴 
	 * @return List
	 * @exception Exception
	 */
	public List<GameListVO> getGameList() throws Exception {
		return gameMapper.getGameList();
	}
	
	/**
	 * 게임상세정보리턴
	 * @param gameId
	 * @return GameListDto
	 * @throws Exception
	 */
	public GameListVO getGame(String gameId) throws Exception {
		return gameMapper.getGame(gameId);
	}
	
	/**
	 * 게임이미지상세정보리턴
	 * @param fileId
	 * @return FileDto
	 * @throws Exception
	 */
	public FileDto getGameImageInfo(Long fileId) throws Exception {
		return gameMapper.getGameImageInfo(fileId);
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
	public Long insertGameImageInfo(FileVO fileVO) throws Exception {
		return gameMapper.insertGameImageInfo(fileVO);
	}
	
	/**
	 * FILE_DETAIL테이블에 파일 정보를 입력한다.
	 * @param fileVO
	 * @return Long 
	 */
	public Long insertGameImageDetailInfo(FileVO fileVO) throws Exception {
		return gameMapper.insertGameImageDetailInfo(fileVO);
	}
	
	/**
	 * 게임 첨부상태 여부를 N으로 변경한다.
	 * @param gameCode
	 * @return int 
	 * @throws Exception
	 */
	public int updateFileAttToN(Long gameCode) throws Exception {
		return gameMapper.updateFileAttToN(gameCode);
	}
	
	public int updateGameInfo(GameListVO gameListVO) throws Exception {
		return gameMapper.updateGameInfo(gameListVO);
	}
}
