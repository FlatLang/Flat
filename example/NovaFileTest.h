#pragma once
#ifndef FILE_FileTest_NOVA
#define FILE_FileTest_NOVA

typedef struct FileTest FileTest;

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
#include <NovaFile.h>

typedef struct nova_VTable_FileTest
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_FileTest;

CCLASS_CLASS
(
	FileTest, 
	
	nova_VTable_FileTest* vtable;
)

FileTest* nova_FileTest_construct(FileTest* this, ExceptionData* exceptionData);
void nova_del_FileTest(FileTest** this, ExceptionData* exceptionData);
void nova_static_FileTest_main(FileTest* this, ExceptionData* exceptionData, String** nova_0_args);
void nova_FileTest_this(FileTest* this, ExceptionData* exceptionData);
void nova_FileTest_super(FileTest* this, ExceptionData* exceptionData);

#endif