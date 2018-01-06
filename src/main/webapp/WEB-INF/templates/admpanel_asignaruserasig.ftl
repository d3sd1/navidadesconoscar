<!DOCTYPE html>
<html>
    <head>
        <title>Inicio</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="/assets/css/materialize.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        </head>
<!--
(MIGUEL: POR DEFECTO MUESTRA SOLO PLANTILLA, URL: /?accion=dameusuarios&iniciousuarios=*&finusuarios=*
URL: /?accion=relacionarlos&id_alumno=15&ids_asignaturas=*,*,*)
Hace falta pantalla para asociar alumnos y asignaturas, Relacion N-N, se paginaran los alumnos.
poner listado de alumnos como en un crud, un boton, se abre una emergente, y se muestran las asignaturas en la emergente con las casillas de seleccion http://materializecss.com/forms.html-->
    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="/assets/images/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <!--<li><a href="#alumnos">(PROFE) Alumnos (crud)</a></li>
                        <li><a href="asignaturas">(PROFE) Notas de alumnos</a></li>
                        <li><a href="notas">(SUPERADMIN) Profesores (CRUD)</a></li>
                        <li><a href="notas">(SUPERADMIN) Asignaturas + cursos (CRUD)</a></li>-->
                        <#if rango == "administrador">
                        <li><a href="/panel/administrador/usuarios">Control de usuarios</a></li>
                        <li><a href="/panel/administrador/asignaturas">Control de asignaturas</a></li>
                        <li class="active"><a href="/panel/administrador/userplusasig">Asignar asignatura a usuario</a></li>
                        <li><a href="/panel/administrador/teaplusasig">Asignar asignatura a profesor</a></li>
                        </#if>
                        <li><a href="/panel/change_password">Cambiar contraseña</a></li>
                        <li><a href="/logout">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://www.pressdigital.es/multimedia/images/Alumnos_en_clase.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <div class="col s12">
                    <a class="waves-effect waves-light btn right"><i class="material-icons right">person_add</i>Agregar usuario</a>
                </div>
                <div class="col s12">

                    <table class="responsive-table centered highlight bordered scrollspy initdatatable" id="users">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Fecha de nacimiento</th>
                                <th>Mayor de edad</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody>
                        <!-- usuarios aqui -->
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
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="/assets/js/panel_start.js"></script>
    </html>