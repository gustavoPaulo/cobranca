package com.algaworks.cobranca.controller;


import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.CobrancaModel;
import com.algaworks.cobranca.model.StatusCobranca;
import com.algaworks.cobranca.repository.filter.CobrancaFilter;
import com.algaworks.cobranca.service.CadastroCobrancaService;


@Controller
@RequestMapping("/cobranca")
public class CobrancaController<T> {
	
	private static final String CADASTRO_VIEW = "CadastroCobranca";
	
	
	@Autowired
	private CadastroCobrancaService cadastroCobrancaService; 
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new CobrancaModel());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated CobrancaModel cobrancaModel, Errors errors, RedirectAttributes attributes) {
		
		if(errors.hasErrors()) {
			
			return CADASTRO_VIEW;
		}

		try {
			cadastroCobrancaService.salvar(cobrancaModel);
			attributes.addFlashAttribute("mensagem","Cobrança salva com sucesso!");
		
			return "redirect:/cobranca/novo";
		
		}catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") CobrancaFilter filtro) {

		List<CobrancaModel> todasCobrancas = cadastroCobrancaService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaCobrancas");
		mv.addObject("cobrancas", todasCobrancas);
		
		return mv;
	}
	
	
	@RequestMapping("/excluir/{codigo}")
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		
		cadastroCobrancaService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem","Cobrança excluída com sucesso!");
		return "redirect:/cobranca?descricao=";
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") CobrancaModel cobrancaModel) {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(cobrancaModel);
		
		return mv;
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		
		return  cadastroCobrancaService.receber(codigo);
	}
	
	
	@ModelAttribute("todosOsStatusCobranca")
	public List<StatusCobranca> todosStatusCobranca(){
		
		return Arrays.asList(StatusCobranca.values());
	}
	
}