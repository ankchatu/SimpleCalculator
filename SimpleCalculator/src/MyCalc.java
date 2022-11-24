
public class MyCalc {

	public static void main(String[] args) {

	}

	// this function will read input file stream
	public void readInputFile() {
	}
	
	//can i process the full stream in a lambda expression 
	//1. which divides single file stream in line stream
	//2. each line stream checked for validation , if not error returned
	//3. if valid calculation is done and returned

	// This function should be able to convert input file data
	// in separate strings where calculation is to be done
	public void processInputFile() {
		// We will divide input data into array of strings
		// till i encounter =

		// Each string is further divided in to secondary array of string

		String[] InstructionArray = null;
		// this secondary array should make sense, else should result in ERROR
		for(String singleInstruction : InstructionArray) {
			processSingleInstruction(singleInstruction);	
		}
		
		// method to check secondary array of string is a valid instruction

		// if yes then process , if no write ERROR corresponding to that entry
		// in output file

		// if char is + call add()

	}

	private String processSingleInstruction(String SingleInstruction) {
		// check if Instruction valid
		
		
		if (checkInstructionValid()) {
			
			//if valid we need to divide instruction into variables and operation.
			
			String operator = null;
			int variable1 = 0;
			int variable2 = 0;
			// identify instruction type
			if (operator.equals("+")) {

				return String.valueOf(variable1 + variable2);
			} else if (operator.equals("-")) {

				return String.valueOf(variable1 + variable2);
			} else if (operator.equals("*")) {

				return String.valueOf(variable1 + variable2);
			} else if (operator.equals("/")) {

				return String.valueOf(variable1 + variable2);
			}
		} else {
			return "ERROR";
		}

		return "";

	}

	private boolean checkInstructionValid() {
		// TODO Auto-generated method stub
		return false;

	}

	private static int add(int a, int b) {
		return 0;
	}

	private static int subtract(int a, int b) {
		return 0;
	}

	private static int multiply(int a, int b) {
		return 0;
	}

	private static int divide(int a, int b) {
		return 0;
	}

}
