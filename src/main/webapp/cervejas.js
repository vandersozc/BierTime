(function () {

    function Cerveja(id, nome, tipo, familia, amargor, cor, teor, observacao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.familia = familia;
        this.amargor = amargor;
        this.cor = cor;
        this.teor = teor;
        this.observacao = observacao;

        return this;
    }

    function cervejaControler() {

        var templateTable;

        var _add = function () {
            _preencheForm(new Cerveja());
        }

        function _habilitaCampos() {
            document.getElementById("nomeCerveja").disabled = false;
            document.getElementById("tipoCerveja").disabled = false;
            document.getElementById("familiaCerveja").disabled = false;
            document.getElementById("amargorCerveja").disabled = false;
            document.getElementById("corCerveja").disabled = false;
            document.getElementById("teorCerveja").disabled = false;
            document.getElementById("observacaoCerveja").disabled = false;
            document.getElementById("btnSalvar").disabled = false;
        }

        var _preencheForm = function (cerveja) {
            $('input[name=id]').val(cerveja.id);
            $('input[name=nome]').val(cerveja.nome);
            $('input[name=tipo]').val(cerveja.tipo);
            $('input[name=familia]').val(cerveja.familia);
            $('input[name=amargor]').val(cerveja.amargor);
            $('input[name=cor]').val(cerveja.cor);
            $('input[name=teor]').val(cerveja.teor);
            $('textarea[name=observacao]').val(cerveja.observacao);
        }

        var _limpaForm = function () {
            $('input[name=id]').val(undefined);
            $('input[name=nome]').val(undefined);
            $('input[name=tipo]').val(undefined);
            $('input[name=familia]').val(undefined);
            $('input[name=amargor]').val(undefined);
            $('input[name=cor]').val(undefined);
            $('input[name=teor]').val(undefined);
            $('textarea[name=observacao]').val(undefined);
        }

        var _save = function () {
            var parametros = $('#form-cadastro').serialize();
            $.post('api/cervejas', parametros, function (data) {
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

                modelo = modelo.replace(/\{\{id\}\}/g, linha.id);
                modelo = modelo.replace(/\{\{codigo\}\}/g, index + 1);
                modelo = modelo.replace(/\{\{nome\}\}/g, linha.nome);
                modelo = modelo.replace(/\{\{tipo\}\}/g, linha.tipo);
                modelo = modelo.replace(/\{\{familia\}\}/g, linha.familia);
                modelo = modelo.replace(/\{\{amargor\}\}/g, linha.amargor);

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _edit = function (id) {
            $.getJSON('api/cervejas?id=' + id, function (registro) {
                _preencheForm(registro);
            });
        }

        var _remove = function (id) {
            var confirmaExclusao = confirm('tem certeza que deseja excluir o registro?');
            if (confirmaExclusao) {
                $.ajax({
                    url: 'api/cervejas?id=' + id,
                    method: 'DELETE'
                }).done(function () {
                    _carrega();
                });
            }
        }

        var _carrega = function () {
            $.getJSON('api/cervejas', function (dados) {
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
        window.ctrl = cervejaControler();
        $('#btnSalvar').click(function () {
            ctrl.save();
        });
        $('#btnAdicionar').click(function () {
            ctrl.habilitaCampos();
            ctrl.add();
        });
    });

})();