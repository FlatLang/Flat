#pragma once
#ifndef FILE_UnstableException_NOVA
#define FILE_UnstableException_NOVA

typedef struct UnstableException UnstableException;

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



UnstableException* nova_UnstableException_UnstableException(UnstableException* this, ExceptionData* exceptionData);
void nova_del_UnstableException(UnstableException** this, ExceptionData* exceptionData);

#endif