#pragma once
#ifndef FILE_EmptyStackException_NOVA
#define FILE_EmptyStackException_NOVA

typedef struct EmptyStackException EmptyStackException;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
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

typedef struct nova_VTable_EmptyStackException
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_EmptyStackException;

CCLASS_CLASS
(
	EmptyStackException, 
	
	nova_VTable_EmptyStackException* vtable;
)

EmptyStackException* nova_0_EmptyStackException_construct(EmptyStackException* this, ExceptionData* exceptionData);
void nova_del_EmptyStackException(EmptyStackException** this, ExceptionData* exceptionData);
void nova_EmptyStackException_this(EmptyStackException* this, ExceptionData* exceptionData);
void nova_EmptyStackException_super(EmptyStackException* this, ExceptionData* exceptionData);

#endif