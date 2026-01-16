package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import model.MemberDao;
import model.MemberDto;

public class MemberLoginService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userid = request.getParameter("userid");
        String pass = request.getParameter("password");
        
        MemberDao dao = new MemberDao();
        MemberDto dto = dao.getMember(userid);
        
        if (dto != null) {
            if (BCrypt.checkpw(pass, dto.getPassword())) {
                // 로그인 성공: 세션에 정보 저장
                HttpSession session = request.getSession();
                session.setAttribute("userid", dto.getUserid());
                session.setAttribute("name", dto.getName());
                session.setAttribute("mgrade", dto.getMgrade());
                
                return "index.do"; 
            }
        }
        
        // 로그인 실패
        request.setAttribute("msg", "IDまたはパスワードが正しくありません。");
        request.setAttribute("url", "login.do");
        return "alert.jsp";
    }
}