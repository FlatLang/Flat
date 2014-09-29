#include <precompiled.h>
#include <example/database/example_database_NovaDatabaseDemo.h>


nova_VTable_example_database_NovaDatabaseDemo nova_VTable_example_database_NovaDatabaseDemo_val =
{
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};

nova_standard_database_NovaDBConnector* example_database_NovaDatabaseDemo_static_Novaconnect(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData);
void example_database_NovaDatabaseDemo_static_Novaclose(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_database_NovaDBConnector* l0_Novaconnection);

example_database_NovaDatabaseDemo* example_database_NovaDatabaseDemo_Nova0_construct(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(example_database_NovaDatabaseDemo, this,);
		this->vtable = &nova_VTable_example_database_NovaDatabaseDemo_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		example_database_NovaDatabaseDemo_Novasuper(this, 0);
		
		{
				example_database_NovaDatabaseDemo_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_DatabaseDemo(example_database_NovaDatabaseDemo** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void example_database_NovaDatabaseDemo_static_Novamain(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_database_NovaDBConnector* l1_Novaconnection;
		nova_standard_database_NovaSQLResult* l1_Novaresult;
		int l2_Novarow;
		int l1_Novaid;
		nova_standard_NovaString* nova_local_0;
		
		l1_Novaconnection = example_database_NovaDatabaseDemo_static_Novaconnect((example_database_NovaDatabaseDemo*)nova_null, exceptionData);
		l1_Novaresult = nova_standard_database_NovaDBConnector_Novaquery(l1_Novaconnection, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "select * from market"));
		l2_Novarow = 0;
		for (; l2_Novarow < l1_Novaresult->nova_standard_database_NovaSQLResult_NovanumRows; l2_Novarow++)
		{
				int l3_Novacol;
				nova_standard_NovaString* nova_local_0;
				
				nova_local_0 = nova_standard_primitive_number_NovaInt_Nova2_toString(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, l2_Novarow), exceptionData);
				nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Found ("), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "): "), exceptionData, l1_Novaresult->nova_standard_database_NovaSQLResult_Novarows[l2_Novarow][0]))));
				l3_Novacol = 1;
				for (; l3_Novacol < l1_Novaresult->nova_standard_database_NovaSQLResult_NovanumCols; l3_Novacol++)
				{
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, ", "), exceptionData, l1_Novaresult->nova_standard_database_NovaSQLResult_Novarows[l2_Novarow][l3_Novacol]));
				}
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, ""));
		}
		l1_Novaid = (int)(nova_standard_primitive_number_NovaInt_NovaparseInt(0, exceptionData, l1_Novaresult->nova_standard_database_NovaSQLResult_Novarows[l1_Novaresult->nova_standard_database_NovaSQLResult_NovanumRows - 1][0]) + 1);
		nova_local_0 = nova_standard_primitive_number_NovaInt_Nova2_toString(nova_standard_primitive_number_NovaInt_Novaconstruct(0, exceptionData, l1_Novaid), exceptionData);
		nova_standard_database_NovaDBConnector_Novaquery(l1_Novaconnection, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "insert into market values("), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, ", 6, 634, 3);"))));
		example_database_NovaDatabaseDemo_static_Novaclose((example_database_NovaDatabaseDemo*)nova_null, exceptionData, l1_Novaconnection);
		nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
}

nova_standard_database_NovaDBConnector* example_database_NovaDatabaseDemo_static_Novaconnect(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		nova_standard_database_NovaDBConnector* l1_Novaconnection;
		nova_standard_NovaString* l1_Novaerror;
		
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Connecting..."));
		l1_Novaconnection = nova_standard_database_NovaDBConnector_Nova2_construct(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "localhost"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "root"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "server"), nova_standard_NovaString_Novaconstruct(0, exceptionData, "test"));
		l1_Novaerror = nova_standard_database_NovaDBConnector_NovagetError(l1_Novaconnection, exceptionData);
		if (l1_Novaerror->nova_standard_NovaString_Novalength > 0)
		{
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Error: "), exceptionData, l1_Novaerror));
		}
		else
		{
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Connected."));
		}
}

void example_database_NovaDatabaseDemo_static_Novaclose(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_database_NovaDBConnector* l0_Novaconnection)
{
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Closing..."));
		nova_standard_database_NovaDBConnector_Novaclose(l0_Novaconnection, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Closed"));
}

void example_database_NovaDatabaseDemo_Novathis(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void example_database_NovaDatabaseDemo_Novasuper(example_database_NovaDatabaseDemo* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}


nova_standard_primitive_NovaNull* nova_null;

int main(int argc, char** argvs)
{
		nova_standard_NovaString** args;
		int      i;
		
		nova_standard_exception_NovaExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_null = nova_standard_primitive_NovaNull_Nova0_construct(0, exceptionData);
		nova_standard_gc_NovaGC_static_Novainit(0, exceptionData);
		
		MathNova_init_static();
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Novaconstruct(0, 0, str);
		}
		
		TRY
		{
				example_database_NovaDatabaseDemo_static_Novamain(0, exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}