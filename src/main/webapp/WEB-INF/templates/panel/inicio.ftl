<!DOCTYPE html>
<html>
    <head>
        <title>Panel - Inicio</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        </head>

    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="https://image.ibb.co/fXxOcG/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right">
                        <#if rango == "administrador">
                        <li><a href="/panel/administrador/usuarios">Control de usuarios</a></li>
                        <li><a href="/panel/administrador/asignaturas">Control de asignaturas</a></li>
                        <li><a href="/panel/administrador/asignaturas_alumnos">Asignar asignatura a alumno</a></li>
                        <li><a href="/panel/administrador/asignaturas_profesores">Asignar asignatura a profesor</a></li>
                        </#if>
                        <#if rango == "profesor">
                        <li><a href="/panel/profesor/asignar_tarea">Asignar tarea</a></li>
                        <li><a href="/panel/profesor/notas_curso">Ver notas mis cursos</a></li>
                        <li><a href="/panel/profesor/notas_alumnado">Ver notas de mis alumnos</a></li>
                        <li><a href="/panel/profesor/modificar_notas">Cambiar notas</a></li>
                        </#if>
                        <#if rango == "usuario">
                        <li><a href="/panel/alumno/tareas">Mis tareas</a></li>
                        <li><a href="/panel/alumno/notas">Mis notas</a></li>
                        </#if>
                        <li><a href="/panel/cambiar_clave">Cambiar contraseña</a></li>
                        <li><a href="/desconectar">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://www.castillalamancha.es/sites/default/files/documentos/fotografias/20130212/201000426_instituto20neurologia_1.jpg"></div>
            </div>
        <div class="container">
            <div class="carousel">
                <a class="carousel-item" href="#one!"><img height="200" height="290" src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Miguel_hernandez.jpg/220px-Miguel_hernandez.jpg"></a>
                <a class="carousel-item" href="#two!"><img height="200" height="290" src="http://blogs.publico.es/memoria-publica/files/2016/06/Lorca.jpg"></a>
                <a class="carousel-item" href="#three!"><img height="200" height="290" src="https://www.buscabiografias.com/img/people/Juan_Ramon_Jimenez.jpg"></a>
                <a class="carousel-item" href="#four!"><img height="200" height="290" src="http://s.libertaddigital.com/2015/09/18/quevedo.jpg"></a>
                <a class="carousel-item" href="#five!"><img height="200" height="290" src="http://ambitocultural.es/wp-content/uploads/2016/09/allende.jpg"></a>
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
        <script>
            $(document).ready(function () {
                $('.carousel').carousel();
                $('.parallax').parallax();
                $('select').material_select();
            });
        </script>
    </html>