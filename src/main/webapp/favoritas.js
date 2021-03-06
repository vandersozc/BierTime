(function () {

    function Cerveja(id, cerveja, usuario, pontuacao, curtida, comentario) {
        this.id = id;
        this.cerveja = cerveja;
        this.usuario = usuario;
        this.pontuacao = pontuacao;
        this.curtida = curtida;
        this.comentario = comentario;

        return this;
    }

    function favoritaControler() {

        var templateTable;
        var nomeCerveja = '';

        var _preencheTable = function (registros) {
            templateTable = templateTable || $('table.table tbody').html();

            var response = '';

            for (var index = 0; index < registros.length; index++) {
                var linha = registros[index];
                var modelo = templateTable;

                modelo = modelo.replace(/\{\{id\}\}/g, linha.id);
                modelo = modelo.replace(/\{\{cerveja\}\}/g, linha.cerveja.nome);
                modelo = modelo.replace(/\{\{tipo\}\}/g, linha.cerveja.tipo);

                modelo = modelo.replace(/\{\{pontuacao\}\}/g, linha.pontuacao);
                modelo = modelo.replace(/\{\{curtida\}\}/g, linha.curtida);
                modelo = modelo.replace(/\{\{comentario\}\}/g, linha.comentario);

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _preencheFormulario = function (favorita) {

            $('input[name=id]').val(favorita.id);
            $('input[name=cerveja]').val(favorita.cerveja.id);
            //$('input[name=pontuacao]').val(favorita.pontuacao);
            $('input[name=curtida]').val(favorita.curtida);
            $('textarea[name=comentario]').val(favorita.comentario);
            $('input[name=nome]').val(favorita.cerveja.nome);

            if (favorita.pontuacao == 1) {
               $('input[name=pontuacao]').val(favorita.pontuacao);
               document.getElementById("ponto1").checked = true; 
            }
            if (favorita.pontuacao == 2) {
               $('input[name=pontuacao]').val(favorita.pontuacao);
               document.getElementById("ponto2").checked = true; 
            }
            if (favorita.pontuacao == 3) {
               $('input[name=pontuacao]').val(favorita.pontuacao);
               document.getElementById("ponto3").checked = true; 
            }
            if (favorita.pontuacao == 4) {
               $('input[name=pontuacao]').val(favorita.pontuacao);
               document.getElementById("ponto4").checked = true; 
            }
            if (favorita.pontuacao == 5) {
               $('input[name=pontuacao]').val(favorita.pontuacao);
               document.getElementById("ponto5").checked = true; 
            }
        }

        var _formFavorita = function (valor) {
            if (valor) {
                document.getElementById("formFavorita").style.display = '';    
            } else {
                document.getElementById("formFavorita").style.display = 'none';    
            }
        }

        //var _check = function (valor) {
        //    if (valor == 1) {
        //        document.getElementById("ponto1").checked = true;
       //     }
       // }

        var _salvar = function () {
            var parametros = $('#formFavorita').serialize();
            $.post('api/favoritas', parametros, function (data) {
                _carrega();
            });
        }

        var _cancelar = function () {
            _formFavorita(false);
        }

        var _pontuacao = function() {

        }

        var _abrirForm = function (id) {
            _formFavorita(true);
            $.getJSON('api/favoritas?id=' + id, function (registro) {
                _preencheFormulario(registro);
                nomeCerveja = registro.cerveja.nome;
            });
        }

        var _carrega = function () {
            _formFavorita(false);
            $.getJSON('api/favoritas', function (dados) {
                _preencheTable(dados);
            });
        }
        _carrega();

        return {
            salvar: _salvar,
            cancelar: _cancelar,
            abrirForm: _abrirForm,
            pontuacao: _pontuacao,
           // check: _check,
        }
    }

    $(function () {
        window.favorita = favoritaControler();

        $('#btnSalvar').click(function () {
            favorita.salvar();
        });

        $('#btnCancelar').click(function () {
            favorita.cancelar();
        });
    });

})();