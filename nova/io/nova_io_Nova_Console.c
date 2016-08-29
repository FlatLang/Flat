#include <precompiled.h>
#include <nova/io/nova_io_Nova_Console.h>



nova_io_Extension_VTable_Console nova_io_Extension_VTable_Console_val =
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
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};



void nova_io_Nova_Console_Nova_flushInput(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_io_Nova_Console_Nova_flushOutput(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_io_Nova_Console_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_io_Nova_Console* nova_io_Nova_Console_Nova_construct(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_io_Nova_Console, this,);
	this->vtable = &nova_io_Extension_VTable_Console_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_io_Nova_Console_Nova_super(this, exceptionData);
	
	{
		nova_io_Nova_Console_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_io_Nova_Console_Nova_destroy(nova_io_Nova_Console** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_io_Nova_Console_0_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("")));
}

void nova_io_Nova_Console_1_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_io_Nova_Console_Nova_text)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_io_Nova_Console_Nova_text), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\n"))));
}

void nova_io_Nova_Console_2_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_io_Nova_Console_Nova_obj)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_io_Nova_Console_Nova_obj), exceptionData));
}

void nova_io_Nova_Console_3_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_4_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, float nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_5_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_6_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_7_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, short nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_8_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_9_Nova_writeLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_io_Nova_Console_Nova_c)
{
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_c));
}

void nova_io_Nova_Console_0_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_io_Nova_Console_Nova_text)
{
	nova_datastruct_list_Nova_CharArray* l1_Nova_cText = (nova_datastruct_list_Nova_CharArray*)nova_null;
	
	l1_Nova_cText = nova_io_Nova_Console_Nova_text->nova_Nova_String_Nova_chars;
	fputs((char*)(l1_Nova_cText->nova_datastruct_list_Nova_Array_Nova_data), stdout);
	nova_io_Nova_Console_Nova_flushOutput(0, exceptionData);
}

void nova_io_Nova_Console_1_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_io_Nova_Console_Nova_obj)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(nova_io_Nova_Console_Nova_obj), exceptionData));
}

void nova_io_Nova_Console_2_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_3_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, float nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_4_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_5_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_6_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, short nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_7_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_io_Nova_Console_Nova_num)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Byte_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_num));
}

void nova_io_Nova_Console_8_Nova_write(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_io_Nova_Console_Nova_c)
{
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, nova_io_Nova_Console_Nova_c));
}

int nova_io_Nova_Console_Nova_readInt(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String* l1_Nova_s = (nova_Nova_String*)nova_null;
	
	l1_Nova_s = nova_io_Nova_Console_Nova_readLine(0, exceptionData);
	return nova_primitive_number_Nova_Int_Nova_parseInt(0, exceptionData, l1_Nova_s);
}

double nova_io_Nova_Console_Nova_readDouble(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String* l1_Nova_s = (nova_Nova_String*)nova_null;
	
	l1_Nova_s = nova_io_Nova_Console_Nova_readLine(0, exceptionData);
	return nova_primitive_number_Nova_Double_Nova_parseDouble(0, exceptionData, l1_Nova_s);
}

char nova_io_Nova_Console_Nova_readChar(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	char l1_Nova_c = 0;
	
	l1_Nova_c = (char)(getchar());
	nova_io_Nova_Console_Nova_flushInput(0, exceptionData);
	return l1_Nova_c;
}

void nova_io_Nova_Console_Nova_flushInput(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	fseek(stdin, 0, SEEK_END);
}

void nova_io_Nova_Console_Nova_flushOutput(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	fflush(stdout);
}

nova_Nova_String* nova_io_Nova_Console_Nova_readLine(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_line = (char*)nova_null;
	nova_Nova_String* l1_Nova_s = (nova_Nova_String*)nova_null;
	
	
	l1_Nova_line = (char*)(ufgets(stdin));
	l1_Nova_s = nova_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_line);
	return l1_Nova_s;
}

nova_Nova_String* nova_io_Nova_Console_Nova_readPassword(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_Nova_String* l1_Nova_pass = (nova_Nova_String*)nova_null;
	
	nova_io_Nova_Console_Nova_setEcho(0, exceptionData, 0);
	l1_Nova_pass = nova_io_Nova_Console_Nova_readLine(0, exceptionData);
	nova_io_Nova_Console_Nova_setEcho(0, exceptionData, 1);
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("\n")));
	return l1_Nova_pass;
}

void nova_io_Nova_Console_Nova_setEcho(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_io_Nova_Console_Nova_echo)
{
	nova_setEcho(nova_io_Nova_Console_Nova_echo);
}

void nova_io_Nova_Console_Nova_clearScreen(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_clearScreen();
}

void nova_io_Nova_Console_Nova_waitForEnter(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_c = (char*)nova_null;
	
	nova_io_Nova_Console_Nova_flushInput(0, exceptionData);
	
	l1_Nova_c = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * 2);
	fgets(l1_Nova_c, 2, stdin);
}

void nova_io_Nova_Console_0_Nova_this(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_io_Nova_Console_Nova_super(nova_io_Nova_Console* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

