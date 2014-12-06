#include <precompiled.h>
#include <nova/standard/io/nova_standard_io_Nova_Console.h>


nova_VTable_nova_standard_io_Nova_Console nova_VTable_nova_standard_io_Nova_Console_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};

void nova_standard_io_Nova_Console_Nova_flush(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_ConsoleNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_io_Nova_Console* nova_standard_io_Nova_Console_2_Nova_construct(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_io_Nova_Console, this,);
	this->vtable = &nova_VTable_nova_standard_io_Nova_Console_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_io_Nova_Console_Nova_super(this, exceptionData);
	
	{
		nova_standard_io_Nova_Console_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_io_Nova_Console_Nova_destroy(nova_standard_io_Nova_Console** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_io_Nova_Console_0_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_text)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, l0_Nova_text->vtable->nova_standard_Nova_String_virtual0_Nova_concat(l0_Nova_text, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "\n")));
}

void nova_standard_io_Nova_Console_1_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_obj)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, l0_Nova_obj->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(l0_Nova_obj, exceptionData));
}

void nova_standard_io_Nova_Console_2_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Double_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_3_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Double_1_Nova_toString(0, exceptionData, (double)(l0_Nova_num)));
}

void nova_standard_io_Nova_Console_4_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_5_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_6_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, short l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, (int)(l0_Nova_num)));
}

void nova_standard_io_Nova_Console_7_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Byte_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_8_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_c)
{
	nova_standard_io_Nova_Console_0_Nova_writeLine((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Char_1_Nova_toString(0, exceptionData, l0_Nova_c));
}

void nova_standard_io_Nova_Console_0_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_text)
{
	char* l1_Nova_cText;
	
	l1_Nova_cText = l0_Nova_text->nova_standard_Nova_String_Nova_chars;
	fputs((char*)(l1_Nova_cText), stdout);
	fflush(stdout);
}

void nova_standard_io_Nova_Console_1_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_obj)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, l0_Nova_obj->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(l0_Nova_obj, exceptionData));
}

void nova_standard_io_Nova_Console_2_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Double_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_3_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Double_1_Nova_toString(0, exceptionData, (double)(l0_Nova_num)));
}

void nova_standard_io_Nova_Console_4_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_5_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_6_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, short l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, (int)(l0_Nova_num)));
}

void nova_standard_io_Nova_Console_7_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_num)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Byte_1_Nova_toString(0, exceptionData, l0_Nova_num));
}

void nova_standard_io_Nova_Console_8_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_c)
{
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_primitive_number_Nova_Char_1_Nova_toString(0, exceptionData, l0_Nova_c));
}

int nova_standard_io_Nova_Console_Nova_readInt(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_s;
	
	l1_Nova_s = nova_standard_io_Nova_Console_Nova_readLine((nova_standard_io_Nova_Console*)nova_null, exceptionData);
	return nova_standard_primitive_number_Nova_Int_Nova_parseInt(0, exceptionData, l1_Nova_s);
}

double nova_standard_io_Nova_Console_Nova_readDouble(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_s;
	
	l1_Nova_s = nova_standard_io_Nova_Console_Nova_readLine((nova_standard_io_Nova_Console*)nova_null, exceptionData);
	return nova_standard_primitive_number_Nova_Double_Nova_parseDouble(0, exceptionData, l1_Nova_s);
}

char nova_standard_io_Nova_Console_Nova_readChar(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char l1_Nova_c;
	
	l1_Nova_c = (char)(getchar());
	nova_standard_io_Nova_Console_Nova_flush((nova_standard_io_Nova_Console*)nova_null, exceptionData);
	return l1_Nova_c;
}

void nova_standard_io_Nova_Console_Nova_flush(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	fseek(stdin, (int)(0), SEEK_END);
}

nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_readLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_line;
	nova_standard_Nova_String* l1_Nova_s;
	
	l1_Nova_line = (char*)(ufgets(stdin));
	l1_Nova_s = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_line);
	return l1_Nova_s;
}

nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_readPassword(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_Nova_String* l1_Nova_pass;
	
	nova_standard_io_Nova_Console_Nova_setEcho((nova_standard_io_Nova_Console*)nova_null, exceptionData, 0);
	l1_Nova_pass = nova_standard_io_Nova_Console_Nova_readLine((nova_standard_io_Nova_Console*)nova_null, exceptionData);
	nova_standard_io_Nova_Console_Nova_setEcho((nova_standard_io_Nova_Console*)nova_null, exceptionData, 1);
	nova_standard_io_Nova_Console_0_Nova_write((nova_standard_io_Nova_Console*)nova_null, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "\n"));
	return l1_Nova_pass;
}

void nova_standard_io_Nova_Console_Nova_setEcho(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_echo)
{
	nova_setEcho(l0_Nova_echo);
}

void nova_standard_io_Nova_Console_Nova_clearScreen(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_clearScreen();
}

void nova_standard_io_Nova_Console_Nova_waitForEnter(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_c;
	
	nova_standard_io_Nova_Console_Nova_flush((nova_standard_io_Nova_Console*)nova_null, exceptionData);
	l1_Nova_c = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char*[2]));
	fgets((char*)(l1_Nova_c), (int)(2), stdin);
}

void nova_standard_io_Nova_Console_Nova_this(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_io_Nova_Console_Nova_super(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

