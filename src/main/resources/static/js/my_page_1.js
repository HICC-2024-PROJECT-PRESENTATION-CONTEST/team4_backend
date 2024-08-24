fetch(`http://localhost:8080/api/users/id`)
    .then((response) => {
        // 응답이 성공적이면 JSON으로 변환
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        return response.json();
    })
    .then((result) => {
        // 'result'는 JSON 객체입니다. 이를 변수에 저장하고 사용합니다.
        let userId = result; // 데이터를 적절히 가공하여 저장
        console.log(userId); // 콘솔에 저장된 데이터 출력

        // 두 번째 fetch 요청
        fetch(`http://localhost:8080/api/likes/${userId}`)
            .then((response) => {
                // 응답이 성공적이면 JSON으로 변환
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then((result) => {
                // 'result'는 JSON 객체입니다. 이를 변수에 저장하고 사용합니다.
                let data = result.data; // 데이터를 적절히 가공하여 저장
                console.log(data); // 콘솔에 저장된 데이터 출력

                // 이후 데이터를 이용한 작업 수행
                processData(data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    })
    .catch((error) => {
        console.error('Error:', error);
    });

// processData 함수 정의
function processData(products) {
    // 템플릿과 부모 요소 가져오기
    const template = document.getElementById('product-template').content;
    const container = document.getElementById('search-list');

    // 제품 개수를 업데이트
    document.getElementById("count").textContent = products.length;

    // 기존 콘텐츠 지우기
    container.innerHTML = '';

    // 제품 데이터를 DOM에 삽입
    products.forEach(product => {
        // 템플릿 복사
        const clone = document.importNode(template, true);

        // 데이터 삽입
        const imageElement = clone.querySelector('.image');
        const brandNameElement = clone.querySelector('.brand-name');
        const productNameElement = clone.querySelector('.prd-name');
        const discountRateElement = clone.querySelector('.discount-rate');
        const priceElement = clone.querySelector('.price');

        imageElement.src = product.imageURL;
        brandNameElement.textContent = product.brand;
        productNameElement.textContent = product.productName;
        discountRateElement.textContent = product.discountRate;
        priceElement.textContent = `${product.price.toLocaleString()}원`;

        // productID를 data attribute에 저장
        imageElement.dataset.productId = product.id;

        // 클릭 이벤트 추가
        imageElement.addEventListener('click', function() {
            const productId = this.dataset.productId;
            window.location.href = `http://localhost:8080/product/${productId}`;
        });

        // productID를 data attribute에 저장
        productNameElement.dataset.productId = product.id;

        // 클릭 이벤트 추가
        productNameElement.addEventListener('click', function() {
            const productId = this.dataset.productId;
            window.location.href = `http://localhost:8080/product/${productId}`;
        });

        // 부모 컨테이너에 추가
        container.appendChild(clone);
    });
}

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
