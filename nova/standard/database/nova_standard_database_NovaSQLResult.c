#include <precompiled.h>
#include <nova/standard/database/nova_standard_database_NovaSQLResult.h>


nova_VTable_nova_standard_database_NovaSQLResult nova_VTable_nova_standard_database_NovaSQLResult_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};

nova_standard_database_NovaSQLResult* nova_standard_database_NovaSQLResult_Novaconstruct(nova_standard_database_NovaSQLResult* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString*** l0_Novarows, int l0_NovanumRows, int l0_NovanumCols)
{
	CCLASS_NEW(nova_standard_database_NovaSQLResult, this,);
	this->vtable = &nova_VTable_nova_standard_database_NovaSQLResult_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_database_NovaSQLResult_Novasuper(this, 0);
	
	{
		nova_standard_database_NovaSQLResult_Novathis(this, exceptionData, l0_Novarows, l0_NovanumRows, l0_NovanumCols);
	}
	
	return this;
}

void nova_del_SQLResult(nova_standard_database_NovaSQLResult** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	nova_del_String(&(*this)->nova_standard_database_NovaSQLResult_Novarows, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_database_NovaSQLResult_Novathis(nova_standard_database_NovaSQLResult* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString*** l0_Novarows, int l0_NovanumRows, int l0_NovanumCols)
{
	this->nova_standard_database_NovaSQLResult_Novarows = l0_Novarows;
	this->nova_standard_database_NovaSQLResult_NovanumRows = l0_NovanumRows;
	this->nova_standard_database_NovaSQLResult_NovanumCols = l0_NovanumCols;
}

void nova_standard_database_NovaSQLResult_Novasuper(nova_standard_database_NovaSQLResult* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaSQLResult_NovanumRows = 0;
	this->nova_standard_database_NovaSQLResult_NovanumCols = 0;
	this->nova_standard_database_NovaSQLResult_Novarows = (nova_standard_NovaString***)nova_null;
}
