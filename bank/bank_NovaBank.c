#include <precompiled.h>
#include <bank/bank_NovaBank.h>

typedef nova_standard_NovaString* (*l0_Nova1_getInput)(void*, nova_standard_exception_NovaExceptionData*);
typedef void (*l0_Nova2_outputData)(void*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
typedef void (*l0_Nova3_clearScreen)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_Nova4_getInput)(void*, nova_standard_exception_NovaExceptionData*);
typedef void (*l0_Nova5_outputData)(void*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
typedef void (*l0_Nova6_clearScreen)(void*, nova_standard_exception_NovaExceptionData*);
typedef void (*l0_Nova7_write)(void*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
typedef void (*l0_Nova8_clearScreen)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_Nova9_readString)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_Nova10_readPassword)(void*, nova_standard_exception_NovaExceptionData*);
typedef void (*l0_Nova11_write)(void*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaString*);
typedef void (*l0_Nova12_clearScreen)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_Nova13_readString)(void*, nova_standard_exception_NovaExceptionData*);
typedef nova_standard_NovaString* (*l0_Nova14_readPassword)(void*, nova_standard_exception_NovaExceptionData*);

nova_VTable_bank_NovaBank nova_VTable_bank_NovaBank_val =
{
		nova_standard_NovaObject_Nova0_getHashCodeLong,
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
		nova_standard_database_NovaDBConnector* bank_NovaBank_Novaconnection;
		nova_standard_network_NovaConnectionSocket* bank_NovaBank_Novasocket;
		
)

