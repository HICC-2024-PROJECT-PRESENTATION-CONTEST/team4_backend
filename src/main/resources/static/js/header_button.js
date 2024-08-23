// Google OAuth2 로그인
function loginWithGoogle() {
    window.location.href = '/oauth2/authorization/google';
}

// 초기 페이지 이동
function goToHome() {
    window.location.href = "/";
}

// 로그인 상태 확인 및 처리
function redirectToPageIfAuthenticated(redirectUrl, fallbackUrl) {
    const isAuthenticated = localStorage.getItem('jwtToken') !== null;
    window.location.href = isAuthenticated ? redirectUrl : fallbackUrl;
}

// 홈 버튼 이벤트 처리
document.getElementById('home').addEventListener('click', function () {
    window.location.href = '/';  // URL 통일
});

// 마이페이지 버튼 이벤트 처리
document.getElementById('my').addEventListener('click', function () {
    redirectToPageIfAuthenticated('/my_page_1', '/login');
});

// 로그인 버튼 클릭 이벤트 처리 (로그인 페이지에서 사용)
document.getElementById('login-button')?.addEventListener('click', function () {
    redirectToPageIfAuthenticated('/my_page_1', '/login');
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

// 검색 폼 제출 이벤트 처리
document.getElementById('search')?.addEventListener('submit', function(event) {
    // 폼 제출 시 페이지 새로 고침 방지
    event.preventDefault();

    // 입력 필드의 값 가져오기
    let searchInput = document.getElementById('search-txt').value;

    // 변수에 저장
    let searchQuery = searchInput;

    fetch(`/api/product/search?query=${encodeURIComponent(searchQuery)}&size=100&page=0`)
        .then((response) => response.text())
        .then((result) => {
            console.log(result);
        });

});
