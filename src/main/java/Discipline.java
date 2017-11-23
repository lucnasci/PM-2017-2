import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Discipline {
	private String code;
	private DisciplineResultEnum situation;
}
