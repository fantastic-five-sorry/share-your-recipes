$(document).ready(function () {
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
      console.log(data);
      var html = data.map((item) => templateRecipe(item));
      // dataContainer.html(html);
    },
  });
});

const templateRecipe = (element) => {
  return `
  
  <div class="col-md-3 ">
                <div class="card" style="width: 15rem;">
                    <a href="'recipe/' + ${element.slug}"><img src="${element.image}" class="card_img card-img-top" alt="${element.title}"></a>
                    <div class="card-body" style="background-color: #f6f7f8;">
                      <h5 class="card_title card-title">${element.title}</h5>
                      <div class="card-text">
                                <span>by</span>
                            <span class="author" >
                              <a href="'/profile/' + ${element.creator.id}" >${element.creator.name}</a>
                            </span>
                            <i id="btn_heart"class=" btn_heart  fas fa-heart"></i>
                            <span>${element.upVoteCount}</span>
                      </div>
                    </div>
                  </div>    
              </div>

  `;
};
