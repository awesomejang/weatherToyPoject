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
import weather.toyproject.com.AuthUtil;
import weather.toyproject.com.file.service.FileService;
import weather.toyproject.haru.user.domain.UserVO;

@Slf4j
public class FileUtil {
	
	@Value("${local.file.uploadPath}")
	private String fileUploadPath;
	private FileVO fileVO;
	private FileService fileService;
	
	@Autowired
	public FileUtil(FileVO fileVO, FileService fileService) {
		this.fileVO = fileVO;
		this.fileService = fileService;
	}
	
	
	/**
	 * 단건 파일 업로드 
	 */
	public FileVO fileStore(MultipartFile files) throws IOException {
		Path targetFolder = Paths.get(fileUploadPath);
		UserVO userVO = (UserVO) AuthUtil.getLoginSession();
		
		if(!Files.exists(targetFolder.getFileName())) {
			Files.createDirectories(targetFolder);
		}
		
		if(!files.isEmpty()) {
			String UUID = fileVO.getUUID();
			Path path = Paths.get(fileUploadPath + File.separator + StringUtils.cleanPath(UUID) + this.getFileExt(files.getOriginalFilename()));
			
			log.info("upload File Name = {}", files.getOriginalFilename()); 
			Files.copy(files.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			fileVO.setRegister(userVO.getUserId());
			fileVO.setFileNm(files.getOriginalFilename());
			fileVO.setVirFileNm(UUID);
			fileVO.setFileSize(files.getSize());
			fileVO.setFileExt(fileVO.getFileExt(files.getOriginalFilename()));
			fileVO.setFilePath(fileUploadPath);
		}
		return fileVO;
	}
	
	/**
	 * 다중 파일 업로드 
	 */
	public List<FileVO> fileListStore(List<MultipartFile> files) throws IOException {
		List<FileVO> uploadList = new ArrayList<FileVO>();
		Path targetFolder = Paths.get(fileUploadPath);
		UserVO userVO = (UserVO) AuthUtil.getLoginSession();
		
		if(!Files.exists(targetFolder.getFileName())) {
			Files.createDirectories(targetFolder);
		}
		
		if(files.size() > 0) {
			for(MultipartFile multipartFile : files) {
				//Path path = Paths.get(fileUploadPath + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename()));
				String UUID = fileVO.getUUID();
				Path path = Paths.get(fileUploadPath + File.separator + StringUtils.cleanPath(UUID));
				
				log.info("upload File Name = {}", multipartFile.getOriginalFilename()); 
				Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				fileVO.setRegister(userVO.getUserId());
				fileVO.setFileNm(multipartFile.getOriginalFilename());
				fileVO.setVirFileNm(UUID);
				fileVO.setFileSize(multipartFile.getSize());
				fileVO.setFileExt(fileVO.getFileExt(multipartFile.getOriginalFilename()));
				fileVO.setFilePath(fileUploadPath);
				uploadList.add(fileVO);
			}
		}
		return uploadList;
	}
	
	/**
	 * FILE_MASTER 테이블의 FILE_DEL_YN컬럼의 상태를 변경한다.
	 * @return int
	 */
	public int deleteFile(Long fileId) {
		return fileService.deleteFile(fileId);
	}
	
	
	private Path getPath() {
		return Paths.get(fileUploadPath);
	}
	
	public String getFileExt(String fileName) {
		int extIndex = fileName.lastIndexOf(".");
		if(extIndex > 0) {
			return fileName.substring(extIndex);
		}
		return null;
	}
}
