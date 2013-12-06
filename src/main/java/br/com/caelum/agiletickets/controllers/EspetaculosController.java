package br.com.caelum.agiletickets.controllers;

import static br.com.caelum.vraptor.view.Results.status;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.agiletickets.domain.Agenda;
import br.com.caelum.agiletickets.domain.DiretorioDeEstabelecimentos;
import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Periodicidade;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;

import com.google.common.base.Strings;

@Resource
public class EspetaculosController {

	private NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

	private final Agenda agenda;
	private Validator validator;
	private Result result;

	private final DiretorioDeEstabelecimentos estabelecimentos;

	public EspetaculosController(Agenda agenda, DiretorioDeEstabelecimentos estabelecimentos, Validator validator, Result result) {
		this.agenda = agenda;
		this.estabelecimentos = estabelecimentos;
		this.validator = validator;
		this.result = result;
	}

	@Get @Path("/espetaculos")
	public List<Espetaculo> lista() {
		// inclui a lista de estabelecimentos
		result.include("estabelecimentos", estabelecimentos.todos());
		return agenda.espetaculos();
	}

	@Post @Path("/espetaculos")
	public void adiciona(Espetaculo espetaculo) {

		// aqui eh onde fazemos as varias validacoes
		// se nao tiver nome, avisa o usuario
		// se nao tiver descricao, avisa o usuario
		validaNomeEDescricao(espetaculo);

		agenda.cadastra(espetaculo);
		result.redirectTo(this).lista();
	}

	private void validaNomeEDescricao(Espetaculo espetaculo) {
		if (Strings.isNullOrEmpty(espetaculo.getNome())) {
			adicionaMensagemDeValidacao("Nome do espetáculo nao pode estar em branco", "");
		}
		if (Strings.isNullOrEmpty(espetaculo.getDescricao())) {
			adicionaMensagemDeValidacao("Descricao do espetaculo nao pode estar em branco", "");
		}
		validator.onErrorRedirectTo(this).lista();
	}

	private void adicionaMensagemDeValidacao(String mensagem, String categoria) {
		validator.add(new ValidationMessage(mensagem, categoria));
	}

	private void validaCampo(String valorCampo, String descricao) {
		if (Strings.isNullOrEmpty(valorCampo)) {
			validator.add(new ValidationMessage(descricao + "nao pode estar em branco", ""));
		}
		validator.onErrorRedirectTo(this).lista();
	}


	@Get @Path("/sessao/{id}")
	public void sessao(Long id) {
		Sessao sessao = agenda.sessao(id);
		if (isSessaoNula(sessao)) {
			result.notFound();
		}

		result.include("sessao", sessao);
	}

	private boolean isSessaoNula(Sessao sessao) {
		return sessao == null;
	}

	@Post @Path("/sessao/{sessaoId}/reserva")
	public void reserva(Long sessaoId, final Integer quantidade) {
		Sessao sessao = agenda.sessao(sessaoId);
		if (isSessaoNula(sessao)) {
			result.notFound();
			return;
		}
		validaQtdeDeVagas(quantidade, sessao);
		
		sessao.reserva(quantidade);
		result.include("message", "Sessao reservada com sucesso");
		result.redirectTo(IndexController.class).index();
	}

	


	private void validaQtdeDeVagas(final Integer quantidade, Sessao sessao) {
		if (quantidade < 1) {
			adicionaMensagemDeValidacao("Voce deve escolher um lugar ou mais", "");
		}

		if (!sessao.podeReservar(quantidade)) {

			adicionaMensagemDeValidacao("Nao existem ingressos dispon√≠veis", "");
		}
		
		validator.onErrorRedirectTo(this).sessao(sessao.getId());


		validator.add(new ValidationMessage("Nao existem ingressos disponiveis", ""));

		sessao.reserva(quantidade);

		BigDecimal precoTotal = sessao.getPreco().multiply(BigDecimal.valueOf(quantidade));

		result.include("message", "Sessao reservada com sucesso por " + CURRENCY.format(precoTotal));

		result.redirectTo(IndexController.class).index();

	}

	
	@Get @Path("/espetaculo/{espetaculoId}/sessoes")
	public void sessoes(Long espetaculoId) {
		Espetaculo espetaculo = carregaEspetaculo(espetaculoId);
		result.include("espetaculo", espetaculo);
	}

	@Post @Path("/espetaculo/{espetaculoId}/sessoes")
	public void cadastraSessoes(Long espetaculoId, LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodo) {
		Espetaculo espetaculo = carregaEspetaculo(espetaculoId);

		// aqui faz a magica!
		// cria sessoes baseado no periodo de inicio e fim passados pelo usuario
		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodo);

		agenda.agende(sessoes);

		result.include("message", sessoes.size() + " sessoes criadas com sucesso");
		result.redirectTo(this).lista();
	}

	private Espetaculo carregaEspetaculo(Long espetaculoId) {
		Espetaculo espetaculo = agenda.espetaculo(espetaculoId);
		if (espetaculo == null) {
			adicionaMensagemDeValidacao("Espetáculo não encontrado!", "");
		}
		validator.onErrorUse(status()).notFound();
		return espetaculo;
	}

}
