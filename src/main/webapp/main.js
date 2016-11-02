(function () {
	function mainControler() {

        var _validaLogin = function () {
            confirm('Validação de usuário em construção!');
        }

        return {
            validaLogin: _validaLogin,
        }
    }

	$(function(){
		window.main = mainControler();
	    $.get('menu-lateral.html', function(data){
	        $('#menu-lateral').html(data);
	    });
	    $.get('menu-superior-cadastro.html', function(resposta){
	        $('#menu-superior-cadastro').html(resposta);
	    });
	    $.get('menu-superior-home.html', function(resposta){
	        $('#menu-superior-home').html(resposta);
	    });

	    $('#btnLogin').click(function () {
	      	main.validaLogin();
	    });
	});

})();	