package weather.toyproject.haru.game.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	/**
	 * 게임등록을 처리한다.
	 * @param files
	 * 
	 */
	public void gameUpload(List<MultipartFile> files) {
		
	}
}
