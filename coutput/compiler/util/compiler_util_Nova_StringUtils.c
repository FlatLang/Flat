#include <precompiled.h>
#include <compiler/util/compiler_util_Nova_StringUtils.h>



compiler_util_Extension_VTable_StringUtils compiler_util_Extension_VTable_StringUtils_val =
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


void compiler_util_Nova_StringUtils_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_util_Nova_StringUtils* compiler_util_Nova_StringUtils_Nova_construct(compiler_util_Nova_StringUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_util_Nova_StringUtils, this,);
	this->vtable = &compiler_util_Extension_VTable_StringUtils_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_StringUtils_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_StringUtils_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_util_Nova_StringUtils_Nova_destroy(compiler_util_Nova_StringUtils** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

char compiler_util_Nova_StringUtils_Nova_isSurroundedByQuotes(compiler_util_Nova_StringUtils* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_util_Nova_StringUtils_Nova_input)
{
	return (char)compiler_util_Nova_StringUtils_Nova_input->nova_Nova_String_Nova_count >= 2 && nova_Nova_String_Nova_charAt(compiler_util_Nova_StringUtils_Nova_input, exceptionData, 0) == '"' && nova_Nova_String_Nova_charAt(compiler_util_Nova_StringUtils_Nova_input, exceptionData, compiler_util_Nova_StringUtils_Nova_input->nova_Nova_String_Nova_count - 1) == '"';
}

nova_Nova_String* compiler_util_Nova_StringUtils_Nova_removeSurroundingQuotes(compiler_util_Nova_StringUtils* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_util_Nova_StringUtils_Nova_input)
{
	while (compiler_util_Nova_StringUtils_Nova_isSurroundedByQuotes(0, exceptionData, compiler_util_Nova_StringUtils_Nova_input))
	{
		compiler_util_Nova_StringUtils_Nova_input = nova_Nova_String_0_Nova_substring(compiler_util_Nova_StringUtils_Nova_input, exceptionData, 1, compiler_util_Nova_StringUtils_Nova_input->nova_Nova_String_Nova_count - 1);
	}
	return compiler_util_Nova_StringUtils_Nova_input;
}

void compiler_util_Nova_StringUtils_0_Nova_this(compiler_util_Nova_StringUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_util_Nova_StringUtils_Nova_super(compiler_util_Nova_StringUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

