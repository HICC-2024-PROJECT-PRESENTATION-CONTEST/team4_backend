// Google OAuth2 로그인
function loginWithGoogle() {
    // 구글 OAuth2 인증 엔드포인트로 리다이렉트
    window.location.href = '/oauth2/authorization/google';
}

// 초기 페이지 이동
function goToHome() {
    window.location.href = "/";
}

// 상품 검색 기능
function searchProduct() {
    const searchQuery = document.getElementById('search-input').value;
    if (searchQuery) {
        window.location.href = `/search?query=${searchQuery}`;
    }
}

// 로그인 상태 확인 및 처리
document.getElementById('login-button').addEventListener('click', function () {
    const isAuthenticated = localStorage.getItem('jwtToken') !== null;
    if (isAuthenticated) {
        window.location.href = '/my_page_1';
    } else {
        window.location.href = '/login';
    }
});

// 로그인 후 JWT 토큰 저장
function handleLoginResponse(token) {
    localStorage.setItem('jwtToken', token);
}

// API 호출 시 JWT 토큰을 Authorization 헤더에 추가
function fetchWithAuth(url, options = {}) {
    const token = localStorage.getItem('jwtToken');
    const headers = new Headers(options.headers || {});
    headers.append('Authorization', `Bearer ${token}`);

    return fetch(url, {
        ...options,
        headers,
    });
}

// OAuth2 로그인 후 백엔드에서 리다이렉트되는 URL 처리
const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');
if (token) {
    handleLoginResponse(token);
    window.location.href = '/my_page_1'; // 로그인 후 메인 페이지로 리다이렉트
}
