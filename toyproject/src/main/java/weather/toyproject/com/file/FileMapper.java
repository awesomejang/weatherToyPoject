package weather.toyproject.com.file;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {
	
	int deleteFile(@Param("fileId") Long fileId, @Param("userId") String userId);
}
