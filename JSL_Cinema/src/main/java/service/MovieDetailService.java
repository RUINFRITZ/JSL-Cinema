package service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MovieDao;
import model.MovieDto;
import model.ReviewDao; 
import model.ReviewDto;

public class MovieDetailService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        int mno = Integer.parseInt(request.getParameter("mno"));
        
        // 1. 영화 정보 가져오기
        MovieDao movieDao = new MovieDao();
        MovieDto movie = movieDao.selectOne(mno);
        
        // 2. 리뷰 & 좋아요 정보 가져오기
        ReviewDao reviewDao = new ReviewDao();
        
        	// 2-1. 리뷰 리스트 가져오기
        List<ReviewDto> reviewList = reviewDao.getReviewList(mno);
        
        	// 2-2. 좋아요 여부 확인 (로그인 한 경우만)
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        boolean isLiked = false;
        
        if(userid != null) {
            isLiked = reviewDao.isLiked(mno, userid); 
        }
        
        // 3. 데이터 포장해서 JSP로 보내기
        request.setAttribute("movie", movie);
        request.setAttribute("reviewList", reviewList); // 리뷰 목록 전달
        request.setAttribute("isLiked", isLiked);       // 좋아요 여부 전달
        
        return "detail.jsp";
    }
}