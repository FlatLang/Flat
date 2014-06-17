#pragma once
#ifndef FILE_Exception_NOVA
#define FILE_Exception_NOVA

typedef struct Exception Exception;

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
#include "NovaDivideByZeroException.h"

Exception* nova_Exception_Exception(ExceptionData* exceptionData);
void nova_del_Exception(Exception** this, ExceptionData* exceptionData);

#endif