#include <precompiled.h>
#include <example/database/example_database_Nova_DatabaseDemo.h>



example_database_Extension_VTable_DatabaseDemo example_database_Extension_VTable_DatabaseDemo_val =
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
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};



nova_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connect(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData);
void example_database_Nova_DatabaseDemo_Nova_close(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connection);
void example_database_Nova_DatabaseDemo_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

example_database_Nova_DatabaseDemo* example_database_Nova_DatabaseDemo_Nova_construct(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(example_database_Nova_DatabaseDemo, this,);
	this->vtable = &example_database_Extension_VTable_DatabaseDemo_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	example_database_Nova_DatabaseDemo_Nova_super(this, exceptionData);
	
	{
		example_database_Nova_DatabaseDemo_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void example_database_Nova_DatabaseDemo_Nova_destroy(example_database_Nova_DatabaseDemo** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void example_database_Nova_DatabaseDemo_Nova_main(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_list_Nova_Array* example_database_Nova_DatabaseDemo_Nova_args)
{
	nova_database_Nova_DBConnector* l1_Nova_connection = (nova_database_Nova_DBConnector*)nova_null;
	nova_database_Nova_ResultSet* l1_Nova_result = (nova_database_Nova_ResultSet*)nova_null;
	int l1_Nova_id = 0;
	int l2_Nova_row = 0;
	
	l1_Nova_connection = example_database_Nova_DatabaseDemo_Nova_connect(0, exceptionData);
	l1_Nova_result = nova_database_Nova_DBConnector_Nova_query(l1_Nova_connection, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("select * from market")));
	l2_Nova_row = (int)0;
	for (; l2_Nova_row < (int)nova_database_Nova_ResultSet_Accessor_Nova_numRows(l1_Nova_result, exceptionData); l2_Nova_row++)
	{
		int l4_Nova_col = 0;
		
		nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Found (")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l2_Nova_row))), exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("): ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)((nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_result->nova_database_Nova_ResultSet_Nova_rows), exceptionData, l2_Nova_row)), exceptionData, 0))), exceptionData)), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")))))));
		l4_Nova_col = (int)1;
		for (; l4_Nova_col < (int)l1_Nova_result->nova_database_Nova_ResultSet_Nova_numCols; l4_Nova_col++)
		{
			nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", ")), exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_result->nova_database_Nova_ResultSet_Nova_rows), exceptionData, l2_Nova_row)), exceptionData, l4_Nova_col)), exceptionData)));
		}
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")));
	}
	l1_Nova_id = nova_primitive_number_Nova_Int_Nova_parseInt(0, exceptionData, (nova_Nova_String*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(nova_datastruct_list_Nova_Array_virtual1_Nova_get((nova_datastruct_list_Nova_Array*)(l1_Nova_result->nova_database_Nova_ResultSet_Nova_rows), exceptionData, nova_database_Nova_ResultSet_Accessor_Nova_numRows(l1_Nova_result, exceptionData) - 1)), exceptionData, 0))) + 1;
	nova_database_Nova_DBConnector_Nova_query(l1_Nova_connection, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("insert into market values(")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, (l1_Nova_id))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)(", 6, 634, 3);")))));
	example_database_Nova_DatabaseDemo_Nova_close(0, exceptionData, l1_Nova_connection);
	nova_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

nova_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connect(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_database_Nova_DBConnector* l1_Nova_connection = (nova_database_Nova_DBConnector*)nova_null;
	nova_Nova_String* l1_Nova_error = (nova_Nova_String*)nova_null;
	
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Connecting...")));
	l1_Nova_connection = nova_database_Nova_DBConnector_Nova_construct(0, exceptionData);
	nova_database_Nova_DBConnector_1_Nova_connect(l1_Nova_connection, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("localhost")), nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("root")), nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("server")), nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("test")));
	l1_Nova_error = l1_Nova_connection->nova_database_Nova_DBConnector_Nova_error;
	if (l1_Nova_error->nova_Nova_String_Nova_count > 0)
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Error: ")), exceptionData, l1_Nova_error));
	}
	else
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Connected.")));
	}
	return l1_Nova_connection;
}

void example_database_Nova_DatabaseDemo_Nova_close(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData, nova_database_Nova_DBConnector* example_database_Nova_DatabaseDemo_Nova_connection)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Closing...")));
	nova_database_Nova_DBConnector_Nova_close(example_database_Nova_DatabaseDemo_Nova_connection, exceptionData);
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Closed")));
}

void example_database_Nova_DatabaseDemo_0_Nova_this(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void example_database_Nova_DatabaseDemo_Nova_super(example_database_Nova_DatabaseDemo* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