void bank_NovaBank_Novainit(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_static_NovalistenClients(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_static_NovavoidData(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novas);
void bank_NovaBank_static_NovavoidScreen(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_Novarun(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova4_getInput l0_NovagetInput, void* l0_Novaref_getInput, l0_Nova5_outputData l0_NovaoutputData, void* l0_Novaref_outputData, l0_Nova6_clearScreen l0_NovaclearScreen, void* l0_Novaref_clearScreen);
char bank_NovaBank_Nova0_connect(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova11_write l0_Novawrite, void* l0_Novaref_write, l0_Nova12_clearScreen l0_NovaclearScreen, void* l0_Novaref_clearScreen, l0_Nova13_readString l0_NovareadString, void* l0_Novaref_readString, l0_Nova14_readPassword l0_NovareadPassword, void* l0_Novaref_readPassword);
char bank_NovaBank_Nova1_connect(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novausername, nova_standard_NovaString* l0_Novapassword);
double bank_NovaBank_NovagetBalance(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData);
void bank_NovaBank_NovainsertQuery(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadate, nova_standard_NovaString* l0_Novaitem, nova_standard_NovaString* l0_Novadesc, char l0_Novadeposit, double l0_Novaamount);
void bank_NovaBankNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
		{
		}
}

bank_NovaBank* bank_NovaBank_Nova0_construct(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(bank_NovaBank, this);
		this->vtable = &nova_VTable_bank_NovaBank_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		bank_NovaBank_Novasuper(this, exceptionData);
		
		{
				bank_NovaBank_Novathis(this, exceptionData);
		}
		
		return this;
}

bank_NovaBank* bank_NovaBank_Nova1_construct(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
		CCLASS_NEW(bank_NovaBank, this);
		this->vtable = &nova_VTable_bank_NovaBank_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		bank_NovaBank_Novasuper(this, exceptionData);
		
		{
				bank_NovaBank_Nova0_this(this, exceptionData, l0_Novasocket);
		}
		
		return this;
}

void nova_del_Bank(bank_NovaBank** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		nova_del_DBConnector(&(*this)->prv->bank_NovaBank_Novaconnection, exceptionData);
		nova_del_ConnectionSocket(&(*this)->prv->bank_NovaBank_Novasocket, exceptionData);
		NOVA_FREE((*this)->prv);
		
		{
		}
		NOVA_FREE(*this);
}

void bank_NovaBank_static_Novamain(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		bank_NovaBank_static_NovalistenClients((bank_NovaBank*)nova_null, exceptionData);
}

void bank_NovaBank_Novathis(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		bank_NovaBank_Novainit(this, exceptionData);
}

void bank_NovaBank_Nova0_this(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
		bank_NovaBank_Novainit(this, exceptionData);
		this->prv->bank_NovaBank_Novasocket = l0_Novasocket;
}

void bank_NovaBank_Novainit(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		this->prv->bank_NovaBank_Novaconnection = nova_standard_database_NovaDBConnector_Nova0_construct(0, exceptionData);
}

void bank_NovaBank_static_NovalistenClients(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		nova_standard_network_NovaServerSocket* l1_Novasocket;
		int l1_Novaport;
		
		l1_Novasocket = nova_standard_network_NovaServerSocket_Nova0_construct(0, exceptionData);
		l1_Novaport = 5675;
		if (!nova_standard_network_NovaServerSocket_Novastart(l1_Novasocket, exceptionData, l1_Novaport))
		{
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to start server"));
		}
		else
		{
				bank_NovaServerOutputThread* l3_Novathread;
				
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Started bank server on port "), exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, l1_Novaport)));
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Waiting for clients..."));
				l3_Novathread = bank_NovaServerOutputThread_Novaconstruct(0, exceptionData, l1_Novasocket);
				nova_standard_thread_NovaThread_Novastart((nova_standard_thread_NovaThread*)(l3_Novathread), exceptionData);
				while (1)
				{
						nova_standard_NovaString* l4_Novainput;
						
						l4_Novainput = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
						if (l4_Novainput->vtable->nova_standard_NovaString_Novavirtual_equals(l4_Novainput, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "q")))
						{
								nova_standard_network_NovaServerSocket_Novaclose(l1_Novasocket, exceptionData);
								nova_standard_thread_NovaThread_Novakill((nova_standard_thread_NovaThread*)(l3_Novathread), exceptionData);
								break;
						}
				}
		}
}

void bank_NovaBank_static_NovavoidData(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novas)
{
}

void bank_NovaBank_static_NovavoidScreen(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void bank_NovaBank_NovarunRemote(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_network_NovaConnectionSocket* l0_Novasocket)
{
		char l1_Novaconnected;
		
		l1_Novaconnected = 0;
		while (!l1_Novaconnected)
		{
				l1_Novaconnected = bank_NovaBank_Nova0_connect(this, exceptionData, (l0_Nova11_write)l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write, l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novaout, (l0_Nova12_clearScreen)&bank_NovaBank_static_NovavoidScreen, this, (l0_Nova13_readString)l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString, l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novain, (l0_Nova14_readPassword)l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString, l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novain);
				if (l1_Novaconnected)
				{
						bank_NovaBank_Novarun(this, exceptionData, (l0_Nova4_getInput)l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novain->vtable->nova_standard_io_NovaInputStream_Novavirtual0_readString, l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novain, (l0_Nova5_outputData)l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novaout->vtable->nova_standard_io_NovaOutputStream_Novavirtual1_write, l0_Novasocket->nova_standard_network_NovaConnectionSocket_Novaout, (l0_Nova6_clearScreen)&bank_NovaBank_static_NovavoidScreen, this);
				}
		}
}

void bank_NovaBank_Novarun(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova4_getInput l0_NovagetInput, void* l0_Novaref_getInput, l0_Nova5_outputData l0_NovaoutputData, void* l0_Novaref_outputData, l0_Nova6_clearScreen l0_NovaclearScreen, void* l0_Novaref_clearScreen)
{
		TRY
		{
				nova_standard_exception_NovaExceptionData_NovaaddCode(exceptionData, exceptionData, 4);
				
				{
						char l2_Novaanswer;
						
						l2_Novaanswer = 'y';
						while (l2_Novaanswer == 'y' || l2_Novaanswer == 'Y')
						{
								nova_standard_time_NovaDate* l3_Novad;
								nova_standard_NovaString* l3_Novadate;
								nova_standard_NovaString* l3_Novainput;
								int l3_Novachoice;
								
								l2_Novaanswer = '4';
								l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "What would you like to do?\n"));
								l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "  (1) View your current balance.\n"));
								l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "  (2) View past transactions.\n"));
								l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "  (3) Record a withdrawal.\n"));
								l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "  (4) Record a deposit.\n"));
								l3_Novad = nova_standard_time_NovaDate_Nova0_construct(0, exceptionData);
								l3_Novadate = nova_standard_time_NovaDate_Nova1_formatDate(l3_Novad, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "%d/%02d/%02d %02d:%02d:%02d"), l3_Novad->nova_standard_time_NovaDate_Novayear, l3_Novad->nova_standard_time_NovaDate_Novamonth, l3_Novad->nova_standard_time_NovaDate_Novaday, l3_Novad->nova_standard_time_NovaDate_Novahour, l3_Novad->nova_standard_time_NovaDate_Novaminute, l3_Novad->nova_standard_time_NovaDate_Novasecond);
								l3_Novainput = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
								if (l3_Novainput == (nova_standard_NovaString*)nova_null)
								{
										break;
								}
								l3_Novachoice = nova_standard_primitive_number_NovaInt_static_NovaparseInt(0, exceptionData, l3_Novainput);
								if (l3_Novachoice == 1)
								{
										double l5_Novabalance;
										nova_standard_NovaString* nova_local_0;
										
										l5_Novabalance = bank_NovaBank_NovagetBalance(this, exceptionData);
										nova_local_0 = nova_standard_primitive_number_NovaDouble_static_Nova1_toString(0, exceptionData, l5_Novabalance);
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "Your current balance is: $"), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "\n"))));
								}
								else if (l3_Novachoice == 2)
								{
										int l6_Novanum;
										
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "How many past transactions would you like to see?\n"));
										l3_Novainput = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										if (l3_Novainput == (nova_standard_NovaString*)nova_null)
										{
												break;
										}
										l6_Novanum = nova_standard_primitive_number_NovaInt_static_NovaparseInt(0, exceptionData, l3_Novainput);
								}
								else if (l3_Novachoice == 3)
								{
										nova_standard_NovaString* l9_Novaitem;
										nova_standard_NovaString* l9_Novadesc;
										double l9_Novaamount;
										
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter item name: "));
										l9_Novaitem = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter transaction description: "));
										l9_Novadesc = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter the withdrawal amount: "));
										l3_Novainput = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										if (l3_Novainput == (nova_standard_NovaString*)nova_null)
										{
												break;
										}
										l9_Novaamount = nova_standard_primitive_number_NovaDouble_static_NovaparseDouble(0, exceptionData, l3_Novainput);
										bank_NovaBank_NovainsertQuery(this, exceptionData, l3_Novadate, l9_Novaitem, l9_Novadesc, 0, l9_Novaamount);
								}
								else if (l3_Novachoice == 4)
								{
										nova_standard_NovaString* l12_Novafrom;
										nova_standard_NovaString* l12_Novadesc;
										double l12_Novaamount;
										
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter what the deposit was from: "));
										l12_Novafrom = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter deposit description: "));
										l12_Novadesc = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter the deposit amount: "));
										l3_Novainput = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										if (l3_Novainput == (nova_standard_NovaString*)nova_null)
										{
												break;
										}
										l12_Novaamount = nova_standard_primitive_number_NovaDouble_static_NovaparseDouble(0, exceptionData, l3_Novainput);
										bank_NovaBank_NovainsertQuery(this, exceptionData, l3_Novadate, l12_Novafrom, l12_Novadesc, 1, l12_Novaamount);
								}
								else
								{
										l2_Novaanswer = 'y';
								}
								while (l2_Novaanswer != 'y' && l2_Novaanswer != 'Y' && l2_Novaanswer != 'n' && l2_Novaanswer != 'N')
								{
										nova_standard_NovaString* l16_Novastr;
										
										l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Would you like to do more? (Y/N): "));
										l16_Novastr = l0_NovagetInput(l0_Novaref_getInput, exceptionData);
										if (l16_Novastr == (nova_standard_NovaString*)nova_null)
										{
												break;
										}
										if (l16_Novastr->nova_standard_NovaString_Novalength <= 0)
										{
												continue;
										}
										l2_Novaanswer = nova_standard_NovaString_NovacharAt(l16_Novastr, exceptionData, 0);
								}
								l0_NovaclearScreen(l0_Novaref_clearScreen, exceptionData);
						}
				}
		}
		CATCH (4)
		{
				bank_NovaBankQueryException* l19_Novae;
				
				l19_Novae = (bank_NovaBankQueryException*)(exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException);
				l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Failed to query bank\n"));
		}
		FINALLY
		{
		}
		END_TRY;
		l0_NovaoutputData(l0_Novaref_outputData, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Bye!\n"));
		nova_standard_database_NovaDBConnector_Novaclose(this->prv->bank_NovaBank_Novaconnection, exceptionData);
}

