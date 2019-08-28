package dept;

public class Paging {
	int pageUnit=10 ; 		//한페이지 출력할 레코드 건수
	int pageSize=10 ; 		//출력할 페이지 수 (1~10, 11~20, ...)
	int totalPageCount;     //페이지 갯수
	int totalRecord ;		//전체 레코드건수
	int page = 1;			//현재 페이지
	int startPage;          //페이지 리스트의 첫 페이지 번호
	int endPage;            //페이지 리스트의 마지막 페이지 번호 
	int first; // 페이지의 첫번째 레코드
	int last;  // 페이지의 마지막 레코드
	
	// toString
	@Override
	public String toString() {
		return "Paging [pageUnit=" + pageUnit + ", pageSize=" + pageSize + ", totalPageCount=" + totalPageCount
				+ ", totalRecord=" + totalRecord + ", page=" + page + ", startPage=" + startPage + ", endPage="
				+ endPage + ", first=" + first + ", last=" + last + "]";
	}	
	
	
	// 한 페이지에 5건씩 출력한다면 -> 레코드 1~5, 6~10, ...
	// 현제 페이지의 첫번째와 마지막 레코드를 설정 및 반환한다.
	public int getFirst() {
		first = (getPage() - 1) * getPageUnit() + 1;
		return first;
	}
	public int getLast() {
		last = getPage() * getPageUnit();
		return last;
	}

	
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}

	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		// ↑ 전체 레코드 수 설정
		totalPageCount = totalRecord / pageUnit + 
				   ( (totalRecord % pageUnit)>0 ? 1 : 0 );
		// ↑ 전체 페이지 수
		startPage = ((page-1)/pageSize) * pageSize + 1;
		// ↑ 페이지 리스트의 첫 페이지 번호
		endPage = ((page-1)/pageSize)  * pageSize  + pageSize ;
		if ( endPage > getTotalPageCount() )
			endPage = getTotalPageCount() ;
		// ↑ 페이지 리스트의 마지막 페이지 번호
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
}
