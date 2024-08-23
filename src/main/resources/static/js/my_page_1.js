document.addEventListener('DOMContentLoaded', function () {
  const emailToggle = document.getElementById('emailToggle');
  const likeListContainer = document.getElementById('likeListContainer');
  const moreButton = document.getElementById('moreButton');
  const likeListItems = document.querySelectorAll('.like-item');
  const myPage2Button = document.getElementById('myPage2Button');

  emailToggle.addEventListener('change', function () {
    if (emailToggle.checked) {
      console.log('Email notifications ON');
      // Add logic to start sending emails
    } else {
      console.log('Email notifications OFF');
      // Add logic to stop sending emails
    }
  });

  moreButton.addEventListener('click', function () {
    likeListContainer.classList.toggle('show-all');
    moreButton.textContent = likeListContainer.classList.contains('show-all')
      ? 'Show Less'
      : '더보기';
  });

  myPage2Button.addEventListener('click', function () {
    window.location.href = 'my_page_2.html';
  });
});
