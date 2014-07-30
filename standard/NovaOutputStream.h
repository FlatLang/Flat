#pragma once
#ifndef FILE_OutputStream_NOVA
#define FILE_OutputStream_NOVA

typedef struct OutputStream OutputStream;

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

typedef struct nova_VTable_OutputStream
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_OutputStream;

CCLASS_CLASS
(
	OutputStream, 
	
	nova_VTable_OutputStream* vtable;
	struct Private* prv;
)

OutputStream* nova_OutputStream_OutputStream(OutputStream* this, ExceptionData* exceptionData);
void nova_del_OutputStream(OutputStream** this, ExceptionData* exceptionData);

#endif