#pragma once
#ifndef FILE_DivideByZeroException_NOVA
#define FILE_DivideByZeroException_NOVA

typedef struct DivideByZeroException DivideByZeroException;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"

DivideByZeroException* nova_DivideByZeroException_DivideByZeroException(ExceptionData* exceptionData);
void nova_del_DivideByZeroException(DivideByZeroException** this, ExceptionData* exceptionData);

#endif