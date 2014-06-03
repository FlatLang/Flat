#ifndef FILE_Test_NOVA
#define FILE_Test_NOVA

typedef struct Test Test;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include "IO.h"
#include "Time.h"
#include "Thread.h"
#include "Person.h"
#include <stdio.h>
#include "List.h"
#include "ListNode.h"
#include "BodyBuilder.h"
#include "GC.h"

CCLASS_CLASS
(
	Test
)


Test* nova_Test_Test(ExceptionData* exceptionData);
void nova_del_Test(Test** this, ExceptionData* exceptionData);
void nova_Test_main(ExceptionData* exceptionData, String** nova_0_args);
#endif