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