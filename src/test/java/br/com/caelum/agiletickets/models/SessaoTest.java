package br.com.caelum.agiletickets.models;

import org.junit.Assert;
import org.junit.Test;

public class SessaoTest {


	@Test
	public void deveVender1ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(2);

        Assert.assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void naoDeveVender3ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		Assert.assertFalse(sessao.podeReservar(3));
	}

	@Test
	public void reservarIngressosDeveDiminuirONumeroDeIngressosDisponiveis() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		sessao.reserva(3);
		Assert.assertEquals(2, sessao.getIngressosDisponiveis().intValue());
	}
	
	@Test

	public void reservarTodaQuantidadeDeIngressosDisponivel() throws Exception{
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		Assert.assertEquals(true, sessao.podeReservar(5));
	}
	
	@Test
	public void naoPodeSerReservadaQuantidadeDeIngressosNula() throws Exception{
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);
		Assert.assertEquals(false, sessao.podeReservar(null));
	}

	public void deveVenderSeingressoDisponiveisIgualAoSolicitado() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);
		Assert.assertTrue(sessao.podeReservar(sessao.getIngressosDisponiveis()));
	}
}
