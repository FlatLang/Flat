#include <precompiled.h>
#include <compiler/tree/compiler_tree_Nova_SyntaxTree.h>



compiler_tree_Extension_VTable_SyntaxTree compiler_tree_Extension_VTable_SyntaxTree_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void compiler_tree_Nova_SyntaxTree_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_tree_Nova_SyntaxTree* compiler_tree_Nova_SyntaxTree_Nova_construct(compiler_tree_Nova_SyntaxTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_tree_Nova_SyntaxTree, this,);
	this->vtable = &compiler_tree_Extension_VTable_SyntaxTree_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_tree_Nova_SyntaxTree_Nova_super(this, exceptionData);
	
	{
		compiler_tree_Nova_SyntaxTree_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_tree_Nova_SyntaxTree_Nova_destroy(compiler_tree_Nova_SyntaxTree** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void compiler_tree_Nova_SyntaxTree_0_Nova_this(compiler_tree_Nova_SyntaxTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_tree_Nova_SyntaxTree_Nova_super(compiler_tree_Nova_SyntaxTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

