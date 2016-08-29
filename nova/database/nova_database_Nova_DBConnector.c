#include <precompiled.h>
#include <nova/database/nova_database_Nova_DBConnector.h>



nova_database_Extension_VTable_DBConnector nova_database_Extension_VTable_DBConnector_val =
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
	MYSQL* nova_database_Nova_DBConnector_Nova_mysql;
	MYSQL_RES* nova_database_Nova_DBConnector_Nova_result;
	
)
void nova_database_Nova_DBConnector_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_database_Nova_DBConnector* nova_database_Nova_DBConnector_Nova_construct(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_database_Nova_DBConnector, this);
	this->vtable = &nova_database_Extension_VTable_DBConnector_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_database_Nova_DBConnector_Nova_super(this, exceptionData);
	
	{
		nova_database_Nova_DBConnector_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_database_Nova_DBConnector_Nova_destroy(nova_database_Nova_DBConnector** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	nova_Nova_String_Nova_destroy(&(*this)->nova_database_Nova_DBConnector_Nova_error, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_database_Nova_DBConnector_0_Nova_this(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_database_Nova_DBConnector_Nova_error = nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(""));
}

void nova_database_Nova_DBConnector_0_Nova_connect(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_host, nova_Nova_String* nova_database_Nova_DBConnector_Nova_user, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password)
{
	this->prv->nova_database_Nova_DBConnector_Nova_mysql = nova_db_connect1((char*)(nova_database_Nova_DBConnector_Nova_host->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_user->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_password->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
	nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_database_Nova_DBConnector_1_Nova_connect(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_host, nova_Nova_String* nova_database_Nova_DBConnector_Nova_user, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password, nova_Nova_String* nova_database_Nova_DBConnector_Nova_database)
{
	this->prv->nova_database_Nova_DBConnector_Nova_mysql = nova_db_connect2((char*)(nova_database_Nova_DBConnector_Nova_host->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_user->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_password->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_database->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
	nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_database_Nova_DBConnector_2_Nova_connect(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_host, nova_Nova_String* nova_database_Nova_DBConnector_Nova_user, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password, nova_Nova_String* nova_database_Nova_DBConnector_Nova_database, int nova_database_Nova_DBConnector_Nova_port, nova_Nova_String* nova_database_Nova_DBConnector_Nova_unixSocket, int nova_database_Nova_DBConnector_Nova_clientFlag)
{
	this->prv->nova_database_Nova_DBConnector_Nova_mysql = nova_db_connect3((char*)(nova_database_Nova_DBConnector_Nova_host->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_user->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_password->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_database->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), nova_database_Nova_DBConnector_Nova_port, (char*)(nova_database_Nova_DBConnector_Nova_unixSocket->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), nova_database_Nova_DBConnector_Nova_clientFlag);
	nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_database_Nova_DBConnector_Nova_updateError(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_database_Nova_DBConnector_Nova_error = nova_Nova_String_1_Nova_construct(0, exceptionData, nova_db_error(this->prv->nova_database_Nova_DBConnector_Nova_mysql));
}

void nova_database_Nova_DBConnector_Nova_changeUser(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_username, nova_Nova_String* nova_database_Nova_DBConnector_Nova_password, nova_Nova_String* nova_database_Nova_DBConnector_Nova_database)
{
	nova_user_select(this->prv->nova_database_Nova_DBConnector_Nova_mysql, (char*)(nova_database_Nova_DBConnector_Nova_username->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_password->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), (char*)(nova_database_Nova_DBConnector_Nova_database->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
	nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

nova_database_Nova_ResultSet* nova_database_Nova_DBConnector_Nova_query(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_database_Nova_DBConnector_Nova_query)
{
	char*** l1_Nova_arrays = (char***)nova_null;
	
	this->prv->nova_database_Nova_DBConnector_Nova_result = nova_exec_query(this->prv->nova_database_Nova_DBConnector_Nova_mysql, (char*)(nova_database_Nova_DBConnector_Nova_query->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data));
	if (this->prv->nova_database_Nova_DBConnector_Nova_result == 0)
	{
		nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
		return (nova_database_Nova_ResultSet*)nova_null;
	}
	
	l1_Nova_arrays = (char***)(nova_get_results(this->prv->nova_database_Nova_DBConnector_Nova_mysql, this->prv->nova_database_Nova_DBConnector_Nova_result));
	if (l1_Nova_arrays != 0)
	{
		int l2_Nova_numRows = 0;
		int l2_Nova_numCols = 0;
		nova_datastruct_list_Nova_Array* l2_Nova_rows = (nova_datastruct_list_Nova_Array*)nova_null;
		nova_database_Nova_ResultSet* l2_Nova_r = (nova_database_Nova_ResultSet*)nova_null;
		int l4_Nova_i = 0;
		
		l2_Nova_numRows = (int)nova_num_rows(this->prv->nova_database_Nova_DBConnector_Nova_mysql);
		l2_Nova_numCols = (int)nova_num_cols(this->prv->nova_database_Nova_DBConnector_Nova_result);
		l2_Nova_rows = nova_datastruct_list_Nova_Array_1_Nova_construct(0, exceptionData, l2_Nova_numRows);
		l4_Nova_i = (int)0;
		for (; l4_Nova_i < (int)l2_Nova_numRows; l4_Nova_i++)
		{
			int l6_Nova_j = 0;
			
			nova_datastruct_list_Nova_Array_Nova_set((nova_datastruct_list_Nova_Array*)(l2_Nova_rows), exceptionData, l4_Nova_i, (nova_Nova_Object*)(nova_datastruct_list_Nova_Array_1_Nova_construct(0, exceptionData, l2_Nova_numCols)));
			l6_Nova_j = (int)0;
			for (; l6_Nova_j < (int)l2_Nova_numCols; l6_Nova_j++)
			{
				nova_datastruct_list_Nova_Array_Nova_set((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l2_Nova_rows), exceptionData, l4_Nova_i)), exceptionData, l6_Nova_j, (nova_Nova_Object*)(nova_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_arrays[l4_Nova_i][l6_Nova_j])));
			}
		}
		l2_Nova_r = nova_database_Nova_ResultSet_Nova_construct(0, exceptionData, l2_Nova_rows, l2_Nova_numCols);
		nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
		return l2_Nova_r;
	}
	nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
	return (nova_database_Nova_ResultSet*)nova_null;
}

void nova_database_Nova_DBConnector_Nova_close(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_db_close(this->prv->nova_database_Nova_DBConnector_Nova_mysql);
	nova_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_database_Nova_DBConnector_Nova_super(nova_database_Nova_DBConnector* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_database_Nova_DBConnector_Nova_error = (nova_Nova_String*)nova_null;
	this->prv->nova_database_Nova_DBConnector_Nova_mysql = 0;
	this->prv->nova_database_Nova_DBConnector_Nova_result = 0;
}

