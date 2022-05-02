package weather.toyproject.com.file;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import lombok.Setter;

import lombok.Getter;


@Getter @Setter
@Component
public class FileDto {
	private Long fileId; // 파일마스터 PK
	private String attachTY; // 첨부파일유형(구분자)
	private String register; // 등록자
	private String updater; // 수정자
	private Timestamp regDate; // 등록일자
	private Timestamp modDate; // 수정일자
	private String fileDelYn; // 사용여부
	//==============//
	
	//==FILE_DETAIL==//
	private Long attachId; // FILE_DETAIL(PK)
	private String fileNm; // 파일명(원본)
	private String virFileNm; // 파일명(서버저장)
	private Long fileSize; // 파일크기
	private String fileExt; // 파일 확장자
	private String filePath; // 파일 저장경로
	
	/**
	private Long attachId; // FILE_DETAIL(PK)
	private String fileNm; // 파일명(원본)
	private String virFileNm; // 파일명(서버저장)
	private Long fileSize; // 파일크기
	private String fileExt; // 파일 확장자
	private String filePath; // 파일 저장경로
	*/
}
