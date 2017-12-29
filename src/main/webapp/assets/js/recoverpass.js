$(document).ready(function () {
    $('.parallax').parallax();
    $('.modal').modal({
        dismissible: false
    });
});
$("#recoverTrigger").click(function () {
    $.ajax({
        data: "accion=mandarmail&" + $("#formRecover").serialize(),
        url: '/recuperarpass1',
        type: 'post',
        beforeSend: function () {
            $('#loadingRecover').modal('open');
        },
        success: function (data) {
            var info = JSON.parse(data);
            if (info['success'])
            {
                Materialize.toast('<span>Email enviado correctamente.</span>', 5000, 'rounded');
            }
            else
            {
                Materialize.toast('<span>Ha ocurrido un error al recuperar la cuenta: ' + info["reason"] + '</span>', 5000, 'rounded');
            }
        },
        error: function(e)
        {
            Materialize.toast("Ha ocurrido un error al procesar la petición", 4000);
        },
        complete: function(c)
        {
            $('#loadingRecover').modal('close');
        }
    });
});