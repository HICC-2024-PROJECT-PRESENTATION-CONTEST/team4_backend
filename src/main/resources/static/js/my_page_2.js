
document.addEventListener('DOMContentLoaded', function () {
    const emailField = document.getElementById('connectedEmail');
    const deleteAccountButton = document.getElementById('deleteAccountButton');

    // Set email
    emailField.textContent = '123@gmail.com';

    // Redirect to delete account page
    deleteAccountButton.addEventListener('click', function () {
        window.location.href = 'delete_account';
    });
});
