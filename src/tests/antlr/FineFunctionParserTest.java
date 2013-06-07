package tests.antlr;

import static org.junit.Assert.assertTrue;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import parsing.FineFunctionParser;

public class FineFunctionParserTest {

	@Test
	public void testIf()
	{
		String input = "if(foo){}";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		System.out.println(output);
		assertTrue(output.contains("(selection_or_iteration if"));
	}

	@Test
	public void testStructInFunc()
	{
		String input = "class foo{ int x; };";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		assertTrue(output.contains("class_def"));
	}
	
	@Test
	public void testFunctionCall()
	{
		String input = "foo(x);";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		assertTrue(output.contains("function_argument_list"));
	}
	
	@Test
	public void testTwoParameters()
	{
		String input = "foo(x,y);";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		assertTrue(output.contains(", (function_argument"));
	}
	
	@Test
	public void testCallViaPtr()
	{
		String input = "ptr->foo(x);";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		assertTrue(output.contains("function_argument_list"));
	}
	
	@Test
	public void testCallWithExprInArg()
	{
		String input = "foo(x == 1, x++);";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		assertTrue(output.contains("function_argument_list"));
	}
	
	@Test
	public void testAssignmentExpr()
	{
		String input = "x = y + 1;";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		System.out.println(output);
		assertTrue(output.contains("assign_expr"));
	}
	
	@Test
	public void testComplexAssignment()
	{
		String input = "k += ((c = text[k]) >= sBMHCharSetSize) ? patlen : skip[c];";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		System.out.println(output);
		assertTrue(output.contains("assign_expr"));
	}
	
	@Test
	public void testDeclInFor()
	{
		String input = "for(int k = 0; k < 10; k++ ){}";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		System.out.println(output);
		assertTrue(output.contains("for ( (for_init_statement (simple_decl (var_decl (type_name (base_type int))"));
	}
	
	@Test
	public void testEmptyFor()
	{
		String input = "for(; ;){}";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		System.out.println(output);
		assertTrue(output.contains("for_init_statement"));
	}
	
	@Test
	public void testSizeofStruct()
	{
		String input = "while((buffer + len) > (tmp + sizeof(struct stun_attrib))) {}";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		assertTrue(output.contains("selection_or_iteration while"));
	}
	
	@Test
	public void testComplexFor()
	{
		String input = "for(int k = 0; k < 10; ( k += ((c = text[k]) >= sBMHCharSetSize) ? patlen : skip[c]) ){}";
		FineFunctionParser functionParser = new FineFunctionParser();
		ParseTree tree = functionParser.parseString(input);
		String output = tree.toStringTree(functionParser.getParser());
		System.out.println(output);
		assertTrue(output.contains("assign_expr"));
	}
	
}
