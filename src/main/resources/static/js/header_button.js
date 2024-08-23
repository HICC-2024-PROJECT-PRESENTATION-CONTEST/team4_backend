document.getElementById('my').addEventListener('click', function () {
  window.location.href = 'login_page.html';
});
document.getElementById('home').addEventListener('click', function () {
  window.location.href = 'Initial_page.html';
});
// 로그인 버튼 이벤트 처리
document.getElementById('my').addEventListener('click', function () {
  // 로그인 상태 확인 (예시로 localStorage 사용, 실제 구현 시 서버와 통신 필요)
  const isLoggedIn = localStorage.getItem('loggedIn');

  if (isLoggedIn) {
    window.location.href = 'My_page_1.html';
  } else {
    window.location.href = 'login_page.html';
  }
});

// 홈 버튼 클릭 시 초기 페이지로 이동
document.getElementById('home').addEventListener('click', function () {
  window.location.href = 'Initial_page.html';
});
