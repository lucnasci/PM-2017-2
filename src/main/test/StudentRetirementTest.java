import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class StudentRetirementTest {

	@Test
	public void test() {
		Random random = new Random();

		float accumulatedCR = (float) cra.nextInt(11);
		
		int disciplineNumberOfFailures = random.nextInt();

		if(accumulatedCR >= 4.0)
			//assert de aluno sem condições de jubilamento
		else {
			if(disciplineNumberOfFailures >= 4)
				//assert de aluno com condições de jubilamento
			else
				//assert de aluno sem condições de jubilamento
		}
	}

}
