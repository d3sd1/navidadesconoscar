$(document).ready(function () {
    $('.parallax').parallax();
    $('.modal').modal({
        dismissible: false
    });
});
$("#loginTrigger").click(function () {
    $.ajax({
        data: "accion=login&" + $("#formLogin").serialize(),
        url: '/login',
        type: 'post',
        beforeSend: function () {
            $('#loadingLogin').modal('open');
        },
        success: function (response) {
            var info = JSON.parse(response);
            Materialize.toast(info.reason, 4000);
        },
        error: function()
        {
            Materialize.toast("Ha ocurrido un error al procesar la petición", 4000);
        },
        complete: function()
        {
            $('#loadingLogin').modal('close');
        }
    });
});