// 현재 URL을 가져옵니다.
let currentUrl = window.location.href;

// URLSearchParams 객체를 사용하여 쿼리 파라미터를 파싱합니다.
const queryParams = new URLSearchParams(window.location.search);

// 'query' 파라미터의 값을 가져옵니다.
const searchQuery = queryParams.get('query');

// fetch 요청을 보내고 응답을 처리합니다.
fetch(`api/product/search?query=${searchQuery}`)
    .then((response) => {
        // 응답이 성공적이면 JSON으로 변환
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        return response.json();
    })
    .then((result) => {
        // 'result'는 JSON 객체입니다. 이를 변수에 저장하고 사용합니다.
        let data = result.data.content; // 데이터를 적절히 가공하여 저장
        console.log(data); // 콘솔에 저장된 데이터 출력

        // 이후 데이터를 이용한 작업 수행
        processData(data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

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

         // 부모 컨테이너에 추가
         container.appendChild(clone);
     });
 }