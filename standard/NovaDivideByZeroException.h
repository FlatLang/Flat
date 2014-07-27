#pragma once
#ifndef FILE_DivideByZeroException_NOVA
#define FILE_DivideByZeroException_NOVA

typedef struct DivideByZeroException DivideByZeroException;

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
#include "NovaDivideByZeroException.h"



DivideByZeroException* nova_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData);
void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData);

#endif