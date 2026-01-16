<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh;">
    <div class="container d-flex justify-content-center align-items-center" style="padding-top: 0.5rem; padding-bottom: 4rem;">
        
        <div class="reserve-container-glass p-5" style="width: 100%; max-width: 500px;">
            <h2 class="text-center text-white fw-bold mb-2">Create Account</h2>
            <p class="text-center text-secondary small mb-4">JSLシネマへようこそ❕</p>
            
            <form action="join_pro.do" method="post" onsubmit="return checkForm()">
                <div class="mb-3">
                    <label class="text-secondary mb-1">Name</label>
                    <input type="text" name="name" id="name" class="form-control bg-transparent text-white border-secondary" placeholder="お名前">
                </div>
                
                <div class="mb-3">
                    <label class="text-secondary mb-1">User ID</label>
                    <input type="text" name="userid" id="userid" class="form-control bg-transparent text-white border-secondary" placeholder="ID (英数字)">
                </div>
                
                <div class="mb-3">
                    <label class="text-secondary mb-1">Password</label>
                    <input type="password" name="password" id="pw" class="form-control bg-transparent text-white border-secondary" placeholder="パスワード">
                </div>
                
                <div class="mb-4">
                    <label class="text-secondary mb-1">Password Check</label>
                    <input type="password" name="password_check" id="pw2" class="form-control bg-transparent text-white border-secondary" placeholder="パスワード確認">
                </div>
                
                <div class="mb-3">
                    <label class="text-secondary mb-1">Phone</label>
                    <input type="text" name="phone" id="pw" class="form-control bg-transparent text-white border-secondary" placeholder="携帯電話番号">
                </div>
                
                <div class="mb-3">
                    <label class="text-secondary mb-1">Email</label>
                    <input type="text" name="email" id="pw" class="form-control bg-transparent text-white border-secondary" placeholder="メールアドレス">
                </div>
                
                <button type="submit" class="btn btn-card-reserve w-100 py-2 fs-5">会員登録</button>
            </form>
        </div>
        
    </div>
</div>

<script>
    // 모달 열기 함수 (msg: 메시지, focusId: 닫은 후 포커스 이동할 input ID)
    let targetFocusId = null; // 닫을 때 포커스 이동할 타겟 저장용

    function showModal(msg, focusId) {
        document.getElementById("modalMessage").innerText = msg; // 메시지 주입
        document.getElementById("customModal").style.display = "flex"; // 모달 보이기
        targetFocusId = focusId; // 포커스 타겟 저장
    }

    // 모달 닫기 함수
    function closeModal() {
        document.getElementById("customModal").style.display = "none"; // 모달 숨기기
        if (targetFocusId) {
            document.getElementById(targetFocusId).focus(); // 저장된 타겟으로 포커스 이동
            targetFocusId = null;
        }
    }

    function checkForm() {
        const name = document.getElementById("name");
        const userid = document.getElementById("userid");
        const pw = document.getElementById("pw");
        const pw2 = document.getElementById("pw2");
        const phone = document.getElementById("phone");
        const email = document.getElementById("email");
        
        if(name.value.trim() === "") {
            showModal("お名前を入力してください。", "name");
            return false;
        }
        if(userid.value.trim() === "") {
            showModal("IDを入力してください。", "userid");
            return false;
        }
        if(pw.value.trim() === "") {
            showModal("パスワードを入力してください。", "pw");
            return false;
        }
        if(pw2.value.trim() === "") {
            showModal("パスワード確認を入力してください。", "pw2");
            return false;
        }
        if(pw.value !== pw2.value) {
            showModal("パスワードが一致しません。", "pw");
            pw.value = "";
            pw2.value = "";
            return false;
        }
        if(phone.value.trim() === "") {
            showModal("携帯電話番号を入力してください。", "phone");
            return false;
        }
        if(email.value.trim() === "") {
            showModal("E-mailを入力してください。", "email");
            return false;
        }
        
        return true;
    }
</script>

<%@ include file="footer.jsp" %>