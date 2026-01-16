package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutService implements MovieService {
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate(); // 세션 전체 무효화
        
        request.setAttribute("msg", "ログアウトしました。");
        request.setAttribute("url", "index.do");
        return "alert.jsp";
    }
}