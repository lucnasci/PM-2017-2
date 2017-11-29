package enums;

public enum DisciplineResultColorEnum {
	APROVADO("#94EF00"), REPROVADO("#FF1800"), MATRICULA("#FFDA00");

	private String text;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            Set the DisciplineResultColorEnum name based on the given text
	 */
	DisciplineResultColorEnum(String text) {
		this.text = text;
	}

	/**
	 * @return the name of the DisciplineResultColorEnum
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @param text
	 * @return the DisciplineResultColorEnum equivalent for the given text. If
	 *         there's none returns null
	 */
	public static DisciplineResultColorEnum fromString(String text) {
		for (DisciplineResultColorEnum disciplineResultColorEnum : DisciplineResultColorEnum.values())
			if (disciplineResultColorEnum.text.equalsIgnoreCase(text))
				return disciplineResultColorEnum;
		return null;
	}
}
