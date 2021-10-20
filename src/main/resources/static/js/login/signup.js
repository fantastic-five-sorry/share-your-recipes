const registerUrl = '/api/account/signup';
const goToSuccessPageAfter = 500;
const registerSuccessUrl = '/register-success';

const emailExistUrl = '/api/account/exists-email';

var token = $("meta[name='_csrf']").attr('content');
var header = $("meta[name='_csrf_header']").attr('content');

console.log(header, token);
$(document).ready(function () {
  var uri = window.location.toString();
  if (uri.indexOf('?') > 0) {
    var clean_uri = uri.substring(0, uri.indexOf('?'));
    window.history.replaceState({}, document.title, clean_uri);
  }
  $('form').submit(function (event) {
    register(event);
  });

  $('#confirmPassword').keyup(function () {
    if ($('#password').val() != $('#confirmPassword').val()) {
      $('#globalError').show().html('not match');
    } else {
      $('#globalError').html('').hide();
    }
  });
  var timeout;
  $('#email').keyup(function (e) {
    if (validateEmail($('#email').val())) {
      clearTimeout(timeout);
      timeout = setTimeout(function () {
        isEmailExist();
      }, 200);
    }
    // isEmailExist();
  });
  //  $('#password').pwstrength(options);
});

function getFormData($form) {
  var unindexed_array = $form.serializeArray();
  var indexed_array = {};

  $.map(unindexed_array, function (n, i) {
    indexed_array[n['name']] = n['value'];
  });

  return indexed_array;
}

function register(event) {
  console.log('sign up click');
  event.preventDefault();
  $('.alert').html('').hide();
  $('.error-list').html('');

  // check pw match
  if ($('#password').val() != $('#confirmPassword').val()) {
    // console.log($('#password').val(), )
    $('#globalError').show().html(message.passwordNotMatch);
    return;
  }

  // post form data
  // var formData = $('form').serialize();
  var formData = getFormData($('form'));

  $.ajax({
    type: 'post',
    url: registerUrl,
    data: JSON.stringify(formData),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: token,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $('#globalError').show().html('ok');
        $.notify('Successfully signup new account.', {
          position: 'top center',
          className: 'success',
        });
        setTimeout(
          () => (window.location.href = registerSuccessUrl),
          goToSuccessPageAfter
        );
      }
    },
    error: function (error) {
      $.notify('Error', {
        position: 'top center',
        className: 'warn',
      });
    },
  });
}

function isEmailExist() {
  var fd = new FormData();
  fd.append('email', $('#email').val());
  $.ajax({
    url: emailExistUrl,
    data: fd,
    processData: false,
    contentType: false,
    type: 'POST',
    success: function (data) {
      $('#globalError').show().html('Email approved');
      // setTimeout(() => {
      //   $('#globalError').hide();
      // }, 1000);
    },
    error: function (data) {
      $('#globalError').show().html('Email already existed');
    },
  });
}

function validateEmail(email) {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(String(email).toLowerCase());
}
