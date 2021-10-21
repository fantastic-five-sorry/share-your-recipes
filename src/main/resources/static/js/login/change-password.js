const changePasswordUrl = '/api/account/change-password';

const changePasswordSuccessUrl = '/logout';

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
  var timeout;
  $('#confirmNewPassword').keyup(function () {
    clearTimeout(timeout);
    timeout = setTimeout(function () {
      if ($('#newPassword').val() != $('#confirmNewPassword').val()) {
        $('#globalError').show().html('not match');
      } else {
        $('#globalError').html('').hide();
      }
    }, 250);
  });
});

function getFormData($form) {
  var unindexed_array = $form.serializeArray();
  var indexed_array = {};

  $.map(unindexed_array, function (n, i) {
    indexed_array[n['name']] = n['value'];
  });

  return indexed_array;
}

function requestEmail(event) {
  console.log('change pw click');
  event.preventDefault();
  $('.alert').html('').hide();
  $('.error-list').html('');

  // check pw match
  if ($('#newPassword').val() != $('#confirmNewPassword').val()) {
    // console.log($('#password').val(), )
    $('#globalError').show().html('not match');
    return;
  }

  var formData = getFormData($('form'));

  $.ajax({
    type: 'post',
    url: changePasswordUrl,
    data: JSON.stringify(formData),
    contentType: 'application/json; charset=utf-8',
    headers: {
      [header]: resetPwToken,
    },
    traditional: true,
    success: function (data, textStatus, xhr) {
      if (xhr.status == 200) {
        $('#globalError').show().html('ok');
        $.notify(
          'Successfully changed password. Automatically logout after 2 seconds',
          {
            position: 'top center',
            className: 'success',
          }
        );
        setTimeout(
          () => (window.location.href = changePasswordSuccessUrl),
          goToSuccessPageAfter
        );
      }
    },
    error: function (error) {
      $.notify(error.responseText, {
        position: 'top center',
        className: 'info',
      });
      $('#globalError').show().html(error.responseText);
    },
  });
}
