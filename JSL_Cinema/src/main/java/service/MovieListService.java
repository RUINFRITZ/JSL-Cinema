package service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MovieDao;
import model.MovieDto;

public class MovieListService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        if(keyword == null) keyword = ""; // null 방지
        
        MovieDao dao = new MovieDao();
        List<MovieDto> list = dao.getMovieList(keyword);
        
        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);      // 검색어 유지용
        request.setAttribute("count", list.size());    // 검색 결과 개수
        
        return "list.jsp";
    }
}