char bank_NovaBank_Nova0_connect(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, l0_Nova11_write l0_Novawrite, void* l0_Novaref_write, l0_Nova12_clearScreen l0_NovaclearScreen, void* l0_Novaref_clearScreen, l0_Nova13_readString l0_NovareadString, void* l0_Novaref_readString, l0_Nova14_readPassword l0_NovareadPassword, void* l0_Novaref_readPassword)
{
		nova_standard_NovaString* l1_Novausername;
		nova_standard_NovaString* l1_Novapassword;
		char l1_Novaconnected;
		
		l0_Novawrite(l0_Novaref_write, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter username: "));
		l1_Novausername = l0_NovareadString(l0_Novaref_readString, exceptionData);
		l0_Novawrite(l0_Novaref_write, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Enter password: "));
		l1_Novapassword = l0_NovareadString(l0_Novaref_readString, exceptionData);
		l0_NovaclearScreen(l0_Novaref_clearScreen, exceptionData);
		l0_Novawrite(l0_Novaref_write, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Connecting...\n"));
		l1_Novaconnected = bank_NovaBank_Nova1_connect(this, exceptionData, l1_Novausername, l1_Novapassword);
		l0_NovaclearScreen(l0_Novaref_clearScreen, exceptionData);
		if (l1_Novaconnected)
		{
				l0_Novawrite(l0_Novaref_write, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "Welcome, Braden!\n"));
				return 1;
		}
		else
		{
				l0_Novawrite(l0_Novaref_write, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "YOU IMPOSTER!\n"));
		}
		return 0;
}

char bank_NovaBank_Nova1_connect(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novausername, nova_standard_NovaString* l0_Novapassword)
{
		nova_standard_database_NovaDBConnector_Nova1_connect(this->prv->bank_NovaBank_Novaconnection, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "localhost"), l0_Novausername, l0_Novapassword, nova_standard_NovaString_Nova1_construct(0, exceptionData, "bank"));
		l0_Novapassword = (nova_standard_NovaString*)((nova_standard_NovaObject*)nova_null);
		if (this->prv->bank_NovaBank_Novaconnection->nova_standard_database_NovaDBConnector_Novaerror->nova_standard_NovaString_Novalength > 0)
		{
				return 0;
		}
		return 1;
}

