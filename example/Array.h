#ifndef FILE_Array_NOVA
#define FILE_Array_NOVA

typedef struct Array Array;

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
	Array, 
	
	int nova_Array_length;
)


Array* nova_Array_Array(ExceptionData* exceptionData);
void nova_del_Array(Array** this, ExceptionData* exceptionData);
#endif