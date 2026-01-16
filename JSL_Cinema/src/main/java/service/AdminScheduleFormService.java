package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MovieDao;
import model.ScheduleDao;

public class AdminScheduleFormService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 영화 목록 가져오기 (드롭다운용)
        MovieDao mDao = new MovieDao();
        request.setAttribute("movieList", mDao.getMovieListSimple());
        
        // 상영관 목록 가져오기 (드롭다운용)
        ScheduleDao sDao = new ScheduleDao();
        request.setAttribute("theaterList", sDao.getTheaterList());
        
        return "admin_schedule_form.jsp";
    }
}