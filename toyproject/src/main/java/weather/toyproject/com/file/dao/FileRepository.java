package weather.toyproject.com.file.dao;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import weather.toyproject.com.file.FileMapper;

@RequiredArgsConstructor
@Repository
public class FileRepository {
	
	private final FileMapper fileMapper;
	
	public int deleteFile(Long fileId) {
		return fileMapper.deleteFile(fileId);
	}
}
