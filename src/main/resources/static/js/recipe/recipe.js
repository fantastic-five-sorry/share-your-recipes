var currentPage = 2;
const size = 5;
console.log(recipeId);
const token = $("meta[name='_csrf']").attr('content');
const header = $("meta[name='_csrf_header']").attr('content');
const voteCommentUrl = `/api/voting/comment`;
const voteRecipeUrl = `/api/voting/recipe`;
const postCommentUrl = `/api/comment/recipe`;

$(document).ready(function () {
  var upTimeOut;
  var downTimeOut;
  getMoreComments(1, size);
  $('#showMoreBtn').click(() => {
    currentPage = currentPage + 1;
    getMoreComments(currentPage, size);
  });

  ///
  $(document).on('click', '.upVoteBtn', function (e) {
    clearTimeout(upTimeOut);
    upTimeOut = setTimeout(function () {
      handleUpVote(e);
    }, 300);
  });
  $(document).on('click', '.downVoteBtn', function (e) {
    clearTimeout(downTimeOut);
    downTimeOut = setTimeout(function () {
      handleDownVote(e);
    }, 300);
  });

  //
  ///
  $(document).on('click', '#recipeUpBtn', function (e) {
    clearTimeout(upTimeOut);
    upTimeOut = setTimeout(function () {
      handleUpRecipe(e);
    }, 300);
  });
  $(document).on('click', '#recipeDownBtn', function (e) {
    clearTimeout(downTimeOut);
    downTimeOut = setTimeout(function () {
      handleDownRecipe(e);
    }, 300);
  });

  //
  $('#commentForm').submit((e) => {
    e.preventDefault();
    const comment = $('#newComment').val();
    if (comment != '') {
      commentToRecipe(comment, recipeId);
    }
    $('#newComment').val('');
  });
});

function getMoreComments(currentPage, size) {
  const getMoreCommentsUrl = `/api/comment/recipe/${recipeId}?size=${size}&page=${currentPage}`;
  $.ajax({
    url: getMoreCommentsUrl,
    headers: {
      [header]: token,
    },
    processData: false,
    contentType: false,
    type: 'GET',
    success: function (data) {
      const commentsDiv = $('#commentsDiv');
      var newComments = '';
      data.content.map((comment) => {
        newComments = newComments + template(comment);
      });
      if (!data.totalPages) {
        newComments = `<p>No comments yet</p>`;
        $('#showMoreBtn').attr('hidden', true);
      }
      if (newComments == '' && data.totalPages !=0) {
        newComments = `<p>You have reached the end</p>`;
        $('#showMoreBtn').attr('hidden', true);
      }
      if (data.totalPages && data.totalPages < 2) {
        $('#showMoreBtn').attr('hidden', true);
      }
      commentsDiv.append(newComments);
    },
    error: function (err) {
      // showError('Email already existed');
      console.log(err);
    },
  });
}

function doVoteComment(subjectId, type) {
  var data = {
    subjectId: subjectId,
    type: type,
  };

  $.ajax({
    type: 'post',
    url: voteCommentUrl,
    data: JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      console.log(data);
    },
    error: function (error) {
      console.log(error);
    },
  });
}

function handleUpVote(e) {
  if (auth == 'anonymousUser') {
    handleNotLoggedIn();
    return;
  }
  const likeBtnId = e.target.id;
  $(`#${likeBtnId}`).toggleClass('text-success');
  const cmtId = e.target.id.split('-')[1];

  if ($(`#downvote-${cmtId}`).hasClass('text-success')) {
    var downVoteCount = parseInt($(`#downVoteCount-${cmtId}`).text(), 10);
    downVoteCount -= 1;
    $(`#downvote-${cmtId}`).removeClass('text-success');
    $(`#downVoteCount-${cmtId}`).text(downVoteCount);
  }

  doVoteComment(cmtId, 'UP');
  var upVoteCount = parseInt($(`#upVoteCount-${cmtId}`).text(), 10);
  if ($(`#${likeBtnId}`).hasClass('text-success')) upVoteCount += 1;
  else upVoteCount -= 1;
  $(`#upVoteCount-${cmtId}`).text(upVoteCount);
}
function handleDownVote(e) {
  if (auth == 'anonymousUser') {
    handleNotLoggedIn();
    return;
  }
  const likeBtnId = e.target.id;
  $(`#${likeBtnId}`).toggleClass('text-success');
  const cmtId = e.target.id.split('-')[1];

  if ($(`#upvote-${cmtId}`).hasClass('text-success')) {
    var downVoteCount = parseInt($(`#upVoteCount-${cmtId}`).text(), 10);
    downVoteCount -= 1;
    $(`#upvote-${cmtId}`).removeClass('text-success');
    $(`#upVoteCount-${cmtId}`).text(downVoteCount);
  }

  $(`#upvote-${cmtId}`).removeClass('text-success');
  doVoteComment(cmtId, 'DOWN');
  var downVoteCount = parseInt($(`#downVoteCount-${cmtId}`).text(), 10);
  if ($(`#${likeBtnId}`).hasClass('text-success')) downVoteCount += 1;
  else downVoteCount -= 1;
  $(`#downVoteCount-${cmtId}`).text(downVoteCount);
}
function doVoteRecipe(subjectId, type) {
  var data = {
    subjectId: subjectId,
    type: type,
  };

  $.ajax({
    type: 'post',
    url: voteRecipeUrl,
    data: JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      console.log(data);
    },
    error: function (error) {
      console.log(error);
    },
  });
}

