$(document).ready(() => {
  $.extend($.fn.pagination.defaults, {
    pageNumber: 0,
  });
  const dataContainer = $('#pageContentTotal');
  $('#buttonGroupsTotal').pagination({
    dataSource: '/api/question',
    locator: 'content',
    totalNumberLocator: function (response) {
      return response.totalElements;
    },

    alias: {
      pageNumber: 'page',
      pageSize: 'size',
    },
    pageSize: 10,
    ajax: {
      beforeSend: function () {
        dataContainer.html('Loading data...');
      },
    },
    callback: function (data, pagination) {
      // template method of yourself
      var html = data.map(
        (item) => `<tr class="recipe-${item.id}">
        <td><a href="#">${item.id}</a></td>
        <td>${item.title}</td>
        <td>${item.creator.name}</td>
        <td>${item.status}</td>
        <td>
      <button
      class="ApproveButton btn btn-success confirm"
      title="Approve"
      id="approve-${item.id}"
    >
      O</button
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
      dataContainer.html(html);
    },
  });
  $(document).on('click', '.DeleteButton', (e) => {
    console.log(e.target.id);
    const recipeId = e.target.id.split('-')[1];

    $.confirm({
      title: 'Confirm',
      content: 'Do you really want to delete this question?',
      type: 'green',
      buttons: {
        ok: {
          text: 'YES',
          btnClass: 'btn-primary',
          keys: ['enter'],
          action: function () {
            doDeleteRecipe(recipeId);
          },
        },
        cancel: function () {},
      },
    });
  });
  $(document).on('click', '.UpdateButton', (e) => {
    console.log(e.target.id);
    const recipeId = e.target.id.split('-')[1];
    window.location.href = `/chef/update/${recipeId}`;
  });
  $(document).on('click', '.ApproveButton', (e) => {
    console.log(e.target.id);
    const recipeId = e.target.id.split('-')[1];
    $.confirm({
      title: 'Confirm',
      content: 'Do you really want to approve this question?',
      type: 'green',
      buttons: {
        ok: {
          text: 'YES',
          btnClass: 'btn-primary',
          keys: ['enter'],
          action: function () {
            doApproveRecipe(recipeId);
          },
        },
        cancel: function () {},
      },
    });
  });
});

const doDeleteRecipe = (recipeId) => {
  $.ajax({
    type: 'delete',
    url: `/api/question/${recipeId}`,
    contentType: 'application/json; charset=utf-8',
    headers: {
      // [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $.notify('Successfully delete this question', {
          position: 'top center',
          className: 'success',
        });
        $(`.recipe-${recipeId}`).hide();
      }
    },
    error: function (error) {
      $.notify(error.responseText, {
        position: 'top center',
        className: 'warn',
      });
    },
  });
};
const doApproveRecipe = (recipeId) => {
  $.ajax({
    type: 'post',
    url: `/api/question/approve/${recipeId}`,
    contentType: 'application/json; charset=utf-8',
    headers: {
      // [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $.notify('Successfully approve this recipe', {
          position: 'top center',
          className: 'success',
        });
      }
    },
    error: function (error) {
      $.notify(error.responseText, {
        position: 'top center',
        className: 'warn',
      });
    },
  });
};
