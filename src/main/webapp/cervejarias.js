(function () {

    function Cervejaria(id, nome, localizacao, estado, pais) {
        this.id = id;
        this.nome = nome;
        this.tipo = localizacao;
        this.familia = estado;
        this.amargor = pais;

        return this;
    }

    function cervejariaControler() {

        var templateTable;

        var _add = function () {
            _preencheForm(new Cervejaria());
        }

        function _habilitaCampos() {
            document.getElementById("nomeCervejaria").disabled = false;
            document.getElementById("localizacaoCervejaria").disabled = false;
            document.getElementById("ufCervejaria").disabled = false;
            document.getElementById("paisCervejaria").disabled = false;
            document.getElementById("btnSalvar").disabled = false;
        }

        var _preencheForm = function (cervejaria) {
            $('input[name=id]').val(cervejaria.id);
            $('input[name=nome]').val(cervejaria.nome);
            $('input[name=localizacao]').val(cervejaria.localizacao);
            $('input[name=estado]').val(cervejaria.estado);
            $('input[name=pais]').val(cervejaria.pais);
        }

        var _limpaForm = function () {
            $('input[name=id]').val(undefined);
            $('input[name=nome]').val(undefined);
            $('input[name=localizacao]').val(undefined);
            $('input[name=estado]').val(undefined);
            $('input[name=pais]').val(undefined);
        }

        var _save = function () {
            var parametros = $('#form-cadastro').serialize();
            $.post('api/cervejarias', parametros, function (data) {
                _carrega();
                _limpaForm();
            });
        }

        var _preencheTable = function (registros) {
            templateTable = templateTable || $('table.table tbody').html();

            var response = '';

            for (var index = 0; index < registros.length; index++) {
                var linha = registros[index];
                var modelo = templateTable;

                modelo = modelo.replace(/\{\{id\}\}/g, index + 1);
                modelo = modelo.replace(/\{\{nome\}\}/g, linha.nome);
                modelo = modelo.replace(/\{\{localizacao\}\}/g, linha.localizacao);
                modelo = modelo.replace(/\{\{estado\}\}/g, linha.estado);
                modelo = modelo.replace(/\{\{pais\}\}/g, linha.pais);

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _edit = function (id) {
            $.getJSON('api/cervejarias?id=' + id, function (registro) {
                _preencheForm(registro);
            });
        }

        var _remove = function (id) {
            var confirmaExclusao = confirm('tem certeza que deseja excluir o registro?');
            if (confirmaExclusao) {
                $.ajax({
                    url: 'api/cervejarias?id=' + id,
                    method: 'DELETE'
                }).done(function () {
                    _carrega();
                });
            }
        }

        var _carrega = function () {
            $.getJSON('api/cervejarias', function (dados) {
                _preencheTable(dados);
            });
        }

        _carrega();

        return {
            add: _add,
            save: _save,
            edit: _edit,
            remove: _remove,
            habilitaCampos: _habilitaCampos,
        }
    }

    $(function () {
        window.cervejaria = cervejariaControler();
        $('#btnSalvar').click(function () {
            cervejaria.save();
        });
        $('#btnAdicionar').click(function () {
            cervejaria.habilitaCampos();
            cervejaria.add();
        });
    });

})();