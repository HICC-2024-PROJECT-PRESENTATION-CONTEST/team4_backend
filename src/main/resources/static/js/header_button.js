document.getElementById('my').addEventListener('click', function () {
    window.location.href = 'login_page';
});
document.getElementById('home').addEventListener('click', function () {
    window.location.href = '';
});
// 로그인 버튼 이벤트 처리
document.getElementById('my').addEventListener('click', function () {
    // 로그인 상태 확인 (예시로 localStorage 사용, 실제 구현 시 서버와 통신 필요)
    const isLoggedIn = localStorage.getItem('loggedIn');

    if (isLoggedIn) {
        window.location.href = 'my_page_1';
    } else {
        window.location.href = 'login';
    }
});

document.getElementById('search').addEventListener('submit', function(event) {
    // 폼 제출 시 페이지 새로 고침 방지
    event.preventDefault();

    // 입력 필드의 값 가져오기
    let searchInput = document.getElementById('search-txt').value;

    // 변수에 저장
    let searchQuery = searchInput;

    fetch("GET /api/product/search?query={searchQuery}&size={사이즈 default = 100 & page={페이지 default = 0}")
        .then((response) => response.txt())
        .then((result) => { console.log(result);});

    // 필요한 추가 작업 수행 (예: 폼 데이터 전송, 필드 비우기 등)
});