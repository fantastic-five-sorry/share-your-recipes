console.log('hello rs pw');

const resetPasswordUrl = '/api/account/reset-password';
const validateTokenUrl = '/api/account/validate-forgot-token';

const resetPasswordSuccessUrl = '/logout';

const missTokenUrl = '/account/forgot-password';

const goToSuccessPageAfter = 3000;

var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

var token = null;
$(document).ready(function () {
  var uri = window.location.toString();

  token = getUrlParameter('token');
  if (!token) window.location.href = missTokenUrl;
  if (uri.indexOf('?') > 0) {
    var clean_uri = uri.substring(0, uri.indexOf('?'));
    window.history.replaceState({}, document.title, clean_uri);
  }
  // validate token
  validateToken(token);

  $('form').submit(function (event) {
    event.preventDefault();
    requestEmail(event, token);
  });
});

const getUrlParameter = function getUrlParameter(sParam) {
  var sPageURL = window.location.search.substring(1),
    sURLVariables = sPageURL.split('&'),
    sParameterName,
    i;

  for (i = 0; i < sURLVariables.length; i++) {
    sParameterName = sURLVariables[i].split('=');

    if (sParameterName[0] === sParam) {
      return typeof sParameterName[1] === undefined
        ? true
        : decodeURIComponent(sParameterName[1]);
    }
  }
  return false;
};

function requestEmail(event, token) {
  console.log('sign up click');
  event.preventDefault();
  $('.alert').html('').hide();
  $('.error-list').html('');

  // check pw match
  if ($('#newPassword').val() != $('#confirmNewPassword').val()) {
    // console.log($('#password').val(), )
    $('#globalError').show().html('not match');
    return;
  }

  // post form data
  // var formData = $('form').serialize();
  var formData = getFormData($('form'));
  formData['token'] = token;
  console.log(formData);
  var token = $("meta[name='_csrf']").attr('content');
  var header = $("meta[name='_csrf_header']").attr('content');

  $.ajax({
    type: 'post',
    url: resetPasswordUrl,
    data: JSON.stringify(formData),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        setTimeout(
          () => (window.location.href = resetPasswordSuccessUrl),
          goToSuccessPageAfter
        );
        $('#globalError').show().html('Success');
      }
    },
    error: function (error) {
      $('#globalError').show().html('Error');
      console.log(error.responseText);
    },
  });
}

function getFormData($form) {
  var unindexed_array = $form.serializeArray();
  var indexed_array = {};

  $.map(unindexed_array, function (n, i) {
    indexed_array[n['name']] = n['value'];
  });

  return indexed_array;
}

function validateToken(token) {
  var fd = new FormData();
  fd.append('token', token);

  $.ajax({
    url: validateTokenUrl,
    data: fd,
    processData: false,
    contentType: false,
    type: 'POST',
    success: function (data) {
      alert(data);
    },
    error: function (data) {
      alert(data.responseText);
      setTimeout(
        () => (window.location.href = missTokenUrl),
        goToSuccessPageAfter
      );
    },
  });
}