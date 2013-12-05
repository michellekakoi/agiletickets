package br.com.caelum.agiletickets.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Sessao;

public class CriadorDeSessoesSemanal implements CriadorDeSessoes {

	@Override
	public List<Sessao> cria(LocalDate inicio, LocalDate fim,
			LocalTime horario, Espetaculo espetaculo) {
		int i = 0;
		List<Sessao> lista = new ArrayList<Sessao>();
		Weeks weeks = Weeks.weeksBetween(inicio, fim);
		do{
			Sessao sessao = new Sessao();
			sessao.setEspetaculo(espetaculo);
			sessao.setInicio(inicio.toDateTime(horario).plusWeeks(i));
			
			lista.add(sessao);
			i++;
		}while(i <= weeks.getWeeks());
		return lista;
	}

}