double bank_NovaBank_NovagetBalance(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		nova_standard_database_NovaResultSet* l1_Novaresult;
		double l1_Novabalance;
		
		l1_Novaresult = nova_standard_database_NovaDBConnector_Novaquery(this->prv->bank_NovaBank_Novaconnection, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, "SELECT balance FROM register ORDER BY date DESC LIMIT 1;"));
		if (this->prv->bank_NovaBank_Novaconnection->nova_standard_database_NovaDBConnector_Novaerror->nova_standard_NovaString_Novalength > 0)
		{
				THROW(4, bank_NovaBankQueryException_Nova1_construct(0, exceptionData, this->prv->bank_NovaBank_Novaconnection->nova_standard_database_NovaDBConnector_Novaerror));
		}
		l1_Novabalance = (double)(0);
		if (l1_Novaresult->nova_standard_database_NovaResultSet_NovanumRows > 0)
		{
				l1_Novabalance = nova_standard_primitive_number_NovaDouble_static_NovaparseDouble(0, exceptionData, l1_Novaresult->nova_standard_database_NovaResultSet_Novarows[0][0]);
		}
		return l1_Novabalance;
}

void bank_NovaBank_NovainsertQuery(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novadate, nova_standard_NovaString* l0_Novaitem, nova_standard_NovaString* l0_Novadesc, char l0_Novadeposit, double l0_Novaamount)
{
		nova_standard_NovaString* l1_Novadeposited;
		double l1_Novabalance;
		nova_standard_NovaString* nova_local_0;
		nova_standard_NovaString* nova_local_1;
		
		l1_Novabalance = bank_NovaBank_NovagetBalance(this, exceptionData);
		if (l0_Novadeposit)
		{
				l1_Novabalance = l1_Novabalance + l0_Novaamount;
				l1_Novadeposited = nova_standard_NovaString_Nova1_construct(0, exceptionData, "true");
		}
		else
		{
				l1_Novabalance = l1_Novabalance - l0_Novaamount;
				l1_Novadeposited = nova_standard_NovaString_Nova1_construct(0, exceptionData, "false");
		}
		nova_local_0 = nova_standard_primitive_number_NovaDouble_static_Nova1_toString(0, exceptionData, l0_Novaamount);
		nova_local_1 = nova_standard_primitive_number_NovaDouble_static_Nova1_toString(0, exceptionData, l1_Novabalance);
		nova_standard_database_NovaDBConnector_Novaquery(this->prv->bank_NovaBank_Novaconnection, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "INSERT INTO register VALUES('"), exceptionData, l0_Novadate->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novadate, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "', '"), exceptionData, l0_Novaitem->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novaitem, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "', '"), exceptionData, l0_Novadesc->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novadesc, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "', '"), exceptionData, l1_Novadeposited->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novadeposited, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, "', "), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Nova1_construct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Nova1_construct(0, exceptionData, ");"))))))))))))));
		if (this->prv->bank_NovaBank_Novaconnection->nova_standard_database_NovaDBConnector_Novaerror->nova_standard_NovaString_Novalength > 0)
		{
				THROW(4, bank_NovaBankQueryException_Nova1_construct(0, exceptionData, this->prv->bank_NovaBank_Novaconnection->nova_standard_database_NovaDBConnector_Novaerror));
		}
}

