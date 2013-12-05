package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;


public class EspetaculoTest {
	
	@Test(expected=IllegalArgumentException.class) 
	public void deveRetornarListaVaziaQuandoNaoPassarQualquerParametro(){
		
		//Entradas - Todas nulas
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.criaSessoes(null, null, null, null);
		
		//Sem Saida - aguardando IllegalArgumentException

	}

	
	@Test 
	public void deveRetornarUmaSessaoQuandoInicioIgualAoFimEPeriodicidadeDiaria(){
		//Entradas
		LocalDate inicio = new LocalDate(2013,12,1);
		LocalDate fim = new LocalDate(2013,12,1);
		LocalTime horario = new LocalTime(17,0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> lista = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		//Saida
		assertEquals(1, lista.size());
		assertEquals(lista.get(0).getInicio(), inicio.toDateTime(horario));
	}
	
	@Test 
	public void deveRetornarListaVaziaQuandoDataInicioMaiorQueDataFim(){
		//Entradas
		LocalDate inicio = new LocalDate(2013,12,2);
		LocalDate fim = new LocalDate(2013,12,1);
		LocalTime horario = new LocalTime(17,0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> lista = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		//Saida
		assertEquals(0, lista.size());
	}
	
	@Test 
	public void deveRetornar11SessoesQuandoPeriodicidadeDiariaEPeriodoDe11Dias(){
		//Entradas
		LocalDate inicio = new LocalDate(2013,12,2);
		LocalDate fim = new LocalDate(2013,12,12);
		LocalTime horario = new LocalTime(17,0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setId(8l);
		List<Sessao> lista = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		//Saida
		assertEquals(11, lista.size());
//		
		int i = 0;
		for (Sessao sessao : lista) {
			assertEquals(sessao.getInicio(), inicio.plusDays(i).toDateTime(horario));
			assertEquals(espetaculo.getId(), sessao.getEspetaculo().getId());
			i++;
		}
	}
	
	@Test 
	public void deveRetornarUmaSessaoQuandoInicioIgualAoFimEPeriodicidadeSemanal(){
		//Entradas
		LocalDate inicio = new LocalDate(2013,12,1);
		LocalDate fim = new LocalDate(2013,12,1);
		LocalTime horario = new LocalTime(17,0);
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> lista = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		//Saida
		assertEquals(1, lista.size());
		assertEquals(lista.get(0).getInicio(), inicio.toDateTime(horario));
	}
	
	@Test 
	public void deveRetornar2SessoesQuandoPeriodicidadeSemanalEPeriodoDe11Dias(){
		//Entradas
		LocalDate inicio = new LocalDate(2013,12,2);
		LocalDate fim = new LocalDate(2013,12,12);
		LocalTime horario = new LocalTime(17,0);
		Periodicidade periodicidade = Periodicidade.SEMANAL;
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setId(8l);
		List<Sessao> lista = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		//Saida
		assertEquals(2, lista.size());
//		
		int i = 0;
		for (Sessao sessao : lista) {
			assertEquals(sessao.getInicio(), inicio.plusWeeks(i).toDateTime(horario));
			assertEquals(espetaculo.getId(), sessao.getEspetaculo().getId());
			i++;
		}
	}
	
	@Test 
	public void deveRetornarSeHorarioEhSessaoNoturna(){
		//Entradas
		LocalDate inicio = new LocalDate(2013,12,2);
		LocalDate fim = new LocalDate(2013,12,2);
		LocalTime horario = new LocalTime(21,0);
		Periodicidade periodicidade = Periodicidade.DIARIA;
		
		//Processamento
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> lista = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		//	Verifica se sessao eh noturna
		
		
		
		int i = 0;
		for (Sessao sessao : lista) {
//			assertTrue(horario.isAfter(sessao.getInicio().toLocalTime()));
			
			assertEquals(horario, sessao.getInicio().toLocalTime());
			i++;
		}
	}
	
	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
}
