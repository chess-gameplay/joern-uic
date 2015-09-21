package languages.c.parsing.Modules.builder;

import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;

import ast.ASTNodeBuilder;
import ast.expressions.Identifier;
import ast.functionDef.FunctionDef;
import ast.functionDef.ParameterList;
import ast.functionDef.ReturnType;
import ast.logical.statements.CompoundStatement;
import languages.c.antlr.ModuleParser.Function_nameContext;
import languages.c.antlr.ModuleParser.Function_param_listContext;
import languages.c.antlr.ModuleParser.Parameter_declContext;
import languages.c.antlr.ModuleParser.Return_typeContext;
import languages.c.parsing.ASTNodeFactory;
import languages.c.parsing.Functions.builder.ParameterListBuilder;
import parsing.ParseTreeUtils;

public class FunctionDefBuilder extends ASTNodeBuilder
{

	FunctionDef thisItem;
	ParameterListBuilder paramListBuilder = new ParameterListBuilder();

	@Override
	public void createNew(ParserRuleContext ctx)
	{
		item = new FunctionDef();
		ASTNodeFactory.initializeFromContext(item, ctx);
		thisItem = (FunctionDef) item;
	}

	public void setName(Function_nameContext ctx,
			Stack<ASTNodeBuilder> itemStack)
	{
		thisItem.setName(new Identifier());
		ASTNodeFactory.initializeFromContext(thisItem.getName(), ctx);
	}

	public void setReturnType(Return_typeContext ctx,
			Stack<ASTNodeBuilder> itemStack)
	{
		ReturnType returnType = new ReturnType();
		ASTNodeFactory.initializeFromContext(returnType, ctx);
		returnType
				.setBaseType(ParseTreeUtils.childTokenString(ctx.type_name()));
		returnType.setCompleteType(ParseTreeUtils.childTokenString(ctx));
		thisItem.setReturnType(returnType);
	}

	public void setParameterList(Function_param_listContext ctx,
			Stack<ASTNodeBuilder> itemStack)
	{
		paramListBuilder.createNew(ctx);
		thisItem.setParameterList((ParameterList) paramListBuilder.getItem());
	}

	public void addParameter(Parameter_declContext ctx,
			Stack<ASTNodeBuilder> itemStack)
	{
		paramListBuilder.addParameter(ctx, itemStack);
	}

	public void setContent(CompoundStatement functionContent)
	{
		thisItem.setContent(functionContent);
	}

}
