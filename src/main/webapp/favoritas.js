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

        var _preencheTable = function (registros) {
            console.log(registros);
            templateTable = templateTable || $('table.table tbody').html();

            var response = '';

            for (var index = 0; index < registros.length; index++) {
                var linha = registros[index];
                var modelo = templateTable;

                modelo = modelo.replace(/\{\{id\}\}/g, linha.id);
                modelo = modelo.replace(/\{\{codigo\}\}/g, index + 1);
                modelo = modelo.replace(/\{\{cerveja\}\}/g, linha.cerveja.nome);
                modelo = modelo.replace(/\{\{tipo\}\}/g, linha.cerveja.tipo);
                //modelo = modelo.replace(/\{\{pontuacao\}\}/g, linha.pontuacao);
               // modelo = modelo.replace(/\{\{curtida\}\}/g, linha.curtida);
               // modelo = modelo.replace(/\{\{comentario\}\}/g, linha.comentario);

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _carrega = function () {
            $.getJSON('api/favoritas', function (dados) {
                _preencheTable(dados);
            });
        }

        _carrega();

    }

    $(function () {
        window.favorita = favoritaControler();
    });

})();