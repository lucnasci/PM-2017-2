package enums;

public enum DisciplineResultEnum {
	MATRICULA("ASC"), APROVADO("APV"), REPROVADO("REP"), REPROVADO_POR_FALTA("REF"), DISPENSADO_COM_NOTA(
			"ADI"), DISPENSADO_SEM_NOTA("DIS"), NAO_CURSADA("");

	private String text;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            Set the DisciplineResultColorEnum name based on the given text
	 */
	DisciplineResultEnum(String text) {
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
	public static DisciplineResultEnum fromString(String text) {
		for (DisciplineResultEnum b : DisciplineResultEnum.values())
			if (b.text.equalsIgnoreCase(text))
				return b;
		return null;
	}
}
