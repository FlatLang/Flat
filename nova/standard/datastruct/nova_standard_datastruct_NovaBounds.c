#include <precompiled.h>
#include <nova/standard/datastruct/nova_standard_datastruct_NovaBounds.h>


nova_VTable_nova_standard_datastruct_NovaBounds nova_VTable_nova_standard_datastruct_NovaBounds_val =
{
	nova_standard_datastruct_NovaBounds_Nova0_toString,
	nova_standard_datastruct_NovaBounds_Nova0_equals,
};
void nova_standard_datastruct_NovaBoundsNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_datastruct_NovaBounds* nova_standard_datastruct_NovaBounds_Novaconstruct(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novastart, int l0_Novaend)
{
	CCLASS_NEW(nova_standard_datastruct_NovaBounds, this,);
	this->vtable = &nova_VTable_nova_standard_datastruct_NovaBounds_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_datastruct_NovaBounds_Novasuper(this, 0);
	
	{
		nova_standard_datastruct_NovaBounds_Novathis(this, exceptionData, l0_Novastart, l0_Novaend);
	}
	
	return this;
}

void nova_del_Bounds(nova_standard_datastruct_NovaBounds** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_datastruct_NovaBounds_Novathis(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novastart, int l0_Novaend)
{
	this->nova_standard_datastruct_NovaBounds_Novastart = l0_Novastart;
	this->nova_standard_datastruct_NovaBounds_Novaend = l0_Novaend;
}

nova_standard_NovaString* nova_standard_datastruct_NovaBounds_NovaextractString(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasource)
{
	if (!nova_standard_datastruct_NovaBounds_NovaisValid(this, exceptionData))
	{
		return (nova_standard_NovaString*)nova_null;
	}
	return nova_standard_NovaString_Nova0_substring(l0_Novasource, exceptionData, this->nova_standard_datastruct_NovaBounds_Novastart, this->nova_standard_datastruct_NovaBounds_Novaend);
}

nova_standard_NovaString* nova_standard_datastruct_NovaBounds_NovaextractPreString(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasource)
{
	if (!nova_standard_datastruct_NovaBounds_NovaisValid(this, exceptionData))
	{
		return (nova_standard_NovaString*)nova_null;
	}
	return nova_standard_NovaString_Nova0_substring(l0_Novasource, exceptionData, 0, this->nova_standard_datastruct_NovaBounds_Novastart);
}

nova_standard_NovaString* nova_standard_datastruct_NovaBounds_NovaextractPostString(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasource)
{
	if (!nova_standard_datastruct_NovaBounds_NovaisValid(this, exceptionData))
	{
		return l0_Novasource;
	}
	return nova_standard_NovaString_Nova1_substring(l0_Novasource, exceptionData, this->nova_standard_datastruct_NovaBounds_Novaend);
}

nova_standard_NovaString* nova_standard_datastruct_NovaBounds_NovatrimString(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novasource)
{
	nova_standard_NovaString* nova_local_0;
	
	if (!nova_standard_datastruct_NovaBounds_NovaisValid(this, exceptionData))
	{
		return l0_Novasource;
	}
	nova_local_0 = nova_standard_datastruct_NovaBounds_NovaextractPreString(this, exceptionData, l0_Novasource);
	return nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_datastruct_NovaBounds_NovaextractPostString(this, exceptionData, l0_Novasource));
}

char nova_standard_datastruct_NovaBounds_NovaisEndless(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_NovaBounds_Novaend < 0;
}

char nova_standard_datastruct_NovaBounds_NovaisOptional(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_NovaBounds_Novastart == 0;
}

int nova_standard_datastruct_NovaBounds_Novalength(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_NovaBounds_Novaend - this->nova_standard_datastruct_NovaBounds_Novastart;
}

char nova_standard_datastruct_NovaBounds_NovaisValid(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->nova_standard_datastruct_NovaBounds_Novastart >= 0 && this->nova_standard_datastruct_NovaBounds_Novaend > 0;
}

void nova_standard_datastruct_NovaBounds_NovasetInvalid(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaBounds_Novastart = -1;
	this->nova_standard_datastruct_NovaBounds_Novaend = -1;
}

char nova_standard_datastruct_NovaBounds_Nova0_equals(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_datastruct_NovaBounds* l0_Novabounds)
{
	return l0_Novabounds != (nova_standard_datastruct_NovaBounds*)nova_null && l0_Novabounds->nova_standard_datastruct_NovaBounds_Novastart == this->nova_standard_datastruct_NovaBounds_Novastart && l0_Novabounds->nova_standard_datastruct_NovaBounds_Novaend == this->nova_standard_datastruct_NovaBounds_Novaend;
}

nova_standard_NovaString* nova_standard_datastruct_NovaBounds_Nova0_toString(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* nova_local_0;
	nova_standard_NovaString* nova_local_1;
	
	nova_local_0 = nova_standard_primitive_number_NovaInt_Nova2_toString(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, this->nova_standard_datastruct_NovaBounds_Novastart), exceptionData);
	nova_local_1 = nova_standard_primitive_number_NovaInt_Nova2_toString(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, this->nova_standard_datastruct_NovaBounds_Novaend), exceptionData);
	return nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "["), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "]")))));
}

void nova_standard_datastruct_NovaBounds_NovacloneTo(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_datastruct_NovaBounds* l0_Novabounds)
{
	l0_Novabounds->nova_standard_datastruct_NovaBounds_Novastart = this->nova_standard_datastruct_NovaBounds_Novastart;
	l0_Novabounds->nova_standard_datastruct_NovaBounds_Novaend = this->nova_standard_datastruct_NovaBounds_Novaend;
}

nova_standard_datastruct_NovaBounds* nova_standard_datastruct_NovaBounds_Novaclone(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return nova_standard_datastruct_NovaBounds_Novaconstruct(0, exceptionData, this->nova_standard_datastruct_NovaBounds_Novastart, this->nova_standard_datastruct_NovaBounds_Novaend);
}

void nova_standard_datastruct_NovaBounds_Novasuper(nova_standard_datastruct_NovaBounds* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_datastruct_NovaBounds_Novastart = 0;
	this->nova_standard_datastruct_NovaBounds_Novaend = 0;
}
