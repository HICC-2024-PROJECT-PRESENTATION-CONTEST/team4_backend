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