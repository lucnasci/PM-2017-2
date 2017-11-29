package model;

import enums.DisciplineResultEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Discipline {
	private String code;
	private DisciplineResultEnum situation;

	/**
	 * Constructor that receives only the discipline code and creates a discipline
	 * with the situation null
	 * 
	 * @param code
	 */
	public Discipline(String code) {
		this.code = code;
		this.situation = null;
	}
}
