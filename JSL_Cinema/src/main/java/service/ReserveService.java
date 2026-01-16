package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MovieDao;
import model.ScheduleDao;

public class ReserveService implements MovieService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
	    if (session.getAttribute("userid") == null) {
	        request.setAttribute("msg", "ログインが必要です。");
	        request.setAttribute("url", "login.do");
	        return "alert.jsp";
	    }
		
		MovieDao dao = new MovieDao();
		ScheduleDao sdao = new ScheduleDao();
		
		int mno = Integer.parseInt(request.getParameter("mno"));
    	String selectedDate = request.getParameter("date");
    	
    	if(selectedDate == null || selectedDate.isEmpty()) {
    		selectedDate = LocalDate.now().toString(); // "2026-01-13" 형식
    	}
    	
    	List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dateList.add(LocalDate.now().plusDays(i));
        }
    	
    	request.setAttribute("movie", dao.selectOne(mno)); 
    	request.setAttribute("scheduleList", sdao.getScheduleByDate(mno, selectedDate));
    	request.setAttribute("dateList", dateList);
        request.setAttribute("selectedDate", selectedDate);
		
		return "reserve.jsp";
	}
	
}
