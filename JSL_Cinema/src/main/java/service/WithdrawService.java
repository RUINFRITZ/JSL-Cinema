package service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MemberDao;

public class WithdrawService implements MovieService {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userid = (String) request.getSession().getAttribute("userid");
        
        MemberDao dao = new MemberDao();
        int result = dao.withdrawMember(userid); // deldate 업데이트
        
        if(result > 0) {
            request.setAttribute("msg", "脱退処理が完了しました。ご利用ありがとうございました。");
            request.setAttribute("url", "logout.do"); // 로그아웃 처리
        } else {
            request.setAttribute("msg", "脱退処理に失敗しました。");
            request.setAttribute("url", "mypage.do");
        }
        return "alert.jsp";
    }
}