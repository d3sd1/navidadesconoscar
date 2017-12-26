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
                <div class="nav-wrapper">
                    <a href="#" class="brand-logo">DAW</a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="#alumnos">(PROFE) Alumnos (crud)</a></li>
                        <li><a href="asignaturas">(PROFE) Notas de alumnos</a></li>
                        <li><a href="notas">(SUPERADMIN) Profesores (CRUD)</a></li>
                        <li><a href="notas">(SUPERADMIN) Asignaturas + cursos (CRUD)</a></li>
                        <li><a href="logout">Desconectar</a></li>
                    </ul>
                </div>
            </nav>
        </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://ies.blasdeotero.madrid.educa.madrid.org/img/instalaciones/10%20Aula%20de%20inform%C3%A1tica.jpg"></div>
        </div>
        <div class="container" style="margin-top: 2em">
            HOLA DOCTOR NICK
        </div>
        <footer class="page-footer">
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
        <!-- Shortcut menu -->
        <div class="fixed-action-btn horizontal click-to-toggle">
            <a class="btn-floating btn-large red">
                <i class="material-icons">menu</i>
            </a>
            <ul>
                <li><a class="btn-floating red" onclick="manageUsr_OpenModal(-1)"><i class="material-icons">person_add</i></a></li>
                <li><a class="btn-floating yellow darken-1" href="notas"><i class="material-icons">library_books</i></a></li>
            </ul>
        </div>

        <!-- MODALS -->
        <div id="manageUsr" class="modal fixDate">
            <div class="modal-content">
                <form>
                    <div class="row">
                        <form class="col s12">
                            <div class="row" id="editUsr">
                                <div class="input-field col s6">
                                    <i class="material-icons prefix">assignment_ind</i>
                                    <input type="number" readonly="readonly" name="id">
                                    <label>ID</label>
                                </div>
                                <div class="input-field col s6">
                                    <i class="material-icons prefix">account_circle</i>
                                    <input id="input_text" type="text" class="validate" data-length="250" required name="name" autocomplete="off">
                                    <label for="input_text" data-error="wrong" data-success="right">Nombre</label>
                                </div>
                                <div class="input-field col s6">
                                    <i class="material-icons prefix">date_range</i>
                                    <input id="icon_prefix" type="text" class="datepicker" required name="birthdate">
                                    <label for="icon_prefix">Fecha de nacimiento</label>
                                </div>
                                <div class="input-field col s6">
                                    <i class="material-icons prefix">pan_tool</i>
                                    <select name="oldage" required>
                                        <option value="" disabled selected>Elige una opción</option>
                                        <option value="true">Sí</option>
                                        <option value="false">No</option>
                                    </select>
                                    <label>¿Es Mayor de edad?</label>
                                </div>
                            </div>
                        </form>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#!" onclick="manageUsr_CloseModal()" class="waves-effect waves-green btn-flat">Cancelar</a>
                <a href="#!" onclick="manageUsr_CheckAction()" class="waves-effect waves-green btn-flat ">Confirmar</a>
            </div>
        </div>

        <div id="delConfirm" class="modal">
            <div class="modal-content">
                <h4>Solicitud de confirmación</h4>
                <p>¿Estás segur@ de que deseas eliminar el usuario? Esta acción no se puede revertir.</p>
            </div>
            <div class="modal-footer">
                <a href="#!" onclick="delUsrConfirm_CloseModal()" class="waves-effect waves-green btn-flat">Cancelar</a>
                <a href="#!" onclick="delUsrConfirm_RemoveUser()" class="waves-effect waves-green btn-flat ">Eliminar usuario</a>
            </div>
        </div>
        <div id="delConfirmWithNotes" class="modal">
            <div class="modal-content">
                <h4>Solicitud de confirmación</h4>
                <p>Se ha producido un error al eliminar, el usuario tiene notas.... ¿Estás segur@ de que deseas eliminar el usuario y sus notas?</p>
            </div>
            <div class="modal-footer">
                <a href="#!" onclick="delUsrWithNotesConfirm_CloseModal()" class="waves-effect waves-green btn-flat">Cancelar</a>
                <a href="#!" onclick="delUsrWithNotesConfirm_RemoveUser()" class="waves-effect waves-green btn-flat ">Eliminar usuario</a>
            </div>
        </div>
        <!--Import jQuery before materialize.js-->
        <script>
            var AJAX_UPDATE_PARAM = "<%= Constants.PARAMETER_VALUE_ACTION_UPDATE %>",
            AJAX_ADD_PARAM = "<%= Constants.PARAMETER_VALUE_ACTION_ADD %>",
            AJAX_FORCE_DELETE_PARAM = "<%= Constants.PARAMETER_VALUE_ACTION_FORCEDELETE %>",
            AJAX_DELETE_PARAM = "<%= Constants.PARAMETER_VALUE_ACTION_DELETE %>";
        </script>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.js"></script>
        <script src="js/constants.js"></script>
        <script src="js/init.js"></script>
        <script src="js/utils.js"></script>
        <script src="js/users_modal_manage.js"></script>
        <script src="js/users_modal_delete.js"></script>
</html>