#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_Console.h>

nova_standard_io_Extension_VTable_Console nova_standard_io_Extension_VTable_Console_val =
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
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



void nova_standard_io_Nova_Console_Nova_flushInput(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_Nova_flushOutput(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_ConsoleNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_Nova_Console* nova_standard_io_Nova_Console_Nova_Console(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_Nova_Console, this,);
	this->vtable = &nova_standard_io_Extension_VTable_Console_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_io_Nova_Console_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_Console_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_io_Nova_Console_Nova_destroy(nova_standard_io_Nova_Console** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_io_Nova_Console_0_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, ""));
}

void nova_standard_io_Nova_Console_1_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_text)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_io_Nova_Console_Nova_text), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "\n")));
}

void nova_standard_io_Nova_Console_2_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_io_Nova_Console_Nova_obj)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(nova_standard_io_Nova_Console_Nova_obj), exceptionData));
}

void nova_standard_io_Nova_Console_3_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_4_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_5_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_6_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_7_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, short nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_8_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_9_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_io_Nova_Console_Nova_c)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_c));
}

void nova_standard_io_Nova_Console_0_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_text)
{
	nova_standard_datastruct_list_Nova_CharArray* l1_Nova_cText = (nova_standard_datastruct_list_Nova_CharArray*)nova_null;
	
	l1_Nova_cText = nova_standard_io_Nova_Console_Nova_text->nova_standard_Nova_String_Nova_chars;
	fputs((char*)(l1_Nova_cText->nova_standard_datastruct_list_Nova_Array_Nova_data), stdout);
	nova_standard_io_Nova_Console_Nova_flushOutput(0, exceptionData);
}

void nova_standard_io_Nova_Console_1_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* nova_standard_io_Nova_Console_Nova_obj)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_Object_virtual1_Nova_toString((nova_standard_Nova_Object*)(nova_standard_io_Nova_Console_Nova_obj), exceptionData));
}

void nova_standard_io_Nova_Console_2_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_3_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_4_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long_long nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_5_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_6_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, short nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_7_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_io_Nova_Console_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_num));
}

void nova_standard_io_Nova_Console_8_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_io_Nova_Console_Nova_c)
{
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, nova_standard_io_Nova_Console_Nova_c));
}

int nova_standard_io_Nova_Console_Nova_readInt(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_s = (nova_standard_Nova_String*)nova_null;
	
	l1_Nova_s = nova_standard_io_Nova_Console_Nova_readLine(0, exceptionData);
	return nova_standard_primitive_number_Nova_Int_Nova_parseInt(0, exceptionData, l1_Nova_s);
}

double nova_standard_io_Nova_Console_Nova_readDouble(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_s = (nova_standard_Nova_String*)nova_null;
	
	l1_Nova_s = nova_standard_io_Nova_Console_Nova_readLine(0, exceptionData);
	return nova_standard_primitive_number_Nova_Double_Nova_parseDouble(0, exceptionData, l1_Nova_s);
}

char nova_standard_io_Nova_Console_Nova_readChar(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char l1_Nova_c = 0;
	
	l1_Nova_c = (char)(getchar());
	nova_standard_io_Nova_Console_Nova_flushInput(0, exceptionData);
	return l1_Nova_c;
}

void nova_standard_io_Nova_Console_Nova_flushInput(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

void nova_standard_io_Nova_Console_Nova_flushOutput(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	fflush(stdout);
}

nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_readLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_line = (char*)nova_null;
	nova_standard_Nova_String* l1_Nova_s = (nova_standard_Nova_String*)nova_null;
	
	
	l1_Nova_line = (char*)(ufgets(stdin));
	l1_Nova_s = nova_standard_Nova_String_1_Nova_String(0, exceptionData, l1_Nova_line);
	return l1_Nova_s;
}

nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_readPassword(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_pass = (nova_standard_Nova_String*)nova_null;
	
	nova_standard_io_Nova_Console_Nova_setEcho(0, exceptionData, 0);
	l1_Nova_pass = nova_standard_io_Nova_Console_Nova_readLine(0, exceptionData);
	nova_standard_io_Nova_Console_Nova_setEcho(0, exceptionData, 1);
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "\n"));
	return l1_Nova_pass;
}

void nova_standard_io_Nova_Console_Nova_setEcho(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_io_Nova_Console_Nova_echo)
{
	nova_setEcho(nova_standard_io_Nova_Console_Nova_echo);
}

void nova_standard_io_Nova_Console_Nova_clearScreen(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_clearScreen();
}

void nova_standard_io_Nova_Console_Nova_waitForEnter(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_c = (char*)nova_null;
	
	nova_standard_io_Nova_Console_Nova_flushInput(0, exceptionData);
	
	l1_Nova_c = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 2);
	fgets(l1_Nova_c, 2, stdin);
}

void nova_standard_io_Nova_Console_0_Nova_this(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_io_Nova_Console_Nova_super(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

