#pragma once
#ifndef FILE_Process_NOVA
#define FILE_Process_NOVA

typedef struct Process Process;

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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaStreamReader.h>

typedef struct nova_VTable_Process
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Process;

CCLASS_CLASS
(
	Process, 
	
	StreamReader* nova_Process_reader;
	nova_VTable_Process* vtable;
)

Process* nova_Process_construct(Process* this, ExceptionData* exceptionData, StreamReader* nova_0_reader);
void nova_del_Process(Process** this, ExceptionData* exceptionData);

#endif