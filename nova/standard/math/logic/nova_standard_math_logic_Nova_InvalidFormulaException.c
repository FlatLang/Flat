#include <precompiled.h>
#include <nova/standard/math/logic/nova_standard_math_logic_Nova_InvalidFormulaException.h>

nova_standard_math_logic_Extension_VTable_InvalidFormulaException nova_standard_math_logic_Extension_VTable_InvalidFormulaException_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_math_logic_Nova_InvalidFormulaExceptionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_logic_Nova_InvalidFormulaException* nova_standard_math_logic_Nova_InvalidFormulaException_4_Nova_construct(nova_standard_math_logic_Nova_InvalidFormulaException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_logic_Nova_InvalidFormulaException_Nova_message)
{
	CCLASS_NEW(nova_standard_math_logic_Nova_InvalidFormulaException, this,);
	this->vtable = &nova_standard_math_logic_Extension_VTable_InvalidFormulaException_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_exception_Nova_Exception_Nova_super((nova_standard_exception_Nova_Exception*)this, exceptionData);
	nova_standard_math_logic_Nova_InvalidFormulaException_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_logic_Nova_InvalidFormulaException_4_Nova_this(this, exceptionData, nova_standard_math_logic_Nova_InvalidFormulaException_Nova_message);
	}
	
	return this;
}

void nova_standard_math_logic_Nova_InvalidFormulaException_Nova_destroy(nova_standard_math_logic_Nova_InvalidFormulaException** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_math_logic_Nova_InvalidFormulaException_4_Nova_this(nova_standard_math_logic_Nova_InvalidFormulaException* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_logic_Nova_InvalidFormulaException_Nova_message)
{
	nova_standard_exception_Nova_Exception_4_Nova_this((nova_standard_exception_Nova_Exception*)(this), exceptionData, nova_standard_math_logic_Nova_InvalidFormulaException_Nova_message);
}

void nova_standard_math_logic_Nova_InvalidFormulaException_0_Nova_super(nova_standard_math_logic_Nova_InvalidFormulaException* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

