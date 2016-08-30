#include <precompiled.h>
#include <compiler/util/compiler_util_Nova_OS.h>



compiler_util_Extension_VTable_OS compiler_util_Extension_VTable_OS_val =
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






char compiler_util_Nova_OS_Nova_OS;
char compiler_util_Nova_OS_Nova_WINDOWS;
char compiler_util_Nova_OS_Nova_MACOSX;
char compiler_util_Nova_OS_Nova_LINUX;
nova_Nova_String* compiler_util_Nova_OS_Nova_OUTPUT_EXTENSION;
nova_Nova_String* compiler_util_Nova_OS_Nova_DYNAMIC_LIB_EXT;
void compiler_util_Nova_OS_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		compiler_util_Nova_OS_Nova_WINDOWS = 1;
		compiler_util_Nova_OS_Nova_MACOSX = 2;
		compiler_util_Nova_OS_Nova_LINUX = 3;
	}
	{
		nova_Nova_String* l1_Nova_osName = (nova_Nova_String*)nova_null;
		
		l1_Nova_osName = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("windows"));
		if (nova_Nova_String_Nova_startsWith(l1_Nova_osName, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("win"))))
		{
			compiler_util_Nova_OS_Nova_OS = compiler_util_Nova_OS_Nova_WINDOWS;
			compiler_util_Nova_OS_Nova_OUTPUT_EXTENSION = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(".exe"));
			compiler_util_Nova_OS_Nova_DYNAMIC_LIB_EXT = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(".dll"));
		}
		else if (nova_Nova_String_Nova_startsWith(l1_Nova_osName, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("mac"))))
		{
			compiler_util_Nova_OS_Nova_OS = compiler_util_Nova_OS_Nova_MACOSX;
			compiler_util_Nova_OS_Nova_OUTPUT_EXTENSION = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
			compiler_util_Nova_OS_Nova_DYNAMIC_LIB_EXT = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(".dylib"));
		}
		else if (nova_Nova_String_Nova_startsWith(l1_Nova_osName, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("lin"))))
		{
			compiler_util_Nova_OS_Nova_OS = compiler_util_Nova_OS_Nova_LINUX;
			compiler_util_Nova_OS_Nova_OUTPUT_EXTENSION = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
			compiler_util_Nova_OS_Nova_DYNAMIC_LIB_EXT = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(".so"));
		}
		else
		{
			compiler_util_Nova_OS_Nova_OS = 0;
			compiler_util_Nova_OS_Nova_OUTPUT_EXTENSION = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
			compiler_util_Nova_OS_Nova_DYNAMIC_LIB_EXT = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
		}
	}
}

compiler_util_Nova_OS* compiler_util_Nova_OS_Nova_construct(compiler_util_Nova_OS* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(compiler_util_Nova_OS, this,);
	this->vtable = &compiler_util_Extension_VTable_OS_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	compiler_util_Nova_OS_Nova_super(this, exceptionData);
	
	{
		compiler_util_Nova_OS_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void compiler_util_Nova_OS_Nova_destroy(compiler_util_Nova_OS** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void compiler_util_Nova_OS_0_Nova_this(compiler_util_Nova_OS* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

char compiler_util_Nova_OS_Accessor_Nova_isWindows(compiler_util_Nova_OS* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return compiler_util_Nova_OS_Nova_OS == compiler_util_Nova_OS_Nova_WINDOWS;
}


char compiler_util_Nova_OS_Accessor_Nova_isMacOsx(compiler_util_Nova_OS* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return compiler_util_Nova_OS_Nova_OS == compiler_util_Nova_OS_Nova_MACOSX;
}


char compiler_util_Nova_OS_Accessor_Nova_isLinux(compiler_util_Nova_OS* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return compiler_util_Nova_OS_Nova_OS == compiler_util_Nova_OS_Nova_LINUX;
}


void compiler_util_Nova_OS_Nova_super(compiler_util_Nova_OS* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

