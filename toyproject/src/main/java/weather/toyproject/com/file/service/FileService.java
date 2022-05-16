package weather.toyproject.com.file.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import weather.toyproject.com.file.dao.FileRepository;

@RequiredArgsConstructor
@Service
public class FileService {

	private final FileRepository fileRepository;
	
	public int deleteFile(Long fileId, String userId) throws Exception {
		return fileRepository.deleteFile(fileId, userId);
	}
}
