package service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MemberDao;
import model.MemberDto;

public class UpdateMemberService implements MovieService {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            String userid = (String) request.getSession().getAttribute("userid");
            
            MemberDto dto = new MemberDto();
            dto.setUserid(userid);
            dto.setName(request.getParameter("name"));
            dto.setPhone(request.getParameter("phone"));
            dto.setEmail(request.getParameter("email"));
            
            MemberDao dao = new MemberDao();
            int result = dao.updateMember(dto);
            
            if(result > 0) {
                request.setAttribute("msg", "会員情報が修正されました。");
            } else {
                request.setAttribute("msg", "情報の修正に失敗しました。");
            }
            request.setAttribute("url", "mypage.do");
            return "alert.jsp";
            
        } catch (Exception e) { e.printStackTrace(); return null; }
    }
}