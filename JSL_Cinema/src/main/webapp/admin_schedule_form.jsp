<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh; padding-top: 4rem;">
    <div class="container" style="max-width: 600px;">
        
        <h3 class="fw-bold text-white mb-4 ps-3 border-start border-4 border-warning">ADMIN : スケジュール登録</h3>

        <div class="reserve-container-glass p-5">
            <form action="adminScheduleInsert.do" method="post">
                
                <div class="mb-4">
                    <label class="text-secondary small mb-1">映画選択</label>
                    <select name="mno" class="form-select bg-dark text-white border-secondary" required>
                        <option value="" disabled selected>映画を選択してください</option>
                        <c:forEach var="m" items="${movieList}">
                            <option value="${m.mno}">${m.title}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-4">
                    <label class="text-secondary small mb-1">上映館選択</label>
                    <select name="tno" class="form-select bg-dark text-white border-secondary" required>
                        <option value="" disabled selected>上映館を選択してください</option>
                        <c:forEach var="t" items="${theaterList}">
                            <option value="${t.tno}">${t.tname}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-5">
                    <label class="text-secondary small mb-1">上映日時</label>
                    <input type="datetime-local" name="sdate" class="form-control bg-dark text-white border-secondary" required>
                </div>

                <div class="text-end">
                    <button type="button" class="btn btn-outline-light me-2" onclick="history.back()">キャンセル</button>
                    <button type="submit" class="btn btn-reserve px-5">スケジュール登録</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>