function handleUpRecipe(e) {
  if (auth == 'anonymousUser') {
    handleNotLoggedIn();
    return;
  }
  const recipeUpBtn = e.target.id;
  $(`#${recipeUpBtn}`).toggleClass('text-success');

  if ($(`#recipeDownBtn`).hasClass('text-success')) {
    var downVoteCount = parseInt($(`#recipeDownVoteCount`).text(), 10);
    downVoteCount -= 1;
    $(`#recipeDownBtn`).removeClass('text-success');
    $(`#recipeDownVoteCount`).text(downVoteCount);
  }

  doVoteRecipe(recipeId, 'UP');
  var upVoteCount = parseInt($(`#recipeUpVoteCount`).text(), 10);
  if ($(`#${recipeUpBtn}`).hasClass('text-success')) upVoteCount += 1;
  else upVoteCount -= 1;
  $(`#recipeUpVoteCount`).text(upVoteCount);
}
function handleDownRecipe(e) {
  if (auth == 'anonymousUser') {
    handleNotLoggedIn();
    return;
  }
  const recipeDownBtn = e.target.id;
  $(`#${recipeDownBtn}`).toggleClass('text-success');

  if ($(`#recipeUpBtn`).hasClass('text-success')) {
    var downVoteCount = parseInt($(`#recipeUpVoteCount`).text(), 10);
    downVoteCount -= 1;
    $(`#recipeUpBtn`).removeClass('text-success');
    $(`#recipeUpVoteCount`).text(downVoteCount);
  }

  doVoteRecipe(recipeId, 'DOWN');
  var downVoteCount = parseInt($(`#recipeDownVoteCount`).text(), 10);
  if ($(`#${recipeDownBtn}`).hasClass('text-success')) downVoteCount += 1;
  else downVoteCount -= 1;
  $(`#recipeDownVoteCount`).text(downVoteCount);
}

function handleNotLoggedIn() {
  $.confirm({
    title: 'Confirm',
    content: 'Please login to vote for a comment',
    type: 'green',
    buttons: {
      ok: {
        text: 'Login',
        btnClass: 'btn-primary',
        keys: ['enter'],
        action: function () {
          window.location.href = '/login';
        },
      },
      cancel: function () {},
    },
  });
}

function commentToRecipe(comment, recipeId) {
  var data = {
    recipeId: recipeId,
    content: comment,
  };

  $.ajax({
    type: 'post',
    url: postCommentUrl,
    data: JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      console.log(data);
      const commentsDiv = $('#commentsDiv');
      if (commentsDiv.text().trim() == 'No comments yet') commentsDiv.text('');
      const newComment = template(data);
      commentsDiv.prepend(newComment);
    },
    error: function (error) {
      console.log(error);
    },
  });
}

function template(comment) {
  return `<div class='commented-section mt-2'>
    <div class='d-flex align-items-center flex-row commented-user'>
    <div class='d-flex flex-row align-items-center'>
    <a href='/profile/${comment.writerId}'>
    <img src='${comment.photoUrl}' class='avatar' /></a>
  <div>${comment.writerName}</div>
  </div>
      <div class='dot'></div>
      <div>${timeSince(comment.createdAt)}</div>
    </div>
    <div class='comment-text-sm'>
      <span>${comment.content}</span>
    </div>
    <div class='reply-section'>
      <div class='d-flex flex-row align-items-center voting-icons'>
        <i class='fa fa-sort-up fa-2x mt-3 hit-voting upVoteBtn ${
          comment.type == 'UP' && 'text-success'
        }' id='upvote-${comment.id}'></i>
        <span class='ml-2 ms-2' id='upVoteCount-${comment.id}' >${
    comment.upVoteCount
  }</span>
        <i class='fa fa-sort-down fa-2x mb-3 hit-voting downVoteBtn ${
          comment.type == 'DOWN' && 'text-success'
        }' id='downvote-${comment.id}'></i>
        <span class='ml-2 ms-2' id='downVoteCount-${comment.id}'>${
    comment.downVoteCount
  }</span>
        <span class='dot ml-2'></span>
      </div>
    </div>
  </div>`;
}
function timeSince(dateString) {
  //   Date.parse(dateString);

  var seconds = Math.floor((new Date() - Date.parse(dateString)) / 1000);

  var interval = seconds / 31536000;

  if (interval > 1) {
    return Math.floor(interval) + ' years';
  }
  interval = seconds / 2592000;
  if (interval > 1) {
    return Math.floor(interval) + ' months';
  }
  interval = seconds / 86400;
  if (interval > 1) {
    return Math.floor(interval) + ' days';
  }
  interval = seconds / 3600;
  if (interval > 1) {
    return Math.floor(interval) + ' hours';
  }
  interval = seconds / 60;
  if (interval > 1) {
    return Math.floor(interval) + ' minutes';
  }
  if (seconds <= 5) return 'just now';
  return Math.floor(seconds) + ' seconds';
}
