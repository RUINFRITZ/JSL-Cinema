package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class ReviewDao {

	// 1. [좋아요] 현재 유저가 좋아요를 눌렀는지 확인 (페이지 로드용)
	public boolean isLiked(int mno, String userid) {
	    boolean result = false;
	    
	    String sql = "SELECT count(*) FROM movie_like WHERE mno= ? AND userid= ?";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, mno);
	        pstmt.setString(2, userid);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next() && rs.getInt(1) > 0) {
	                result = true;
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return result;
	}

	// 2. [좋아요] 토글 기능 (없으면 넣고, 있으면 삭제) -> 리턴: true(추가됨), false(삭제됨)
	public boolean toggleLike(int mno, String userid) {
	    boolean isAdded = false;
	    
	    // 먼저 좋아요 상태 확인
	    if (isLiked(mno, userid)) {
	        // 이미 있음 -> 삭제 (좋아요 취소)
	        String sql = "DELETE FROM movie_like WHERE mno= ? AND userid= ?";
	        try (Connection conn = DBManager.getInstance();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, mno);
	            pstmt.setString(2, userid);
	            pstmt.executeUpdate();
	            isAdded = false; // 삭제됨
	        } catch (Exception e) { e.printStackTrace(); }
	    } else {
	        // 없음 -> 추가 (좋아요)
	        String sql = "INSERT INTO movie_like(likeno, mno, userid) VALUES(seq_movie_like.nextval, ?, ?)";
	        try (Connection conn = DBManager.getInstance();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, mno);
	            pstmt.setString(2, userid);
	            pstmt.executeUpdate();
	            isAdded = true; // 추가됨
	        } catch (Exception e) { e.printStackTrace(); }
	    }
	    return isAdded;
	}

	// 3. [리뷰] 리뷰 등록
	public int insertReview(ReviewDto dto) {
	    int result = 0;
	    String sql = "INSERT INTO movie_review(rno, mno, userid, content, rating, regdate) "
	               + "VALUES(seq_review.nextval, ?, ?, ?, ?, SYSDATE)";
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, dto.getMno());
	        pstmt.setString(2, dto.getUserid());
	        pstmt.setString(3, dto.getContent());
	        pstmt.setInt(4, dto.getRating());
	        result = pstmt.executeUpdate();
	    } catch (Exception e) { e.printStackTrace(); }
	    return result;
	}

	// 4. [리뷰] 리뷰 목록 가져오기
	public List<ReviewDto> getReviewList(int mno) {
	    List<ReviewDto> list = new ArrayList<ReviewDto>();
	    String sql = "SELECT * FROM movie_review WHERE mno= ? ORDER BY rno DESC";
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, mno);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                ReviewDto dto = new ReviewDto();
	                dto.setRno(rs.getInt("rno"));
	                dto.setMno(rs.getInt("mno"));
	                dto.setUserid(rs.getString("userid") == null ? "Unknown" : rs.getString("userid"));
	                dto.setContent(rs.getString("content"));
	                dto.setRating(rs.getInt("rating"));
	                dto.setRegdate(rs.getDate("regdate"));
	                list.add(dto);
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
	
}
