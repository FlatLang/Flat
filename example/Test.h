#ifndef FILE_Test_NOVA
#define FILE_Test_NOVA

typedef struct Test Test;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include "IO.h"
#include "Time.h"
#include "Thread.h"

CCLASS_CLASS
(
	Test
)


Test* nova_Test_Test(ExceptionData* exceptionData);
void nova_del_Test(Test** this, ExceptionData* exceptionData);
void nova_Test_main(ExceptionData* exceptionData, String** nova_args_6);
#endif