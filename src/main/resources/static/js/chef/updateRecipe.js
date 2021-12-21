var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var registerSuccessUrl = "/chef/recipe";
const goToSuccessPageAfter = 300;
function doAjaxPost() {
  var ingredients = {};
  $(".ingredientInput")
    .map(function () {
      ingredients = {
        ...ingredients,
        [$(this).find('[name="ingred"]').val()]: $(this)
          .find('[name="soluong"]')
          .val(),
      };
    })
    .get();
  var steps = $(".stepInput")
    .map(function () {
      return $(this).find('[name="step"]').val();
    })
    .get();
  var id = $(".id").val();
  var title = $(".title").val();
  var guideUrl = $(".guideUrl").val();
  var formData = new FormData();
  var files = $("#recipeImage")[0].files;

  if (files.length) formData.append("image", files[0]);
  // formData.append("document", documentJson); instead of this, use the line below.
  formData.append("ingredients", JSON.stringify(ingredients));
  formData.append("steps", JSON.stringify(steps));
  formData.append("title", title);
  formData.append("guideUrl", guideUrl);
  formData.append("id", id);

  axios({
    method: "put",
    url: "/api/recipes/",
    headers: {
      "Content-Type": undefined,
    },
    data: formData,
  })
    .then(function (response) {
      $.notify(
        "Successfully post new recipe. Please wait for approve from Cookwithme! Admin",
        {
          position: "top center",
          className: "success",
        }
      );
      setTimeout(
        () => (window.location.href = registerSuccessUrl),
        goToSuccessPageAfter
      );
    })
    .catch(function (response) {
      $.notify(response, {
        position: "top center",
        className: "warn",
      });
    });
}
$(document).ready(function () {
  $("#more-ingredient").click(() => {
    $(".ingredientBody").append(`<div class='ingredientsGroup '>
    <div>
      <div class="ingred-layout">
        <div class='ingredientInput'>
          <input type="text" class="ingred" name='ingred' placeholder="Ingredient" required/>
          <span>:</span>
          <input type="text" class="quantily" name='soluong' placeholder="Quantily" required/>
        </div>
      </div>
    </div>
              <span class="deleteBtn">X</span>
            </div>
    </div>`);
  });
  $("#more-step").click(() => {
    $(".stepBody").append(`<div class='stepsGroup step-layout step-body'>
    <div class='stepInput'>
      <input type="text" class="step" placeholder="Step" name='step' required/>
      <span class="deleteBtn">X</span>
    </div>
          
          </div>`);
  });
  $(document).on("submit", "form", function (event) {
    event.preventDefault();
    $.confirm({
      title: "Confirm",
      content: "Do you really want to post new recipe?",
      type: "green",
      buttons: {
        ok: {
          text: "YES",
          btnClass: "btn-primary",
          keys: ["enter"],
          action: function () {
            doAjaxPost();
          },
        },
        cancel: function () {},
      },
    });
  });
  $(document).on("click", ".deleteBtn", function (event) {
    event.target.parentElement.remove();
  });
});
