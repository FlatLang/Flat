#include <precompiled.h>
#include <compiler/util/compiler_util_Nova_FileUtils.h>



compiler_util_Extension_VTable_FileUtils compiler_util_Extension_VTable_FileUtils_val =
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


void compiler_util_Nova_FileUtils_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_util_Nova_FileUtils* compiler_util_Nova_FileUtils_Nova_construct(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_util_Nova_FileUtils, this,);
	this->vtable = &compiler_util_Extension_VTable_FileUtils_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_FileUtils_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_FileUtils_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_util_Nova_FileUtils_Nova_destroy(compiler_util_Nova_FileUtils** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_Nova_String* compiler_util_Nova_FileUtils_Nova_formatPath(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_util_Nova_FileUtils_Nova_path)
{
	compiler_util_Nova_FileUtils_Nova_path = nova_Nova_String_Nova_replace(compiler_util_Nova_FileUtils_Nova_path, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\\")), nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("/")));
	compiler_util_Nova_FileUtils_Nova_path = compiler_util_Nova_FileUtils_Nova_formAbsolutePath(0, exceptionData, compiler_util_Nova_FileUtils_Nova_path);
	if (compiler_util_Nova_OS_Accessor_Nova_isWindows(0, exceptionData))
	{
		compiler_util_Nova_FileUtils_Nova_path = compiler_util_Nova_StringUtils_Nova_removeSurroundingQuotes(0, exceptionData, compiler_util_Nova_FileUtils_Nova_path);
		return nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, '"')), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(compiler_util_Nova_FileUtils_Nova_path), exceptionData, nova_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, '"')));
	}
	else
	{
		return compiler_util_Nova_FileUtils_Nova_escapeSpaces(0, exceptionData, compiler_util_Nova_FileUtils_Nova_path);
	}
}

nova_Nova_String* compiler_util_Nova_FileUtils_Nova_formAbsolutePath(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_util_Nova_FileUtils_Nova_path)
{
	return compiler_util_Nova_FileUtils_Nova_path;
}

nova_Nova_String* compiler_util_Nova_FileUtils_Nova_escapeSpaces(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_util_Nova_FileUtils_Nova_input)
{
	return compiler_util_Nova_FileUtils_Nova_input;
}

nova_Nova_String* compiler_util_Nova_FileUtils_Nova_getWorkingDirectoryPath(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
}

void compiler_util_Nova_FileUtils_0_Nova_this(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_util_Nova_FileUtils_Nova_super(compiler_util_Nova_FileUtils* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

