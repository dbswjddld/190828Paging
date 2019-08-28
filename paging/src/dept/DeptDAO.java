package dept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * DAO : Data Access Object (DB처리)
 */
public class DeptDAO {
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	//싱글톤 구현
	public static DeptDAO instance = new DeptDAO();
	public static DeptDAO getInstance() {
		return instance;
	}
	
	//등록
	public void deptInsert(DeptDto vo) {		
		try {
			conn = ConnectionManager.connect();
			//트랙잭션 모드 변경(autocommit 해제)
			conn.setAutoCommit(false);
			String sql = " insert into departments(department_id, "
				 	+    " department_name, manager_id, location_id)"
					+    " values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getDepartmentId());
			pstmt.setString(2, vo.getDepartmentName());
			pstmt.setString(3, vo.getManagerId());
			pstmt.setString(4, vo.getLocationId());
			
			pstmt.executeUpdate();

			conn.commit();  //정상실행이면 커밋
			
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();  //에러나면 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//수정
	public void deptUpdate(DeptDto vo) {		
		try {
			conn = ConnectionManager.connect();
			String sql = " UPDATE departments set      "
				 	+    " department_name = ?, "
				 	+    "        manager_id = ?,      "
				 	+    "        location_id = ?      "
				 	+    "  WHERE department_id = ?    ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getDepartmentName());
			pstmt.setString(2, vo.getManagerId());
			pstmt.setString(3, vo.getLocationId());
			pstmt.setString(4, vo.getDepartmentId());
			pstmt.executeUpdate();			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//삭제
	public void deptDelete(String department_id) {		
		try {
			conn = ConnectionManager.connect();
			String sql = " DELETE FROM departments  "
				 	+    "  WHERE department_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_id);
			pstmt.executeUpdate();			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//단건조회
	public DeptDto getDept(String department_id) {
		DeptDto vo = null;
		try {
			conn = ConnectionManager.connect();
			String sql ="SELECT department_id, department_name, manager_id, location_id "
					+   "  FROM departments"
					+   " WHERE department_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new DeptDto();
				vo.setDepartmentId(rs.getString("department_id"));
				vo.setDepartmentName(rs.getString("department_name"));
				vo.setManagerId(rs.getString("manager_id"));
				vo.setLocationId(rs.getString("location_id"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			try {  
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}  
		return vo;
	}
	//검색조회
	public List<DeptDto> getDeptList(DeptDto searchVO) {
		List<DeptDto> list = new ArrayList<DeptDto>();
		DeptDto vo = null;
		
		// 검색조건
		String whereCondition = " where 1 = 1 ";
		if(searchVO.getLocationId() != null && !searchVO.getLocationId().equals("")) {
			whereCondition += " and location_id = ? ";
		}
		if(searchVO.getManagerId() != null && !searchVO.getManagerId().equals("")) {
			whereCondition += " and manager_id = ? ";
		}
		
		try {			
			conn = ConnectionManager.connect();			
			String sql = "select b.*  from( select a.*, rownum rn  from ( "
			        +   "SELECT department_id, department_name, manager_id, location_id "
					+   "  FROM departments"
			        +   whereCondition					
					+   " ORDER BY department_id "
					+   " ) a   ) b  where rn between ? and ?	";
			pstmt = conn.prepareStatement(sql);
			int pos = 1;
			
			// 검색조건
			if(searchVO.getLocationId() != null && !searchVO.getLocationId().equals("")) {
				pstmt.setString(pos++, searchVO.getLocationId());
			}
			if(searchVO.getManagerId() != null && !searchVO.getManagerId().equals("")) {
				pstmt.setString(pos++, searchVO.getManagerId());
			}
			
			// BETWEEN ? AND ?
			pstmt.setInt(pos++, searchVO.getStart());
			pstmt.setInt(pos++, searchVO.getEnd());
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new DeptDto();
				vo.setDepartmentId(rs.getString("department_id"));
				vo.setDepartmentName(rs.getString("department_name"));
				vo.setManagerId(rs.getString("manager_id"));
				vo.setLocationId(rs.getString("location_id"));
				list.add(vo);
			}
		} catch(Exception e) {	e.printStackTrace();
		} finally{
			ConnectionManager.close(conn);
		}
		return list;
	}	
	
	//레코드 건수 조회
	public int count(DeptDto searchVO) {
		int cnt = 0;
		try {
			conn = ConnectionManager.connect();
			
			//동적 조건 추가(검색)
			String whereCondition = " where 1 = 1 ";
			if(searchVO.getLocationId() != null && !searchVO.getLocationId().equals("")) {
				whereCondition += " and location_id = ? ";
			}
			if(searchVO.getManagerId() != null && !searchVO.getManagerId().equals("")) {
				whereCondition += " and manager_id = ? ";
			}
			
			String sql = "select count(*) from departments" + whereCondition;
			pstmt = conn.prepareStatement(sql);

			// 조건값 셋팅(검색)
			int pos = 1;
			if(searchVO.getLocationId() != null && !searchVO.getLocationId().equals("")) {
				pstmt.setString(pos++, searchVO.getLocationId());
			}
			if(searchVO.getManagerId() != null && !searchVO.getManagerId().equals("")) {
				pstmt.setString(pos++, searchVO.getManagerId());
			}
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.close(conn);
		}
		return cnt;
	}
}
