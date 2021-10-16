const registerUrl = '/api/account/signup';

const registerSuccessUrl = '/register-success';

$(document).ready(function () {
  var uri = window.location.toString();
  if (uri.indexOf('?') > 0) {
    var clean_uri = uri.substring(0, uri.indexOf('?'));
    window.history.replaceState({}, document.title, clean_uri);
  }
  $('form').submit(function (event) {
    register(event);
  });

  $(':password').keyup(function () {
    if ($('#password').val() != $('#confirmPassword').val()) {
      $('#globalError').show().html(/*[[#{PasswordMatches.user}]]*/);
    } else {
      $('#globalError').html('').hide();
    }
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
  var token = $("meta[name='_csrf']").attr('content');
  var header = $("meta[name='_csrf_header']").attr('content');
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
        window.location.href = registerSuccessUrl;
      }
    },
    error: function (error) {
      console.log(error.responseText);
    },
  });

  // $.post('/' + 'api/account/signup', formData, function (data) {
  //   console.log(data);
  //   // if (data.message == 'success') {
  //   //   window.location.href = serverContext + 'successRegister.html';
  //   // }
  // }).fail(function (data) {
  //   if (data.responseJSON.error.indexOf('MailError') > -1) {
  //     window.location.href = serverContext + 'emailError.html';
  //   } else if (data.responseJSON.error == 'UserAlreadyExist') {
  //     $('#emailError').show().html(data.responseJSON.message);
  //   } else if (data.responseJSON.error.indexOf('InternalError') > -1) {
  //     window.location.href =
  //       serverContext + 'login?message=' + data.responseJSON.message;
  //   } else {
  //     var errors = $.parseJSON(data.responseJSON.message);
  //     $.each(errors, function (index, item) {
  //       if (item.field) {
  //         $('#' + item.field + 'Error')
  //           .show()
  //           .append(item.defaultMessage + '<br/>');
  //       } else {
  //         $('#globalError')
  //           .show()
  //           .append(item.defaultMessage + '<br/>');
  //       }
  //     });
  //   }
  // });
}
