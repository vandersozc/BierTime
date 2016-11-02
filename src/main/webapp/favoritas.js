(function () {

    function favoritaControler() {

        var templateTable;

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

                response += modelo;
            }
            $('table.table tbody').html(response);
        }

        var _carrega = function () {
            $.getJSON('api/cervejas', function (dados) {
                _preencheTable(dados);
            });
        }

        _carrega();

    }

    $(function () {
        window.ctrl = favoritaControler();
    });

})();