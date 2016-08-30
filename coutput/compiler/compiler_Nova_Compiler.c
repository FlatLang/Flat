#include <precompiled.h>
#include <compiler/compiler_Nova_Compiler.h>



compiler_Extension_VTable_Compiler compiler_Extension_VTable_Compiler_val =
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



void compiler_Nova_Compiler_Nova_testClasses(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData);
char compiler_Nova_Compiler_Nova_DEBUG;
void compiler_Nova_Compiler_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_Nova_Compiler* compiler_Nova_Compiler_Nova_construct(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_Nova_Compiler, this,);
	this->vtable = &compiler_Extension_VTable_Compiler_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_Nova_Compiler_Nova_super(this, exceptionData);
	
	{
		compiler_Nova_Compiler_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_Nova_Compiler_Nova_destroy(compiler_Nova_Compiler** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void compiler_Nova_Compiler_Nova_main(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* compiler_Nova_Compiler_Nova_args)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("In compiler")));
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void compiler_Nova_Compiler_0_Nova_this(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_Nova_Compiler_Nova_compile(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* compiler_Nova_Compiler_Nova_args)
{
	nova_Nova_String* l1_Nova_directory = (nova_Nova_String*)nova_null;
	
	l1_Nova_directory = (nova_Nova_String*)(nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(compiler_util_Nova_FileUtils_Nova_getWorkingDirectoryPath(0, exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("/"))));
	if (compiler_Nova_Compiler_Nova_DEBUG)
	{
		compiler_Nova_Compiler_Nova_testClasses(this, exceptionData);
	}
}

void compiler_Nova_Compiler_Nova_testClasses(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void compiler_Nova_Compiler_Nova_super(compiler_Nova_Compiler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

