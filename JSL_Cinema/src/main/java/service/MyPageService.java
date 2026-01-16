package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MemberDao;
import model.MemberDto;

public class MyPageService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        
        if(userid == null) return "login.do";
        
        MemberDao dao = new MemberDao();
        MemberDto member = dao.getMember(userid);
        
        request.setAttribute("pointList", dao.getPointHistory(userid));
        request.setAttribute("member", member);
        
        return "mypage.jsp";
    }
}