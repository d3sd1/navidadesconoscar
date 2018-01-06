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

    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="/assets/images/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <#if rango == "administrador">
                        <li><a href="/panel/administrador/usuarios">Control de usuarios</a></li>
                        <li><a href="/panel/administrador/asignaturas">Control de asignaturas</a></li>
                        <li><a href="/panel/administrador/userplusasig">Asignar asignatura a usuario</a></li>
                        <li><a href="/panel/administrador/teaplusasig">Asignar asignatura a profesor</a></li>
                        </#if>
                        <#if rango == "profesor">
                        <li><a href="/panel/profesor/notas">Notas de alumnos</a></li>
                        </#if>
                        <#if rango == "usuario">
                        <li><a href="/panel/usuario/notas">Mis notas</a></li>
                        </#if>
                        <li class="active"><a href="/panel/change_password">Cambiar contraseña</a></li>
                        <li><a href="/logout">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="https://www.esan.edu.pe/conexion/actualidad/2017/04/10/principal-seguridad-informatica.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <form class="col s12 center-align">
                    <div class="row">
                        <div class="input-field col s12">
                            <input placeholder="**********" name="old_pass" type="password" class="validate">
                            <label for="first_name">Contraseña actual</label>
                        </div>
                        <div class="input-field col s6">
                            <input placeholder="**********" name="new_pass1" type="password" class="validate">
                            <label for="first_name">Contraseña nueva</label>
                        </div>
                        <div class="input-field col s6">
                            <input placeholder="**********" name="new_pass2" type="password" class="validate">
                            <label for="first_name">Repite la nueva contraseña</label>
                        </div>
                        <a class="waves-effect waves-light btn" id="changePassTrigger"><i class="material-icons right">arrow_forward</i>Cambiar contraseña</a>
                    </form>
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
        <script src="/assets/js/panel_start.js"></script>
        <script src="/assets/js/change_password.js"></script>
    </html>