<!DOCTYPE html>
<html>
    <head>
        <title>Control de usuarios</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"  media="screen,projection"/>

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        </head>

    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="https://image.ibb.co/fXxOcG/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li class="active"><a href="/panel/administrador/usuarios">Control de usuarios</a></li>
                        <li><a href="/panel/administrador/asignaturas">Control de asignaturas</a></li>
                        <li><a href="/panel/administrador/asignaturas_usuarios">Asignar asignatura a alumno</a></li>
                        <li><a href="/panel/administrador/asignaturas_profesores">Asignar asignatura a profesor</a></li>
                        <li><a href="/panel/cambiar_clave">Cambiar contraseña</a></li>
                        <li><a href="/desconectar">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://aasantoangelgijon.com/wp-content/gallery/charla-como-cuidar-de-nuestros-padres-y-abuelos/charla_marzo13_08.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <div class="col s12">
                    <a class="waves-effect waves-light btn right" onclick="addView()"><i class="material-icons right">person_add</i>Agregar usuario</a>
                </div>
                <div class="col s12">

                    <table class="responsive-table centered highlight bordered scrollspy initdatatable" id="crud_usuarios">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Activo</th>
                                <th>Tipo</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody>
                            <#list users as user>
                                <tr id="user-${user.getId()}">
                                    <td>${user.getId()}</td>
                                    <td>${user.getNombre()}</td>
                                    <td>${user.getEmail()}</td>
                                    <td>${user.getActivo()?string("Si","No")}</td>
                                    <td><#switch user.getId_permiso()><#case 1>Administrador<#break><#case 2>Profesor<#break><#case 3>Alumno<#break><#default>Rango no identificado</#switch></td>
                                    <td><a class='dropdown-button btn' href='#' onclick="markActualUser(${user.getId()})" data-activates='acciones'>Acciones</a></td>
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
        <div id="manage" class="modal">
            <div class="modal-content">
              <h4>Modificar usuario</h4>
                <div class="row">
                    <form class="col s12">
                      <div class="row">
                        <div class="input-field col s6">
                          <input name="nombre" type="text" class="validate">
                          <label for="first_name">Nombre</label>
                        </div>
                        <div class="input-field col s6">
                          <input name="mail" type="email" class="validate">
                          <label>Email</label>
                        </div>
                          <div class="input-field col s12">
                            <select name="tipo">
                              <option value="" disabled selected>Tipo de usuario</option>
                              <option value="1">Administrador</option>
                              <option value="2">Profesor</option>
                              <option value="3">Usuario</option>
                            </select>
                            <label>Tipo de usuario</label>
                          </div>
                      </div>
                      
                      </div>
                    </form>
            </div>
            <div class="modal-footer">
              <a onclick="closeView()" class="waves-effect waves-green btn-flat">Cancelar</a>
              <a onclick="add()" class="waves-effect waves-green btn-flat">Confirmar</a>
            </div>
        </div>
        <div id="deleteConfirm" class="modal">
            <div class="modal-content">
              <h4>Eliminar usuario</h4>
              <p>¿Seguro que deseas eliminar el usuario?</p>
            </div>
            <div class="modal-footer">
              <a class="modal-action modal-close waves-effect waves-green btn-flat">No</a>
              <a onclick="del()" class="waves-effect waves-green btn-flat">Si</a>
            </div>
        </div>    
        <ul id='acciones' class='dropdown-content'>
            <li><a onclick="edit()">Editar</a></li>
            <li><a onclick="deleteConfirm()">Eliminar</a></li>
        </ul>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script>
            var actualuser, $dataTable;
            $(document).ready(function () {
                $dataTable = $('#crud_usuarios').DataTable({
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
            function markActualUser(userId)
            {
                actualuser = userId;
            }
            function validateEmail(email) {
                var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                return regex.test(email);
            }
            /* ADD */
            function addView()
            {
                $("#manage").modal("open");
            }
            function closeView()
            {
                $("#manage input").each(
                    function( index, element ){
                        $(element).val("");
                    }
                );
                $("#manage select").each(
                    function( index, element ){
                        $(element).val("");
                        $(element).material_select();
                    }
                );
                $("#manage .validate").each(
                    function( index, element ){
                        $(element).removeClass("valid").removeClass("invalid");
                    }
                );
                Materialize.updateTextFields();
                $("#manage").modal("close");
            }
            function add()
            {
                var email = $("#manage").find("input[name='mail']").val(),
                    nombre = $("#manage").find("input[name='nombre']").val(),
                    tipo = $("#manage").find("select[name='tipo']").val();
                if(email == "" || nombre == "" || tipo == "" || email == null || nombre == null || tipo == null)
                {
                    Materialize.toast('<span>Por favor, rellena todos los campos.</span>', 5000, 'rounded');
                }
                else if(!validateEmail(email))
                {
                    Materialize.toast('<span>Por favor, introduce un email válido.</span>', 5000, 'rounded');
                }
                else
                {
                    $.ajax({
                        data: "accion=insertar&email=" + email + "&nombre=" + nombre + "&tipo=" + tipo,
                        url: '/panel/administrador/usuarios',
                        type: 'post',
                        beforeSend: function () {
                            $('#manage').modal('close');
                            $('#loading').modal('open');
                        },
                        success: function (data) {
                            var info = JSON.parse(data);
                            if (info['success'])
                            {
                                Materialize.toast('<span>Usuario añadido correctamente</span>', 5000, 'rounded');
                                var uinfo = info["data"],
                                    tipoUsuario = "Rango no identificado";
                                switch(parseInt(uinfo["tipo"])){
                                    case 1:
                                        tipoUsuario = "Administrador";
                                    break;
                                    case 2:
                                        tipoUsuario = "Profesor";
                                    break;
                                    case 3:
                                        tipoUsuario = "Alumno";
                                    break;
                                }
                                var newCell = $dataTable.row.add( [
                                    uinfo["id"],
                                    uinfo["nombre"],
                                    uinfo["email"],
                                    (uinfo["activo"] == "true" ? "Si":"No"),
                                    tipoUsuario,
                                    "<a class='dropdown-button btn' href='#' onclick='markActualUser(" + uinfo["id"] + ")' data-activates='acciones'>Acciones</a>"
                                ] ).draw().node();
                                $(newCell).attr("id","user-" + info["id"]);
                            }
                            else
                            {
                                Materialize.toast('<span>Ha ocurrido un error al añadir el usuario: ' + info["reason"] + '</span>', 5000, 'rounded');
                            }
                        },
                        error: function(e)
                        {
                            Materialize.toast("Ha ocurrido un error al procesar la petición.", 4000);
                        },
                        complete: function(c)
                        {
                            $('#loading').modal('close');
                            closeView();
                        }
                    });
                }
            }
            /* DELETE */
            function deleteConfirm()
            {
                $("#deleteConfirm").modal("open");
            }
            function del()
            {
                $.ajax({
                    data: "accion=borrar&id=" + actualuser,
                    url: '/panel/administrador/usuarios',
                    type: 'post',
                    beforeSend: function () {
                        $('#deleteConfirm').modal('close');
                        $('#loading').modal('open');
                    },
                    success: function (data) {
                        var info = JSON.parse(data);
                        if (info['success'])
                        {
                            Materialize.toast('<span>Usuario elminado correctamente</span>', 5000, 'rounded');
                            $dataTable.row('#user-' + actualuser).remove().draw();
                        }
                        else
                        {
                            Materialize.toast('<span>Ha ocurrido un error al eliminar el usuario: ' + info["reason"] + '</span>', 5000, 'rounded');
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