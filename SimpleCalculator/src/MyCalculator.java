import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This Class takes an input text file with name inputFile with a </br>
 * list of mathematical statements and provides result in an output file</br>
 * Prerequisite : please provide inputFile with mathematical expressions</br> at your user home directory.
 * 
 * @author AnkChaturvedi
 * 
 */
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

	/**
	 * entry point to the execution logic.
	 */
	private void startCalculation() {
			   
		String inputFile = routePath+"\\inputFile.txt";
		List<String> stringList = readInputFile(inputFile);
		
		writeToOutputFile(stringList);
	}

	/**
	 * writes list of string to output file.
	 * 
	 * @param results calculated results to be printed in output file.
	 */
	private void writeToOutputFile(List<String> results) {
		try {
			String outputFile = routePath+"\\ouputFile.txt";
			Files.write(Paths.get(outputFile), results);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * reads from the filename and initiates calculation logic.
	 * 
	 * @param fileName name of the input file
	 * @return results after calculation completion as a List<String>.
	 */
	private List<String> readInputFile(String fileName) {

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			return stream.takeWhile(i -> !i.trim().contains("="))
					.map(i -> processInstruction(i)).collect(Collectors.toList());	        

		} catch (java.nio.file.NoSuchFileException e) {
			System.out.println("\n Text File with name inputFile not found at user's home directory :" + routePath+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @param instruction single mathematical expression to be calculated
	 * @return result: single math expression calculation.</br>
	 *         ERROR: if invalid mathematical expression.
	 */
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

	/**
	 * Iterates through a single mathematical expression and decides on which type of calculation to be done.
	 * 
	 * @param instruction a single mathematical expression
	 * @param result calculated result for single instruction
	 * @param operatorList operators expression
	 * @param operandList operands in math expression
	 * @return
	 */
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

	/**
	 * splits a mathematical expression into operator and operands.
	 * 
	 * @param operatorList operator in math expression
	 * @param operandList operands in math expression
	 * @param st a single token of instruction
	 */
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

	/**
	 * checks if an instruction is a valid mathematical expression.
	 * 
	 * @param instruction mathematical expression to be validated
	 * @return
	 */
	private boolean isValidInstruction(String instruction) {
		Pattern pattern = Pattern.compile(VALID_MATH_EXPRESSION);
		return pattern.matcher(instruction.trim()).matches();
	}

}
