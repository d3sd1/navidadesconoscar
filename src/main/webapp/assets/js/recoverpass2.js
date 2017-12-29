$(document).ready(function () {
    $('.parallax').parallax();
    $('.modal').modal({
        dismissible: false
    });
});
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};
$("#recoverTrigger").click(function () {
    if($("#newpass1").val() != null && $("#newpass2").val() != null && $("#newpass1").val() != "" && $("#newpass1").val() == $("#newpass2").val())
    {
        $.ajax({
            data: "accion=restaurar&codigo=" + getUrlParameter('codigo') + "&nuevacontra=" + $("#newpass1").val(),
            url: '/recuperarpass2',
            type: 'post',
            beforeSend: function () {
                $('#loadingRecover').modal('open');
            },
            success: function (data) {
                var info = JSON.parse(data);
                if (info['success'])
                {
                    Materialize.toast('<span>Contraseña cambiada correctamente.</span>', 5000, 'rounded');
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
    }
    else
    {
        Materialize.toast('<span>Las contraseñas no coinciden.</span>', 5000, 'rounded');
    }
});