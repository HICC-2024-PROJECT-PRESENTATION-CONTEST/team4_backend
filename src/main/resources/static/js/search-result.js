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

// 데이터 처리 함수
function processData(products) {
    // 예를 들어, 제품 정보를 DOM에 표시하는 코드
    let imageElements = document.getElementsByClassName("image");
    let brandNameElements = document.getElementsByClassName("brand-name");
    let productNameElements = document.getElementsByClassName("prd-name");
    let discountRateElements = document.getElementsByClassName("discount-rate");
    let priceElements = document.getElementsByClassName("price");

    // 제품 개수를 업데이트
    document.getElementById("count").textContent = products.length;

    // 제품 데이터를 DOM에 삽입
    for (let i = 0; i < products.length; i++) {
        const product = products[i];

        if (imageElements[i]) {
            imageElements[i].src = product.imageURL;
        }

        if (brandNameElements[i]) {
            brandNameElements[i].innerHTML = product.brand;
        }

        if (productNameElements[i]) {
            productNameElements[i].innerHTML = product.productName;
        }

        if (discountRateElements[i]) {
            discountRateElements[i].innerHTML = product.discountRate;
        }

        if (priceElements[i]) {
            priceElements[i].innerHTML = `${product.price.toLocaleString()}원`;
        }
    }
}