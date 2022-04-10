package weather.toyproject.haru.game.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.dao.GameRepository;
import weather.toyproject.haru.game.domain.GameListVO;
import weather.toyproject.haru.user.domain.GameListDto;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final FileUtil fileUtil;
	
	/**
	 * 게임목록을 리턴한다.
	 * @return List
	 * @throws Exception
	 */
	public List<GameListVO> gameList() throws Exception {  
		return gameRepository.getGameList();
	}
	
	/**
	public GameListDto getGame(String gameId) {
		try {
			return gameRepository.getGame(gameId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * 게임등록을 처리한다.
	 * @param files
	 * @return boolean
	 */
	public boolean gameUpload(MultipartFile files, GameListVO gameListVO) {
		UserVO userVO = (UserVO) AuthUtil.getLoginSession();
		try {
			// 1. 단건 파일업로드
			FileVO fileInfo = fileUtil.fileStore(files);
			if(fileInfo.getFileId() != null) {
				// 2. 파일정보 DB입력
				gameRepository.insertGameImageInfo(fileInfo);
				// 3. 파일디테일정보 DB입력
				gameRepository.insertGameImageDetailInfo(fileInfo);
			}
			// 4. 게임정보 DB입력 
			gameListVO.setUserId(userVO.getUserId());
			gameListVO.setGameImageInfo(fileInfo);
			gameRepository.insertGameInfo(gameListVO);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
