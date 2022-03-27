package weather.toyproject.haru.game.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.dao.GameRepository;
import weather.toyproject.haru.game.domain.GameListVO;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final FileUtil fileUtil;
	
	public List<GameListVO> gameList() throws Exception {  
		return gameRepository.getGameList();
	}
	
	/**
	 * 게임등록을 처리한다.
	 * @param files
	 * 
	 */
	public void gameUpload(MultipartFile files, GameListVO gameListVO) {
		UserVO userVO = (UserVO) AuthUtil.getLoginSession();
		try {
			// 1. 단건 파일업로드
			FileVO fileInfo = fileUtil.fileStore(files);
			// 2. 파일정보 DB입력
			gameRepository.insertGameImage(fileInfo);
			// 3. 게임정보 DB입력 
			gameListVO.setUserId(userVO.getUserId());
			gameListVO.setGameImageInfo(fileInfo);
			int result = gameRepository.insertGameInfo(gameListVO);
			
			log.info("insert file_master_PK = {}", fileInfo.getFileId());
			log.info("result = {}", result);
		
		} catch (IOException e) {
			e.printStackTrace();
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
}
