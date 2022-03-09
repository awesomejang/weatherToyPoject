package weather.toyproject.com.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtil {
	
	@Value("${service.file.uploadPath}")
	private String fileUploadPath;
	
	
	/**
	 * 다중 파일 업로드 
	 */
	public void store(List<MultipartFile> files) throws IOException {
		/**
        ///"경로/.." 및 내부 단순 점과 같은 시퀀스를 억제하여 경로를 정규화합니다.
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		InputStream inputStream = file.getInputStream();
		Files.copy(inputStream, getPath().resolve(fileName),
				StandardCopyOption.REPLACE_EXISTING);
		*/
		log.info("fileUploadPath = {}", fileUploadPath);
		if(files.size() > 0) {
			for(MultipartFile multipartFile : files) {
				Path path = Paths.get(fileUploadPath + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename()));
				log.info("upload File Name = {}", multipartFile.getOriginalFilename()); 
				log.info("fileName = {}", path);
				//File file = new File(path.);
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}
		}
		
		//for(MultipartFile file : )
		
	}
	
	private Path getPath() {
		return Paths.get(fileUploadPath);
	}
}
