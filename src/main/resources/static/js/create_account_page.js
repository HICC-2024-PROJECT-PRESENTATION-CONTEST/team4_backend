document.addEventListener('DOMContentLoaded', function () {
    const allAgreeCheckbox = document.getElementById('allAgree');
    const checkboxes = document.querySelectorAll('.terms-checkbox');
    const googleButton = document.getElementById('googleButton');

    allAgreeCheckbox.addEventListener('change', function () {
        checkboxes.forEach((checkbox) => {
            checkbox.checked = allAgreeCheckbox.checked;
            checkbox.parentElement.querySelector('span').style.color =
                checkbox.checked ? 'white' : 'gray';
        });
        updateGoogleButtonState();
    });

    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', function () {
            const allChecked = Array.from(checkboxes).every((cb) => cb.checked);
            allAgreeCheckbox.checked = allChecked;

            checkbox.parentElement.querySelector('span').style.color =
                checkbox.checked ? 'white' : 'gray';
            updateGoogleButtonState();
        });
    });

    function updateGoogleButtonState() {
        const allRequiredChecked = Array.from(checkboxes)
            .filter((cb) => cb.required)
            .every((cb) => cb.checked);
        googleButton.disabled = !allRequiredChecked;
        googleButton.style.backgroundColor = allRequiredChecked
            ? '#4285F4'
            : 'gray';
        googleButton.style.cursor = allRequiredChecked ? 'pointer' : 'not-allowed';
    }

    googleButton.addEventListener('click', function () {
        if (!googleButton.disabled) {
            // Implement Google Auto Login logic here
            alert('Google Auto Login requested');
        }
    });

    updateGoogleButtonState(); // Initialize button state
});