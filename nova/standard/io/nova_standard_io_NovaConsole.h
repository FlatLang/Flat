#pragma once
#ifndef FILE_Console_NOVA
#define FILE_Console_NOVA

typedef struct nova_standard_io_NovaConsole nova_standard_io_NovaConsole;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/io/nova_standard_io_NovaConsole.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaNumber.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaByte.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaShort.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaInt.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaLong.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaFloat.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaDouble.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_NovaChar.h>
#include <nova/standard/primitive/nova_standard_primitive_NovaBool.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <stdio.h>
#include <stdlib.h>
#include <nova/standard/io/NativeConsole.h>

typedef struct nova_VTable_nova_standard_io_NovaConsole
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_io_NovaConsole;

CCLASS_CLASS
(
	nova_standard_io_NovaConsole, 
	
	nova_VTable_nova_standard_io_NovaConsole* vtable;
)

void nova_standard_io_NovaConsoleNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_io_NovaConsole* nova_standard_io_NovaConsole_Nova0_construct(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Console(nova_standard_io_NovaConsole** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaConsole_static_Nova0_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novatext);
void nova_standard_io_NovaConsole_static_Nova1_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaobj);
void nova_standard_io_NovaConsole_static_Nova2_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova3_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova4_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, long l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova5_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova6_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova7_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova8_writeLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac);
void nova_standard_io_NovaConsole_static_Nova0_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novatext);
void nova_standard_io_NovaConsole_static_Nova1_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaobj);
void nova_standard_io_NovaConsole_static_Nova2_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova3_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova4_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, long l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova5_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova6_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova7_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanum);
void nova_standard_io_NovaConsole_static_Nova8_write(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac);
int nova_standard_io_NovaConsole_static_NovareadInt(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
double nova_standard_io_NovaConsole_static_NovareadDouble(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_io_NovaConsole_static_NovareadChar(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_io_NovaConsole_static_NovareadLine(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_io_NovaConsole_static_NovareadPassword(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaConsole_static_NovasetEcho(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novaecho);
void nova_standard_io_NovaConsole_static_NovaclearScreen(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaConsole_static_NovawaitForEnter(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaConsole_Novathis(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_io_NovaConsole_Novasuper(nova_standard_io_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif