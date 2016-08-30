#include <precompiled.h>
#include <compiler/util/compiler_util_Nova_SyntaxUtils.h>



compiler_util_Extension_VTable_SyntaxUtils compiler_util_Extension_VTable_SyntaxUtils_val =
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


void compiler_util_Nova_SyntaxUtils_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_util_Nova_SyntaxUtils* compiler_util_Nova_SyntaxUtils_Nova_construct(compiler_util_Nova_SyntaxUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_util_Nova_SyntaxUtils, this,);
	this->vtable = &compiler_util_Extension_VTable_SyntaxUtils_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_SyntaxUtils_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_SyntaxUtils_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_util_Nova_SyntaxUtils_Nova_destroy(compiler_util_Nova_SyntaxUtils** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void compiler_util_Nova_SyntaxUtils_0_Nova_this(compiler_util_Nova_SyntaxUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_util_Nova_SyntaxUtils_Nova_super(compiler_util_Nova_SyntaxUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

