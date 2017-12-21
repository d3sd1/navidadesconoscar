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
        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="" class="brand-logo center"><img alt="Logo" src="assets/images/logo.png" style="height: 50px"/></a>
                </div>
            </nav>
        </div>
        <div class="parallax-container valign-wrapper" style="height: 100vh;">
            <div class="parallax"><img alt="Fondo" src="assets/images/bg.jpg"/></div>
                <div class="row">
                    <div class="col s1 card-panel blue-grey lighten-5" style="padding: 20px;">
                            <form>
                                <div class="row">
                                    <div class="input-field col s12">
                                            <input name="mail" type="email" class="validate">
                                            <label for="email">Correo electrónico</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <input name="password" type="password" class="validate">
                                        <label for="password">Contraseña</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="input-field col s12">
                                        <a class="light-blue accent-2 waves-effect waves-light btn" id="loginTrigger"><i class="material-icons right">arrow_forward</i>Conectar</a>
                                    </div>
                                </div>
                            </form>
                    </div>
                </div>
        </div>
        <footer class="page-footer cyan accent-4">
            <div class="container">
                <div class="row">
                    <div class="col l6 s12">
                        <h5 class="white-text">Navidad con oscar</h5>
                        <p class="grey-text text-lighten-4">Realizado por Andrei García Cuadra y Miguel Ángel Díaz.</p>
                    </div>
                </div>
            </div>
            <div class="footer-copyright">
                <div class="container">
                    © 2018 Óscar nos jode las vacaciones
                </div>
            </div>
        </footer>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="assets/js/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="assets/js/materialize.min.js"></script>
        <script type="text/javascript" src="assets/js/login.js"></script>
    </body>
</html>