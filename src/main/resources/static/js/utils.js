var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

$('#showCmtBtn').click(function () {
  console.log('getting all cmt');
  handleGetComments(1000);
});

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
      response.map(cmt => console.log(cmt.id, cmt.content))
    },
  });
}
