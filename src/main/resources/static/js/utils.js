var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

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
  const apiUrl = `/api/recipes/${recipeId}`;
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
      const commentsDiv = comments.map(
        (comment) =>
          `<div class="text-danger comment"><p>${comment.content} by ${comment.writer.email}</p>
          <button id='comment-${comment.id}' class="like">Like</button>
          </div>`
      );

      $('#list').html(commentsDiv);
    },
  });
}

$(document).ready(function () {
  // handleGetComments(1000);
  // handleGetRecipe(1000);
  $('#showCmtBtn').click(function () {
    handleGetRecipe(1000);
  });

  $(document).on('click', '.like', function (e) {
    const likeBtnId = e.target.id;
    $(`#${likeBtnId}`).toggleClass('text-success');
    const cmtId = e.target.id.split('-')[1];
    console.log(cmtId);
  });
});
