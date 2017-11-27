package enums;

public enum DisciplineResultColorEnum {
	APROVADO("#94EF00"), REPROVADO("#FF1800"), MATRICULA("#FFDA00");

	private String text;

	DisciplineResultColorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static DisciplineResultColorEnum fromString(String text) {
		for (DisciplineResultColorEnum b : DisciplineResultColorEnum.values())
			if (b.text.equalsIgnoreCase(text))
				return b;
		return null;
	}
}
