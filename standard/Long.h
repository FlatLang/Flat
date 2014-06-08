#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct Long Long;

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

CCLASS_CLASS
(
	Long, 
	
	long_long nova_Long_value;
)

Long* nova_Long_Long(ExceptionData* exceptionData, long_long nova_0_value);
void nova_del_Long(Long** this, ExceptionData* exceptionData);
int nova_Long_numDigits(ExceptionData* exceptionData, long_long nova_0_number);
String* nova_Long_toAString(ExceptionData* exceptionData, long_long nova_0_value);
String* nova_Long_toString(Long* this, ExceptionData* exceptionData);

#endif