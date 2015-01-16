#include <precompiled.h>
#include <nova/standard/database/nova_standard_database_Nova_ResultSet.h>


nova_standard_database_VTable_ResultSet nova_standard_database_VTable_ResultSet_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_database_Nova_ResultSetNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_database_Nova_ResultSet* nova_standard_database_Nova_ResultSet_Nova_construct(nova_standard_database_Nova_ResultSet* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String*** l0_Nova_rows, int l0_Nova_numRows, int l0_Nova_numCols)
{
	CCLASS_NEW(nova_standard_database_Nova_ResultSet, this,);
	this->vtable = &nova_standard_database_VTable_ResultSet_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_database_Nova_ResultSet_Nova_super(this, exceptionData);
	
	{
		nova_standard_database_Nova_ResultSet_Nova_this(this, exceptionData, l0_Nova_rows, l0_Nova_numRows, l0_Nova_numCols);
	}
	
	return this;
}

void nova_standard_database_Nova_ResultSet_Nova_destroy(nova_standard_database_Nova_ResultSet** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->nova_standard_database_Nova_ResultSet_Nova_rows);
	
	NOVA_FREE(*this);
}

void nova_standard_database_Nova_ResultSet_Nova_this(nova_standard_database_Nova_ResultSet* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String*** l0_Nova_rows, int l0_Nova_numRows, int l0_Nova_numCols)
{
	this->nova_standard_database_Nova_ResultSet_Nova_rows = l0_Nova_rows;
	this->nova_standard_database_Nova_ResultSet_Nova_numRows = l0_Nova_numRows;
	this->nova_standard_database_Nova_ResultSet_Nova_numCols = l0_Nova_numCols;
}

void nova_standard_database_Nova_ResultSet_Nova_super(nova_standard_database_Nova_ResultSet* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_database_Nova_ResultSet_Nova_numRows = 0;
	this->nova_standard_database_Nova_ResultSet_Nova_numCols = 0;
	this->nova_standard_database_Nova_ResultSet_Nova_rows = (nova_standard_Nova_String***)nova_null;
}

