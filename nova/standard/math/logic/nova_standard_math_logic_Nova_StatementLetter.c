#include <precompiled.h>
#include <nova/standard/math/logic/nova_standard_math_logic_Nova_StatementLetter.h>

nova_standard_math_logic_Extension_VTable_StatementLetter nova_standard_math_logic_Extension_VTable_StatementLetter_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_Nova_String* nova_standard_math_logic_Nova_StatementLetter_Nova_letter;
	nova_standard_Nova_String* nova_standard_math_logic_Nova_StatementLetter_Nova_representation;
	
)
void nova_standard_math_logic_Nova_StatementLetterNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_logic_Nova_StatementLetter* nova_standard_math_logic_Nova_StatementLetter_Nova_construct(nova_standard_math_logic_Nova_StatementLetter* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_logic_Nova_StatementLetter_Nova_letter, nova_standard_Nova_String* nova_standard_math_logic_Nova_StatementLetter_Nova_representation)
{
	CCLASS_NEW(nova_standard_math_logic_Nova_StatementLetter, this);
	this->vtable = &nova_standard_math_logic_Extension_VTable_StatementLetter_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_logic_Nova_StatementComponent_Nova_super((nova_standard_math_logic_Nova_StatementComponent*)this, exceptionData);
	nova_standard_math_logic_Nova_StatementLetter_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_logic_Nova_StatementLetter_Nova_this(this, exceptionData, nova_standard_math_logic_Nova_StatementLetter_Nova_letter, nova_standard_math_logic_Nova_StatementLetter_Nova_representation);
	}
	
	return this;
}

void nova_standard_math_logic_Nova_StatementLetter_Nova_destroy(nova_standard_math_logic_Nova_StatementLetter** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_Nova_String_Nova_destroy(&(*this)->prv->nova_standard_math_logic_Nova_StatementLetter_Nova_letter, exceptionData);
	nova_standard_Nova_String_Nova_destroy(&(*this)->prv->nova_standard_math_logic_Nova_StatementLetter_Nova_representation, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_math_logic_Nova_StatementLetter_Nova_this(nova_standard_math_logic_Nova_StatementLetter* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_logic_Nova_StatementLetter_Nova_letter, nova_standard_Nova_String* nova_standard_math_logic_Nova_StatementLetter_Nova_representation)
{
	this->prv->nova_standard_math_logic_Nova_StatementLetter_Nova_letter = nova_standard_math_logic_Nova_StatementLetter_Nova_letter;
	this->prv->nova_standard_math_logic_Nova_StatementLetter_Nova_representation = nova_standard_math_logic_Nova_StatementLetter_Nova_representation;
}

void nova_standard_math_logic_Nova_StatementLetter_2_Nova_super(nova_standard_math_logic_Nova_StatementLetter* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_math_logic_Nova_StatementLetter_Nova_letter = (nova_standard_Nova_String*)nova_null;
	this->prv->nova_standard_math_logic_Nova_StatementLetter_Nova_representation = (nova_standard_Nova_String*)nova_null;
}

