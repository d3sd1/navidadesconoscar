<!DOCTYPE html>
<html>
    <head>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="assets/css/materialize.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        </head>

    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="assets/images/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <!--<li><a href="#alumnos">(PROFE) Alumnos (crud)</a></li>
                        <li><a href="asignaturas">(PROFE) Notas de alumnos</a></li>
                        <li><a href="notas">(SUPERADMIN) Profesores (CRUD)</a></li>
                        <li><a href="notas">(SUPERADMIN) Asignaturas + cursos (CRUD)</a></li>-->
                        <#if rango == "administrador">
                        <li><a href="panel/administrador/usuarios">Control de usuarios</a></li>
                        </#if>
                        
                        <li><a href="logout">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://www.pressdigital.es/multimedia/images/Alumnos_en_clase.jpg"></div>
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
        <script src="assets/js/panel_start.js"></script>
    </html>