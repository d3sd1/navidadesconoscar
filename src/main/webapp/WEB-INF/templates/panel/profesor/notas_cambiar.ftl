<!DOCTYPE html>
<html>
    <head>
        <title>Cambiar notas del alumno</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"  media="screen,projection"/>

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        </head>
    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="https://image.ibb.co/fXxOcG/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="/panel/profesor/notas_curso">Ver notas mis cursos</a></li>
                        <li><a href="/panel/profesor/notas_alumnado">Ver notas de mis alumnos</a></li>
                        <li class="active"><a href="/panel/profesor/modificar_notas">Cambiar notas</a></li>
                        <li><a href="/panel/cambiar_clave">Cambiar contraseña</a></li>
                        <li><a href="/desconectar">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="https://diariodigital.uja.es/sites/default/files/imagen/2015-09/np_Alumnado%20Facultad%20de%20Humanidades%20y%20Ciencias%20de%20la%20Educaci%C3%B3n.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <div class="col s12" id="actual_page">

                    <table class="responsive-table centered highlight bordered scrollspy initdatatable" id="notas">
                        <thead>
                            <tr>
                                <th>Alumno</th>
                                <th>Nota</th>
                                <th>Curso</th>
                                <th>Asignatura</th>
                                <th></th>
                            </tr>
                            </thead>

                            <tbody>
                                 <#list notas as nota>
                                    <tr>
                                        <td>${nota.getAlumno().getNombre()}</td>
                                        <td><input id="alumno-${nota.getAlumno().getId()}-asg-${nota.getAsignatura().getId()}-course-${nota.getCurso().getId()}-nota" value="${nota.getNota()}" type="number" min="0" max="10"></td>
                                        <td>${nota.getCurso().getNombre()}</td>
                                        <td>${nota.getAsignatura().getNombre()}</td>
                                        <td><a class="waves-effect waves-light btn" onclick="changeGrade(${nota.getAlumno().getId()},${nota.getAsignatura().getId()},${nota.getCurso().getId()})">Guardar</a></td>
                                    </tr>
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
        <script>
            var $dataTable;
            $(document).ready(function () {
                $dataTable = $('#notas').DataTable({
                    "language": {
                        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
                    },
                    "order": [[ 2, 'asc' ]],
                    "columnDefs": [
                        { "visible": false, "targets": [2,3] }
                    ],
                    "drawCallback": function ( settings ) {
                        var api = this.api();
                        var rows = api.rows( {page:'current'} ).nodes();
                        var last=null;

                        api.column(2, {page:'current'} ).data().each( function ( group, i ) {
                            if ( last !== group ) {
                                $(rows).eq( i ).before(
                                    '<tr class="group blue lighten-3"><td colspan="5">'+group+'</td></tr>'
                                );

                                last = group;
                            }
                        } );
                        api.column(3, {page:'current'} ).data().each( function ( group, i ) {
                            if ( last !== group ) {
                                $(rows).eq( i ).before(
                                    '<tr class="group blue lighten-4"><td colspan="5">'+group+'</td></tr>'
                                );

                                last = group;
                            }
                        } );
                    }
                });
                $('#notas tbody').on( 'click', 'tr.group', function () {
                    var currentOrder = $dataTable.order()[0];
                    if ( currentOrder[0] === 2 && currentOrder[2] === 'asc' ) {
                        $dataTable.order( [ 2, 'desc' ] ).draw();
                    }
                    else {
                        $dataTable.order( [ 2, 'asc' ] ).draw();
                    }
                } );
                
                $('.carousel').carousel();
                $('.parallax').parallax();
                $('select').material_select();
                $('.modal').modal({
                    dismissible: false
                });
            });
            function changeGrade(idAlumno, idAsig, idCourse)
            {
                var nota = $("#alumno-" + idAlumno + "-asg-" + idAsig + "-course-" + idCourse + "-nota").val();
                if(nota > 10 || nota < 0)
                {
                    Materialize.toast('<span>La nota debe de estar comprendida entre 0 y 10</span>', 5000, 'rounded');
                }
                else
                {
                    $.ajax({
                        data: "accion=modificar&id_alumno=" + idAlumno + "&id_asignatura=" + idAsig + "&nota=" + nota,
                        url: '/panel/profesor/modificar_notas',
                        type: 'post',
                        beforeSend: function () {
                            $('#loading').modal('open');
                        },
                        success: function (data) {
                            var info = JSON.parse(data);
                            if (info['success'])
                            {
                                Materialize.toast('<span>Nota asignada correctamente.</span>', 5000, 'rounded');
                            }
                            else
                            {
                                Materialize.toast('<span>Ha ocurrido un error al asignar la nota: ' + info["reason"] + '</span>', 5000, 'rounded');
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
            }
        </script>
    </html>