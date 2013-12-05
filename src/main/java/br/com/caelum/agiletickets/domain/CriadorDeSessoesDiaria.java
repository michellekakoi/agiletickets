package br.com.caelum.agiletickets.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Sessao;

public class CriadorDeSessoesDiaria implements CriadorDeSessoes {

	@Override
	public List<Sessao> cria(LocalDate inicio, LocalDate fim,
			LocalTime horario, Espetaculo espetaculo) {
		List<Sessao> lista = new ArrayList<Sessao>();
		int i = 0;
		 Days days = Days.daysBetween(inicio, fim);
		do{
			Sessao sessao = new Sessao();
			sessao.setEspetaculo(espetaculo);
			sessao.setInicio(inicio.toDateTime(horario).plusDays(i));
			
			lista.add(sessao);
			i++;
		}while(i <= days.getDays());
		return lista;
	}

}
