function handleLoginResponse(token) {
    try {
        localStorage.setItem('jwtToken', token);
        console.log("Token stored in localStorage:", token);
    } catch (e) {
        console.error("Failed to save the token to localStorage", e);
    }
}

// 토큰을 Local Storage에 저장하고 URL에서 제거하는 함수
function storeTokenAndRemoveFromUrl() {
    const token = getTokenFromUrl();
    if (token) {
        handleLoginResponse(token);

        // URL에서 토큰을 제거 (history API 사용)
        const url = new URL(window.location);
        url.searchParams.delete('token');
        window.history.replaceState({}, document.title, url);

        // 메인 페이지로 리다이렉트
        window.location.href = '/my_page_1';
    }
}

// 로그아웃 버튼에 이벤트 리스너 추가
document.getElementById('logoutButton').addEventListener('click', function() {
    // 로컬 스토리지에서 토큰 삭제
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('refresh_token');

    // 로그인 페이지로 리디렉션
    window.location.href = '/login'; // 로그인 페이지 경로로 변경
});

// 회원 탈퇴 버튼에 이벤트 리스너 추가
document.getElementById('deleteAccountButton').addEventListener('click', function() {
    const token = localStorage.getItem('jwtToken');

    fetch('/api/users/profile', {  // 서버의 @DeleteMapping 경로와 일치하도록 수정
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                // 탈퇴 성공 시 처리
                localStorage.removeItem('jwtToken');
                localStorage.removeItem('refresh_token');
                window.location.href = '/login';
            } else {
                alert('회원 탈퇴에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});

// 페이지 로드 시 함수 실행
window.onload = storeTokenAndRemoveFromUrl;
