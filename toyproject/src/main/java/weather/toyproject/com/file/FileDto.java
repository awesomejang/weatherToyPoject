package weather.toyproject.com.file;

import org.springframework.stereotype.Component;

import lombok.Setter;

import lombok.Getter;


@Getter @Setter
@Component
public class FileDto {
	
	private Long attachId; // FILE_DETAIL(PK)
	private String fileNm; // 파일명(원본)
	private String virFileNm; // 파일명(서버저장)
	private Long fileSize; // 파일크기
	private String fileExt; // 파일 확장자
	private String filePath; // 파일 저장경로
}
