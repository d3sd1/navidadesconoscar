$(document).ready(function () {
    $('.parallax').parallax();
    $('.modal').modal({
        dismissible: false
    });
});
$("#changePassTrigger").click(function () {
    var oldPass = $("input[name='old_pass']").val(),
        newpass1 = $("input[name='new_pass1']").val(),
        newpass2 = $("input[name='new_pass2']").val();
        
    if(oldPass === "" || newpass1 === "")
    {
        Materialize.toast("Debes introducir una contraseña.", 4000);
    }
    else if(newpass1 !== newpass2)
    {
        Materialize.toast("Las nuevas contraseñas no coinciden.", 4000);
    }
    else
    {
        $("input[name='old_pass']").val("");
        $("input[name='new_pass1']").val("");
        $("input[name='new_pass2']").val("");
        $.ajax({
            data: "accion=cambiarpass&passactual=" + oldPass + "&nuevapass=" + newpass1,
            url: '/panel/change_password',
            type: 'post',
            beforeSend: function () {
                $('#loading').modal('open');
            },
            success: function (data) {
                var info = JSON.parse(data);
                if (info['success'])
                {
                    Materialize.toast('<span>Contraseña cambiada correctamente.</span>', 5000, 'rounded');
                    window.setTimeout(function () {
                        close();
                    }, 3000);
                }
                else
                {
                    Materialize.toast('<span>Se ha producido un error al cambiar la contraseña: ' + info["reason"] + '</span>', 5000, 'rounded');
                }
            },
            error: function(e)
            {
                Materialize.toast("Ha ocurrido un error al procesar la petición.", 4000);
            },
            complete: function(c)
            {
                $('#loading').modal('close');
            }
        });
    }
});