// 현재 URL에서 productID를 추출
const url = window.location.href; // 현재 페이지의 URL 가져오기
const urlParts = url.split('/'); // URL을 '/'로 분할
const productID = urlParts[urlParts.length - 1]; // 마지막 부분이 productID

const fetchUrl = `http://localhost:8080/api/product/search/${productID}`;
console.log(fetchUrl); // URL 확인
fetch(fetchUrl)
    .then((response) => {
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        return response.json();
    })
    .then((result) => {
        console.log(result); // 전체 result 객체를 출력하여 구조 확인
        let data = result.data; // data가 있는지 확인하며 접근
        console.log(data); // data 값을 확인
        processData(data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

function processData(data) {
    const imageElement = document.getElementById('image');
    imageElement.src = data.imageURL;

    const brand_name = document.getElementById('brand-name');
    brand_name.textContent = data.brand;

    const product_name = document.getElementById('product-name');
    product_name.textContent = data.productName;

    const discount_rate = document.getElementById('discount-rate');
    discount_rate.textContent = data.discountRate;

    const price = document.getElementById('prc');
    price.textContent = `${data.price.toLocaleString()}원`
}
document.addEventListener('DOMContentLoaded', () => {
    const heartIcon = document.querySelector('#heart');

    heartIcon.onclick = heartonoff;

    function heartonoff() {
        // 현재 src 속성을 가져옵니다.
        const currentSrc = heartIcon.getAttribute('src');

        // 상대 경로로 이미지 URL을 정의합니다.
        const heartEmpty = '/img/heart.png';
        const heartFilled = '/img/heartc.png';

        // src가 빈 하트 이미지인지 확인하고 이미지 변경
        if (currentSrc.includes(heartEmpty)) {
            heartIcon.setAttribute('src', heartFilled); // 채워진 하트 이미지로 변경
        } else {
            heartIcon.setAttribute('src', heartEmpty); // 빈 하트 이미지로 변경
        }
    }
});