#pragma once
#ifndef FILE_System_NOVA
#define FILE_System_NOVA

typedef struct System System;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
#include <NovaObject.h>
#include <NovaString.h>
#include "NovaSystem.h"
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
#include <NovaFile.h>
#include <NovaTime.h>
#include <NativeSystem.h>
#include <NovaProcess.h>

typedef struct nova_VTable_System
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_System;

CCLASS_CLASS
(
	System, 
	
	nova_VTable_System* vtable;
)

System* nova_0_System_construct(System* this, ExceptionData* exceptionData);
void nova_del_System(System** this, ExceptionData* exceptionData);
void nova_static_0_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code);
void nova_static_1_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code, String* nova_0_message);
void nova_static_2_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code, String* nova_0_message, char nova_0_log);
Process* nova_static_System_execute(System* this, ExceptionData* exceptionData, String* nova_0_command);
void nova_System_this(System* this, ExceptionData* exceptionData);
void nova_System_super(System* this, ExceptionData* exceptionData);

#endif