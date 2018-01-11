<!DOCTYPE html>
<html>
    <head>
        <title>Ver notas del curso</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"  media="screen,projection"/>

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        <style>
            .group {
                background-color: #ddd !important;
            }
        </style>
        </head>
    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="https://image.ibb.co/fXxOcG/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li class="active"><a href="/panel/profesor/notas_curso">Ver notas mis cursos</a></li>
                        <li><a href="/panel/profesor/notas_alumnado">Ver notas de mis alumnos</a></li>
                        <li><a href="/panel/profesor/modificar_notas">Cambiar notas</a></li>
                        <li><a href="/panel/cambiar_clave">Cambiar contraseña</a></li>
                        <li><a href="/desconectar">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://www.pressdigital.es/multimedia/images/Alumnos_en_clase.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <div class="col s12" id="actual_page">

                    <table class="responsive-table centered highlight bordered scrollspy initdatatable" id="notas">
                        <thead>
                            <tr>
                                <th>Nombre Alumno</th>
                                <th>Nombre asignatura</th>
                                <th>Nota</th>
                                </tr>
                            </thead>

                            <tbody>
                                 <#list notas as nota>
                                    <tr>
                                        <td>${nota.getAlumno().getNombre()}</td>
                                        <td>${nota.getAsignatura().getNombre()}</td>
                                        <td>${nota.getNota()}</td>
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
        <script>
            var $dataTable;
            $(document).ready(function () {
                $dataTable = $('#notas').DataTable({
                    "language": {
                        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
                    },
                    "order": [[ 2, 'asc' ]],
                    "columnDefs": [
                        { "visible": false, "targets": 1 }
                    ],
                    "drawCallback": function ( settings ) {
                        var api = this.api();
                        var rows = api.rows( {page:'current'} ).nodes();
                        var last=null;

                        api.column(1, {page:'current'} ).data().each( function ( group, i ) {
                            if ( last !== group ) {
                                $(rows).eq( i ).before(
                                    '<tr class="group"><td colspan="5">'+group+'</td></tr>'
                                );

                                last = group;
                            }
                        } );
                    }
                });
                $('#notas tbody').on( 'click', 'tr.group', function () {
                    var currentOrder = $dataTable.order()[0];
                    if ( currentOrder[0] === 1 && currentOrder[1] === 'asc' ) {
                        $dataTable.order( [ 1, 'desc' ] ).draw();
                    }
                    else {
                        $dataTable.order( [ 1, 'asc' ] ).draw();
                    }
                } );
                $('.carousel').carousel();
                $('.parallax').parallax();
                $('select').material_select();
            });
                
        </script>
    </html>