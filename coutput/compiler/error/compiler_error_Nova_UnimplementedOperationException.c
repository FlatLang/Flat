#include <precompiled.h>
#include <compiler/error/compiler_error_Nova_UnimplementedOperationException.h>



compiler_error_Extension_VTable_UnimplementedOperationException compiler_error_Extension_VTable_UnimplementedOperationException_val =
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


void compiler_error_Nova_UnimplementedOperationException_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

compiler_error_Nova_UnimplementedOperationException* compiler_error_Nova_UnimplementedOperationException_Nova_construct(compiler_error_Nova_UnimplementedOperationException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_error_Nova_UnimplementedOperationException_Nova_message)
{
	CCLASS_NEW(compiler_error_Nova_UnimplementedOperationException, this,);
	this->vtable = &compiler_error_Extension_VTable_UnimplementedOperationException_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_exception_Nova_Exception_Nova_super((nova_exception_Nova_Exception*)this, exceptionData);
	compiler_error_Nova_UnimplementedOperationException_0_Nova_super(this, exceptionData);
	
	{
		compiler_error_Nova_UnimplementedOperationException_4_Nova_this(this, exceptionData, compiler_error_Nova_UnimplementedOperationException_Nova_message);
	}
	
	return this;
}

void compiler_error_Nova_UnimplementedOperationException_Nova_destroy(compiler_error_Nova_UnimplementedOperationException** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void compiler_error_Nova_UnimplementedOperationException_4_Nova_this(compiler_error_Nova_UnimplementedOperationException* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* compiler_error_Nova_UnimplementedOperationException_Nova_message)
{
	nova_exception_Nova_Exception_4_Nova_this((nova_exception_Nova_Exception*)(this), exceptionData, compiler_error_Nova_UnimplementedOperationException_Nova_message);
}

void compiler_error_Nova_UnimplementedOperationException_0_Nova_super(compiler_error_Nova_UnimplementedOperationException* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

