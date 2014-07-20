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
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaFile.h>

FileTest* nova_FileTest_FileTest(ExceptionData* exceptionData);
void nova_del_FileTest(FileTest** this, ExceptionData* exceptionData);
void nova_static_FileTest_main(FileTest* this, ExceptionData* exceptionData, String** nova_0_args);

#endif