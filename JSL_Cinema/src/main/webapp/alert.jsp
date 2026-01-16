<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notification</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        /* alert.jsp 전용 스타일: 배경을 어둡게 하고 모달만 띄움 */
        body {
            background-color: #0f0f0f;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            overflow: hidden;
        }
        /* 모달 강제 표시 (이미 오버레이가 깔려있으므로) */
        .modal-overlay {
            display: flex !important; 
        }
    </style>
</head>
<body>

    <div class="modal-overlay">
        <div class="glass-modal">
            <div class="modal-title">NOTICE</div>
            <div class="modal-message">
                ${msg} </div>
            <button class="btn-modal-ok" onclick="moveToNext()">確認</button>
        </div>
    </div>

    <script>
        // 확인 버튼 누르면 실행될 함수
        function moveToNext() {
            const url = "${url}"; // 서버에서 보낸 이동 경로
            
            if (url === "history.back()") {
                history.back();
            } else {
                location.href = url;
            }
        }
        
        // 엔터키를 눌러도 넘어가지게 편의성 추가
        document.addEventListener("keydown", function(event) {
            if (event.key === "Enter") {
                moveToNext();
            }
        });
    </script>

</body>
</html>