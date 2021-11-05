const token = $("meta[name='_csrf']").attr('content');
const header = $("meta[name='_csrf_header']").attr('content');
var currentPage = 1;


$(document).ready(function() {
   
    getListQuestion(currentPage, 3);

    $('#showMoreBtn').click(() => {
        currentPage = currentPage + 1;
        getListQuestion(currentPage, 3);
    })

    
});


function getListQuestion(currentPage, size) {
    const getListQuestionUrl = `/api/question/getQuestionApproved?size=${size}&page=${currentPage}`;
    $.ajax({
        type: "get",
        url: getListQuestionUrl,
        header: {
            [header]: token,
        },
        processData: false,
        contentType: false,
        success: function(data) {
            const questionDiv = $('#questionDiv');
            var newQuestion = '';
            data.content.map((question) => {
                newQuestion = newQuestion + templateQuestion(question);
            });
            if (newQuestion == '') {
                newQuestion = `<p>You have reached the end</p>`;

                $('#showMoreBtn').attr('disabled', true);
                $('.showMore').prepend(newQuestion);
            } else {
                questionDiv.append(newQuestion);
            }
        },
        error: function(error) {
            console.log(error);
        }
    
    });
}


function templateQuestion(question) {
    
    return `<div class="question">
        <div class="question-as">
        <div>
            <img
            src=${question.creator.photoUrl}
            class="author-img avatar"
            alt="author"
            />
            <div class="ten">
            
            <h6 class="author-name">${question.creator.name}</h6>
            <p class="date">
                Added an question on ${question.createAt}
            </p>
            </div>
            <p class="question-detail">
            ${question.content}
            </p>
        </div>
        <div style="margin-left: 58px">
            <div class="like-dis">
            <i class="icon-question fas fa-caret-square-up"></i>
            <span>
                ${question.upVoteCount}
            </span>
            <i class="icon-question-down fas fa-caret-square-down"></i>
            <span>
                ${question.downVoteCount}
            </span>
            </div>
            <div class="like-dis">
            <i class="icon-question reply fas fa-reply"></i>
            <a href="/answer/${question.slug}"><p class="reply">anwer</p></a>
            </div>
        </div>
        </div>
    </div>`;
}
