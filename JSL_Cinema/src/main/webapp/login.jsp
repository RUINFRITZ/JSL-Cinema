<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="detail-hero" style="background-color: #0f0f0f; min-height: 100vh;">
    <div class="container d-flex justify-content-center" style="padding-top: 1rem; padding-bottom: 6rem;">
        
        <div class="reserve-container-glass p-5" style="width: 100%; max-width: 450px;">
            <h2 class="text-center text-white fw-bold mb-4">LOGIN</h2>
            
            <form action="login_pro.do" method="post" onsubmit="return checkLogin()">
                <div class="mb-3">
                    <label class="text-secondary mb-1">User ID</label>
                    <input type="text" name="userid" class="form-control bg-transparent text-white border-secondary" placeholder="IDを入力してください。">
                </div>
                <div class="mb-4">
                    <label class="text-secondary mb-1">Password</label>
                    <input type="password" name="password" class="form-control bg-transparent text-white border-secondary" placeholder="パスワードを入力してください。">
                </div>
                
                <button type="submit" class="btn btn-card-reserve w-100 py-2 fs-5 mb-3">ログイン</button>
                
                <div class="text-center">
                    <a href="join.do" class="text-decoration-none text-orange small">新規会員登録</a>
                </div>
            </form>
        </div>
        
    </div>
</div>

<%@ include file="footer.jsp" %>

<script>
    function checkLogin() {
        const useridInput = document.getElementsByName("userid")[0];
        const passInput = document.getElementsByName("password")[0];

        if(useridInput.value.trim() === "") {
            showModal("IDを入力してください。", useridInput);
            return false;
        }
        if(passInput.value.trim() === "") {
            showModal("パスワードを入力してください。", passInput);
            return false;
        }
        
        return true;
    }
</script>