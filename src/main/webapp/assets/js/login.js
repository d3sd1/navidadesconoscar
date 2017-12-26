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
        success: function (data) {
            var info = JSON.parse(data);
            if (info['success'])
            {
                Materialize.toast('<span>Te has conectado correctamente. Estás siendo redireccionado...</span>', 5000, 'rounded');
                window.setTimeout(function () {
                    window.location.href = "/panel";
                }, 3000);
            }
            else
            {
                Materialize.toast('<span>Ha ocurrido un error al conectar: ' + info["reason"] + '</span>', 5000, 'rounded');
            }
        },
        error: function(e)
        {
            Materialize.toast("Ha ocurrido un error al procesar la petición", 4000);
        },
        complete: function(c)
        {
            $('#loadingLogin').modal('close');
        }
    });
});