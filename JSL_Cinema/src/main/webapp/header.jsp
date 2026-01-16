<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JSL シネマ | 最高の感動をあなたに</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
    <header class="header-nav sticky-top">
        <div class="container d-flex align-items-center justify-content-between">
            <a href="index.do" class="logo">JSL シネマ</a>
            
			<div class="search-container">
			    <form action="list.do" method="get" class="search-form">
			        <input class="form-control search-bar" type="search" name="keyword" placeholder="どの感動をお探しですか？" value="${param.keyword}">
			        
			        <button type="submit" class="btn-search-submit">
			            <i class="bi bi-search"></i>
			        </button>
			    </form>
			</div>
            
            <div class="nav-icons d-flex align-items-center gap-3">
                <%-- 1. 로그인/티켓 아이콘 로직 --%>
                <c:choose>
                    <c:when test="${empty sessionScope.userid}">
                        <%-- 비로그인 시: 로그인 버튼 --%>
                        <a href="login.do" class="btn-header-login">ログイン</a>
                    </c:when>
                    <c:otherwise>
                        <%-- 로그인 시: 티켓 아이콘 --%>
                        <a href="myticket.do" class="text-white" style="text-decoration: none;">
                            <i class="bi bi-ticket-perforated fs-2" title="マイチケット"></i>
                        </a>
                    </c:otherwise>
                </c:choose>

                <%-- 2. 햄버거 메뉴 (드롭다운) --%>
                <div class="dropdown-wrapper">
                    <i class="bi bi-list menu-icon"></i>
                    
                    <div class="dropdown-menu-glass">
                        <c:if test="${not empty sessionScope.userid}">
                            <a href="mypage.do" class="menu-item">
                                <i class="bi bi-person-circle me-2"></i> マイページ
                            </a>
                       </c:if>
                        
                        <a href="list.do" class="menu-item">
                            <i class="bi bi-film me-2"></i> 映画一覧
    					</a>
    					
                        <a href="center.do" class="menu-item">
                            <i class="bi bi-headset me-2"></i> カスタマーセンター
                        </a>
                        
                        <c:if test="${sessionScope.mgrade == 0}">
                            <hr class="dropdown-divider border-secondary opacity-50">
                            
							<a href="adminMovieForm.do" class="menu-item text-warning">
							    <i class="bi bi-camera-reels-fill me-2"></i> 映画登録
							</a>
                            <a href="adminScheduleForm.do" class="menu-item text-warning">
							    <i class="bi bi-calendar-week-fill me-2"></i> 上映日程登録
							</a>
						</c:if>

                        <c:if test="${not empty sessionScope.userid}">
                            <hr class="dropdown-divider">
                            <a href="logout.do" class="menu-item text-danger">
                                <i class="bi bi-box-arrow-right me-2"></i> ログアウト
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </header>