package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MovieService {
    // 이동할 페이지 경로(path)를 리턴하도록 설계
	// 모든 서비스 객체들이 공통으로 가질 메서드
    // 리턴값 String은 컨트롤러가 다음에 이동할 JSP 페이지 경로
    String execute(HttpServletRequest request, HttpServletResponse response);
}