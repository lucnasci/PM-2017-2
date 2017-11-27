package enums;
// PRECISA CRIAR UM VALOR PARA CASO A DISCIPLINA NÃO ESTEJA LISTADA NO HISTORICO
// OU SEJA, CASO O VALOR PASSADO PARA O ENUM SEJA NULL.

public enum DisciplineResultEnum {
	MATRICULA("ASC"),
	APROVADO("APV"),
	REPROVADO("REP"),
	REPROVADO_POR_FALTA("REF"),
	DISPENSADO_COM_NOTA("ADI"),
	DISPENSADO_SEM_NOTA("DIS"),
	NAO_CURSADA("");
	
	private String text;
	
	DisciplineResultEnum(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public static DisciplineResultEnum fromString(String text) {
	    for (DisciplineResultEnum b : DisciplineResultEnum.values()) 
	    	if (b.text.equalsIgnoreCase(text)) 
	    		return b;
	    return null;
	 }
}
