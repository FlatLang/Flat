#include <precompiled.h>
#include <example/database/example_database_Nova_DatabaseDemo.h>

example_database_Extension_VTable_DatabaseDemo example_database_Extension_VTable_DatabaseDemo_val =
{
	{
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
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



nova_standard_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connect(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void example_database_Nova_DatabaseDemo_Nova_close(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connection);
void example_database_Nova_DatabaseDemoNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_database_Nova_DatabaseDemo* example_database_Nova_DatabaseDemo_Nova_DatabaseDemo(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_database_Nova_DatabaseDemo, this,);
	this->vtable = &example_database_Extension_VTable_DatabaseDemo_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	example_database_Nova_DatabaseDemo_Nova_super(this, exceptionData);
	
	{
		example_database_Nova_DatabaseDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_database_Nova_DatabaseDemo_Nova_destroy(example_database_Nova_DatabaseDemo** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_database_Nova_DatabaseDemo_Nova_main(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** example_database_Nova_DatabaseDemo_Nova_args)
{
	nova_standard_database_Nova_DBConnector* l1_Nova_connection;
	nova_standard_database_Nova_ResultSet* l1_Nova_result;
	int l1_Nova_id;
	int l2_Nova_row;
	
	l1_Nova_connection = example_database_Nova_DatabaseDemo_Nova_connect(0, exceptionData);
	l1_Nova_result = nova_standard_database_Nova_DBConnector_Nova_query(l1_Nova_connection, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "select * from market"));
	l2_Nova_row = (int)(0);
	for (; l2_Nova_row < l1_Nova_result->nova_standard_database_Nova_ResultSet_Nova_numRows; l2_Nova_row++)
	{
		int l4_Nova_col;
		
		nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Found ("), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_3_Nova_toString(0, exceptionData, l2_Nova_row)), exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "): "), exceptionData, l1_Nova_result->nova_standard_database_Nova_ResultSet_Nova_rows[l2_Nova_row][0]))));
		l4_Nova_col = (int)(1);
		for (; l4_Nova_col < l1_Nova_result->nova_standard_database_Nova_ResultSet_Nova_numCols; l4_Nova_col++)
		{
			nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", "), exceptionData, l1_Nova_result->nova_standard_database_Nova_ResultSet_Nova_rows[l2_Nova_row][l4_Nova_col]));
		}
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""));
	}
	l1_Nova_id = nova_standard_primitive_number_Nova_Int_Nova_parseInt(0, exceptionData, l1_Nova_result->nova_standard_database_Nova_ResultSet_Nova_rows[l1_Nova_result->nova_standard_database_Nova_ResultSet_Nova_numRows - 1][0]) + 1;
	nova_standard_database_Nova_DBConnector_Nova_query(l1_Nova_connection, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "insert into market values("), exceptionData, nova_standard_Nova_String_virtual0_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Int_3_Nova_toString(0, exceptionData, l1_Nova_id)), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ", 6, 634, 3);"))));
	example_database_Nova_DatabaseDemo_Nova_close(0, exceptionData, l1_Nova_connection);
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

nova_standard_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connect(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_database_Nova_DBConnector* l1_Nova_connection;
	nova_standard_Nova_String* l1_Nova_error;
	
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Connecting..."));
	l1_Nova_connection = nova_standard_database_Nova_DBConnector_Nova_DBConnector(0, exceptionData);
	nova_standard_database_Nova_DBConnector_1_Nova_connect(l1_Nova_connection, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "localhost"), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "root"), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "server"), nova_standard_Nova_String_1_Nova_String(0, exceptionData, "test"));
	l1_Nova_error = l1_Nova_connection->nova_standard_database_Nova_DBConnector_Nova_error;
	if (l1_Nova_error->nova_standard_Nova_String_Nova_size > 0)
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Error: "), exceptionData, l1_Nova_error));
	}
	else
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Connected."));
	}
	return l1_Nova_connection;
}

void example_database_Nova_DatabaseDemo_Nova_close(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connection)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Closing..."));
	nova_standard_database_Nova_DBConnector_Nova_close(example_database_Nova_DatabaseDemo_Nova_connection, exceptionData);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Closed"));
}

void example_database_Nova_DatabaseDemo_0_Nova_this(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void example_database_Nova_DatabaseDemo_Nova_super(example_database_Nova_DatabaseDemo* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

