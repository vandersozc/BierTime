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

        function _controlaCampos(valor) {
            document.getElementById("nomeCervejaria").disabled = valor;
            document.getElementById("localizacaoCervejaria").disabled = valor;
            document.getElementById("ufCervejaria").disabled = valor;
            document.getElementById("paisCervejaria").disabled = valor;
            document.getElementById("btnSalvar").disabled = valor;
        }

        var _preencheFormulario = function (cervejaria) {
            $('input[name=id]').val(cervejaria.id);
            $('input[name=nome]').val(cervejaria.nome);
            $('input[name=localizacao]').val(cervejaria.localizacao);
            $('input[name=estado]').val(cervejaria.estado);
            $('input[name=pais]').val(cervejaria.pais);
        }

        var _limpaFormulario = function () {
            $('input[name=id]').val(undefined);
            $('input[name=nome]').val(undefined);
            $('input[name=localizacao]').val(undefined);
            $('input[name=estado]').val(undefined);
            $('input[name=pais]').val(undefined);
        }

        var _preencheTabela = function (registros) {
            templateTable = templateTable || $('table.table tbody').html();
            var response = '';

            for (var index = 0; index < registros.length; index++) {
                var linha = registros[index];
                var modelo = templateTable;

                modelo = modelo.replace(/\{\{id\}\}/g, linha.id);
                modelo = modelo.replace(/\{\{codigo\}\}/g, index + 1);
                modelo = modelo.replace(/\{\{nome\}\}/g, linha.nome);
                modelo = modelo.replace(/\{\{localizacao\}\}/g, linha.localizacao);
                modelo = modelo.replace(/\{\{estado\}\}/g, linha.estado);
                modelo = modelo.replace(/\{\{pais\}\}/g, linha.pais);

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _adicionar = function () {
            _preencheFormulario(new Cervejaria());
        }

        var _salvar = function () {
            var parametros = $('#form-cadastro').serialize();
            $.post('api/cervejarias', parametros, function (data) {
                _carrega();
                _limpaFormulario();
                _controlaCampos(true);
            });
        }

        var _editar = function (id) {
            _controlaCampos(false);
            $.getJSON('api/cervejarias?id=' + id, function (registro) {
                _preencheFormulario(registro);
            });
        }

        var _remover = function (id) {
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
                _preencheTabela(dados);
            });
        }

        _carrega();

        return {
            adicionar: _adicionar,
            salvar: _salvar,
            editar: _editar,
            remover: _remover,
            controlaCampos: _controlaCampos,
        }

    }

    $(function () {
        window.cervejaria = cervejariaControler();
        $('#btnSalvar').click(function () {
            cervejaria.salvar();
        });
        $('#btnAdicionar').click(function () {
            cervejaria.controlaCampos();
            cervejaria.adicionar();
        });
    });

})();