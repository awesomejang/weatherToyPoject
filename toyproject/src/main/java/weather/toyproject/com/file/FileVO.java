package weather.toyproject.com.file;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileVO {
	//==FILE_MASTER==//
	private String fileId; // 파일마스터 PK
	private String attachTY; // 첨부파일유형(구분자)
	private String register; // 등록자
	private String updater; // 수정자
	private Timestamp regDate; // 등록일자
	private Timestamp modDate; // 수정일자
	//==============//
	//==FILE_DETAIL==//
	private String attachId; // FILE_DETAIL(PK)
	//private String fileId; // FK
	private String fileNm; // 파일명(원본)
	private String virFileNm; // 파일명(서버저장)
	private String fileSize; // 파일크기
	private String fileExt; // 파일 확장자
	private String filePath; // 파일 저장경로
	
}