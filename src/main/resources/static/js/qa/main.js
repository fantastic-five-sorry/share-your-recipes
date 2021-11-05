const token = $("meta[name='_csrf']").attr('content');
const header = $("meta[name='_csrf_header']").attr('content');
const postAnswerUrl = `/api/answer`;
const size = 2;
var currentPage = 1;

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

$(document).ready(function() {
    getListAnswers(currentPage, size);

    $('#showMoreBtn').click(() => {
        currentPage = currentPage + 1;
        getListAnswers(currentPage, size);
    })

    $(document).on('click', '#postBtn', function(e) {
        e.preventDefault();
        var content = $('#exampleFormControlTextarea3').val();
        if (content != '') {
            answerTheQuestion(content, questionId);
        } else {
            $('#exampleFormControlTextarea3').val('');
        }
    });

    $(document).on('click', '#cancelBtn', function(e) {
        $('#exampleFormControlTextarea3').val('');
    })
});



function  getListAnswers(currentPage, size) {
    const getListAnswersUrl = `/api/answer/getByIdQuestion/${questionId}?size=${size}&page=${currentPage}`;
    $.ajax({
        type: "get",
        url: getListAnswersUrl,
        header: {
            [header]: token,
        },
        processData: false,
        contentType: false,
        success: function (data) {
            const answersDiv = $('#answersDiv');
            var newAnswer = '';
            data.content.map((answer) => {
                newAnswer = newAnswer + template(answer);
            });
            if (newAnswer == '') {
                newAnswer = `<p>You have reached the end</p>`;
                
                $('#showMoreBtn').attr('disabled', true);
                $('.showMore').prepend(newAnswer);
            } else {
                answersDiv.append(newAnswer);
            }
            
        },
        error: function(error) {
            console.log(error);
        }
    });
}


function answerTheQuestion(content, questionId) { 
    var data = {
        questionId: questionId,
        content: content,
    };
    $.ajax({
        type: "post",
        url: `/api/answer`,
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        headers: {
            [header]: token,
        },
        traditional: true,
        // dataType: "dataType",
        success: function (data) {
            const answersDiv =  $('#answersDiv');
            console.log(data);
            const newAnswer = template(data);
            if (answersDiv.children().length >= 2) {
                answersDiv.children().last().remove();
            } 
            answersDiv.prepend(newAnswer);
            $('#showMoreBtn').attr('disabled', false);
            $('.showMore').children().eq(0).remove();
            $('#exampleFormControlTextarea3').val('');
        },
        error: function (error) {
            console.log(error);
        }
    });
}




function template(answer) {
    // console.log(answer.answerer.photoUrl);
    return `<div class="question"> 
            <div class="question-as">
                <div>
                    <img
                        src=${answer.answerer.photoUrl}
                        class="author-img avatar"
                        alt="author"
                    />
                    <div class="ten">            
                        <h6 class="author-name">${answer.answerer.name}</h6>
                        <p class="date" >
                            Added an answer on  +${answer.createAt}
                        </p>
                    </div>
                    <p class="question-detail">
                    ${answer.content}
                    </p>
                </div>
                <div style="margin-left: 58px">
                    <div class="like-dis">
                        <i class="icon-question fas fa-caret-square-up"></i>
                        <span>${answer.upVoteCount}</span>
                        <i class="icon-question-down fas fa-caret-square-down"></i>
                        <span>${answer.downVoteCount}</span>
                    </div>
                </div>
            </div>
        </div>`;
}
