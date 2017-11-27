
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Aluno {
	private String matricula;
	private int anoDeIngresso;
	private int semestreDeIngresso;
	private float coeficienteDeRendimentoAcumulado;
}