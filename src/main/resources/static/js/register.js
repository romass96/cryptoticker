$('#register').click(function() {
    $('.loader').show();
    removeValidations();
    register();
});

function register() {
    let userDTO = {
        firstName: $('#firstName').val(),
        lastName: $('#lastName').val(),
        email: $('#email').val(),
        password: $('#password').val(),
        confirmPassword: $('#confirmPassword').val()
    };
    let csrfToken = $("meta[name='_csrf']").attr("content");
    let csrfHeader = $("meta[name='_csrf_header']").attr("content");
    let headers = {};
    headers[csrfHeader] = csrfToken;

    $.ajax({
      type: "POST",
      url: "/registration",
      data: JSON.stringify(userDTO),
      contentType: "application/json",
      dataType: 'json',
      headers: headers
    }).done(function(){
        $('.loader').hide();
        window.location.href = "/successRegistration";
    }).fail(function(jqXHR) {
        $('.loader').hide();
        if (jqXHR.status == 400) {
                let errorMessages = createErrorMessagesObject(jqXHR);
                $('.form-control').each(function(index, element) {
                    let messages = errorMessages[element.id];
                    if (messages.length) {
                        element.classList.add('is-invalid');
                        messages.forEach(function(message) {
                            createInvalidFeedback(message, $(element));
                        });
                    } else {
                        element.classList.add('is-valid');
                    }
                });
        }
    });
}

function createInvalidFeedback(message, elementBefore) {
    let div = document.createElement('div');
    div.innerHTML = message;
    div.classList.add('invalid-feedback');
    $(div).insertAfter(elementBefore);
}

function removeValidations() {
        $('.form-control').each(function(index, element) {
            element.classList.remove('is-valid', 'is-invalid');
        });
        $('.invalid-feedback').remove();
}

function createErrorMessagesObject(jqXHR) {
    let errorMessages = {};
    $('.form-control').each((index, element) => errorMessages[element.id] = []);
    jqXHR.responseJSON.forEach(function(error) {
        errorMessages[error.field].push(error.code);
    });
    return errorMessages;
}