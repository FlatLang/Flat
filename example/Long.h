#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct Long Long;

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
	Long
)


Long* nova_Long_Long(ExceptionData* exceptionData);
void nova_del_Long(Long** this, ExceptionData* exceptionData);
#endif