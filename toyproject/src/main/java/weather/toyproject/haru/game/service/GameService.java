package weather.toyproject.haru.game.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.file.FileUtil;
import weather.toyproject.com.file.FileVO;
import weather.toyproject.haru.game.dao.GameRepository;
import weather.toyproject.haru.game.domain.GameListVO;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final FileUtil fileUtil;
	
	public List<GameListVO> gameList() {  
		return gameRepository.getGameList();
	}
	
	/**
	 * 게임등록을 처리한다.
	 * @param files
	 * 
	 */
	public void gameUpload(MultipartFile files, GameListVO gameListVO) {
		try {
			// 1. 단건 파일업로드
			FileVO fileInfo = fileUtil.fileStore(files);
			// 2. 파일정보 DB입력
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
