document.addEventListener('DOMContentLoaded', () => {
    const heartIcon = document.querySelector('#heart');

    heartIcon.onclick = heartonoff;

    function heartonoff() {
        // 현재 src 속성을 가져옵니다.
        const currentSrc = heartIcon.getAttribute('src');

        // 상대 경로로 이미지 URL을 정의합니다.
        const heartEmpty = 'img/찜하기.png';
        const heartFilled = 'img/찜하기f.png';

        // src가 빈 하트 이미지인지 확인하고 이미지 변경
        if (currentSrc.includes(heartEmpty)) {
            heartIcon.setAttribute('src', heartFilled); // 채워진 하트 이미지로 변경
        } else {
            heartIcon.setAttribute('src', heartEmpty); // 빈 하트 이미지로 변경
        }
    }
});