package weather.toyproject.com.file;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
	
	int deleteFile(Long fileId);
}
