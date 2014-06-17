#pragma once
#ifndef FILE_IntegerTest_NOVA
#define FILE_IntegerTest_NOVA

typedef struct IntegerTest IntegerTest;

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
#include "NovaIO.h"
#include "NovaTime.h"
#include "NovaThread.h"
#include "NovaList.h"
#include "NovaListNode.h"
#include "NovaGC.h"

IntegerTest* nova_IntegerTest_IntegerTest(ExceptionData* exceptionData);
void nova_del_IntegerTest(IntegerTest** this, ExceptionData* exceptionData);
void nova_IntegerTest_main(ExceptionData* exceptionData, String** nova_0_args);

#endif