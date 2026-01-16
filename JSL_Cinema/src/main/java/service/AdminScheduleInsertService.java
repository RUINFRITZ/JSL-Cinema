package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ScheduleDao;
import model.ScheduleDto;

public class AdminScheduleInsertService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            int mno = Integer.parseInt(request.getParameter("mno"));
            int tno = Integer.parseInt(request.getParameter("tno"));
            String sdate = request.getParameter("sdate"); // yyyy-MM-ddTHH:mm

            ScheduleDto dto = new ScheduleDto();
            dto.setMno(mno);
            dto.setTno(tno);
            dto.setSdate(sdate);

            ScheduleDao dao = new ScheduleDao();
            int result = dao.insertSchedule(dto);

            if (result > 0) {
                request.setAttribute("msg", "上映スケジュールを登録しました。");
                request.setAttribute("url", "adminScheduleForm.do"); // 계속 등록하도록 폼으로 이동
            } else {
                request.setAttribute("msg", "登録失敗");
                request.setAttribute("url", "history.back()");
            }
            return "alert.jsp";
            
        } catch (Exception e) { e.printStackTrace(); return null; }
    }
}