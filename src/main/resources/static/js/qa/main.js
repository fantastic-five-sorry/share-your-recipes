
function clickHeart(x){
    
    var allClass = x.classList.toString();
    
    // kiểm tra nếu chưa tồn tại class active thì thêm class active
    if (allClass.indexOf('red') == -1) {
        x.classList.add("red");
    }
    else{
        //Thêm hoặc xóa class fa-thumbs-down
        x.classList.remove("red");
    }
}
function clickLike(x){
   
    var allClass = x.classList.toString();
  
    // kiểm tra nếu chưa tồn tại class active thì thêm class active
    if (allClass.indexOf('blue') == -1) {
        x.classList.add("blue");
    }
    else{
        //Thêm hoặc xóa class fa-thumbs-down
        x.classList.remove("blue");
    }
}
