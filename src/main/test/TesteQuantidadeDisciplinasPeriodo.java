import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class TesteQuantidadeDisciplinasPeriodo {

	@Test
	public void test() {
		Random random = new Random();
		
		int disciplinesOnPeriod = random.nextInt(8);
		
		boolean ehFormando = random.nextBoolean();
		
		if(ehFormando==true)
			System.out.println("Matrícula regular");
		else {
			if(disciplinesOnPeriod >= 3)
				System.out.println("Matrícula regular");
			else
				System.out.println("Matrícula irregular");
		}
	}
}
