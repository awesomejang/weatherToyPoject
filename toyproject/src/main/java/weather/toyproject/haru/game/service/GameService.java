package weather.toyproject.haru.game.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.FileDto;
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
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<GameListVO> gameList() throws Exception {
		return gameRepository.getGameList();
	}
	
	/**
	 * 게임의 상세정보를 리턴한다.
	 * @param gameId
	 * @return GameListDto
	 * @throws Exception
	 */
	public GameListVO getGame(String gameId) throws Exception {
		return gameRepository.getGame(gameId);
	}
	
	public FileDto getGameImageInfo(Long fileId) throws Exception {
		return gameRepository.getGameImageInfo(fileId);
	}

	/**
	 * 게임등록을 처리한다.
	 * 
	 * @param files
	 * @return boolean
	 */
	public boolean gameUpload(MultipartFile files, GameListVO gameListVO) {
		UserVO userVO = (UserVO) AuthUtil.getLoginSession();
		try {
			// 1. 단건 파일업로드
			FileVO fileInfo = fileUtil.fileStore(files);
			if (fileInfo.getRegister() != null) {
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
	
	/**
	 * FILE의 삭제여부를 Y로 변경한다.
	 * @param fileId
	 * @return Map<String, String>
	 */
	public Map<String, String> deleteGameImage(Long fileId, Long gameCode) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			fileUtil.deleteFile(fileId);
			gameRepository.updateFileAttToN(gameCode);
			resultMap.put("msg", "이미지 삭제에 성공했습니다.");
			resultMap.put("code", "OK");
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("msg", "이미지 삭제에 실패했습니다.");
			resultMap.put("code", "FAIL");
		}
		return resultMap;
	}
	
	public boolean updateGame(GameListVO gameListVO, FileVO files) {
		UserVO userVO = (UserVO) AuthUtil.getLoginSession();
		try {
			FileVO fileInfo = fileUtil.fileStore(files.getMultipartFile());
			gameListVO.setUserId(userVO.getUserId());
			if(fileUtil.uploadCheck()) { // 파일이 첨부되었다.
				fileUtil.deleteFile(gameListVO.getFileId());
				// 2. 파일정보 DB입력
				gameRepository.insertGameImageInfo(fileInfo);
				// 3. 파일디테일정보 DB입력
				gameRepository.insertGameImageDetailInfo(fileInfo);
				// 4. 게임정보 DB입력
				//gameListVO.setUserId(userVO.getUserId());
				//gameListVO.setFileId(fileInfo.getFileId());
				gameListVO.setGameImageInfo(fileInfo);
			}else {
				gameListVO.setFileId(null);
			}
			gameRepository.updateGameInfo(gameListVO);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
