$(document).ready(function () {
  
  

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
