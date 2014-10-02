#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>


nova_VTable_nova_standard_io_NovaConsole nova_VTable_nova_standard_io_NovaConsole_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};

void nova_standard_io_NovaConsole_static_Novaflush(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaConsoleNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_NovaConsole* nova_standard_io_NovaConsole_Nova0_construct(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_NovaConsole, this,);
	this->vtable = &nova_VTable_nova_standard_io_NovaConsole_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_io_NovaConsole_Novasuper(this, 0);
	
	{
		nova_standard_io_NovaConsole_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_Console(nova_standard_io_NovaConsole** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_io_NovaConsole_static_Nova0_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novatext)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, l0_Novatext->vtable->nova_standard_NovaString_Novavirtual0_concat(l0_Novatext, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "\n")));
}

void nova_standard_io_NovaConsole_static_Nova1_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaobj)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, l0_Novaobj->vtable->nova_standard_NovaObject_Novavirtual0_toString(l0_Novaobj, exceptionData));
}

void nova_standard_io_NovaConsole_static_Nova2_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaDouble_static_Nova2_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova3_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaDouble_static_Nova2_toString(0, exceptionData, (double)(l0_Novanum)));
}

void nova_standard_io_NovaConsole_static_Nova4_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaLong_static_Nova2_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova5_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova6_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, (int)(l0_Novanum)));
}

void nova_standard_io_NovaConsole_static_Nova7_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaByte_static_Nova1_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova8_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	nova_standard_io_NovaConsole_static_Nova0_writeLine((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_NovaChar_Nova1_toString(0, exceptionData, l0_Novac));
}

void nova_standard_io_NovaConsole_static_Nova0_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novatext)
{
	char* l1_NovacText;
	
	l1_NovacText = nova_standard_NovaString_NovatoCharArray(l0_Novatext, exceptionData);
	fputs(l1_NovacText, stdout);
}

void nova_standard_io_NovaConsole_static_Nova1_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaobj)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, l0_Novaobj->vtable->nova_standard_NovaObject_Novavirtual0_toString(l0_Novaobj, exceptionData));
}

void nova_standard_io_NovaConsole_static_Nova2_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaDouble_static_Nova2_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova3_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaDouble_static_Nova2_toString(0, exceptionData, (double)(l0_Novanum)));
}

void nova_standard_io_NovaConsole_static_Nova4_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaLong_static_Nova2_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova5_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova6_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaInt_static_Nova1_toString(0, exceptionData, (int)(l0_Novanum)));
}

void nova_standard_io_NovaConsole_static_Nova7_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanum)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_number_NovaByte_static_Nova1_toString(0, exceptionData, l0_Novanum));
}

void nova_standard_io_NovaConsole_static_Nova8_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac)
{
	nova_standard_io_NovaConsole_static_Nova0_write((nova_standard_io_NovaConsole*)nova_null, exceptionData, nova_standard_primitive_NovaChar_Nova1_toString(0, exceptionData, l0_Novac));
}

int nova_standard_io_NovaConsole_static_NovareadInt(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* l1_Novas;
	
	l1_Novas = nova_standard_io_NovaConsole_static_NovareadLine((nova_standard_io_NovaConsole*)nova_null, exceptionData);
	return nova_standard_primitive_number_NovaInt_NovaparseInt(0, exceptionData, l1_Novas);
}

double nova_standard_io_NovaConsole_static_NovareadDouble(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	nova_standard_NovaString* l1_Novas;
	
	l1_Novas = nova_standard_io_NovaConsole_static_NovareadLine((nova_standard_io_NovaConsole*)nova_null, exceptionData);
	return nova_standard_primitive_number_NovaDouble_NovaparseDouble(0, exceptionData, l1_Novas);
}

char nova_standard_io_NovaConsole_static_NovareadChar(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	char l1_Novac;
	
	l1_Novac = getchar();
	nova_standard_io_NovaConsole_static_Novaflush((nova_standard_io_NovaConsole*)nova_null, exceptionData);
	return l1_Novac;
}

void nova_standard_io_NovaConsole_static_Novaflush(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

nova_standard_NovaString* nova_standard_io_NovaConsole_static_NovareadLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	char* l1_Novaline;
	nova_standard_NovaString* l1_Novas;
	
	l1_Novaline = ufgets(stdin);
	l1_Novas = nova_standard_NovaString_Novaconstruct(0, exceptionData, l1_Novaline);
	return l1_Novas;
}

void nova_standard_io_NovaConsole_static_NovawaitForEnter(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	char* l1_Novac;
	
	nova_standard_io_NovaConsole_static_Novaflush((nova_standard_io_NovaConsole*)nova_null, exceptionData);
	l1_Novac = (char*)NOVA_MALLOC(sizeof(char[2]));
	fgets(l1_Novac, 2, stdin);
}

void nova_standard_io_NovaConsole_Novathis(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_io_NovaConsole_Novasuper(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
