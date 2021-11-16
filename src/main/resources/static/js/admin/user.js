$(document).ready(() => {
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
      console.log(data);
      var html = data.map(
        (item) => `<tr class="user-${item.id}">
        <td><a href="#">${item.id}</a></td>
        <td>${item.name}</td>
        <td>${item.email}</td>
        <td>${item.roles}</td>
        <td>${item.enabled}</td>
        <td>${item.blocked}</td>
      </tr>`
      );
      dataContainer.html(html);
    },
  });
});
