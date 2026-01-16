package service;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ReviewDao;
import model.ReviewDto;

public class InsertReviewService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain; charset=UTF-8");
        
        try {
            HttpSession session = request.getSession();
            String userid = (String) session.getAttribute("userid");
            
            if(userid == null) {
                response.getWriter().write("fail");
                return null;
            }
            
            int mno = Integer.parseInt(request.getParameter("mno"));
            int rating = Integer.parseInt(request.getParameter("rating"));
            String content = request.getParameter("content");
            
            ReviewDto dto = new ReviewDto();
            dto.setMno(mno);
            dto.setUserid(userid);
            dto.setRating(rating);
            dto.setContent(content);
            
            ReviewDao dao = new ReviewDao();
            int result = dao.insertReview(dto);
            
            // 성공하면 "success", 실패하면 "fail" 문자열 전송
            response.getWriter().write(result > 0 ? "success" : "fail");
            
        } catch (Exception e) {
            e.printStackTrace();
            try { response.getWriter().write("fail"); } catch (IOException io) {}
        }
        
        return null;
    }
}