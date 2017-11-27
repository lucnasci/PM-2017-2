package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Student {
	private String registration;
	private int entryYear;
	private int admissionSemester;
	private float cumulativeYieldCoefficient;
}