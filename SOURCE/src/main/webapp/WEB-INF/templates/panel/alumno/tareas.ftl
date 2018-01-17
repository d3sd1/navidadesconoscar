<!DOCTYPE html>
<html>
    <head>
        <title>Mis tareas</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        </head>

    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="https://image.ibb.co/fXxOcG/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right">
                        <li class="active"><a href="/panel/alumno/tareas">Mis tareas</a></li>
                        <li><a href="/panel/alumno/notas">Mis notas</a></li>
                        <li><a href="/panel/cambiar_clave">Cambiar contraseña</a></li>
                        <li><a href="/desconectar">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://www.eljurista.eu/wp-content/uploads/2014/09/diego.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <div class="col s12">

                    <table class="responsive-table centered highlight bordered scrollspy initdatatable" id="notas">
                        <thead>
                            <tr>
                                <th>Nombre de la tarea</th>
                                <th>Asignatura</th>
                                <th>Fecha límite de entrega</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                        <#list tareas as tarea>
                            <tr id="tarea-${tarea.getId_tarea()}">
                                <td>${tarea.getNombre_tarea()}</td>
                                <td>${tarea.getAsignatura().getNombre()}</td>
                                <td>${tarea.getFecha_entrega()}</td>
                                <#if tarea.isCompletada()>
                                <td><a class="waves-effect waves-light btn teal lighten-3" style="cursor: default"><i class="material-icons left">check</i> Completada</a></td>
                                <#else>
                                <td><button class="btn waves-effect waves-light blue lighten-1" onclick="markAsComplete(${tarea.getId_tarea()})"><i class="material-icons right">send</i>Marcar como completada</button></td>
                                </#if>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <footer class="page-footer cyan accent-4">
            <div class="container">
                <div class="row">
                    <div class="col l6 s12">
                        <h5 class="white-text">CRUD Alumnos, asignaturas y notas</h5>
                        <p class="grey-text text-lighten-4">Creado para el IES Tierno Galván, Madrid.</p>
                        </div>
                    </div>
                </div>
            <div class="footer-copyright">
                <div class="container">
                    © 2017 Andrei García Cuadra
                    </div>
                </div>
            </footer>
        <div id="loading" class="modal">
            <div class="modal-content">
                <h1 class="center-align">Cargando...</h1>
                <div class="progress">
                    <div class="indeterminate"></div>
                    </div>
                </div>
        </div>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script>
            var $dataTable;
            $(document).ready(function(){
                $dataTable = $('#notas').DataTable({
                    "language": {
                        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
                    }
                });
                $('.carousel').carousel();
                $('.parallax').parallax();
                $('select').material_select();
                $('.modal').modal({
                    dismissible: false
                });
            });
            function markAsComplete(idTarea)
            {
                $.ajax({
                    data: "accion=completar&id_tarea=" + idTarea,
                    url: '/panel/alumno/tareas',
                    type: 'post',
                    beforeSend: function () {
                        $('#loading').modal('open');
                    },
                    success: function (data) {
                        var info = JSON.parse(data);
                        if (info['success'])
                        {
                            Materialize.toast('<span>¡Has marcado la tarea como completada!</span>', 5000, 'rounded');
                            var nombre = $('#tarea-' + idTarea).eq(0).children("td").eq(0).text(),
                                asignatura = $('#tarea-' + idTarea).eq(0).children("td").eq(1).text(),
                                fecha = $('#tarea-' + idTarea).eq(0).children("td").eq(2).text();
                            $dataTable.row('#tarea-' + idTarea).remove().draw();
                            $dataTable.row.add( [
                                nombre,
                                asignatura,
                                fecha,
                                '<a class="waves-effect waves-light btn teal lighten-3" style="cursor: default"><i class="material-icons left">check</i> Completada</a>'
                                 ] ).draw().node();
                        }
                        else
                        {
                            Materialize.toast('<span>Ha ocurrido un error al marcar la tarea: ' + info["reason"] + '</span>', 5000, 'rounded');
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
    </script>
    </html>