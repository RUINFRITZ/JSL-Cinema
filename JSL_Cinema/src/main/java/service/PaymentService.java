package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDao;
import model.MemberDto;
import model.ScheduleDao;
import model.ScheduleDto;

public class PaymentService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int sno = Integer.parseInt(request.getParameter("sno"));
        String seats = request.getParameter("seats"); // "A-1,B-2" 형태
        
        ScheduleDao dao = new ScheduleDao();
        ScheduleDto schedule = dao.getScheduleOne(sno);
        ScheduleDto theater = dao.getTheaterInfo(schedule.getTno());
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userid");
        
        int point = 0;
        if(userId != null) {
            // 여기서 바로 호출!
            point = dao.getUserPoint(userId); 
        }
        
        int unitPrice = 15000;
        if(theater.getTname().contains("Dolby")) {
            unitPrice = 20000;
        }
        
        int seatCount = seats.split(",").length;
        int totalPrice = seatCount * unitPrice;
        
        request.setAttribute("schedule", schedule);
        request.setAttribute("theater", theater);
        request.setAttribute("seats", seats);    		// 화면 표시용
        request.setAttribute("totalPrice", totalPrice); // 총액
        request.setAttribute("unitPrice", unitPrice);   // 단가 (DB 입력용)
        request.setAttribute("point", point); 
        
        return "payment.jsp";
    }
}