package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class ScheduleDao {

	public List<ScheduleDto> getScheduleByDate(int mno, String selectedDate) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT s.sno, s.mno, s.tno, "
		           + "TO_CHAR(s.sdate, 'YYYY-MM-DD HH24:MI:SS') as sdate, " // 시간을 포함한 풀 문자열로 별칭 지정
		           + "t.tname, t.total_seats, "
		           + "(SELECT COUNT(*) FROM reservation WHERE sno = s.sno) as booked_cnt "
		           + "FROM schedule s JOIN theater t ON s.tno = t.tno "
		           + "WHERE s.mno = ? AND TO_CHAR(s.sdate, 'YYYY-MM-DD') = ? "
		           + "ORDER BY s.sdate ASC";
		
		List<ScheduleDto> list = new ArrayList<ScheduleDto>();
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mno);
			pstmt.setString(2, selectedDate);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScheduleDto dto = new ScheduleDto(
					rs.getInt("sno"),
					rs.getInt("mno"),
					rs.getInt("tno"),
					rs.getString("sdate")
				);
				dto.setTname(rs.getString("tname"));
				dto.setTotal_seats(rs.getInt("total_seats"));
				dto.setBooked_cnt(rs.getInt("booked_cnt"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	// 특정 스케줄 상세 정보 가져오기
	public ScheduleDto getScheduleOne(int sno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	    String sql = "SELECT s.*, m.title, m.poster FROM schedule s "
	               + "JOIN movie m ON s.mno = m.mno WHERE s.sno = ?";

	    ScheduleDto dto = null;
	    try {
	    	conn = DBManager.getInstance();
	    	pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, sno);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	        	dto = new ScheduleDto(
	            rs.getInt("sno"), rs.getInt("mno"), 
	            rs.getInt("tno"), rs.getString("sdate")
	        	);
	        	dto.setTitle(rs.getString("title"));
		    	dto.setPoster(rs.getString("poster"));
	        }
	    } catch (Exception e) { e.printStackTrace(); } finally { DBManager.close(conn, pstmt, rs); }
	    return dto;
	}

	// 상영관 정보(가로/세로 크기) 가져오기 - 최신 try문 !
	public ScheduleDto getTheaterInfo(int tno) {
	    String sql = "SELECT * FROM theater WHERE tno = ?";
	    
	    ScheduleDto dto = null;
	    
	    try (Connection conn = DBManager.getInstance(); 
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, tno);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                dto = new ScheduleDto();
	                dto.setTno(rs.getInt("tno"));
	                dto.setTname(rs.getString("tname"));
	                dto.setTrow(rs.getInt("trow")); 
	                dto.setTcol(rs.getInt("tcol"));
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return dto;
	}

	// 해당 스케줄에 이미 예약된 좌석 번호 목록 가져오기
	public List<String> getReservedSeats(int sno) {
	    List<String> list = new ArrayList<>();
	    
	    String sql = "SELECT seat_info FROM reservation WHERE sno = ?";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, sno);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                list.add(rs.getString("seat_info")); // 예: "A-1", "C-5"
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
	
	// 예약 정보 1건을 저장
	public int insertReservation(int sno, String userId, String seat, int price) {
	    int result = 0;
	    String sql = "INSERT INTO reservation (rno, sno, userid, seat_info, price, rdate) "
	               + "VALUES (resv_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, sno);
	        pstmt.setString(2, userId);
	        pstmt.setString(3, seat);
	        pstmt.setInt(4, price);
	        
	        result = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	// 사용자의 현재 포인트 조회
	public int getUserPoint(String userid) {
	    int point = 0;
	    String sql = "SELECT point FROM member_cinema WHERE userid = ?";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, userid);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                point = rs.getInt("point");
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return point;
	}

	// 포인트 차감 (결제)
	public int deductPoint(String userid, int amount) {
	    int result = 0;
	    String sql = "UPDATE member_cinema SET point = point - ? WHERE userid = ?";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, amount);
	        pstmt.setString(2, userid);
	        result = pstmt.executeUpdate();
	    } catch (Exception e) { e.printStackTrace(); }
	    return result;
	}
	
	// 회원의 예매 내역(영화정보+예약정보)을 가져오는 조인 쿼리
	public List<ScheduleDto> getReservationList(String userid) {
	    List<ScheduleDto> list = new ArrayList<>();
	    // 예약 + 스케줄 + 영화 + 극장 4개 테이블 조인
	    String sql = "SELECT r.rno, r.seat_info, r.price, r.rdate, "
	               + "s.sdate, m.title, m.poster, t.tname "
	               + "FROM reservation r "
	               + "JOIN schedule s ON r.sno = s.sno "
	               + "JOIN movie m ON s.mno = m.mno "
	               + "JOIN theater t ON s.tno = t.tno "
	               + "WHERE r.userid = ? "
	               + "ORDER BY r.rno DESC";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, userid);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while(rs.next()) {
	                ScheduleDto dto = new ScheduleDto();
	                dto.setRno(rs.getInt("rno"));
	                dto.setSeat_info(rs.getString("seat_info"));
	                dto.setPrice(rs.getInt("price"));
	                dto.setRdate(rs.getString("rdate")); // 예매일
	                dto.setSdate(rs.getString("sdate")); // 상영일
	                dto.setTitle(rs.getString("title")); // 영화제목 
	                dto.setPoster(rs.getString("poster")); // 포스터
	                dto.setTname(rs.getString("tname"));
	                list.add(dto);
	            }
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}
	
	// == 관리자 모드 == //

	// 1. 상영관 목록 조회 (드롭다운용)
	public List<ScheduleDto> getTheaterList() {
	    List<ScheduleDto> list = new ArrayList<>();
	    String sql = "SELECT tno, tname FROM theater ORDER BY tno ASC";
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        while(rs.next()) {
	            ScheduleDto dto = new ScheduleDto();
	            dto.setTno(rs.getInt("tno"));
	            dto.setTname(rs.getString("tname")); // DTO에 tname 필드 있다고 가정
	            list.add(dto);
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	    return list;
	}

	// 2. 스케줄 등록
	public int insertSchedule(ScheduleDto dto) {
	    int result = 0;
	    // sdate는 String ("2026-01-16T14:30") 형태로 들어옴 -> TO_DATE로 변환
	    String sql = "INSERT INTO schedule (sno, mno, tno, sdate) "
	               + "VALUES (schedule_seq.nextval, ?, ?, TO_DATE(?, 'YYYY-MM-DD\"T\"HH24:MI'))";
	    
	    try (Connection conn = DBManager.getInstance();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, dto.getMno());
	        pstmt.setInt(2, dto.getTno());
	        pstmt.setString(3, dto.getSdate()); // String 그대로 넣으면 SQL에서 변환
	        result = pstmt.executeUpdate();
	    } catch (Exception e) { e.printStackTrace(); }
	    return result;
	}
	
}
