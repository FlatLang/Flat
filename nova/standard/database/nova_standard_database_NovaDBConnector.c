#include <precompiled.h>
#include <nova/standard/database/nova_standard_database_NovaDBConnector.h>


nova_VTable_nova_standard_database_NovaDBConnector nova_VTable_nova_standard_database_NovaDBConnector_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	MYSQL* nova_standard_database_NovaDBConnector_Novamysql;
	MYSQL_RES* nova_standard_database_NovaDBConnector_Novaresult;
	
)
void nova_standard_database_NovaDBConnectorNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_database_NovaDBConnector* nova_standard_database_NovaDBConnector_Nova0_construct(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_database_NovaDBConnector, this);
	this->vtable = &nova_VTable_nova_standard_database_NovaDBConnector_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_database_NovaDBConnector_Novasuper(this, exceptionData);
	
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
	
	
	
	NOVA_FREE((*this)->prv);
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
	this->prv->nova_standard_database_NovaDBConnector_Novamysql = nova_db_connect1((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novahost, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novauser, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_Nova1_connect(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novahost, nova_standard_NovaString* l0_Novauser, nova_standard_NovaString* l0_Novapassword, nova_standard_NovaString* l0_Novadatabase)
{
	this->prv->nova_standard_database_NovaDBConnector_Novamysql = nova_db_connect2((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novahost, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novauser, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadatabase, exceptionData)));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_Nova2_connect(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novahost, nova_standard_NovaString* l0_Novauser, nova_standard_NovaString* l0_Novapassword, nova_standard_NovaString* l0_Novadatabase, int l0_Novaport, nova_standard_NovaString* l0_NovaunixSocket, int l0_NovaclientFlag)
{
	this->prv->nova_standard_database_NovaDBConnector_Novamysql = nova_db_connect3((char*)(nova_standard_NovaString_NovatoCharArray(l0_Novahost, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novauser, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadatabase, exceptionData)), (int)(l0_Novaport), (char*)(nova_standard_NovaString_NovatoCharArray(l0_NovaunixSocket, exceptionData)), (int)(l0_NovaclientFlag));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_NovaupdateError(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaDBConnector_Novaerror = nova_standard_NovaString_Nova1_construct(0, exceptionData, (char*)(nova_db_error(this->prv->nova_standard_database_NovaDBConnector_Novamysql)));
}

void nova_standard_database_NovaDBConnector_NovachangeUser(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novausername, nova_standard_NovaString* l0_Novapassword, nova_standard_NovaString* l0_Novadatabase)
{
	nova_user_select(this->prv->nova_standard_database_NovaDBConnector_Novamysql, (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novausername, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novapassword, exceptionData)), (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novadatabase, exceptionData)));
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

nova_standard_database_NovaResultSet* nova_standard_database_NovaDBConnector_Novaquery(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaquery)
{
	char*** l1_Novaarrays;
	
	this->prv->nova_standard_database_NovaDBConnector_Novaresult = nova_exec_query(this->prv->nova_standard_database_NovaDBConnector_Novamysql, (char*)(nova_standard_NovaString_NovatoCharArray(l0_Novaquery, exceptionData)));
	if (this->prv->nova_standard_database_NovaDBConnector_Novaresult == 0)
	{
		nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
		return (nova_standard_database_NovaResultSet*)nova_null;
	}
	l1_Novaarrays = (char***)(nova_get_results(this->prv->nova_standard_database_NovaDBConnector_Novamysql, this->prv->nova_standard_database_NovaDBConnector_Novaresult));
	if (l1_Novaarrays != 0)
	{
		int l3_NovanumRows;
		int l3_NovanumCols;
		nova_standard_NovaString*** l3_Novarows;
		nova_standard_database_NovaResultSet* l3_Novar;
		int l4_Novai;
		
		l3_NovanumRows = (int)nova_num_rows(this->prv->nova_standard_database_NovaDBConnector_Novamysql);
		l3_NovanumCols = (int)nova_num_cols(this->prv->nova_standard_database_NovaDBConnector_Novaresult);
		l3_Novarows = (nova_standard_NovaString***)nova_gen_array(NOVA_MALLOC(sizeof(nova_standard_NovaString*[l3_NovanumRows][l3_NovanumCols])), (int[]) { l3_NovanumRows, l3_NovanumCols }, 0, 1, sizeof(nova_standard_NovaString));
		l4_Novai = 0;
		for (; l4_Novai < l3_NovanumRows; l4_Novai++)
		{
			int l5_Novaj;
			
			l5_Novaj = 0;
			for (; l5_Novaj < l3_NovanumCols; l5_Novaj++)
			{
				l3_Novarows[l4_Novai][l5_Novaj] = nova_standard_NovaString_Nova1_construct(0, exceptionData, l1_Novaarrays[l4_Novai][l5_Novaj]);
			}
		}
		l3_Novar = nova_standard_database_NovaResultSet_Novaconstruct(0, exceptionData, l3_Novarows, l3_NovanumRows, l3_NovanumCols);
		nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
		return l3_Novar;
	}
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
	return (nova_standard_database_NovaResultSet*)nova_null;
}

void nova_standard_database_NovaDBConnector_Novaclose(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_db_close(this->prv->nova_standard_database_NovaDBConnector_Novamysql);
	nova_standard_database_NovaDBConnector_NovaupdateError(this, exceptionData);
}

void nova_standard_database_NovaDBConnector_Novasuper(nova_standard_database_NovaDBConnector* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_database_NovaDBConnector_Novaerror = (nova_standard_NovaString*)nova_null;
	this->prv->nova_standard_database_NovaDBConnector_Novamysql = 0;
	this->prv->nova_standard_database_NovaDBConnector_Novaresult = 0;
}
