package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class MovieDao {

	public List<MovieDto> selectAll(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 1. 안쪽 쿼리에서 평점(score)을 미리 계산합니다. (NVL로 null 방지, ROUND로 반올림)
	    // 2. 바깥쪽 쿼리에서 ROWNUM <= 8 로 최신 8개만 자릅니다.
	    String sql = "SELECT * FROM ("
	               + "    SELECT m.*, "
	               + "           (SELECT NVL(ROUND(AVG(rating), 1), 0) FROM movie_review WHERE mno = m.mno) as score "
	               + "    FROM movie m "
	               + "    ORDER BY mno DESC "
	               + ") WHERE ROWNUM <= 8";
		
		List<MovieDto> list = new ArrayList<MovieDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MovieDto dto = new MovieDto(
					rs.getInt("mno"),
					rs.getString("title"),
					rs.getString("catchphrase"),
					rs.getString("poster"),
					rs.getString("content"),
					rs.getInt("runtime"),
					rs.getString("opendate"),
					rs.getString("regdate"));
				dto.setScore(rs.getDouble("score"));
				
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public MovieDto selectOne(int mno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT m.*, "
	               + " (SELECT NVL(ROUND(AVG(rating), 1), 0) FROM movie_review WHERE mno = m.mno) as score "
	               + " FROM movie m WHERE mno = ?";
		
		MovieDto dto = null;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MovieDto(
					rs.getInt("mno"),
					rs.getString("title"),
					rs.getString("catchphrase"),
					rs.getString("poster"),
					rs.getString("content"),
					rs.getInt("runtime"),
					rs.getString("opendate"),
					rs.getString("regdate")
				);
				dto.setScore(rs.getDouble("score"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}
	
	// 검색 기능이 포함된 리스트 조회
	public List<MovieDto> getMovieList(String keyword) {
	    List<MovieDto> list = new ArrayList<>();
	    String sql = "SELECT * FROM movie WHERE UPPER(title) LIKE UPPER(?) ORDER BY title ASC";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        // 키워드가 없으면 "%%"가 되어 전체 검색, 있으면 "%검색어%"가 됨
	        pstmt.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                MovieDto dto = new MovieDto();
	                dto.setMno(rs.getInt("mno"));
	                dto.setTitle(rs.getString("title"));
	                dto.setPoster(rs.getString("poster"));
	                dto.setOpendate(rs.getString("opendate"));
	                dto.setCatchphrase(rs.getString("catchphrase"));
	                
	                list.add(dto);
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
	
	// == 관리자 모드 //

	// 영화 등록 (관리자용)
	public int insertMovie(MovieDto dto) {
	    int result = 0;
	    String sql = "INSERT INTO movie (mno, title, content, poster, runtime, opendate, catchphrase, regdate) "
	               + "VALUES (movie_seq.nextval, ?, ?, ?, ?, ?, ?, SYSDATE)";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, dto.getTitle());
	        pstmt.setString(2, dto.getContent());
	        pstmt.setString(3, dto.getPoster());
	        pstmt.setInt(4, dto.getRuntime());
	        pstmt.setString(5, dto.getOpendate()); // String(YYYY-MM-DD)으로 받음
	        pstmt.setString(6, dto.getCatchphrase());
	        result = pstmt.executeUpdate();
	    } catch (Exception e) { e.printStackTrace(); }
	    return result;
	}

	// 영화 목록 심플 조회 (스케줄 등록 드롭다운용 - 번호와 제목만 필요)
	public List<MovieDto> getMovieListSimple() {
	    List<MovieDto> list = new ArrayList<>();
	    String sql = "SELECT mno, title FROM movie ORDER BY mno DESC";
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        while(rs.next()) {
	            MovieDto dto = new MovieDto();
	            dto.setMno(rs.getInt("mno"));
	            dto.setTitle(rs.getString("title"));
	            list.add(dto);
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
	
}
