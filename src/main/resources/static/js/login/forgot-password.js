const requestResetEmailUrl = '/api/account/request-forgot-password-email';

const requestEmailSuccessUrl = '/logout';

const goToSuccessPageAfter = 3000;

var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

$(document).ready(function () {
  var uri = window.location.toString();
  if (uri.indexOf('?') > 0) {
    var clean_uri = uri.substring(0, uri.indexOf('?'));
    window.history.replaceState({}, document.title, clean_uri);
  }
  $('form').submit(function (event) {
    requestEmail(event);
  });
});

function requestEmail(event) {
  console.log('forgot pw click');
  event.preventDefault();
  const formData = $('form').serialize();

  $.post(requestResetEmailUrl, formData, function (data) {
    console.log(data);
    $.notify('Successfully send reset password.\nPlease check your email', {
      position: 'top center',
      className: 'success',
    });
    // if (data.message == 'success') {
    //   window.location.href = serverContext + 'successRegister.html';
    // }
  }).fail(function (data) {
    $.notify('Email not found', {
      position: 'top center',
      className: 'warn',
    });

    console.log(data);
  });
}
