import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class TesteJubilamentoAluno {

	@Test
	public void test() {
		Random random = new Random();

		float accumulatedCR = (float) random.nextInt(11);
		
		int disciplineNumberOfFailures = random.nextInt();

		if(accumulatedCR >= 4.0)
			System.out.println("Não jubilado");
			//assert a ser criado para aluno sem condições de jubilamento
		else {
			if(disciplineNumberOfFailures >= 4)
				System.out.println("Jubilado");
				//assert a ser criado para aluno com condições de jubilamento
			else
				System.out.println("Não jubilado");
				//assert a ser criado para aluno sem condições de jubilamento
		}
	}
}
