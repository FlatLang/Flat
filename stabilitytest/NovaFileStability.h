#pragma once
#ifndef FILE_FileStability_NOVA
#define FILE_FileStability_NOVA

typedef struct FileStability FileStability;

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
#include <NovaTime.h>
#include <NovaStabilityTest.h>

typedef struct nova_VTable_FileStability
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_FileStability;

CCLASS_CLASS
(
	FileStability, 
	
	nova_VTable_FileStability* vtable;
)

FileStability* nova_FileStability_FileStability(FileStability* this, ExceptionData* exceptionData);
void nova_del_FileStability(FileStability** this, ExceptionData* exceptionData);
void nova_static_FileStability_test(FileStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif