<!DOCTYPE html>
<html>
    <head>
        <title>Control de asignaturas</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css"  media="screen,projection"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta charset="UTF-8">
        </head>
    <body>
        <header>
            <nav>
                <div class="nav-wrapper blue lighten-1">
                    <a href="#" class="brand-logo"><img alt="Logo" src="https://image.ibb.co/fXxOcG/logo.png" style="height: 50px"/></a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li><a href="/panel/administrador/usuarios">Control de usuarios</a></li>
                        <li class="active"><a href="/panel/administrador/asignaturas">Control de asignaturas</a></li>
                        <li><a href="/panel/administrador/asignaturas_usuarios">Asignar asignatura a alumno</a></li>
                        <li><a href="/panel/administrador/asignaturas_profesores">Asignar asignatura a profesor</a></li>
                        <li><a href="/panel/cambiar_clave">Cambiar contraseña</a></li>
                        <li><a href="/desconectar">Desconectar</a></li>
                        </ul>
                    </div>
                </nav>
            </header>
        <div class="parallax-container">
            <div class="parallax"><img src="http://www.iespedrodeluna.es/wp-content/uploads/Tablones-de-anuncios-IES-Pedro-de-Luna-0-M.jpg"></div>
            </div>
        <div class="container" style="margin-top: 2em">
            <div class="row">
                <div class="col s12">
                    <a class="waves-effect waves-light btn right" onclick="addView()"><i class="material-icons right">person_add</i>Agregar asignatura</a>
                </div>
                <div class="col s12">

                    <table class="responsive-table centered highlight bordered scrollspy initdatatable" id="crud_asignaturas">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Curso</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody>
                            <#list asignaturas as asignatura>
                                <tr id="asignatura-${asignatura.getId()}">
                                    <td>${asignatura.getId()}</td>
                                    <td><input id="asignatura-${asignatura.getId()}-nombre" value="${asignatura.getNombre()}" type="text"></td>
                                    <td>
                                    <select name="cursos">
                                        <option value="" disabled selected>Seleccionar curso</option>
                                    <#list cursos as curso>
                                        <#if curso.getId() == asignatura.getId_curso()>
                                        <option value="${curso.getId()}" selected>${curso.getNombre()}</option>
                                        <#else>
                                        <option value="${curso.getId()}">${curso.getNombre()}</option>
                                        </#if>
                                    </#list>
                                    </select>
                                    <label>Seleccionar curso</label></td>
                                    <td><a class='dropdown-button btn' href='#' onclick="markActualAsig(${asignatura.getId()})" data-activates='dropdown-${asignatura.getId()}'>Acciones</a><ul id='dropdown-${asignatura.getId()}' class='dropdown-content'><li><a onclick="edit()">Confirmar</a></li><li><a onclick="deleteConfirm()">Eliminar</a></li></ul></td>
                                </tr>
                            </#list>
                        </tbody>

                        <tbody>
                        <!-- usuarios aqui -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="manage" class="modal">
            <div class="modal-content">
              <h4>Añadir asignatura</h4>
                <div class="row">
                    <form class="col s12">
                        <input name="id" type="hidden">
                      <div class="row">
                        <div class="input-field col s12">
                          <input name="nombre" type="text" class="validate">
                          <label for="first_name">Nombre</label>
                        </div>
                          <div class="input-field col s12">
                            <select name="curso">
                              <option value="" disabled selected>Curso</option>
                                <#list cursos as curso>
                                    <option value="${curso.getId()}">${curso.getNombre()}</option>
                                </#list>
                            </select>
                            <label>Curso</label>
                          </div>
                      </div>
                      
                      </div>
                    </form>
            </div>
            <div class="modal-footer">
              <a onclick="closeView()" class="waves-effect waves-green btn-flat">Cancelar</a>
              <a onclick="add()" class="waves-effect waves-green btn-flat">Añadir</a>
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
        <div id="loading" class="modal">
            <div class="modal-content">
                <h1 class="center-align">Cargando...</h1>
                <div class="progress">
                    <div class="indeterminate"></div>
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
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script>
            var actualAsig, $dataTable;
            $(document).ready(function () {
                $dataTable = $('#crud_asignaturas').DataTable({
                    "language": {
                        "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
                    },
                    "drawCallback": function(){
                        $('select').material_select();
                        Materialize.updateTextFields();
                    }
                });
                $('.carousel').carousel();
                $('.parallax').parallax();
                $('.modal').modal({
                    dismissible: false
                });
                $('select').material_select();
                Materialize.updateTextFields();
                });
            function add()
            {
                var nombre = $("#manage").find("input[name='nombre']").val(),
                    curso = $("#manage").find("select[name='curso']").val();
                if(nombre == "" || curso == "" || curso == null || nombre == null)
                {
                    Materialize.toast('<span>Por favor, rellena todos los campos.</span>', 5000, 'rounded');
                }
                else
                {
                    console.log("accion=insertar&nombre=" + nombre + "&curso=" + curso);
                    $.ajax({
                        data: "accion=insertar&nombre=" + nombre + "&id_curso=" + curso,
                        url: '/panel/administrador/asignaturas',
                        type: 'post',
                        beforeSend: function () {
                            $('#manage').modal('close');
                            $('#loading').modal('open');
                        },
                        success: function (data) {
                            var info = JSON.parse(data);
                            if (info['success'])
                            {
                                Materialize.toast('<span>Asignatura añadida correctamente</span>', 5000, 'rounded');
                                
                                var uinfo = info["data"], newCell = $dataTable.row.add( [
                                    uinfo["id"],
                                    '<input id="asignatura-' + uinfo["id"] + '-nombre" value="' + uinfo["nombre"] + '" type="text">',
                                    '<select name="cursos"> <option value="" disabled selected>Seleccionar curso</option>' +
                                    <#list cursos as curso>
                                        '<option value="${curso.getId()}" ' + (parseInt(uinfo["id_curso"]) == ${curso.getId()} ? "selected":"") + '>${curso.getNombre()}</option>' +
                                    </#list>
                                    '</select><label>Seleccionar curso</label>',
                                    "<a class='dropdown-button btn' href='#' onclick='markActualAsig(" + uinfo["id"] + ")' data-activates='dropdown-" + uinfo["id"] + "'>Acciones</a><ul id='dropdown-" + uinfo["id"] + "' class='dropdown-content'><li><a onclick='editView()'>Editar</a></li><li><a onclick='deleteConfirm()'>Eliminar</a></li></ul>"
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
            function addView()
            {
                $("#manage").modal("open");
            }
            function markActualAsig(asg)
            {
                actualAsig = asg;
            }
            function edit()
            {
                var nombre = $("#asignatura-" + actualAsig + "-nombre").val(),
                    curso = $("#asignatura-" + actualAsig).find("select[name=cursos]").val();
                $.ajax({
                    data: "accion=modificar&id=" + actualAsig + "&nombre=" + nombre + "&id_curso=" + curso,
                    url: '/panel/administrador/asignaturas',
                    type: 'post',
                    beforeSend: function () {
                        $('#loading').modal('open');
                    },
                    success: function (data) {
                        var info = JSON.parse(data);
                        if (info['success'])
                        {
                            Materialize.toast('<span>Asignatura modificado correctamente</span>', 5000, 'rounded');
                        }
                        else
                        {
                            Materialize.toast('<span>Ha ocurrido un error al modificar la asignatura: ' + info["reason"] + '</span>', 5000, 'rounded');
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
            function deleteConfirm()
            {
                $("#deleteConfirm").modal("open");
            }
            function del()
            {
                $.ajax({
                    data: "accion=borrar&id=" + actualAsig,
                    url: '/panel/administrador/asignaturas',
                    type: 'post',
                    beforeSend: function () {
                        $('#deleteConfirm').modal('close');
                        $('#loading').modal('open');
                    },
                    success: function (data) {
                        var info = JSON.parse(data);
                        if (info['success'])
                        {
                            Materialize.toast('<span>Asignatura eliminada correctamente</span>', 5000, 'rounded');
                            $dataTable.row('#asignatura-' + actualAsig).remove().draw();
                        }
                        else
                        {
                            Materialize.toast('<span>Ha ocurrido un error al eliminar la asignatura: ' + info["reason"] + '</span>', 5000, 'rounded');
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