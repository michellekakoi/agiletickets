package br.com.caelum.agiletickets.domain;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Sessao;

public interface CriadorDeSessoes {
	
	List<Sessao> cria(LocalDate inicio, LocalDate fim, LocalTime horario, Espetaculo espetaculo);

}
