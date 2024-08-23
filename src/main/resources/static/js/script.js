// Google OAuth2 로그인
function loginWithGoogle() {
    window.location.href = '/oauth2/authorization/google';
}
//
// // 초기 페이지 이동
// function goToHome() {
//     window.location.href = "/";
// }

// 로그인 상태 확인 및 처리
document.getElementById('login-button').addEventListener('click', function () {
    const isAuthenticated = localStorage.getItem('jwtToken') !== null;
    if (isAuthenticated) {
        window.location.href = '/my_page_1';
    } else {
        window.location.href = '/login';
    }
});

// // 홈 버튼 이벤트 처리
// document.getElementById('home').addEventListener('click', function () {
//     window.location.href = 'Initial_page';
// });

// 마이페이지 버튼 이벤트 처리
document.getElementById('my').addEventListener('click', function () {
    const isLoggedIn = localStorage.getItem('jwtToken') !== null;
    if (isLoggedIn) {
        window.location.href = 'my_page_1';
    } else {
        window.location.href = 'login';
    }
});

// 로그인 후 JWT 토큰 저장
function handleLoginResponse(token) {
    localStorage.setItem('jwtToken', token);
}

// OAuth2 로그인 후 백엔드에서 리다이렉트되는 URL 처리
const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');
if (token) {
    handleLoginResponse(token);
    window.location.href = '/my_page_1';
}
