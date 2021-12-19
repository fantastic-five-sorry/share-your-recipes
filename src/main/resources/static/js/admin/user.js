$(document).ready(() => {
  init();
  $(document).on('click', '.BlockButton', (e) => {
    console.log(e.target.id);
    const userId = e.target.id.split('-')[1];

    $.confirm({
      title: 'Confirm',
      content: 'Do you really want to block this user?',
      type: 'green',
      buttons: {
        ok: {
          text: 'YES',
          btnClass: 'btn-primary',
          keys: ['enter'],
          action: function () {
            doBlockUser(userId);
          },
        },
        cancel: function () {},
      },
    });
  });

  $(document).on('click', '.UnblockButton', (e) => {
    console.log(e.target.id);
    const userId = e.target.id.split('-')[1];
    $.confirm({
      title: 'Confirm',
      content: 'Do you really want to approve this recipe?',
      type: 'green',
      buttons: {
        ok: {
          text: 'YES',
          btnClass: 'btn-primary',
          keys: ['enter'],
          action: function () {
            doUnblockUser(userId);
          },
        },
        cancel: function () {},
      },
    });
  });
});

const init = () => {
  $.extend($.fn.pagination.defaults, {
    pageNumber: 0,
  });
  const dataContainer = $('#userTable');
  $('#buttonGroup').pagination({
    dataSource: '/api/admin/users',
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
      var html = data.map(
        (item) => `<tr class="user-${item.id}">
        <td><a href="#">${item.id}</a></td>
        <td>${item.name}</td>
        <td>${item.email}</td>
        <td>${item.roles}</td>
        <td>${item.blocked}</td>
        <td>
      <button
      class="UnblockButton btn btn-success confirm"
      title="Unblock"
      id="enable-${item.id}"
      ${!item.blocked ? 'hidden' : ''}
    >
      O</button>
    <button
      class="BlockButton btn btn-danger delete"
      title="Block"
      id="block-${item.id}"
      ${item.blocked ? 'hidden' : ''}
    >
      X
    </button>
      </td>
      </tr>`
      );
      dataContainer.html(html);
    },
  });
};
const doBlockUser = (userId) => {
  $.ajax({
    type: 'post',
    url: `/api/admin/blockUser/${userId}`,
    contentType: 'application/json; charset=utf-8',
    headers: {
      // [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $.notify('Successfully block this user', {
          position: 'top center',
          className: 'success',
        });
        // $(`.recipe-${userId}`).hide();
        init();
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
const doUnblockUser = (userId) => {
  $.ajax({
    type: 'post',
    url: `/api/admin/unblockUser/${userId}`,
    contentType: 'application/json; charset=utf-8',
    headers: {
      // [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $.notify('Successfully unblock this user', {
          position: 'top center',
          className: 'success',
        });
        init();
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
