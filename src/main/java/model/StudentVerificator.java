package model;

public class StudentVerificator {
	private static StudentVerificator studentVerificator = null;

	private StudentVerificator() {
	};

	/**
	 * @return an instance of StudentVerificator, if it's the first time it is
	 *         called than the instance is instantiated.
	 */
	public static StudentVerificator getInstance() {
		if (studentVerificator == null)
			studentVerificator = new StudentVerificator();
		return studentVerificator;
	}

	/**
	 * @param studentAccumulatedEfficiencyCoefficient
	 *            Float number that refers to student acummulated efficiency
	 *            coefficient.
	 */
	public static void verifyStudentAccumulatedEfficiencyCoefficientSituation(
			float studentAccumulatedEfficiencyCoefficient) {
		if (studentAccumulatedEfficiencyCoefficient > 7.0)
			System.out.println("CRA do aluno é maior que 7,0.");
		else
			System.out.println("CRA do aluno é menor que 7,0.");
	}

	/**
	 * @param studentMatriculatedDisciplinesNumber
	 *            Integer that refers to the number of student matriculated
	 *            disciplines.
	 */
	public static void verifyIfStudentIsMatriculatedOnThreeDisciplines(int studentMatriculatedDisciplinesNumber) {
		if (studentMatriculatedDisciplinesNumber >= 3)
			System.out.println("Aluno está matriculado em 3 ou mais disciplinas.");
		else
			System.out.println("Aluno não está matriculado em pelo menos 3 disciplinas.");
	}

	/**
	 * @param studentAdmissionYear
	 *            Integer that refers to the student admission year.
	 */
	public static void verifyStudentGraduationDeadline(int studentAdmissionYear) {
		if (studentAdmissionYear > 2013)
			System.out.println(
					"Prazo máximo para formação do aluno é de 12 semestres, devendo-se pedir a prorrogação no 7º período e não podendo ultrapassar o limite para formação sob nenhuma hipótese.");
		else
			System.out.println(
					"Prazo máximo para formação do aluno é de 14 semestres, sendo necessário pedir prorrogação no 12º período.");
	}

	/**
	 * @param studentAccumulatedEfficiencyCoefficient
	 *            Float number that refers to student acummulated efficiency
	 *            coefficient.
	 * @param disciplineNumberOfTries
	 *            Integer that refers to number of times that the student tries
	 *            to study a discipline.
	 */
	public static void verifyStudentRetirementSituation(float studentAccumulatedEfficiencyCoefficient,
			int disciplineNumberOfTries) {
		if (studentAccumulatedEfficiencyCoefficient >= 4.0)
			System.out.println("Aluno não está em condições de jubilamento.");
		else {
			if (disciplineNumberOfTries > 4)
				System.out.println("Aluno será jubilado.");
			else
				System.out.println("Aluno não está em condições de jubilamento.");
		}
	}
}
