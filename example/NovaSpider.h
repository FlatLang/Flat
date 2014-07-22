#pragma once
#ifndef FILE_Spider_NOVA
#define FILE_Spider_NOVA

typedef struct Spider Spider;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Spider
{
	int (*nova_virtual_getNumLegs)(Spider*, ExceptionData*);
	int (*nova_virtual_getNumEyes)(Spider*, ExceptionData*);
	String* (*nova_virtual_getDescription)(Spider*, ExceptionData*);
} nova_VTable_Spider;

CCLASS_CLASS
(
	Spider, 
	
	nova_VTable_Spider* vtable;
)

Spider* nova_Spider_Spider(ExceptionData* exceptionData);
void nova_del_Spider(Spider** this, ExceptionData* exceptionData);
int nova_Spider_getNumLegs(Spider* this, ExceptionData* exceptionData);
int nova_Spider_getNumEyes(Spider* this, ExceptionData* exceptionData);
String* nova_Spider_getDescription(Spider* this, ExceptionData* exceptionData);

#endif