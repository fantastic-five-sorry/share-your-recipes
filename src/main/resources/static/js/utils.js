var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

const votingUrl = `/api/voting/comment`;

function handleGetComments(recipeId) {
  const apiUrl = `/api/comment/recipe/${recipeId}`;

  console.log(apiUrl);
  $.ajax({
    type: 'get',
    url: apiUrl,
    header: {
      [header]: token,
    },
    success: function (response) {
      response.map((cmt) => console.log(cmt.id, cmt.content));
    },
  });
}
function handleGetRecipe(recipeId) {
  const apiUrl = `/api/comment/recipe/${recipeId}`;
  console.log(apiUrl);
  $.ajax({
    type: 'get',
    url: apiUrl,
    header: {
      [header]: token,
    },
    success: function (response) {
      const { comments, creator, ingredients, steps } = response;
      // console.log(comments, creator, ingredients, steps);
      console.log(response);
      const commentsDiv = response.content.map(
        (comment) =>
          `<div class="text-danger comment"><p>${comment.id} by ${
            comment.writerEmail
          }</p>
          <button id='upvote-${comment.id}' class="up-vote ${
            comment.type == 'UP' && 'text-success'
          }">UP VOTE</button>
          <button id='downvote-${comment.id}' class="down-vote ${
            comment.type == 'DOWN' && 'text-success'
          }">DOWN VOTE</button>
          </div>`
      );

      $('#list').html(commentsDiv);
    },
    error: function (err) {
      console.log(err);
    },
  });
}

$(document).ready(function () {
  // handleGetComments(1000);
  // handleGetRecipe(1000);
  $('#showCmtBtn').click(function () {
    handleGetRecipe(1000);
  });

  $(document).on('click', '.up-vote', function (e) {
    const likeBtnId = e.target.id;
    $(`#${likeBtnId}`).toggleClass('text-success');
    const cmtId = e.target.id.split('-')[1];

    $(`#downvote-${cmtId}`).removeClass('text-success');
    doVote(cmtId, 'UP');
  });
  $(document).on('click', '.down-vote', function (e) {
    const likeBtnId = e.target.id;
    $(`#${likeBtnId}`).toggleClass('text-success');
    const cmtId = e.target.id.split('-')[1];
    $(`#upvote-${cmtId}`).removeClass('text-success');
    doVote(cmtId,'DOWN');
  });
});

function doVote(subjectId, type) {
  var data = {
    subjectVotingToId: subjectId,
    voterId: 1001,
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
      console.log('oke');
    },
    error: function (error) {
      console.log('errr');
    },
  });
}
