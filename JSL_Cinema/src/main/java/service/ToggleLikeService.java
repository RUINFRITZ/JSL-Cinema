package service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ReviewDao;

public class ToggleLikeService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain; charset=UTF-8");
        
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        
        // 로그인 안 한 경우 (혹시 모를 서버단 방어)
        if(userid == null) {
            try { response.getWriter().write("error"); } catch (IOException e) {}
            return null; // AJAX는 페이지 이동 없음
        }

        int mno = Integer.parseInt(request.getParameter("mno"));
        
        ReviewDao dao = new ReviewDao();
        boolean isLiked = dao.toggleLike(mno, userid);
        
        try {
            // true면 좋아요 됨(꽉 찬 하트), false면 취소됨(빈 하트)
            response.getWriter().write(String.valueOf(isLiked));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null; // 페이지 이동 없음 (null 반환)
    }
}