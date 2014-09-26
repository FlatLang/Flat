#pragma once
#ifndef FILE_Console_NOVA
#define FILE_Console_NOVA

typedef struct nova_standard_NovaConsole nova_standard_NovaConsole;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>
#include <nova/standard/exception/nova_standard_exception_NovaException.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>
#include <nova/standard/nova_standard_NovaNull.h>
#include <nova/standard/nova_standard_NovaObject.h>
#include <nova/standard/nova_standard_NovaString.h>
#include <nova/standard/nova_standard_NovaSystem.h>
#include <nova/standard/nova_standard_NovaMath.h>
#include <nova/standard/nova_standard_NovaConsole.h>
#include <nova/standard/nova_standard_NovaGC.h>
#include <nova/standard/nova_standard_NovaNumber.h>
#include <nova/standard/nova_standard_NovaByte.h>
#include <nova/standard/nova_standard_NovaShort.h>
#include <nova/standard/nova_standard_NovaInt.h>
#include <nova/standard/nova_standard_NovaLong.h>
#include <nova/standard/nova_standard_NovaFloat.h>
#include <nova/standard/nova_standard_NovaDouble.h>
#include <nova/standard/nova_standard_NovaChar.h>
#include <nova/standard/nova_standard_NovaBool.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct nova_VTable_nova_standard_NovaConsole
{
	nova_standard_NovaString* (*nova_standard_NovaObject_Novavirtual0_toString)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*);
	char (*nova_standard_NovaObject_Novavirtual0_equals)(nova_standard_NovaObject*, nova_standard_exception_NovaExceptionData*, nova_standard_NovaObject*);
} nova_VTable_nova_standard_NovaConsole;

CCLASS_CLASS
(
	nova_standard_NovaConsole, 
	
	nova_VTable_nova_standard_NovaConsole* vtable;
)

nova_standard_NovaConsole* nova_standard_NovaConsole_Novaconstruct(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_del_Console(nova_standard_NovaConsole** this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaConsole_static_Novanull0_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novatext);
void nova_standard_NovaConsole_static_Novanull1_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaobj);
void nova_standard_NovaConsole_static_Novanull2_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanum);
void nova_standard_NovaConsole_static_Novanull3_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanum);
void nova_standard_NovaConsole_static_Novanull4_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanum);
void nova_standard_NovaConsole_static_Novanull5_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum);
void nova_standard_NovaConsole_static_Novanull6_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanum);
void nova_standard_NovaConsole_static_Novanull7_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanum);
void nova_standard_NovaConsole_static_Novanull8_writeLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac);
void nova_standard_NovaConsole_static_Novanull0_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novatext);
void nova_standard_NovaConsole_static_Novanull1_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novaobj);
void nova_standard_NovaConsole_static_Novanull2_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, double l0_Novanum);
void nova_standard_NovaConsole_static_Novanull3_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, float l0_Novanum);
void nova_standard_NovaConsole_static_Novanull4_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, long_long l0_Novanum);
void nova_standard_NovaConsole_static_Novanull5_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novanum);
void nova_standard_NovaConsole_static_Novanull6_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, short l0_Novanum);
void nova_standard_NovaConsole_static_Novanull7_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novanum);
void nova_standard_NovaConsole_static_Novanull8_write(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData, char l0_Novac);
int nova_standard_NovaConsole_static_NovareadInt(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
double nova_standard_NovaConsole_static_NovareadDouble(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
char nova_standard_NovaConsole_static_NovareadChar(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
nova_standard_NovaString* nova_standard_NovaConsole_static_NovareadLine(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaConsole_static_NovawaitForEnter(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaConsole_Novathis(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);
void nova_standard_NovaConsole_Novasuper(nova_standard_NovaConsole* this, nova_standard_exception_NovaExceptionData* exceptionData);

#endif