var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

const votingUrl = `/api/voting/recipe`;

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
      const commentsDiv = response.map(
        (comment) =>
          `<div class="text-danger comment"><p>${comment.content} by ${comment.writer.email}</p>
          <button id='upvote-${comment.id}' class="up-vote">UP VOTE</button>
          <button id='downvote-${comment.id}' class="down-vote">DOWN VOTE</button>
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
    doVote('UP');
  });
  $(document).on('click', '.down-vote', function (e) {
    const likeBtnId = e.target.id;
    $(`#${likeBtnId}`).toggleClass('text-success');
    const cmtId = e.target.id.split('-')[1];
    $(`#upvote-${cmtId}`).removeClass('text-success');
    doVote('DOWN');
  });
});

function doVote(type) {
  var data = {
    subjectVotingToId: 1000,
    voterId: 1000,
    type: type,
  };

  $.ajax({
    type: 'post',
    url: votingUrl,
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
