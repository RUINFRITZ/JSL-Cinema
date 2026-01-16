<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>

<div class="container main-content" style="max-width: 900px;">
    
    <h3 class="fw-bold text-white mb-4 ps-3 border-start border-4 border-warning">マイページ</h3>

    <div class="row">
        <div class="col-md-3 mb-4">
            <div class="reserve-container-glass p-3 text-center">
                <i class="bi bi-person-circle display-4 text-orange mb-3"></i>
                <h5 class="text-white fw-bold mb-1">${member.name} 様</h5>
                <p class="text-secondary small mb-3">${member.userid}</p>
                
                <div class="mb-2 fw-bold" style="letter-spacing: 2px;">
                    <c:choose>
                        <c:when test="${member.mgrade == 0}"><span class="text-danger"><i class="bi bi-gear-fill"></i> ADMIN</span></c:when>
                        <c:when test="${member.mgrade == 1}"><span class="text-secondary">MEMBER</span></c:when>
                        <c:when test="${member.mgrade == 2}"><span class="text-info">VIP</span></c:when>
                        <c:when test="${member.mgrade == 3}"><span class="text-primary">VVIP</span></c:when>
                        <c:when test="${member.mgrade == 4}"><span class="text-warning"><i class="bi bi-trophy-fill"></i> MVP</span></c:when>
                    </c:choose>
                </div>

                <div class="badge bg-warning text-dark fs-6 mb-3">
                    Point : <fmt:formatNumber value="${member.point}" /> P
                </div>
            </div>
            
            <div class="list-group mt-3">
                <button class="list-group-item list-group-item-action active" onclick="showTab('point')">ポイント履歴</button>
                <button class="list-group-item list-group-item-action" onclick="showTab('info')">基本情報変更</button>
                <button class="list-group-item list-group-item-action" onclick="showTab('security')">パスワード変更</button>
                <button class="list-group-item list-group-item-action text-danger" onclick="showTab('withdraw')">会員脱退</button>
            </div>
        </div>

        <div class="col-md-9">
            
            <div id="tab-point" class="tab-content">
                <div class="reserve-container-glass p-4">
                    <h5 class="text-white fw-bold mb-4"><i class="bi bi-coin me-2"></i> ポイント履歴</h5>
                    <div class="table-responsive">
                        <table class="table table-dark table-hover small">
                            <thead>
                                <tr class="text-secondary">
                                    <th>日付</th>
                                    <th>内容</th>
                                    <th class="text-end">金額</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty pointList}">
                                        <c:forEach var="p" items="${pointList}">
                                            <tr>
                                                <td>${p.regdate}</td>
                                                <td>${p.description}</td>
                                                <td class="text-end fw-bold ${p.amount > 0 ? 'text-success' : 'text-danger'}">
                                                    ${p.amount > 0 ? '+' : ''}<fmt:formatNumber value="${p.amount}" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="3" class="text-center py-4 text-secondary">ポイント履歴がありません。</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div id="tab-info" class="tab-content d-none">
                <div class="reserve-container-glass p-4">
                    <h5 class="text-white fw-bold mb-4"><i class="bi bi-pencil-square me-2"></i> 基本情報変更</h5>
                    <form action="updateMember.do" method="post">
                        <div class="mb-3">
                            <label class="text-secondary small">お名前</label>
                            <input type="text" name="name" class="form-control bg-dark text-white border-secondary" value="${member.name}">
                        </div>
                        <div class="mb-3">
                            <label class="text-secondary small">電話番号</label>
                            <input type="text" name="phone" class="form-control bg-dark text-white border-secondary" value="${member.phone}">
                        </div>
                        <div class="mb-3">
                            <label class="text-secondary small">メールアドレス</label>
                            <input type="email" name="email" class="form-control bg-dark text-white border-secondary" value="${member.email}">
                        </div>
                        <div class="text-end mt-4">
                            <button type="submit" class="btn btn-reserve px-4">保存する</button>
                        </div>
                    </form>
                </div>
            </div>

            <div id="tab-security" class="tab-content d-none">
                <div class="reserve-container-glass p-4">
                    <h5 class="text-white fw-bold mb-4"><i class="bi bi-shield-lock me-2"></i> パスワード変更</h5>
                    <form action="updatePw.do" method="post">
                        <div class="mb-3">
                            <label class="text-secondary small">現在のパスワード</label>
                            <input type="password" name="curPw" class="form-control bg-dark text-white border-secondary">
                        </div>
                        <div class="mb-3">
                            <label class="text-secondary small">新しいパスワード</label>
                            <input type="password" name="newPw" class="form-control bg-dark text-white border-secondary">
                        </div>
                        <div class="text-end mt-4">
                            <button type="submit" class="btn btn-reserve px-4">変更する</button>
                        </div>
                    </form>
                </div>
            </div>

            <div id="tab-withdraw" class="tab-content d-none">
                <div class="reserve-container-glass p-4 border border-danger">
                    <h5 class="text-danger fw-bold mb-3"><i class="bi bi-exclamation-triangle-fill me-2"></i> 会員脱退</h5>
                    <p class="text-secondary mb-4">
                        脱退すると、保有しているポイントおよびクーポンはすべて消滅し、復旧することはできません。<br>
                        本当に脱退しますか？
                    </p>
                    <div class="text-end">
                        <button type="button" class="btn btn-outline-danger" onclick="confirmWithdraw()">脱退する</button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    // 탭 전환 스크립트
    function showTab(tabName) {
        // 모든 탭 숨기기
        document.querySelectorAll('.tab-content').forEach(el => el.classList.add('d-none'));
        // 선택한 탭 보이기
        document.getElementById('tab-' + tabName).classList.remove('d-none');
        
        // 버튼 활성화 스타일
        document.querySelectorAll('.list-group-item').forEach(el => el.classList.remove('active'));
        event.target.classList.add('active');
    }
    
    // 탈퇴 확인
    function confirmWithdraw() {
        // 커스텀 모달 활용 (확인/취소 로직이 필요하다면 window.confirm 사용이 더 간단할 수 있음)
        if(confirm("本当に脱退しますか？ ポイントが消滅します。\n(この操作は取り消せません)")) {
            location.href = "withdraw.do";
        }
    }
</script>

<style>
    /* 사이드 메뉴 스타일 커스텀 */
    .list-group-item {
        background: rgba(255, 255, 255, 0.05);
        border: 1px solid rgba(255, 255, 255, 0.1);
        color: #ccc;
        transition: 0.2s;
        cursor: pointer;
    }
    .list-group-item:hover {
        background: rgba(255, 255, 255, 0.1);
        color: white;
    }
    .list-group-item.active {
        background: var(--jsl-orange);
        border-color: var(--jsl-orange);
        color: white;
        font-weight: bold;
    }
</style>

<%@ include file="footer.jsp" %>