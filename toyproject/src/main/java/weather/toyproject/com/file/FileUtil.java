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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import groovy.transform.AutoImplement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtil {
	
	@Value("${local.file.uploadPath}")
	private String fileUploadPath;
	
	private FileVO fileVO;
	
	
	public FileUtil() {
		
	}
	
	
	@Autowired
	public FileUtil(FileVO fileVO) {
		this.fileVO = fileVO;
	}
	
	/**
	 * 다중 파일 업로드 
	 */
	public void store(List<MultipartFile> files, String attachTY) throws IOException {
		List<FileVO> uploadList = new ArrayList<FileVO>();
		Path targetFolder = Paths.get(fileUploadPath);
		log.info("fileUploadPath = {}", fileUploadPath);
		
		if(files.size() > 0) {
			if(!Files.exists(targetFolder.getFileName())) {
				Files.createDirectories(targetFolder);
			}
			
			for(MultipartFile multipartFile : files) {
				String UUID = fileVO.getUUID();
				//Path path = Paths.get(fileUploadPath + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename()));
				Path path = Paths.get(fileUploadPath + File.separator + StringUtils.cleanPath(UUID));
				
				log.info("upload File Name = {}", multipartFile.getOriginalFilename()); 
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				fileVO.set
				
				fileVO.set
				

			}
		}
		
		//for(MultipartFile file : )
		
	}
	
	private Path getPath() {
		return Paths.get(fileUploadPath);
	}
}
