#include <precompiled.h>
#include <bank/bank_NovaBank.h>


nova_VTable_bank_NovaBank nova_VTable_bank_NovaBank_val =
{
		nova_standard_NovaObject_Nova0_toString,
		nova_standard_NovaObject_Nova0_equals,
};

double bank_NovaBank_static_NovagetBalance(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_database_NovaDBConnector* l0_Novaconnection);
void bank_NovaBank_static_NovainsertQuery(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_database_NovaDBConnector* l0_Novaconnection, nova_standard_NovaString* l0_Novadate, nova_standard_NovaString* l0_Novaitem, nova_standard_NovaString* l0_Novadesc, char l0_Novadeposit, double l0_Novaamount);

bank_NovaBank* bank_NovaBank_Nova0_construct(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		CCLASS_NEW(bank_NovaBank, this,);
		this->vtable = &nova_VTable_bank_NovaBank_val;
		nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
		nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
		bank_NovaBank_Novasuper(this, 0);
		
		{
				bank_NovaBank_Novathis(this, exceptionData);
		}
		
		return this;
}

void nova_del_Bank(bank_NovaBank** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void bank_NovaBank_static_Novamain(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString** l0_Novaargs)
{
		nova_standard_NovaString* l1_Novausername;
		nova_standard_NovaString* l1_Novapassword;
		nova_standard_database_NovaDBConnector* l1_Novaconnection;
		char l1_Novaanswer;
		
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter username: "));
		l1_Novausername = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
		nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter password: "));
		l1_Novapassword = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
		l1_Novaconnection = nova_standard_database_NovaDBConnector_Nova0_construct(0, exceptionData);
		nova_standard_database_NovaDBConnector_Nova1_connect(l1_Novaconnection, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "localhost"), l1_Novausername, l1_Novapassword, nova_standard_NovaString_Novaconstruct(0, exceptionData, "bank"));
		if (nova_standard_database_NovaDBConnector_NovagetError(l1_Novaconnection, exceptionData)->nova_standard_NovaString_Novalength > 0)
		{
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "YOU IMPOSTER!"));
				nova_standard_io_NovaConsole_static_NovawaitForEnter(0, exceptionData);
				nova_standard_NovaSystem_static_Nova0_exit(0, exceptionData, 1);
		}
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Welcome, Braden!"));
		l1_Novaanswer = 'y';
		while (l1_Novaanswer == 'y' || l1_Novaanswer == 'Y')
		{
				nova_standard_time_NovaDate* l3_Novad;
				nova_standard_NovaString* l3_Novadate;
				int l3_Novachoice;
				
				l1_Novaanswer = '4';
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "What would you like to do?"));
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "  (1) View your current balance."));
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "  (2) View past transactions."));
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "  (3) Record a withdrawal."));
				nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "  (4) Record a deposit."));
				l3_Novad = nova_standard_time_NovaDate_Nova0_construct(0, exceptionData);
				l3_Novadate = nova_standard_time_NovaDate_Nova1_formatDate(l3_Novad, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "%d/%d/%d %d:%02d:%02d"), l3_Novad->nova_standard_time_NovaDate_Novayear, l3_Novad->nova_standard_time_NovaDate_Novamonth, l3_Novad->nova_standard_time_NovaDate_Novaday, l3_Novad->nova_standard_time_NovaDate_Novahour, l3_Novad->nova_standard_time_NovaDate_Novaminute, l3_Novad->nova_standard_time_NovaDate_Novasecond);
				l3_Novachoice = nova_standard_io_NovaConsole_static_NovareadInt(0, exceptionData);
				if (l3_Novachoice == 1)
				{
						double l4_Novabalance;
						
						l4_Novabalance = bank_NovaBank_static_NovagetBalance((bank_NovaBank*)nova_null, exceptionData, l1_Novaconnection);
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "Your current balance is: $"), exceptionData, nova_standard_primitive_number_NovaDouble_Nova3_toString(nova_standard_primitive_number_NovaDouble_Novaconstruct(0, exceptionData, l4_Novabalance), exceptionData)));
				}
				else if (l3_Novachoice == 2)
				{
						int l5_Novanum;
						
						nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "How many past transactions would you like to see?"));
						l5_Novanum = nova_standard_io_NovaConsole_static_NovareadInt(0, exceptionData);
				}
				else if (l3_Novachoice == 3)
				{
						nova_standard_NovaString* l7_Novaitem;
						nova_standard_NovaString* l7_Novadesc;
						double l7_Novaamount;
						
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter item name: "));
						l7_Novaitem = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter transaction description: "));
						l7_Novadesc = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter the withdrawal amount: "));
						l7_Novaamount = nova_standard_io_NovaConsole_static_NovareadDouble(0, exceptionData);
						bank_NovaBank_static_NovainsertQuery((bank_NovaBank*)nova_null, exceptionData, l1_Novaconnection, l3_Novadate, l7_Novaitem, l7_Novadesc, 0, l7_Novaamount);
				}
				else if (l3_Novachoice == 4)
				{
						nova_standard_NovaString* l9_Novafrom;
						nova_standard_NovaString* l9_Novadesc;
						double l9_Novaamount;
						
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter what the deposit was from: "));
						l9_Novafrom = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter deposit description: "));
						l9_Novadesc = nova_standard_io_NovaConsole_static_NovareadLine(0, exceptionData);
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Enter the deposit amount: "));
						l9_Novaamount = nova_standard_io_NovaConsole_static_NovareadDouble(0, exceptionData);
						bank_NovaBank_static_NovainsertQuery((bank_NovaBank*)nova_null, exceptionData, l1_Novaconnection, l3_Novadate, l9_Novafrom, l9_Novadesc, 1, l9_Novaamount);
				}
				else
				{
						l1_Novaanswer = 'y';
				}
				while (l1_Novaanswer != 'y' && l1_Novaanswer != 'Y' && l1_Novaanswer != 'n' && l1_Novaanswer != 'N')
				{
						nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Would you like to do more? (Y/N): "));
						l1_Novaanswer = nova_standard_io_NovaConsole_static_NovareadChar(0, exceptionData);
				}
		}
		nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Bye!"));
}

