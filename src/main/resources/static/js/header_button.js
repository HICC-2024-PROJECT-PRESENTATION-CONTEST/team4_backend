document.getElementById('home').addEventListener('click', function () {
    window.location.href = '';
});

//로그인 여부 체크 후, 토큰 있으면 my_page_1 페이지, 없으면 login 페이지
function checkToken() {
    // 로컬 스토리지에서 JWT 가져오기
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        window.location.href = 'login';
        return;
    }
    else{
        window.location.href = 'my_page_1';
    }
}
document.getElementById('my').addEventListener('click', checkToken);

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