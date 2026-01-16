package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDao;
import model.ScheduleDao;
import model.ScheduleDto;

public class InsertResvService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userid");
        
        if(userId == null) {
            return "login.jsp";
        }
        
        int sno = Integer.parseInt(request.getParameter("sno"));
        int unitPrice = Integer.parseInt(request.getParameter("price"));
        String seatsStr = request.getParameter("seats"); 
        String[] seatArray = seatsStr.split(",");
        
        // 1. 총 결제 금액 계산
        int totalAmount = unitPrice * seatArray.length;
        
        ScheduleDao dao = new ScheduleDao();
        ScheduleDto schedule = dao.getScheduleOne(sno); 
        String movieTitle = schedule.getTitle();
        
        // 2. 포인트 잔액 확인
        int currentPoint = dao.getUserPoint(userId);
        
        if (currentPoint < totalAmount) {
            String msg = "申し訳ございません。\nポイント残高が不足しております。(保有ポイント: " + currentPoint + "pt)";
            request.setAttribute("msg", msg);
            request.setAttribute("url", "history.back()");
            return "alert.jsp";
        }
        
        // 3. 포인트 차감 (Member 테이블 Update)
        int result = dao.deductPoint(userId, totalAmount);
        
        if (result > 0) {
        	
        	MemberDao mDao = new MemberDao();
            // 차감이니 금액을 음수(-)로 변환해서 저장
            mDao.insertPointHistory(userId, -totalAmount, "映画予約: " + movieTitle);
            
            // 4. 예약 정보 저장 (반복문)
            int count = 0;
            for(String seat : seatArray) {
                if(dao.insertReservation(sno, userId, seat.trim(), unitPrice) > 0) {
                    count++;
                }
            }
            
            // 5. 결과 확인 및 이동
            if(count == seatArray.length) {
                request.setAttribute("msg", "ご予約が完了いたしました。\nポイントにて決済されました。");
                request.setAttribute("url", "myticket.do"); 
                return "alert.jsp";
            }
        }
        
        // 실패 시
        request.setAttribute("msg", "システムエラーが発生いたしました。\nしばらく経ってからもう一度お試しください。");
        request.setAttribute("url", "history.back()");
        return "alert.jsp";
    }
}