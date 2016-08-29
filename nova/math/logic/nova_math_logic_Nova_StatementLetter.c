#include <precompiled.h>
#include <nova/math/logic/nova_math_logic_Nova_StatementLetter.h>



nova_math_logic_Extension_VTable_StatementLetter nova_math_logic_Extension_VTable_StatementLetter_val =
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
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_Nova_String* nova_math_logic_Nova_StatementLetter_Nova_letter;
	nova_Nova_String* nova_math_logic_Nova_StatementLetter_Nova_representation;
	
)
void nova_math_logic_Nova_StatementLetter_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_logic_Nova_StatementLetter* nova_math_logic_Nova_StatementLetter_Nova_construct(nova_math_logic_Nova_StatementLetter* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_logic_Nova_StatementLetter_Nova_letter, nova_Nova_String* nova_math_logic_Nova_StatementLetter_Nova_representation)
{
	CCLASS_NEW(nova_math_logic_Nova_StatementLetter, this);
	this->vtable = &nova_math_logic_Extension_VTable_StatementLetter_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_logic_Nova_StatementComponent_Nova_super((nova_math_logic_Nova_StatementComponent*)this, exceptionData);
	nova_math_logic_Nova_StatementLetter_0_Nova_super(this, exceptionData);
	
	{
		nova_math_logic_Nova_StatementLetter_Nova_this(this, exceptionData, nova_math_logic_Nova_StatementLetter_Nova_letter, nova_math_logic_Nova_StatementLetter_Nova_representation);
	}
	
	return this;
}

void nova_math_logic_Nova_StatementLetter_Nova_destroy(nova_math_logic_Nova_StatementLetter** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_Nova_String_Nova_destroy(&(*this)->prv->nova_math_logic_Nova_StatementLetter_Nova_letter, exceptionData);
	nova_Nova_String_Nova_destroy(&(*this)->prv->nova_math_logic_Nova_StatementLetter_Nova_representation, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_logic_Nova_StatementLetter_Nova_this(nova_math_logic_Nova_StatementLetter* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_logic_Nova_StatementLetter_Nova_letter, nova_Nova_String* nova_math_logic_Nova_StatementLetter_Nova_representation)
{
	this->prv->nova_math_logic_Nova_StatementLetter_Nova_letter = nova_math_logic_Nova_StatementLetter_Nova_letter;
	this->prv->nova_math_logic_Nova_StatementLetter_Nova_representation = nova_math_logic_Nova_StatementLetter_Nova_representation;
}

void nova_math_logic_Nova_StatementLetter_0_Nova_super(nova_math_logic_Nova_StatementLetter* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_math_logic_Nova_StatementLetter_Nova_letter = (nova_Nova_String*)nova_null;
	this->prv->nova_math_logic_Nova_StatementLetter_Nova_representation = (nova_Nova_String*)nova_null;
}

