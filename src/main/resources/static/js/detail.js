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

    const musinsaLinkElement = document.getElementById('musinsa-link');
        musinsaLinkElement.onclick = function() {
            window.open(data.productURL);
        };

}

const categoryUrl = `http://localhost:8080/api/product/1/category`;

fetch(categoryUrl)
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
        processCategory(data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

function processCategory(data) {
    const category = document.getElementById('ctg');
    category.textContent = data;
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
        const isAuthenticated = localStorage.getItem('jwtToken') !== null;
        if (currentSrc.includes(heartEmpty) && isAuthenticated) {
            heartIcon.setAttribute('src', heartFilled); // 채워진 하트 이미지로 변경

            // userid, productid를 사용해서 찜목록 추가 post fetch 코드 필요
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
                    fetch(`http://localhost:8080/api/likes/${userId}/product/${productID}`, {
                            method: 'post',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                user_id: userId,
                                product_id: productID
                            })
                        })
                        .then((response) => {
                            // 응답이 성공적이면 JSON으로 변환
                            if (!response.ok) {
                                throw new Error('Network response was not ok.');
                            }
                            return response.json();
                        })
                        .then((result) => console.log(result));
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        } else if (isAuthenticated === false) {
            alert("찜하기는 로그인 이후에 가능합니다.");
            // login_page로 redirect 코드 필요
        } else {
            heartIcon.setAttribute('src', heartEmpty); // 빈 하트 이미지로 변경
            // userid, productid를 사용해서 찜목록 해제 delete fetch 코드 필요
        }
    }
});