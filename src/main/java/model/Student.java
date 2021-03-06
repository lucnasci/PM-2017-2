package model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Student {
	private String registrationCode;
	private int admissionYear;
	private int admissionSemester;
	private int semestersTaken;
	private float accumulatedEfficiencyCoefficient;
}