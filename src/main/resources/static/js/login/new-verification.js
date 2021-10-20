const requestVerificationUrl = '/api/account/request-resend-verify-email';

const requestVerificationSuccessUrl = '/register-success';

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

  $.post(requestVerificationUrl, formData, function (data) {
    $.notify('Successfully sent verification email.\nPlease check your email', {
      position: 'top center',
      className: 'success',
    });
    setTimeout(() => {
      window.location.href = requestVerificationSuccessUrl;
    }, 1000);
  }).fail(function (data) {
    $.notify(data.responseText, {
      position: 'top center',
      className: 'warn',
    });

    console.log(data);
  });
}
