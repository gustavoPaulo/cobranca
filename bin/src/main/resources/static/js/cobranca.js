$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget);
	
	var codigoCobranca = button.data('codigo');
	var descricaoCobranca = button.data('descricao');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if(!action.endsWith('/')){
		
		action += '/';
	}
	
	form.attr('action', action + codigoCobranca);
	
	modal.find('.modal-body span').html('Você deseja mesmo excluir a cobrança <strong>' + descricaoCobranca + '</strong>?');
	
});

$(function(){
	
	$('[rel="tooltip"]').tooltip();
	$('.js-currency').maskMoney({decimal: ',', thousands: '.', allowZero: true});
	
	$('.js-atualizar-status').on('click', function(event){
		event.preventDefault();
		
		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');
		
		var response = $.ajax({
			
			url: urlReceber,
			type: 'PUT'
		});
		
		
		response.done(function(e){
			
			var codigoCobranca = botaoReceber.data('codigo');
		
			$('[data-role=' + codigoCobranca + ']').html('<span class="label label-success">' + e + '</span>');
			botaoReceber.hide();
			
		});
		
		response.fail(function(e){
		
			alert('Erro ao receber a cobrança');
		});
		
	});
});