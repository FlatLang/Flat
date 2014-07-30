#pragma once
#ifndef FILE_InputStream_NOVA
#define FILE_InputStream_NOVA

typedef struct InputStream InputStream;

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
#include <NovaShort.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaFile.h>

typedef struct nova_VTable_InputStream
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_InputStream;

CCLASS_CLASS
(
	InputStream, 
	
	nova_VTable_InputStream* vtable;
	struct Private* prv;
)

InputStream* nova_InputStream_InputStream(InputStream* this, ExceptionData* exceptionData);
void nova_del_InputStream(InputStream** this, ExceptionData* exceptionData);

#endif