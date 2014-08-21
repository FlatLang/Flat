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
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Process;

CCLASS_CLASS
(
	Process, 
	
	nova_VTable_Process* vtable;
	StreamReader* nova_Process_reader;
)

Process* nova_Process_construct(Process* this, ExceptionData* exceptionData, StreamReader* nova_0_reader);
void nova_del_Process(Process** this, ExceptionData* exceptionData);
void nova_Process_this(Process* this, ExceptionData* exceptionData, StreamReader* nova_0_reader);
void nova_Process_super(Process* this, ExceptionData* exceptionData);

#endif