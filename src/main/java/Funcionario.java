
public class Funcionario  {
	
	private Integer matricula;
	private String nome;
	private double salario;
	private Cargo cargo;
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
}
