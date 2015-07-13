#pragma once
#ifndef FILE_nova_standard_io_Nova_Console_NOVA
#define FILE_nova_standard_io_Nova_Console_NOVA

typedef struct nova_standard_io_Nova_Console nova_standard_io_Nova_Console;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/standard/exception/nova_standard_exception_Nova_ExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_Nova_Exception.h>
#include <nova/standard/exception/nova_standard_exception_Nova_DivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_Nova_Console.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Number.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Byte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Short.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Int.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Long.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Float.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Double.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Null.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Char.h>
#include <nova/standard/primitive/nova_standard_primitive_Nova_Bool.h>
#include <nova/standard/datastruct/nova_standard_datastruct_Nova_Array.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>
#include <nova/standard/nova_standard_Nova_Object.h>
#include <nova/standard/nova_standard_Nova_String.h>
#include <nova/standard/nova_standard_Nova_System.h>
#include <nova/standard/math/nova_standard_math_Nova_Math.h>
#include <stdio.h>
#include <stdlib.h>
#include <nova/standard/io/NativeConsole.h>


typedef struct nova_standard_io_Extension_VTable_Console nova_standard_io_Extension_VTable_Console;
struct nova_standard_io_Extension_VTable_Console
{
	nova_Interface_VTable itable;
	long (*nova_standard_Nova_Object_virtual0_Nova_getHashCodeLong)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	nova_standard_Nova_String* (*nova_standard_Nova_Object_virtual0_Nova_toString)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*);
	char (*nova_standard_Nova_Object_virtual0_Nova_equals)(nova_standard_Nova_Object*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*);
};

extern nova_standard_io_Extension_VTable_Console nova_standard_io_Extension_VTable_Console_val;


CCLASS_CLASS
(
	nova_standard_io_Nova_Console, 
	
	nova_standard_io_Extension_VTable_Console* vtable;
)

void nova_standard_io_Nova_ConsoleNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_io_Nova_Console* nova_standard_io_Nova_Console_2_Nova_construct(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_Nova_destroy(nova_standard_io_Nova_Console** this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_0_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_text);
void nova_standard_io_Nova_Console_1_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_obj);
void nova_standard_io_Nova_Console_2_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_num);
void nova_standard_io_Nova_Console_3_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float l0_Nova_num);
void nova_standard_io_Nova_Console_4_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long l0_Nova_num);
void nova_standard_io_Nova_Console_5_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_num);
void nova_standard_io_Nova_Console_6_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, short l0_Nova_num);
void nova_standard_io_Nova_Console_7_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_num);
void nova_standard_io_Nova_Console_8_Nova_writeLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_c);
void nova_standard_io_Nova_Console_0_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_text);
void nova_standard_io_Nova_Console_1_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_obj);
void nova_standard_io_Nova_Console_2_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_num);
void nova_standard_io_Nova_Console_3_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, float l0_Nova_num);
void nova_standard_io_Nova_Console_4_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, long l0_Nova_num);
void nova_standard_io_Nova_Console_5_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_num);
void nova_standard_io_Nova_Console_6_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, short l0_Nova_num);
void nova_standard_io_Nova_Console_7_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_num);
void nova_standard_io_Nova_Console_8_Nova_write(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_c);
int nova_standard_io_Nova_Console_Nova_readInt(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
double nova_standard_io_Nova_Console_Nova_readDouble(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
char nova_standard_io_Nova_Console_Nova_readChar(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_readLine(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_String* nova_standard_io_Nova_Console_Nova_readPassword(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_Nova_setEcho(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_echo);
void nova_standard_io_Nova_Console_Nova_clearScreen(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_Nova_waitForEnter(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_2_Nova_this(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void nova_standard_io_Nova_Console_Nova_super(nova_standard_io_Nova_Console* this, nova_standard_exception_Nova_ExceptionData* exceptionData);

#endif
