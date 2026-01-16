package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class MemberDao {

    public int insertMember(MemberDto dto) {
        int result = 0;
        String sql = "INSERT INTO member_cinema (userid, password, name, phone, email) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dto.getUserid());
            pstmt.setString(2, dto.getPassword());
            pstmt.setString(3, dto.getName());
            pstmt.setString(4, dto.getPhone());
            pstmt.setString(5, dto.getEmail());
            
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 아이디로 회원 정보 조회 (로그인 / 중복체크 / 비밀번호 검증 겸용)
    public MemberDto getMember(String userid) {
        MemberDto dto = null;
        String sql = "SELECT * FROM member_cinema WHERE userid = ? AND deldate IS NULL";
        
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = new MemberDto();
                    dto.setUserid(rs.getString("userid"));
                    dto.setPassword(rs.getString("password"));
                    dto.setName(rs.getString("name"));
                    dto.setPoint(rs.getInt("point"));
                    dto.setMgrade(rs.getInt("mgrade"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setEmail(rs.getString("email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
    
    // 회원 정보 수정 (비밀번호 제외, 이름/전화번호 등)
    public int updateMember(MemberDto dto) {
        int result = 0;
        String sql = "UPDATE member_cinema SET name= ?, phone= ?, email= ? WHERE userid = ?";
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getPhone());
            pstmt.setString(3, dto.getEmail());
            pstmt.setString(4, dto.getUserid());
            result = pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    // 비밀번호 변경
    public int updatePassword(String userid, String newPwd) {
        int result = 0;
        
        String sql = "UPDATE member_cinema SET password = ? WHERE userid = ?";
        
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPwd);
            pstmt.setString(2, userid);
            
            result = pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    // 회원 탈퇴 (Soft Delete)
    public int withdrawMember(String userid) {
        int result = 0; // 진짜 삭제하지 않고, 날짜만 찍음
        
        String sql = "UPDATE member_cinema SET deldate = SYSDATE WHERE userid = ?";
        
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            result = pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    // 포인트 내역 조회
    public List<MemberDto> getPointHistory(String userid) {
    	
        List<MemberDto> list = new ArrayList<MemberDto>();
        
        String sql = "SELECT * FROM point_history WHERE userid = ? ORDER BY pno DESC";
        
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                	MemberDto dto = new MemberDto();
                    dto.setPno(rs.getInt("pno"));
                    dto.setAmount(rs.getInt("amount"));
                    dto.setDescription(rs.getString("description"));
                    dto.setRegdate(rs.getString("regdate"));
                    
                    list.add(dto);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    
 // 포인트 내역 기록하기 (amount: 충전은 양수, 사용은 음수로 들어옴)
    public void insertPointHistory(String userid, int amount, String description) {
        String sql = "INSERT INTO point_history(pno, userid, amount, description, regdate) "
                   + "VALUES(point_seq.nextval, ?, ?, ?, SYSDATE)";
        
        try (Connection conn = DBManager.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.setInt(2, amount);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}