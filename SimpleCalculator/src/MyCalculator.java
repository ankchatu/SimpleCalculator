import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyCalculator {

	private static final String VALID_MATH_EXPRESSION = "([-+]?[0-9]*\\.?[0-9]+\\s*+[\\/\\+\\-\\*])+\\s*+([-+]?[0-9]*\\.?[0-9]+)";
	private static final String DIVIDE = "/";
	private static final String MULTIPLY = "*";
	private static final String MINUS = "-";
	private static final String PLUS = "+";
	private static final String ALL_OPERATORS = "+-*/";
	String routePath = System.getProperty("user.home");


	public static void main(String[] args) {
		
		MyCalculator myCalculator = new MyCalculator();
		myCalculator.startCalculation();
		
	}

	private void startCalculation() {
			   
		String inputFile = routePath+"\\inputFile.txt";
		List<String> stringList = readInputFile(inputFile);
		
		writeToOutputFile(stringList);
	}

	private  void writeToOutputFile(List<String> stringList) {
		try {
			String outputFile = routePath+"\\ouputFile.txt";
			Files.write(Paths.get(outputFile), stringList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// this function will read input file stream
	private List<String> readInputFile(String fileName) {

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			return stream.takeWhile(i -> !i.trim().contains("="))
					.map(i -> processInstruction(i)).collect(Collectors.toList());	        

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String processInstruction(String instruction) {

		String result = "ERROR";
		
		if (isValidInstruction(instruction)) {

			List<String> operatorList = new ArrayList<String>();
			List<String> operandList = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(instruction.trim(), ALL_OPERATORS, true);

			retrieveOperator(operatorList, operandList, st);

			result = executeInstruction(instruction, result, operatorList, operandList);

		}
		return result;
	}

	private String executeInstruction(String instruction, String result, List<String> operatorList,
			List<String> operandList) {
		if (operatorList.get(0).equals(PLUS)) {
			result = instruction + " = "
					+ (Math.addExact(Integer.parseInt(operandList.get(0)), Integer.parseInt(operandList.get(1))));
		} else if (operatorList.get(0).equals(MINUS)) {
			result = instruction + " = " + (Math.subtractExact(Integer.parseInt(operandList.get(0)),
					Integer.parseInt(operandList.get(1))));
		} else if (operatorList.get(0).equals(MULTIPLY)) {
			result = instruction + " = " + (Math.multiplyExact(Integer.parseInt(operandList.get(0)),
					Integer.parseInt(operandList.get(1))));
		} else if (operatorList.get(0).equals(DIVIDE)) {
			result = instruction + " = "
					+ (Math.floorDiv(Integer.parseInt(operandList.get(0)), Integer.parseInt(operandList.get(1))));
		}
		return result;
	}

	private void retrieveOperator(List<String> operatorList, List<String> operandList, StringTokenizer st) {
		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (ALL_OPERATORS.contains(token)) {
				operatorList.add(token);
			} else {
				operandList.add(token);
			}
		}
	}

	private boolean isValidInstruction(String instruction) {
		Pattern pattern = Pattern.compile(VALID_MATH_EXPRESSION);
		return pattern.matcher(instruction.trim()).matches();
	}

}
