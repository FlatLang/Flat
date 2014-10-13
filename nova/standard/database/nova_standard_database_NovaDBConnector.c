#include <precompiled.h>
#include <nova/standard/database/nova_standard_database_NovaDBConnector.h>


nova_VTable_nova_standard_database_NovaDBConnector nova_VTable_nova_standard_database_NovaDBConnector_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_database_NovaDBConnectorNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_database_NovaDBConnector* nova_standard_database_NovaDBConnector_Nova0_construct(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_database_NovaDBConnector, this,);
	this->vtable = &nova_VTable_nova_standard_database_NovaDBConnector_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_database_NovaDBConnector_Novasuper(this, 0);
	
	{
		nova_standard_database_NovaDBConnector_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_DBConnector(nova_standard_database_NovaDBConnector** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_String(&(*this)->nova_standard_database_NovaDBConnector_Novaerror, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_database_NovaDBConnector_Novathis(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaDBConnector_Novaerror = nova_standard_NovaString_Nova1_construct(0, exceptionData, "");
}

void nova_standard_database_NovaDBConnector_Nova0_connect(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novahost, nova_standard_NovaString* l0_Novauser, nova_standard_NovaString* l0_Novapassword)
{
	nova_db_connect1((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novahost, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novauser, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_Nova1_connect(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novahost, nova_standard_NovaString* l0_Novauser, nova_standard_NovaString* l0_Novapassword, nova_standard_NovaString* l0_Novadatabase)
{
	nova_db_connect2((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novahost, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novauser, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadatabase, exceptionData)));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_Nova2_connect(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novahost, nova_standard_NovaString* l0_Novauser, nova_standard_NovaString* l0_Novapassword, nova_standard_NovaString* l0_Novadatabase, int l0_Novaport, nova_standard_NovaString* l0_NovaunixSocket, int l0_NovaclientFlag)
{
	nova_db_connect3((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novahost, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novauser, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadatabase, exceptionData)), (int)(l0_Novaport), (char*)(nova_standard_NovaString_NovatoCharArray(l0_NovaunixSocket, exceptionData)), (int)(l0_NovaclientFlag));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_NovaupdateError(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaDBConnector_Novaerror = nova_standard_NovaString_Nova1_construct(0, exceptionData, (char*)(nova_db_error()));
}

void nova_standard_database_NovaDBConnector_NovachangeUser(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novausername, nova_standard_NovaString* l0_Novapassword, nova_standard_NovaString* l0_Novadatabase)
{
	nova_user_select((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novausername, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadatabase, exceptionData)));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

nova_standard_database_NovaResultSet* nova_standard_database_NovaDBConnector_Novaquery(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaquery)
{
	char*** l1_Novaarrays;
	
	nova_exec_query((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novaquery, exceptionData)));
	l1_Novaarrays = (char***)(nova_get_results());
	if (l1_Novaarrays != 0)
	{
		int l2_NovanumRows;
		int l2_NovanumCols;
		nova_standard_NovaString*** l2_Novarows;
		nova_standard_database_NovaResultSet* l2_Novaresult;
		int l3_Novai;
		
		l2_NovanumRows = (int)nova_num_rows();
		l2_NovanumCols = (int)nova_num_cols();
		l2_Novarows = (nova_standard_NovaString***)nova_gen_array(NOVA_MALLOC(sizeof(nova_standard_NovaString*[l2_NovanumRows][l2_NovanumCols])), (int[]) { l2_NovanumRows, l2_NovanumCols }, 0, 1, sizeof(nova_standard_NovaString));
		l3_Novai = 0;
		for (; l3_Novai < l2_NovanumRows; l3_Novai++)
		{
			int l4_Novaj;
			
			l4_Novaj = 0;
			for (; l4_Novaj < l2_NovanumCols; l4_Novaj++)
			{
				l2_Novarows[l3_Novai][l4_Novaj] = nova_standard_NovaString_Nova1_construct(0, exceptionData, l1_Novaarrays[l3_Novai][l4_Novaj]);
			}
		}
		l2_Novaresult = nova_standard_database_NovaResultSet_Novaconstruct(0, exceptionData, l2_Novarows, l2_NovanumRows, l2_NovanumCols);
		nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
		return l2_Novaresult;
	}
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
	return (nova_standard_database_NovaResultSet*)nova_null;
}

void nova_standard_database_NovaDBConnector_Novaclose(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_db_close();
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_Novasuper(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaDBConnector_Novaerror = (nova_standard_NovaString*)nova_null;
}
