package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import model.MemberDao;
import model.MemberDto;

public class UpdatePwService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        
        String curPw = request.getParameter("curPw");
        String newPw = request.getParameter("newPw");
        
        MemberDao dao = new MemberDao();
        // [검증] 현재 비밀번호가 맞는지 DB의 해시값과 비교
        // 우선 회원의 정보를 가져와서 저장된 암호화 비밀번호를 확인합니다.
        MemberDto member = dao.getMember(userid);
        
        if (member == null) {
            request.setAttribute("msg", "会員情報が見つかりません。");
            request.setAttribute("url", "logout.do");
            return "alert.jsp";
        }
        
        // BCrypt.checkpw(입력받은 평문 비번, DB에 저장된 암호화 비번)
        if (!BCrypt.checkpw(curPw, member.getPassword())) {
            request.setAttribute("msg", "現在のパスワードが正しくありません。"); // 현재 비번 틀림
            request.setAttribute("url", "history.back()");
            return "alert.jsp";
        }
        
        String hashedNewPw = BCrypt.hashpw(newPw, BCrypt.gensalt());
        
        int result = dao.updatePassword(userid, hashedNewPw);
        
        if(result > 0) {
            // 보안상 비번 바꾸면 강제 로그아웃
            session.invalidate(); 
            request.setAttribute("msg", "パスワードが変更されました。\nセキュリティのため、再度ログインしてください。");
            request.setAttribute("url", "login.do");
            return "alert.jsp";
        } else {
            request.setAttribute("msg", "パスワード変更に失敗しました。");
            request.setAttribute("url", "history.back()");
            return "alert.jsp";
        }
    }
}