const requestResetEmailUrl = '/api/account/request-forgot-password-email';

const requestEmailSuccessUrl = '/request-success';

const goToSuccessPageAfter = 1000;

var resetPwToken = $("meta[name='_csrf']").attr('content');
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
  event.preventDefault();
  const formData = $('form').serialize();

  $.post(requestResetEmailUrl, formData, function (data) {
    $.notify(
      'Successfully sent reset password email.\nPlease check your email',
      {
        position: 'top center',
        className: 'success',
      }
    );
    setTimeout(() => {
      window.location.href = requestEmailSuccessUrl;
    }, 1000);
  }).fail(function (data) {
    $.notify('Email not found', {
      position: 'top center',
      className: 'warn',
    });

    console.log(data);
  });
}
