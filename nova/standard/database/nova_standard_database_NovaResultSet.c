#include <precompiled.h>
#include <nova/standard/database/nova_standard_database_NovaResultSet.h>


nova_VTable_nova_standard_database_NovaResultSet nova_VTable_nova_standard_database_NovaResultSet_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_database_NovaResultSetNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_database_NovaResultSet* nova_standard_database_NovaResultSet_Novaconstruct(nova_standard_database_NovaResultSet* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString*** l0_Novarows, int l0_NovanumRows, int l0_NovanumCols)
{
	CCLASS_NEW(nova_standard_database_NovaResultSet, this,);
	this->vtable = &nova_VTable_nova_standard_database_NovaResultSet_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_database_NovaResultSet_Novasuper(this, 0);
	
	{
		nova_standard_database_NovaResultSet_Novathis(this, exceptionData, l0_Novarows, l0_NovanumRows, l0_NovanumCols);
	}
	
	return this;
}

void nova_del_ResultSet(nova_standard_database_NovaResultSet** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->nova_standard_database_NovaResultSet_Novarows);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_database_NovaResultSet_Novathis(nova_standard_database_NovaResultSet* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString*** l0_Novarows, int l0_NovanumRows, int l0_NovanumCols)
{
	this->nova_standard_database_NovaResultSet_Novarows = l0_Novarows;
	this->nova_standard_database_NovaResultSet_NovanumRows = l0_NovanumRows;
	this->nova_standard_database_NovaResultSet_NovanumCols = l0_NovanumCols;
}

void nova_standard_database_NovaResultSet_Novasuper(nova_standard_database_NovaResultSet* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaResultSet_NovanumRows = 0;
	this->nova_standard_database_NovaResultSet_NovanumCols = 0;
	this->nova_standard_database_NovaResultSet_Novarows = (nova_standard_NovaString***)nova_null;
}
