#ifndef FILE_Number_NOVA
#define FILE_Number_NOVA

typedef struct Number Number;

#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"

CCLASS_CLASS
(
	Number
)


Number* nova_Number_Number(ExceptionData* exceptionData);
void nova_del_Number(Number** this, ExceptionData* exceptionData);
#endif