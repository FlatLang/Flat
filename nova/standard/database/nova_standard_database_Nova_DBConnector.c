#include <precompiled.h>
#include <nova/standard/database/nova_standard_database_Nova_DBConnector.h>

nova_standard_database_Extension_VTable_DBConnector nova_standard_database_Extension_VTable_DBConnector_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	MYSQL* nova_standard_database_Nova_DBConnector_Nova_mysql;
	MYSQL_RES* nova_standard_database_Nova_DBConnector_Nova_result;
	
)
void nova_standard_database_Nova_DBConnectorNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_database_Nova_DBConnector* nova_standard_database_Nova_DBConnector_0_Nova_construct(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_database_Nova_DBConnector, this);
	this->vtable = &nova_standard_database_Extension_VTable_DBConnector_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_database_Nova_DBConnector_Nova_super(this, exceptionData);
	
	{
		nova_standard_database_Nova_DBConnector_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_database_Nova_DBConnector_Nova_destroy(nova_standard_database_Nova_DBConnector** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	nova_standard_Nova_String_Nova_destroy(&(*this)->nova_standard_database_Nova_DBConnector_Nova_error, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_database_Nova_DBConnector_0_Nova_this(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_database_Nova_DBConnector_Nova_error = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "");
}

void nova_standard_database_Nova_DBConnector_0_Nova_connect(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_host, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_user, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_password)
{
	this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql = nova_db_connect1((char*)(nova_standard_database_Nova_DBConnector_Nova_host->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_user->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_password->nova_standard_Nova_String_Nova_chars));
	nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_standard_database_Nova_DBConnector_1_Nova_connect(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_host, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_user, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_password, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_database)
{
	this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql = nova_db_connect2((char*)(nova_standard_database_Nova_DBConnector_Nova_host->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_user->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_password->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_database->nova_standard_Nova_String_Nova_chars));
	nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_standard_database_Nova_DBConnector_2_Nova_connect(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_host, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_user, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_password, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_database, int nova_standard_database_Nova_DBConnector_Nova_port, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_unixSocket, int nova_standard_database_Nova_DBConnector_Nova_clientFlag)
{
	this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql = nova_db_connect3((char*)(nova_standard_database_Nova_DBConnector_Nova_host->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_user->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_password->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_database->nova_standard_Nova_String_Nova_chars), (int)(nova_standard_database_Nova_DBConnector_Nova_port), (char*)(nova_standard_database_Nova_DBConnector_Nova_unixSocket->nova_standard_Nova_String_Nova_chars), (int)(nova_standard_database_Nova_DBConnector_Nova_clientFlag));
	nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_standard_database_Nova_DBConnector_Nova_updateError(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_database_Nova_DBConnector_Nova_error = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, (char*)(nova_db_error(this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql)));
}

void nova_standard_database_Nova_DBConnector_Nova_changeUser(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_username, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_password, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_database)
{
	nova_user_select(this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql, (char*)(nova_standard_database_Nova_DBConnector_Nova_username->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_password->nova_standard_Nova_String_Nova_chars), (char*)(nova_standard_database_Nova_DBConnector_Nova_database->nova_standard_Nova_String_Nova_chars));
	nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

nova_standard_database_Nova_ResultSet* nova_standard_database_Nova_DBConnector_Nova_query(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_database_Nova_DBConnector_Nova_query)
{
	char*** l1_Nova_arrays;
	
	this->prv->nova_standard_database_Nova_DBConnector_Nova_result = nova_exec_query(this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql, (char*)(nova_standard_database_Nova_DBConnector_Nova_query->nova_standard_Nova_String_Nova_chars));
	if (this->prv->nova_standard_database_Nova_DBConnector_Nova_result == 0)
	{
		nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
		return (nova_standard_database_Nova_ResultSet*)nova_null;
	}
	l1_Nova_arrays = (char***)(nova_get_results(this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql, this->prv->nova_standard_database_Nova_DBConnector_Nova_result));
	if (l1_Nova_arrays != 0)
	{
		int l2_Nova_numRows;
		int l2_Nova_numCols;
		nova_standard_Nova_String*** l2_Nova_rows;
		nova_standard_database_Nova_ResultSet* l2_Nova_r;
		int l4_Nova_i;
		
		l2_Nova_numRows = (int)nova_num_rows(this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql);
		l2_Nova_numCols = (int)nova_num_cols(this->prv->nova_standard_database_Nova_DBConnector_Nova_result);
		l2_Nova_rows = (nova_standard_Nova_String***)nova_gen_array(NOVA_MALLOC(sizeof(nova_standard_Nova_String) * l2_Nova_numRows * l2_Nova_numCols), (int[]) { l2_Nova_numRows, l2_Nova_numCols }, 0, 1, sizeof(nova_standard_Nova_String));
		l4_Nova_i = 0;
		for (; l4_Nova_i < l2_Nova_numRows; l4_Nova_i++)
		{
			int l6_Nova_j;
			
			l6_Nova_j = 0;
			for (; l6_Nova_j < l2_Nova_numCols; l6_Nova_j++)
			{
				l2_Nova_rows[l4_Nova_i][l6_Nova_j] = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, l1_Nova_arrays[l4_Nova_i][l6_Nova_j]);
			}
		}
		l2_Nova_r = nova_standard_database_Nova_ResultSet_Nova_construct(0, exceptionData, l2_Nova_rows, l2_Nova_numRows, l2_Nova_numCols);
		nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
		return l2_Nova_r;
	}
	nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
	return (nova_standard_database_Nova_ResultSet*)nova_null;
}

void nova_standard_database_Nova_DBConnector_Nova_close(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_db_close(this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql);
	nova_standard_database_Nova_DBConnector_Nova_updateError(this, exceptionData);
}

void nova_standard_database_Nova_DBConnector_Nova_super(nova_standard_database_Nova_DBConnector* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_database_Nova_DBConnector_Nova_error = (nova_standard_Nova_String*)nova_null;
	this->prv->nova_standard_database_Nova_DBConnector_Nova_mysql = 0;
	this->prv->nova_standard_database_Nova_DBConnector_Nova_result = 0;
}

