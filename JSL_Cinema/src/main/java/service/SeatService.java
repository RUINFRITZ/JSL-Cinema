package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ScheduleDao;
import model.ScheduleDto;

public class SeatService implements MovieService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		ScheduleDao sdao = new ScheduleDao();
		int sno = Integer.parseInt(request.getParameter("sno"));
        
        // 1. 스케줄 정보 가져오기 (mno, tno, sdate 등)
        ScheduleDto schedule = sdao.getScheduleOne(sno); 
        
        // 2. 상영관 정보 가져오기 (trow, tcol 확인용)
        ScheduleDto theater = sdao.getTheaterInfo(schedule.getTno());
        
        // 3. 가격 결정 로직 (비즈니스 로직)
        int price = 15000; // 기본 가격
        if (theater.getTname() != null && theater.getTname().contains("Dolby")) {
            price = 20000; // 돌비시네마 특별관
        }
        schedule.setPrice(price);
        
        // 4. 이미 예약된 좌석 리스트 가져오기 (예: ["A-1", "A-2"])
        List<String> reservedSeats = sdao.getReservedSeats(sno);

        request.setAttribute("schedule", schedule);
        request.setAttribute("theater", theater);
        request.setAttribute("reservedSeats", reservedSeats);
        
		return "seat.jsp";
	}

}
