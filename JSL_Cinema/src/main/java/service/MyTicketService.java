package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ScheduleDao;

public class MyTicketService implements MovieService {
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userid");
        if(userId == null) {
        	// 로그인 안 된 상태라면 (테스트용으로 guest01 강제 할당하거나 alert 띄움)
            // return "login.jsp";
        	userId = "guest01"; // 테스트용
        }
        
        ScheduleDao dao = new ScheduleDao();
        request.setAttribute("ticketList", dao.getReservationList(userId));
        
        return "myticket.jsp";
    }
}