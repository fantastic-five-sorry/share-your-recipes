var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(() => {
  $.extend($.fn.pagination.defaults, {
    pageNumber: 0,
  });
  const dataContainer = $("#pageContentTotal");
  $("#buttonGroupsTotal").pagination({
    dataSource: "/api/recipes/myRecipe",
    locator: "content",
    totalNumberLocator: function (response) {
      return response.totalElements;
    },

    alias: {
      pageNumber: "page",
      pageSize: "size",
    },
    pageSize: 10,
    ajax: {
      beforeSend: function () {
        dataContainer.html("Loading data...");
      },
    },
    callback: function (data, pagination) {
      // template method of yourself
      var html = data.map(
        (item) => `<tr class="recipe-${item.id}">
      <td><a href="#">${item.id}</a></td>
      <td>${item.creator.name}</td>
      <td>${item.title}</td>
      <td>${item.status}</td>
      <td>
      <button
      class="UpdateButton btn btn-success confirm"
      style="

      title="Edit"
      id="update-${item.id}"
    >
      V</button
    ><button
      class="DeleteButton btn btn-danger delete"
      title="Delete"
      id="del-${item.id}"
    >
      X
    </button>
    </tr>`
      );
      dataContainer.html(html);
    },
  });

  ///

  const approvedContainer = $("#pageContentApproved");
  $("#buttonGroupsApproved").pagination({
    dataSource: "/api/recipes/myRecipe/approved",
    locator: "content",
    totalNumberLocator: function (response) {
      return response.totalElements;
    },

    alias: {
      pageNumber: "page",
      pageSize: "size",
    },
    pageSize: 10,
    ajax: {
      beforeSend: function () {
        approvedContainer.html("Loading data...");
      },
    },
    callback: function (data, pagination) {
      var html = data.map(
        (item) => `<tr class="recipe-${item.id}">
      <td><a href="#">${item.id}</a></td>
      <td>${item.creator.name}</td>
      <td>${item.title}</td>
      <td>${new Date(item.createdAt).toISOString()}</td>
      <td>
      <button
      class="UpdateButton btn btn-success confirm"
      title="Edit"
      id="update-${item.id}"
    >
      V</button
    ><button
      class="DeleteButton btn btn-danger delete"
      title="Delete"
      id="del-${item.id}"
    >
      X
    </button>
      </td>
    </tr>`
      );
      approvedContainer.html(html);
    },
  });
  ///

  const pendingContainer = $("#pageContentPending");
  $("#buttonGroupsPending").pagination({
    dataSource: "/api/recipes/myRecipe/pending",
    locator: "content",
    totalNumberLocator: function (response) {
      return response.totalElements;
    },

    alias: {
      pageNumber: "page",
      pageSize: "size",
    },
    pageSize: 10,
    ajax: {
      beforeSend: function () {
        pendingContainer.html("Loading data...");
      },
    },
    callback: function (data, pagination) {
      // template method of yourself

      var html = data.map(
        (item) => `<tr class="recipe-${item.id}">
      <td><a href="#">${item.id}</a></td>
      <td>${item.creator.name}</td>
      <td>${item.title}</td>
      <td>${new Date(item.createdAt).toISOString()}</td>
      <td>
      <button
      class="UpdateButton btn btn-success confirm"
      title="Edit"
      id="update-${item.id}"
    >
      V</button
    ><button
      class="DeleteButton btn btn-danger delete"
      title="Delete"
      id="del-${item.id}"
    >
      X
    </button>
      </td>
    </tr>`
      );
      pendingContainer.html(html);
    },
  });
  //

  // handle del, update

  $(document).on("click", ".DeleteButton", (e) => {
    console.log(e.target.id);
    const recipeId = e.target.id.split("-")[1];

    $.confirm({
      title: "Confirm",
      content: "Do you really want to delete this recipe?",
      type: "green",
      buttons: {
        ok: {
          text: "YES",
          btnClass: "btn-primary",
          keys: ["enter"],
          action: function () {
            doDeleteRecipe(recipeId);
          },
        },
        cancel: function () {},
      },
    });
  });
  $(document).on("click", ".UpdateButton", (e) => {
    console.log(e.target.id);
    const recipeId = e.target.id.split("-")[1];
    window.location.href = `/chef/update/${recipeId}`;
  });
});

const doDeleteRecipe = (recipeId) => {
  $.ajax({
    type: "delete",
    url: `/api/recipes/${recipeId}`,
    contentType: "application/json; charset=utf-8",
    headers: {
      [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $.notify("Successfully delete this recipe", {
          position: "top center",
          className: "success",
        });
        $(`.recipe-${recipeId}`).hide();
      }
    },
    error: function (error) {
      $.notify(error.responseText, {
        position: "top center",
        className: "warn",
      });
    },
  });
};
