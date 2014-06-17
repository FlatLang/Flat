#pragma once
#ifndef FILE_Bool_NOVA
#define FILE_Bool_NOVA

typedef struct Bool Bool;

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

Bool* nova_Bool_Bool(ExceptionData* exceptionData);
void nova_del_Bool(Bool** this, ExceptionData* exceptionData);

#endif