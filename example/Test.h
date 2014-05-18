#ifndef FILE_Test_FATHOM
#define FILE_Test_FATHOM

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


Test* fathom_Test_Test(ExceptionData* exceptionData);
void fathom_del_Test(Test** this, ExceptionData* exceptionData);
void fathom_Test_main(ExceptionData* exceptionData, String** fathom_args_51);
#endif