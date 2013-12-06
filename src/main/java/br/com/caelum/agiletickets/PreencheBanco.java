package br.com.caelum.agiletickets;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;

import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Estabelecimento;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;
import br.com.caelum.vraptor.util.jpa.EntityManagerFactoryCreator;

public class PreencheBanco {
	
	EntityManagerFactoryCreator creator;
	EntityManagerCreator managerCreator;
	EntityManager manager;
	
	public PreencheBanco(){
		inicializa();
	}

	public static void main(String[] args) {

		EntityManagerFactoryCreator creator = new EntityManagerFactoryCreator();
		creator.create();
		EntityManagerCreator managerCreator = new EntityManagerCreator(creator.getInstance());
		managerCreator.create();
		EntityManager manager = managerCreator.getInstance();

		manager.getTransaction().begin();
		executaUpdate(manager, "delete from Sessao");
		executaUpdate(manager, "delete from Espetaculo");
		executaUpdate(manager, "delete from Estabelecimento");
		Estabelecimento estabelecimento = criaEstabelecimento();

		Espetaculo espetaculo = criaEspetaculo(estabelecimento);

		manager.persist(estabelecimento);
		manager.persist(espetaculo);

		new PreencheBanco();
	}
	
	public void realizaInsercoes(){
		createQueries();
		Estabelecimento estabelecimento = criaEstabelecimento();
		Espetaculo espetaculo = criaEspetaculo(estabelecimento);
		persist(estabelecimento, espetaculo);
		populaSessoes(espetaculo);
	}


	private void populaSessoes(Espetaculo espetaculo) {
		for (int i = 0; i < 10; i++) {

			criaSessao(manager, espetaculo, i);

			Sessao sessao = new Sessao();
			sessao.setEspetaculo(espetaculo);
			sessao.setInicio(new DateTime().plusDays(7+i));
			sessao.setDuracaoEmMinutos(60 * 3);
			sessao.setTotalIngressos(10);
			sessao.setIngressosReservados(10 - i);
			sessao.setPreco(new BigDecimal("12.34"));
			manager.persist(sessao);

		}
	}

	private void persist(Estabelecimento estabelecimento, Espetaculo espetaculo) {
		manager.persist(estabelecimento);
		manager.persist(espetaculo);
	}

	private void createQueries() {
		manager.createQuery("delete from Sessao").executeUpdate();
		manager.createQuery("delete from Espetaculo").executeUpdate();
		manager.createQuery("delete from Estabelecimento").executeUpdate();
	}
	
	public void inicializa(){
		creator = new EntityManagerFactoryCreator();
		creator.create();
		managerCreator = new EntityManagerCreator(creator.getInstance());
		managerCreator.create();
		manager = managerCreator.getInstance();
		manager.getTransaction().begin();
		realizaInsercoes();
		manager.getTransaction().commit();
		manager.close();
	}

	private static void criaSessao(EntityManager manager,
			Espetaculo espetaculo, int i) {
		Sessao sessao = new Sessao();
		sessao.setEspetaculo(espetaculo);
		sessao.setInicio(new DateTime().plusDays(7+i));
		sessao.setDuracaoEmMinutos(60 * 3);
		sessao.setTotalIngressos(10);
		sessao.setIngressosReservados(10 - i);
		manager.persist(sessao);
	}

	private static Espetaculo criaEspetaculo(Estabelecimento estabelecimento) {
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setEstabelecimento(estabelecimento);
		espetaculo.setNome("Depeche Mode");
		espetaculo.setTipo(TipoDeEspetaculo.SHOW);
		return espetaculo;
	}

	private static Estabelecimento criaEstabelecimento() {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome("Casa de shows");
		estabelecimento.setEndereco("Rua dos Silveiras, 12345");
		return estabelecimento;
	}

	private static void executaUpdate(EntityManager manager, String sql) {
		manager.createQuery(sql).executeUpdate();
	}
}
