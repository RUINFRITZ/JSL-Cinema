package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import model.MemberDao;
import model.MemberDto;

public class MemberJoinService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userid = request.getParameter("userid");
            String pass = request.getParameter("password");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            
            MemberDao dao = new MemberDao();
            if(dao.getMember(userid) != null) {
                request.setAttribute("msg", "すでに使用されているIDです。");
                request.setAttribute("url", "history.back()");
                return "alert.jsp";
            }
            
            String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());
            
            MemberDto dto = new MemberDto();
            dto.setUserid(userid);
            dto.setPassword(hashPass);
            dto.setName(name);
            dto.setPhone(phone);
            dto.setEmail(email);
            
            int result = dao.insertMember(dto);
            
            if(result > 0) {
                request.setAttribute("msg", "会員登録が完了いたしました。");
                request.setAttribute("url", "login.do");
                return "alert.jsp";
            } else {
                request.setAttribute("msg", "エラーが発生しました。");
                request.setAttribute("url", "history.back()");
                return "alert.jsp";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}