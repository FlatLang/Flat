#pragma once
#ifndef FILE_InputStream_NOVA
#define FILE_InputStream_NOVA

typedef struct InputStream InputStream;

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
#include <NovaFile.h>

typedef struct nova_VTable_InputStream
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_InputStream;

CCLASS_CLASS
(
	InputStream, 
	
	nova_VTable_InputStream* vtable;
	struct Private* prv;
)

InputStream* nova_2_InputStream_construct(InputStream* this, ExceptionData* exceptionData);
void nova_del_InputStream(InputStream** this, ExceptionData* exceptionData);
void nova_InputStream_this(InputStream* this, ExceptionData* exceptionData);
void nova_InputStream_super(InputStream* this, ExceptionData* exceptionData);

#endif