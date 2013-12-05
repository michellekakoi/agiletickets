
public class CalculadoraDeSalario {
	
	
	public double calcula(Funcionario f){
		return f.getCargo().calcula(f);
	}
	
	public static void main(String[] args) {
		Programador programador = new Programador();
		Funcionario funcionario = new Funcionario();
		funcionario.setSalario(2000);
		funcionario.setCargo(programador);
		CalculadoraDeSalario calc = new CalculadoraDeSalario();
		System.out.println(calc.calcula(funcionario));
	}

}
