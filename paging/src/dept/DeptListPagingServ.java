package dept;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeptListPagingServ")
public class DeptListPagingServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  //페이징번호 파라미터	
	      String p = request.getParameter("p");
	      int pageNum = 1;
	      if(p != null && ! p.equals("")) {
	         pageNum = Integer.parseInt(p);
	      }   
	      
	      // 검색
	      DeptDto dept = new DeptDto();
	      dept.setLocationId(request.getParameter("locationId"));
	      dept.setManagerId(request.getParameter("managerId"));
	      // dept.set** 해서 검색조건 넣기
	      
	      //paging 정보 저장
	      Paging paging = new Paging();
	      paging.setPageUnit(1); // 한 페이지에 출력할 레코드
	      paging.setPage(pageNum); // set 페이지 번호
	      paging.setTotalRecord(DeptDAO.getInstance().count(dept));
	      // dao.count :레코드 건수 조회 (매개변수는 검색할때 쓰인다)
	      // setTotalRecord :전체 레코드 건수 + 전체 페이지 수 + 페이지 리스트(startPage, endPage) 설정
	      
	      
	      
	      //부서목록
	      dept.setStart(paging.getFirst()); // 페이지의 첫 레코드
	      dept.setEnd(paging.getLast()); // 페이지의 마지막 레코드
	      // getFirst, getLast :현재 페이지의 첫번째와 마지막 레코드 번호를 설정 및 반환
	      // dto의 start, end? :리스트를 검색할 때 몇번째 부터 몇번째까지 검색할 것인지에 쓰인다
	      System.out.println(paging.toString());
	      request.setAttribute("list", DeptDAO.getInstance().getDeptList(dept));
	      	// dept에 설정된 start, end로 start~end번째 레코드를 검색
	      request.setAttribute("paging", paging);
	      request.getRequestDispatcher("deptList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
