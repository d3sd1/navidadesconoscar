$(document).ready(function () {
    $('.parallax').parallax();
});
$("#loginTrigger").click(function () {
    $.ajax({
        data: $("#formLogin").serialize(),
        url: 'ejemplo_ajax_proceso.php',
        type: 'post',
        beforeSend: function () {
            $("#resultado").html("Procesando, espere por favor...");
        },
        success: function (response) {
            $("#resultado").html(response);
        }
    });
});