#include <precompiled.h>
#include <nova/database/nova_database_Nova_ResultSet.h>



nova_database_Extension_VTable_ResultSet nova_database_Extension_VTable_ResultSet_val =
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


void nova_database_Nova_ResultSet_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_database_Nova_ResultSet* nova_database_Nova_ResultSet_Nova_construct(nova_database_Nova_ResultSet* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_database_Nova_ResultSet_Nova_rows, int nova_database_Nova_ResultSet_Nova_numCols)
{
	CCLASS_NEW(nova_database_Nova_ResultSet, this,);
	this->vtable = &nova_database_Extension_VTable_ResultSet_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_database_Nova_ResultSet_Nova_super(this, exceptionData);
	
	{
		nova_database_Nova_ResultSet_Nova_this(this, exceptionData, nova_database_Nova_ResultSet_Nova_rows, nova_database_Nova_ResultSet_Nova_numCols);
	}
	
	return this;
}

void nova_database_Nova_ResultSet_Nova_destroy(nova_database_Nova_ResultSet** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->nova_database_Nova_ResultSet_Nova_rows, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_database_Nova_ResultSet_Nova_this(nova_database_Nova_ResultSet* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* nova_database_Nova_ResultSet_Nova_rows, int nova_database_Nova_ResultSet_Nova_numCols)
{
	this->nova_database_Nova_ResultSet_Nova_rows = nova_database_Nova_ResultSet_Nova_rows;
	this->nova_database_Nova_ResultSet_Nova_numCols = nova_database_Nova_ResultSet_Nova_numCols;
}

int nova_database_Nova_ResultSet_Accessor_Nova_numRows(nova_database_Nova_ResultSet* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_database_Nova_ResultSet_Nova_rows->nova_datastruct_list_Nova_Array_Nova_count;
}

void nova_database_Nova_ResultSet_Nova_super(nova_database_Nova_ResultSet* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_database_Nova_ResultSet_Nova_numCols = 0;
	this->nova_database_Nova_ResultSet_Nova_rows = (nova_datastruct_list_Nova_Array*)nova_null;
}

