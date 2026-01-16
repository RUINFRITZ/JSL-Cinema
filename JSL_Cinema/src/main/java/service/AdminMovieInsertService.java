package service;

import java.io.File;
// import java.util.UUID; // [수정] UUID 불필요
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.MovieDao;
import model.MovieDto;

public class AdminMovieInsertService implements MovieService {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
        	// [기존 표준 방식] - 배포 시에는 이게 맞음
        	// String uploadPath = request.getServletContext().getRealPath("/poster");

        	// [개발자 전용 하드코딩 모드] - 이클립스 프로젝트 경로를 직접 지정!
        	// (본인의 실제 프로젝트 경로를 탐색기에서 복사해서 넣으세요)
        	String uploadPath = "C:\\JSL\\workspace\\JSL_Cinema\\src\\main\\webapp\\poster";
            
            // 폴더가 없으면 생성 (안전장치)
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // 파일 처리 로그 확인용
            System.out.println("uploadPath: " + uploadPath);

            Part part = request.getPart("poster");
            String fileName = "default.jpg"; // 파일 미업로드시 기본값
            
            if (part != null && part.getSize() > 0) {
                fileName = part.getSubmittedFileName();
                
                // 파일명에 경로가 포함된 경우(일부 브라우저), 파일명만 추출
                fileName = new File(fileName).getName();

                // [중요] part.write에 '절대 경로'를 주어야 해당 위치로 이동합니다.
                // 만약 파일명이 같으면 덮어쓰기 됩니다. (관리자가 관리하므로 OK)
                part.write(uploadPath + File.separator + fileName);
                
                System.out.println("fileName : " + fileName);
                System.out.println(" - File Upload Complete !");
            }

            MovieDto dto = new MovieDto();
            dto.setTitle(request.getParameter("title"));
            dto.setCatchphrase(request.getParameter("catchphrase"));
            dto.setContent(request.getParameter("content"));
            // 숫자 파싱 시 오류 대비
            String runtimeStr = request.getParameter("runtime");
            dto.setRuntime(runtimeStr != null && !runtimeStr.isEmpty() ? Integer.parseInt(runtimeStr) : 0);
            
            dto.setOpendate(request.getParameter("opendate"));
            dto.setPoster(fileName);

            MovieDao dao = new MovieDao();
            int result = dao.insertMovie(dto);

            if (result > 0) {
                request.setAttribute("msg", "映画登録が完了しました。");
                request.setAttribute("url", "adminMovieForm.do"); 
            } else {
                request.setAttribute("msg", "映画登録に失敗しました。DB Error。");
                request.setAttribute("url", "history.back()");
            }
            return "alert.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "登録中にエラーが発生しました。\nログを確認してください。" + e.getMessage());
            request.setAttribute("url", "history.back()");
            return "alert.jsp";
        }
    }
}