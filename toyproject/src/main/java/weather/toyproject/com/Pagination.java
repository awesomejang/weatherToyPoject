package weather.toyproject.com;

public class Pagination {

	private int currPageNo;/// 현재 페이지 번호
	private int sizePerPage = 10; // 한 페이지당 보여질 리스트 개수
	private int totalCnt; // 전체 목록 개수
	private int pageCnt; // 전체 페이지 개수
	
	private int startList; // 게시판 시작 번호
	private int range = 1; // 페이지 범위.    1 : 1~5     2: 6~10   ...
	private int pageSize = 5; // 한 페이지 범위에 보여질 페이지 개수
	private int startPage; // 각 페이지 범위의 시작 번호
	
	// 검색
	private String searchType; // 검색타입 (글제목, 글쓴이 등등)
	private String keyword; // 키워드
	

	private String fromDate; // 날짜 기한
	private String toDate; // 날짜 기한
	
	private int endPage; // 각 페이지 범위 끝 번호
	
	private boolean prev; // 이전 페이지 여부
	private boolean next; // 다음 페이지 여부
    
    // 핵심
    public void pageInfo(int currPageNo, int range, int totalCnt) {
		this.currPageNo = currPageNo;
		this.range = range;
		this.totalCnt = totalCnt;
		
		// 전체 페이지 수
		this.pageCnt = (int) Math.ceil((double)totalCnt/sizePerPage);
		// 시작 페이지
		this.startPage = (range - 1) * pageSize + 1;
		// 끝 페이지
		this.endPage = range * pageSize;
		// 게시판 시작 번호
		this.startList = (currPageNo - 1) * sizePerPage + 1;
		// 이전 버튼 상태
		this.prev = range == 1 ? false : true;
		// 다음 버튼 상태
		this.next = endPage >= pageCnt ? false : true;
		if(this.endPage > this.pageCnt) {
			this.endPage = this.pageCnt;
			this.next = false;
		}
		
	}
}
