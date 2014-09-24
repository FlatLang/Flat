#pragma once
#ifndef FILE_Null_NOVA
#define FILE_Null_NOVA

typedef struct Null Null;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include "NovaNull.h"
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

typedef struct nova_VTable_Null
{
	String* (*nova_virtual_0_toString)(Null*, ExceptionData*);
	char (*nova_virtual_0_equals)(String*, ExceptionData*, String*);
	String* (*nova_virtual_0_concat)(Null*, ExceptionData*, String*);
} nova_VTable_Null;

CCLASS_CLASS
(
	Null, 
	
	nova_VTable_Null* vtable;
	int nova_String_length;
	struct Private* prv;
)

Null* nova_0_Null_construct(Null* this, ExceptionData* exceptionData);
void nova_del_Null(Null** this, ExceptionData* exceptionData);
String* nova_0_Null_toString(Null* this, ExceptionData* exceptionData);
String* nova_0_Null_concat(Null* this, ExceptionData* exceptionData, String* nova_0_other);
void nova_Null_this(Null* this, ExceptionData* exceptionData);
void nova_Null_super(Null* this, ExceptionData* exceptionData);

#endif