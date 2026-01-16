package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MovieDao;
import model.MovieDto;
import model.ReviewDao;
import model.ReviewDto;
import model.ScheduleDao;
import model.ScheduleDto;
import service.AdminMovieInsertService;
import service.AdminScheduleFormService;
import service.AdminScheduleInsertService;
import service.InsertResvService;
import service.InsertReviewService;
import service.MemberJoinService;
import service.MemberLoginService;
import service.MemberLogoutService;
import service.MovieDetailService;
import service.MovieListService;
import service.MovieService;
import service.MyPageService;
import service.MyTicketService;
import service.PaymentService;
import service.ReserveService;
import service.SeatService;
import service.ToggleLikeService;
import service.UpdateMemberService;
import service.UpdatePwService;
import service.WithdrawService;

@WebServlet("*.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class MovieController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = uri.substring(contextPath.length());
        
        System.out.println("Requested Command : " + command);

        MovieService service = null;
        MovieDao dao = new MovieDao();
        ReviewDao rdao = new ReviewDao();
        String path = "";

        if (command.equals("/index.do")) { 					// [ 메인 페이지 영화 목록 로직 ]
            request.setAttribute("movieList", dao.selectAll());
            path = "index.jsp";
        } else if (command.equals("/detail.do")) {			// 영화 상세 정보 조회 로직 - (리뷰, 좋아요 포함)
        	service = new MovieDetailService();
        } else if (command.equals("/toggleLike.do")) {		// 좋아요 토글 (AJAX)
            service = new ToggleLikeService();
        } else if (command.equals("/insertReview.do")) {	// 리뷰 등록 (AJAX)
            service = new InsertReviewService();
        } else if (command.equals("/list.do")) {			// 영화 목록
            service = new MovieListService();
        } else if(command.equals("/reserve.do")) {			// [ 영화 예매 ] - (날짜/시간 선택)
        	service = new ReserveService();
        } else if (command.equals("/seat.do")) {			// 좌석 선택
            service = new SeatService();
        } else if (command.equals("/payment.do")) {			// 결제 확인
            service = new PaymentService();
        } else if (command.equals("/insertResv.do")) {		// 결제 및 예매 처리Pro
            service = new InsertResvService();
        } else if (command.equals("/join.do")) {			// [ 회원가입 화면 ]
            path = "join.jsp";
        } else if (command.equals("/join_pro.do")) { 		// 회원가입 처리Pro
            service = new MemberJoinService();
        } else if (command.equals("/login.do")) { 			// 로그인 화면
            path = "login.jsp";
        } else if (command.equals("/login_pro.do")) { 		// 로그인 처리Pro
            service = new MemberLoginService();
        } else if (command.equals("/logout.do")) { 			// 로그아웃
            service = new MemberLogoutService();
        } else if (command.equals("/myticket.do")) {		// 내 티켓
            service = new MyTicketService();
        } else if (command.equals("/mypage.do")) {			// 마이 페이지       
            service = new MyPageService();
        } else if (command.equals("/updateMember.do")) {	// 회원 정보 수정
            service = new UpdateMemberService();
        } else if (command.equals("/updatePw.do")) {		// 비밀번호 수정
            service = new UpdatePwService();
        } else if (command.equals("/withdraw.do")) {		// 회원 탈퇴
            service = new WithdrawService();    			
    	} else if (command.equals("/center.do")) {       	// [ 고객센터 ]
    	    path = "center.jsp";
    	} else if (command.equals("/terms.do")) {        	// 이용약관
    	    path = "terms.jsp";
    	} else if (command.equals("/privacy.do")) {      	// 개인정보처리방침
    	    path = "privacy.jsp";
    	} else if (command.equals("/adminMovieForm.do")) {	// [ 관리자 영화 등록 ]
    		path = "admin_movie_form.jsp";
    	} else if (command.equals("/adminMovieInsert.do")) {// 관리자 영화 등록Pro
    		service = new AdminMovieInsertService();
    	} else if (command.equals("/adminScheduleForm.do")){// 관리자 상영 일정 등록
    		service = new AdminScheduleFormService();
    	}else if(command.equals("/adminScheduleInsert.do")){// 관리자 상영 일정 등록Pro
    		service = new AdminScheduleInsertService(); 
    	}
        
        if (service != null) {
            path = service.execute(request, response);
        }

        if (path != null && !path.isEmpty()) {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }
}