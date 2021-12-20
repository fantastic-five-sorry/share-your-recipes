$(document).ready(() => {
  $.extend($.fn.pagination.defaults, {
    pageNumber: 0,
  });
  const dataContainer = $('#pageContentTotal');
  $('#buttonGroupsTotal').pagination({
    dataSource: '/api/recipes',
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
        <td>${item.creator.name}</td>
        <td>${item.title}</td>
        <td>${item.status}</td>
      </tr>`
      );
      dataContainer.html(html);
    },
  });
});