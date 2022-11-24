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

	// CHECKS

	// DONE
	// if 3 characters - DONE
	// if 1st and 3rd is numeric - DONE
	// improve the logic to check the middle char - DONE
	// check when 33 3 + 5 -> this should be invalid but here it will be 333+5 -
	// DONE
	// add logic for minus, multiply , divide - DONE
	// send back result in a file - DONE

	// TODO

	// execute operation and send back result
	// stop execution if only "=" in a new line
	// make filename relative
	// change program to instance not Static

	public static void main(String[] args) {
		String fileName = "C:\\Users\\AnkChaturvedi\\eclipse-workspace\\SimpleCalculator\\src\\inputFile.txt";
		readInputFile(fileName);
	}

	// this function will read input file stream
	private static void readInputFile(String fileName) {

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			List<String> stringList = stream.flatMap(instruction -> Stream.of(instruction))
					.map(i -> processInstruction(i)).collect(Collectors.toList());
			
		//  Writes the list into the output file
	        Files.write(Paths.get("C:\\Users\\AnkChaturvedi\\eclipse-workspace\\SimpleCalculator\\src\\ouputFile.txt"), stringList);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String processInstruction(String instruction) {
		// TODO Auto-generated method stub
		// System.out.println("instruction Processing...");
		// check if instruction is valid

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

	private static String executeInstruction(String instruction, String result, List<String> operatorList,
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

	private static void retrieveOperator(List<String> operatorList, List<String> operandList, StringTokenizer st) {
		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (ALL_OPERATORS.contains(token)) {
				operatorList.add(token);
			} else {
				operandList.add(token);
			}
		}
	}

	private static boolean isValidInstruction(String instruction) {
		Pattern pattern = Pattern.compile(VALID_MATH_EXPRESSION);
		return pattern.matcher(instruction.trim()).matches();
	}

}
