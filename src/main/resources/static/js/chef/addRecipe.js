var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');
var registerSuccessUrl = '/chef/recipe';
const goToSuccessPageAfter = 300;
function doAjaxPost() {
  var ingredients = {};
  $('.ingredientInput')
    .map(function () {
      ingredients = {
        ...ingredients,
        [$(this).find('[name="ingred"]').val()]: $(this)
          .find('[name="soluong"]')
          .val(),
      };
    })
    .get();
  var steps = $('.stepInput')
    .map(function () {
      return $(this).find('[name="step"]').val();
    })
    .get();
  var title = $('.title').val();
  var guideUrl = $('.guideUrl').val();
  var formData = new FormData();
  var files = $('#recipeImage')[0].files;
  console.log(title);
  console.log(guideUrl);
  console.log(files);
  if (files.length) formData.append('image', files[0]);
  // formData.append("document", documentJson); instead of this, use the line below.
  formData.append('ingredients', JSON.stringify(ingredients));
  formData.append('steps', JSON.stringify(steps));
  formData.append('title', title);
  formData.append('guideUrl', guideUrl);

  axios({
    method: 'post',
    url: '/api/recipes/',
    headers: {
      'Content-Type': undefined,
    },
    data: formData,
  })
    .then(function (response) {
      $.notify(
        'Successfully post new recipe. Please wait for approve from Cookwithme! Admin',
        {
          position: 'top center',
          className: 'success',
        }
      );
      setTimeout(
        () => (window.location.href = registerSuccessUrl),
        goToSuccessPageAfter
      );
    })
    .catch(function (response) {
      $.notify(response, {
        position: 'top center',
        className: 'warn',
      });
    });
}
$(document).ready(function () {
  $('#more-ingredient').click(() => {
    $('.ingredientsGroup').append(`<div class='ingredientInput'>
              <input type="text" name='ingred' placeholder="Nguyen lieu" required/>
              <span>:</span>
              <input type="text" name='soluong' placeholder="So luong" required/>
              <span class="deleteBtn">X</span>
            </div>`);
  });
  $('#more-step').click(() => {
    $('.stepsGroup').append(`<div class='stepInput'>
            <input type="text" placeholder="step" name='step' required/>
            <span class="deleteBtn">X</span>
          </div>`);
  });
  $(document).on('submit', 'form', function (event) {
    event.preventDefault();
    $.confirm({
      title: 'Confirm',
      content: 'Do you really want to post new recipe?',
      type: 'green',
      buttons: {
        ok: {
          text: 'YES',
          btnClass: 'btn-primary',
          keys: ['enter'],
          action: function () {
            doAjaxPost();
          },
        },
        cancel: function () {},
      },
    });
  });
  $(document).on('click', '.deleteBtn', function (event) {
    event.target.parentElement.remove();
  });
});
