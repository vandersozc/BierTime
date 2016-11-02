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

        function _controlaCampos(valor) {
            document.getElementById("nomeCerveja").disabled = valor;
            document.getElementById("tipoCerveja").disabled = valor;
            document.getElementById("familiaCerveja").disabled = valor;
            document.getElementById("amargorCerveja").disabled = valor;
            document.getElementById("corCerveja").disabled = valor;
            document.getElementById("teorCerveja").disabled = valor;
            document.getElementById("observacaoCerveja").disabled = valor;
            document.getElementById("btnSalvar").disabled = valor;
        }

        var _preencheFormulario = function (cerveja) {
            $('input[name=id]').val(cerveja.id);
            $('input[name=nome]').val(cerveja.nome);
            $('input[name=tipo]').val(cerveja.tipo);
            $('input[name=familia]').val(cerveja.familia);
            $('input[name=amargor]').val(cerveja.amargor);
            $('input[name=cor]').val(cerveja.cor);
            $('input[name=teor]').val(cerveja.teor);
            $('textarea[name=observacao]').val(cerveja.observacao);
        }

        var _limpaFormulario = function () {
            $('input[name=id]').val(undefined);
            $('input[name=nome]').val(undefined);
            $('input[name=tipo]').val(undefined);
            $('input[name=familia]').val(undefined);
            $('input[name=amargor]').val(undefined);
            $('input[name=cor]').val(undefined);
            $('input[name=teor]').val(undefined);
            $('textarea[name=observacao]').val(undefined);
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
                modelo = modelo.replace(/\{\{tipo\}\}/g, linha.tipo);
                modelo = modelo.replace(/\{\{familia\}\}/g, linha.familia);
                modelo = modelo.replace(/\{\{amargor\}\}/g, linha.amargor);

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _somenteNumero = function(e){
            var tecla=(window.event)?event.keyCode:e.which;   
        if((tecla>47 && tecla<58)) return true;
            else{
                if (tecla==8 || tecla==0) return true;
                    else  return false;
                }
        }

        var _adicionar = function () {
            _preencheFormulario(new Cerveja());
        }

        var _salvar = function () {
            var parametros = $('#form-cadastro').serialize();
            $.post('api/cervejas', parametros, function (data) {
                _carrega();
                _limpaFormulario();
                _controlaCampos(true);
            });
        }

        var _editar = function (id) {
            _controlaCampos(false);
            $.getJSON('api/cervejas?id=' + id, function (registro) {
                _preencheFormulario(registro);
            });
        }

        var _remover = function (id) {
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
            somenteNumero: _somenteNumero,
        }
    }

    $(function () {
        window.cerveja = cervejaControler();
        $('#btnSalvar').click(function () {
            cerveja.salvar();
        });
        $('#btnAdicionar').click(function () {
            cerveja.controlaCampos();
            cerveja.adicionar();
        });
    });

})();