double bank_NovaBank_static_NovagetBalance(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_database_NovaDBConnector* l0_Novaconnection)
{
		nova_standard_database_NovaResultSet* l1_Novaresult;
		double l1_Novabalance;
		
		l1_Novaresult = nova_standard_database_NovaDBConnector_Novaquery(l0_Novaconnection, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "SELECT balance FROM register ORDER BY date DESC LIMIT 1;"));
		l1_Novabalance = (double)(0);
		if (l1_Novaresult->nova_standard_database_NovaResultSet_NovanumRows > 0)
		{
				l1_Novabalance = nova_standard_primitive_number_NovaDouble_NovaparseDouble(0, exceptionData, l1_Novaresult->nova_standard_database_NovaResultSet_Novarows[0][0]);
		}
		return l1_Novabalance;
}

void bank_NovaBank_static_NovainsertQuery(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_database_NovaDBConnector* l0_Novaconnection, nova_standard_NovaString* l0_Novadate, nova_standard_NovaString* l0_Novaitem, nova_standard_NovaString* l0_Novadesc, char l0_Novadeposit, double l0_Novaamount)
{
		nova_standard_NovaString* l1_Novadeposited;
		double l1_Novabalance;
		nova_standard_NovaString* nova_local_0;
		nova_standard_NovaString* nova_local_1;
		
		l1_Novabalance = bank_NovaBank_static_NovagetBalance((bank_NovaBank*)nova_null, exceptionData, l0_Novaconnection);
		if (l0_Novadeposit)
		{
				l1_Novabalance = l1_Novabalance + l0_Novaamount;
				l1_Novadeposited = nova_standard_NovaString_Novaconstruct(0, exceptionData, "true");
		}
		else
		{
				l1_Novabalance = l1_Novabalance - l0_Novaamount;
				l1_Novadeposited = nova_standard_NovaString_Novaconstruct(0, exceptionData, "false");
		}
		nova_local_0 = nova_standard_primitive_number_NovaDouble_Nova3_toString(nova_standard_primitive_number_NovaDouble_Novaconstruct(0, exceptionData, l0_Novaamount), exceptionData);
		nova_local_1 = nova_standard_primitive_number_NovaDouble_Nova3_toString(nova_standard_primitive_number_NovaDouble_Novaconstruct(0, exceptionData, l1_Novabalance), exceptionData);
		nova_standard_database_NovaDBConnector_Novaquery(l0_Novaconnection, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "INSERT INTO register VALUES('"), exceptionData, l0_Novadate->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novadate, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "', '"), exceptionData, l0_Novaitem->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novaitem, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "', '"), exceptionData, l0_Novadesc->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novadesc, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "', '"), exceptionData, l1_Novadeposited->vtable->nova_standard_NovaString_Novavirtual0_concat(l1_Novadeposited, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, "', "), exceptionData, nova_local_0->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_0, exceptionData, nova_standard_NovaString_Nova0_concat(nova_standard_NovaString_Novaconstruct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_NovaString_Novavirtual0_concat(nova_local_1, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, ");"))))))))))))));
}

void bank_NovaBank_Novathis(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void bank_NovaBank_Novasuper(bank_NovaBank* this, nova_standard_exception_NovaExceptionData* exceptionData)
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
				bank_NovaBank_static_Novamain(0, exceptionData, args);
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