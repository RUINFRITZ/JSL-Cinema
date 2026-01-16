<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh;">
    <div class="container" style="padding-top: 0.1rem; padding-bottom: 8rem;">
        
        <div class="mb-5 border-start border-4 border-warning ps-2">
            <h2 class="text-white fw-bold mb-1">MOVIES</h2>
            <p class="text-secondary mb-0">
                <c:if test="${not empty keyword}">
                    "<span class="text-orange fw-bold">${keyword}</span>" の検索結果 : ${count}件
                </c:if>
                <c:if test="${empty keyword}">
                    現在上映中の映画 : 名前順整列
                </c:if>
            </p>
        </div>

        <div class="row gx-4 gy-5 row-cols-2 row-cols-md-3 row-cols-lg-5">
            <c:choose>
                <c:when test="${not empty list}">
                    <c:forEach var="movie" items="${list}">
                        <div class="col">
                            <div class="movie-card h-100">
                                <a href="detail.do?mno=${movie.mno}">
                                    <div class="position-relative overflow-hidden rounded-3 mb-3 shadow-lg">
                                        <img src="${pageContext.request.contextPath}/poster/${movie.poster}" 
                                             class="w-100 object-fit-cover" 
                                             style="aspect-ratio: 2/3;" 
                                             alt="${movie.title}">
                                        <div class="hover-overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center opacity-0 hover-opacity-100 bg-dark bg-opacity-50 transition-all">
                                            <span class="btn btn-card-reserve btn-sm">詳細を見る</span>
                                        </div>
                                    </div>
                                </a>
                                <h5 class="text-white fw-bold mb-1 text-truncate">${movie.title}</h5>
                                <p class="text-secondary small mb-0">${movie.opendate.substring(0, 10)} 公開</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                
                <%-- 검색 결과 없을 때 --%>
                <c:otherwise>
                    <div class="col-12 py-5 text-center">
                        <div class="reserve-container-glass d-inline-block px-5">
                            <i class="bi bi-camera-reels fs-1 text-secondary d-block mb-3"></i>
                            <p class="text-white fs-5 mb-1">検索結果が見つかりませんでした。</p>
                            <a href="list.do" class="btn btn-outline-light mt-3 btn-sm">全体リストを見る</a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
    </div>
</div>

<style>
    /* 카드 이미지 호버 효과 */
    .movie-card:hover img { transform: scale(1.05); transition: 0.3s; }
    .movie-card:hover .hover-overlay { opacity: 1 !important; }
    .movie-card a { text-decoration: none; }
</style>

<%@ include file="footer.jsp" %>