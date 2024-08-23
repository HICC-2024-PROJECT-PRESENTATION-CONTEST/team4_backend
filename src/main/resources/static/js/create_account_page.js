// document.addEventListener('DOMContentLoaded', function () {
//     const allAgreeCheckbox = document.getElementById('allAgree');
//     const checkboxes = document.querySelectorAll('.terms-checkbox');
//     const googleButton = document.getElementById('googleButton');
//
//     // 전체 동의 체크박스 클릭 시 모든 개별 약관 체크박스에 반영
//     allAgreeCheckbox.addEventListener('change', function () {
//         checkboxes.forEach(checkbox => checkbox.checked = allAgreeCheckbox.checked);
//         toggleGoogleButton();
//     });
//
//     // 개별 약관 체크박스 클릭 시 전체 동의 체크박스 상태 업데이트 및 버튼 활성화 여부 확인
//     checkboxes.forEach(checkbox => {
//         checkbox.addEventListener('change', function () {
//             allAgreeCheckbox.checked = Array.from(checkboxes).every(chk => chk.checked);
//             toggleGoogleButton();
//         });
//     });
//
//     // 모든 필수 약관이 체크되면 Google 버튼 활성화
//     function toggleGoogleButton() {
//         const allChecked = Array.from(checkboxes).every(chk => chk.checked);
//         googleButton.disabled = !allChecked;
//         googleButton.classList.toggle('enabled', allChecked);
//     }
// });
