var currentPage = 2;
const size = 5;
var currentPageCmt = 0;
var turnOn = true;
const sizeCmt = 99999;
const token = $("meta[name='_csrf']").attr('content');
const header = $("meta[name='_csrf_header']").attr('content');
const voteCommentUrl = `/api/voting/comment`;
const voteRecipeUrl = `/api/voting/recipe`;
const postCommentUrl = `/api/comment/recipe`;

$(document).ready(function () {
  // var upTimeOut;
  // var downTimeOut;
  var timeOut;
  getMoreComments(1, size);
  $('#showMoreBtn').click(() => {
    getMoreComments(currentPage, size);
    currentPage = currentPage + 1;
  });

  ///
  $(document).on('click', '.reply', function (e) {
    var commentId = e.currentTarget.id;
    // console.log(e.currentTarget.id);
    getReplyComment(commentId, currentPageCmt, sizeCmt);
  });

  ///
  $(document).on('click', '.upVoteBtn', function (e) {
    clearTimeout(timeOut);
    timeOut = setTimeout(function () {
      handleUpVote(e);
    }, 300);
  });
  $(document).on('click', '.downVoteBtn', function (e) {
    clearTimeout(timeOut);
    timeOut = setTimeout(function () {
      handleDownVote(e);
    }, 300);
  });

  //
  ///
  $(document).on('click', '#recipeUpBtn', function (e) {
    clearTimeout(timeOut);
    timeOut = setTimeout(function () {
      handleUpRecipe(e);
    }, 300);
  });
  $(document).on('click', '#recipeDownBtn', function (e) {
    clearTimeout(timeOut);
    timeOut = setTimeout(function () {
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

  //
  $(document).on('click', '.replyBtn', function(e) {
    var parentId = e.target.id.slice(8);
    var replyContent = $('#newReply'+parentId).val();
    if (replyContent != '') {
      replyToComment(replyContent, parentId);
    }
    $('#newReply'+parentId).val('');
  });
  
});

function getReplyComment(commentId, currentPage, size) {
  
  const getReplyCommentUrl = `/api/reply/${commentId}?size=${size}&page=${currentPage}`;
  const replyListDiv = $('.list-reply'+commentId);
  if (turnOn == false) {
    replyListDiv.empty();
    turnOn = true;
  } else {
    $.ajax({
      url: getReplyCommentUrl,
      headers: {
        [header]: token,
      },
      processData: false,
      contentType: false,
      type: 'GET',
      success: function(data) {
        var replyArr = data.content;
        if (replyArr.length > 0) {
          var newReply = '';
          replyArr.map((reply) => {
            newReply = newReply + templateReply(reply);
          });
          replyListDiv.append(newReply);
          
        }
        replyListDiv.append(formReply(commentId));
        turnOn = false

      },
      error: function(error) {
        console.log(error);
      }
    });
    
  }
  
}

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
      if (newComments == '' && data.totalPages != 0) {
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

function replyToComment(content, parentId) {
  var postReplyUrl = `/api/reply`;
  var data = {
    parentId: parentId,
    content: content
  }

  $.ajax({
    type: 'POST', 
    url: postReplyUrl,
    data: JSON.stringify(data),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: token,
    }, 
    traditional: true,
    success: function(data) {
      const listReply = $('.list-reply'+parentId);
      var newReply = templateReply(data);
      listReply.prepend(newReply);
    },
    error: function(error) {
      console.log(error);
    }
  })
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
  return `<div class='commented-section' style="margin-top: 3rem">
    <div class='d-flex align-items-center flex-row commented-user'>
    <div class='d-flex flex-row align-items-center'>
    <a href='/profile/${comment.writerId}'>
    <img src='${comment.photoUrl}' class='avatar' /></a>
  <div style="margin-left: 10px">${comment.writerName}</div>
  </div>
      <div class='dot'></div>
      <div>${timeSince(comment.createdAt)}</div>
    </div>
    <div class='comment-text-sm' style="margin-left: 3rem">
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
        <div class='reply-cmt'>reply</div>
      </div>
      <div id='${comment.id}' class='reply' style="margin-bottom: 1rem;">
        <i class="fa fa-reply fa-flip-horizontal fa-flip-vertical" aria-hidden="true"></i>
        <span >view reply</span>
      </div>
      <div style="margin-left: 30px" class='list-reply${comment.id}'></div>
      <div style="border-bottom: 3px solid #1eaeca; margin-top: 1rem"></div>
    </div>
  </div>`;
}

function templateReply(reply) {
  return `<div class='commented-section'>
    <div class='d-flex align-items-center flex-row commented-user'>
    <div class='d-flex flex-row align-items-center'>
    <a href='/profile/${reply.writerId}'>
    <img src='${reply.photoUrl}' class='avatar' /></a>
  <div style="margin-left: 10px">${reply.writerName}</div>
  </div>
      <div class='dot'></div>
      <div>${timeSince(reply.createdAt)}</div>
    </div>
    <div class='comment-text-sm' style="margin-left: 3rem">
      <span>${reply.content}</span>
    </div>
  </div>`;
}

function formReply(commentId) {
  return `<div
            class="d-flex flex-row add-comment-section mt-4 mb-4"
            >
            <input
              type="text"
              class="form-control mr-3"
              placeholder="Reply this comment"
              id="newReply${commentId}"
            />
            <button class="btn btn-primary replyBtn" id="replyBtn${commentId}">Reply</button>
          </div>
          `;
          // <div
          //   sec:authorize="!isAuthenticated()"
          //   class="d-flex flex-row add-comment-section mt-4 mb-4"
          // >
          //   <h2>
          //     To reply this comment
          //     <span><a th:href="@{/login}">login?</a></span>
          //   </h2>
          // </div>
          // ;
}

function timeSince(dateString) {
  //   Date.parse(dateString);

  var seconds = Math.floor(
    (Date.parse(new Date().toISOString()) - Date.parse(dateString)) / 1000
  );
  // for heroku only
  seconds -= 7 * 60 * 60;
  //
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