void bank_NovaBank_Novasuper(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		this->prv->bank_NovaBank_Novaconnection = (nova_standard_database_NovaDBConnector*)nova_null;
		this->prv->bank_NovaBank_Novasocket = (nova_standard_network_NovaConnectionSocket*)nova_null;
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
		
		nova_standard_NovaStringNova_init_static(exceptionData);
		nova_standard_NovaMathNova_init_static(exceptionData);
		nova_standard_NovaObjectNova_init_static(exceptionData);
		nova_standard_NovaSystemNova_init_static(exceptionData);
		nova_standard_database_NovaDBConnectorNova_init_static(exceptionData);
		nova_standard_database_NovaResultSetNova_init_static(exceptionData);
		nova_standard_network_NovaSocketNova_init_static(exceptionData);
		nova_standard_network_NovaServerSocketNova_init_static(exceptionData);
		nova_standard_network_NovaClientSocketNova_init_static(exceptionData);
		nova_standard_network_NovaConnectionSocketNova_init_static(exceptionData);
		nova_standard_network_NovaNetworkInputStreamNova_init_static(exceptionData);
		nova_standard_network_NovaNetworkOutputStreamNova_init_static(exceptionData);
		nova_standard_logic_NovaConclusionNova_init_static(exceptionData);
		nova_standard_logic_NovaHypothesisNova_init_static(exceptionData);
		nova_standard_logic_NovaLogicalConnectiveNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementComponentNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementLetterNova_init_static(exceptionData);
		nova_standard_logic_NovaWFFNova_init_static(exceptionData);
		nova_standard_logic_NovaStatementGroupNova_init_static(exceptionData);
		nova_standard_logic_NovaInvalidFormulaExceptionNova_init_static(exceptionData);
		nova_standard_process_NovaProcessNova_init_static(exceptionData);
		nova_standard_primitive_NovaBoolNova_init_static(exceptionData);
		nova_standard_primitive_NovaNullNova_init_static(exceptionData);
		nova_standard_primitive_NovaPrimitiveNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaCharNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaByteNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaShortNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaIntNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaLongNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaFloatNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaDoubleNova_init_static(exceptionData);
		nova_standard_primitive_number_NovaNumberNova_init_static(exceptionData);
		nova_standard_time_NovaTimeNova_init_static(exceptionData);
		nova_standard_time_NovaDateNova_init_static(exceptionData);
		nova_standard_thread_NovaThreadNova_init_static(exceptionData);
		nova_standard_thread_NovaUncaughtExceptionHandlerNova_init_static(exceptionData);
		nova_standard_io_NovaInputStreamNova_init_static(exceptionData);
		nova_standard_io_NovaOutputStreamNova_init_static(exceptionData);
		nova_standard_io_NovaStreamReaderNova_init_static(exceptionData);
		nova_standard_io_NovaFileNova_init_static(exceptionData);
		nova_standard_io_NovaConsoleNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGComponentNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGComponentListNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGComponentNodeNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGMainComponentNova_init_static(exceptionData);
		nova_standard_svg_NovaSVGCircleNova_init_static(exceptionData);
		nova_standard_exception_NovaExceptionDataNova_init_static(exceptionData);
		nova_standard_exception_NovaDivideByZeroExceptionNova_init_static(exceptionData);
		nova_standard_exception_NovaExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_NovaArrayListNova_init_static(exceptionData);
		nova_standard_datastruct_NovaQueueNova_init_static(exceptionData);
		nova_standard_datastruct_NovaListNova_init_static(exceptionData);
		nova_standard_datastruct_NovaListNodeNova_init_static(exceptionData);
		nova_standard_datastruct_NovaArrayNova_init_static(exceptionData);
		nova_standard_datastruct_NovaStackNova_init_static(exceptionData);
		nova_standard_datastruct_NovaEmptyStackExceptionNova_init_static(exceptionData);
		nova_standard_datastruct_NovaHashMapNova_init_static(exceptionData);
		nova_standard_datastruct_NovaBoundsNova_init_static(exceptionData);
		nova_standard_security_NovaMD5Nova_init_static(exceptionData);
		nova_standard_gc_NovaGCNova_init_static(exceptionData);
		bank_NovaBankQueryExceptionNova_init_static(exceptionData);
		bank_NovaBankNova_init_static(exceptionData);
		bank_NovaConnectionThreadNova_init_static(exceptionData);
		bank_NovaServerOutputThreadNova_init_static(exceptionData);
		
		args = (nova_standard_NovaString**)NOVA_MALLOC(argc * sizeof(nova_standard_NovaString));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_standard_NovaString_Nova1_construct(0, 0, str);
		}
		
		TRY
		{
				bank_NovaBank_static_Novamain(0, exceptionData, args);
		}
		CATCH (1)
		{
				nova_standard_exception_NovaException* base = (nova_standard_exception_NovaException*)exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException;
				printf("Exception in Thread 'main': %s", nova_standard_NovaString_NovatoCharArray(base->nova_standard_exception_NovaException_Novamessage, 0));
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		nova_standard_gc_NovaGC_static_Novacollect(0, exceptionData);
		
		
		return 0;
}