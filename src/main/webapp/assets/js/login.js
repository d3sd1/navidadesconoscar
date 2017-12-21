$(document).ready(function () {
    $('.parallax').parallax();
    $('.modal').modal({
        dismissible: true
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
            console.log(response);
            var info = JSON.parse(response);
            Materialize.toast(info.reason, 4000);
            $('#loadingLogin').modal('close');
        }
    });
});