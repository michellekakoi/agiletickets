
public class Programador implements Cargo {

	@Override
	public double calcula(Funcionario f) {
		return f.getSalario() - (f.getSalario() * 0.25);
	}

}
