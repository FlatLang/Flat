#pragma once
#ifndef FILE_System_NOVA
#define FILE_System_NOVA

typedef struct System System;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include "NovaSystem.h"
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaStreamReader.h>
#include <NovaFile.h>
#include <NovaTime.h>
#include <NativeSystem.h>

typedef struct nova_VTable_System
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_System;

CCLASS_CLASS
(
	System, 
	
	nova_VTable_System* vtable;
)

System* nova_System_System(System* this, ExceptionData* exceptionData);
void nova_del_System(System** this, ExceptionData* exceptionData);
void nova_static_1_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code);
void nova_static_2_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code, String* nova_0_message);
void nova_static_3_System_exit(System* this, ExceptionData* exceptionData, int nova_0_code, String* nova_0_message, char nova_0_log);
int nova_static_System_execute(System* this, ExceptionData* exceptionData, String* nova_0_command);

#endif